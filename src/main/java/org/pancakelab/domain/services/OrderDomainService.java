package org.pancakelab.domain.services;

import lombok.extern.slf4j.Slf4j;
import org.pancakelab.domain.PancakeOrderEntity;
import org.pancakelab.domain.event.DomainEvent;
import org.pancakelab.domain.event.DomainEventPublisher;
import org.pancakelab.domain.event.EventType;
import org.pancakelab.domain.model.pancakes.BasicPancake;
import org.pancakelab.domain.model.pancakes.Pancake;
import org.pancakelab.domain.model.pancakes.PancakeDecorator;
import org.pancakelab.domain.repository.OrderRepository;
import org.pancakelab.domain.repository.PancakeRepository;
import org.pancakelab.domain.util.OrderLog;
import org.pancakelab.domain.vo.Building;
import org.pancakelab.domain.vo.RoomNumber;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Slf4j
public class OrderDomainService {

    private final OrderRepository orderRepository;
    private final PancakeRepository pancakeRepository;

    public OrderDomainService(OrderRepository orderRepository, PancakeRepository pancakeRepository) {
        this.orderRepository = orderRepository;
        this.pancakeRepository = pancakeRepository;
    }

    public void saveOrder(PancakeOrderEntity pancakeOrderEntity) {
        orderRepository.save(pancakeOrderEntity);
        pancakeRepository.save(pancakeOrderEntity);
    }

    public PancakeOrderEntity createOrder(Building building, RoomNumber roomNumber) {
        PancakeOrderEntity pancakeOrderEntity = PancakeOrderEntity.createOrder(building, roomNumber);
        saveOrder(pancakeOrderEntity);
        return pancakeOrderEntity;
    }

    public PancakeOrderEntity getOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> {
                    log.error("Order not found: {}", orderId);
                    return new IllegalArgumentException("Order not found: " + orderId);
                });
    }

    public void addPancakes(UUID orderId, List<Class<? extends PancakeDecorator>> decorators, int count) {
        PancakeOrderEntity pancakeOrderEntity = getOrderById(orderId);
        List<Pancake> pancakes = pancakeRepository.findByOrderId(orderId);
        for (int i = 0; i < count; i++) {
            Pancake pancake = new BasicPancake(orderId);
            pancake = pancakeOrderEntity.decoratePancake(pancake, decorators);
            pancakes.add(pancake);
        }
        pancakeRepository.saveAll(orderId, pancakes);
        OrderLog.logAddPancake(pancakeOrderEntity, pancakes.get(0).description(), pancakes);
    }

    public void removePancake(UUID orderId, Set<String> ingredients, int count) {
        List<Pancake> pancakes = pancakeRepository.findByOrderId(orderId);
        AtomicInteger removedCount = new AtomicInteger(0);
        pancakes.removeIf(pancake -> {
            if (removedCount.get() >= count) return false;
            boolean matches = ingredients.stream().allMatch(pancake.description()::contains);
            if (matches) removedCount.incrementAndGet();
            return matches;
        });
        pancakeRepository.saveAll(orderId, pancakes);
        OrderLog.logRemovePancakes(getOrderById(orderId), ingredients.toString(), removedCount.get(), pancakes);
    }

    public void cancelOrder(UUID orderId) {
        PancakeOrderEntity pancakeOrderEntity = getOrderById(orderId);
        pancakeRepository.deleteByOrderId(orderId);
        orderRepository.deleteById(orderId);
        orderRepository.getCompletedOrders().remove(orderId);
        orderRepository.getPreparedOrders().remove(orderId);
        OrderLog.logCancelOrder(pancakeOrderEntity, Collections.emptyList());
    }

    public void completeOrder(UUID orderId) {
        orderRepository.getCompletedOrders().add(orderId);
        DomainEventPublisher.publish(new DomainEvent(orderId, EventType.ORDER_COMPLETED));
        log.info("Order completed: {}", orderId);
    }

    public void prepareOrder(UUID orderId) {
        if (!orderRepository.getCompletedOrders().contains(orderId)) {
            throw new IllegalArgumentException("Order not completed: " + orderId);
        }
        orderRepository.getPreparedOrders().add(orderId);
        orderRepository.getCompletedOrders().remove(orderId);
        DomainEventPublisher.publish(new DomainEvent(orderId, EventType.ORDER_PREPARED));
        log.info("Order prepared: {}", orderId);
    }

    public Object[] deliverOrder(UUID orderId) {
        if (!orderRepository.getPreparedOrders().contains(orderId)) {
            return null;
        }
        PancakeOrderEntity pancakeOrderEntity = getOrderById(orderId);
        List<String> pancakesToDeliver = viewOrder(orderId);
        pancakeRepository.deleteByOrderId(orderId);
        orderRepository.findAll().remove(orderId);
        orderRepository.getPreparedOrders().remove(orderId);
        DomainEventPublisher.publish(new DomainEvent(orderId, EventType.ORDER_DELIVERED));
        return new Object[]{pancakeOrderEntity, pancakesToDeliver};
    }

    public List<String> viewOrder(UUID orderId) {
        return Optional.ofNullable(pancakeRepository.findByOrderId(orderId))
                .orElse(Collections.emptyList())
                .stream()
                .map(Pancake::description)
                .collect(Collectors.toList());
    }

    public Set<UUID> listCompletedOrders() {
        return orderRepository.getCompletedOrders();
    }

    public Set<UUID> listPreparedOrders() {
        return orderRepository.getPreparedOrders();
    }
}

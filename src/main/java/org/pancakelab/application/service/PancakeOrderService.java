package org.pancakelab.application.service;

import lombok.extern.slf4j.Slf4j;
import org.pancakelab.domain.PancakeOrderEntity;
import org.pancakelab.domain.exception.GlobalExceptionHandler;
import org.pancakelab.domain.model.pancakes.PancakeDecorator;
import org.pancakelab.domain.services.OrderDomainService;
import org.pancakelab.domain.util.LockUtils;
import org.pancakelab.domain.vo.Building;
import org.pancakelab.domain.vo.RoomNumber;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Manages the lifecycle of pancake orders, including creation, modification,
 * and delivery. Ensures thread safety and data integrity through the use of locks.
 */
@Slf4j
public class PancakeOrderService {

    private OrderDomainService orderDomainService;

    private final Lock lock = new ReentrantLock();  // Ensures thread-safe operations


    public PancakeOrderService(OrderDomainService orderDomainService) {
        this.orderDomainService = orderDomainService;
    }

    /**
     * Creates a new order for the specified building and room.
     *
     * @param building   The building where the order is to be delivered.
     * @param roomNumber The room number where the order is to be delivered.
     * @return The created Order object.
     */
    public PancakeOrderEntity createOrder(Building building, RoomNumber roomNumber) {
        try {
            return LockUtils.executeWithLock(lock, () -> {
                PancakeOrderEntity pancakeOrderEntity = orderDomainService.createOrder(building, roomNumber);
                log.info("Order created: {}", pancakeOrderEntity.getOrderId());
                return pancakeOrderEntity;
            });
        } catch (Exception e) {
            GlobalExceptionHandler.handle(e);
            throw e;  // Re-throwing the exception after handling
        }
    }

    /**
     * Adds a specified number of pancakes to an existing order.
     *
     * @param orderId    The UUID of the order.
     * @param decorators The list of decorator classes to apply.
     * @param count      The number of pancakes to add.
     */
    public void addPancakes(UUID orderId, List<Class<? extends PancakeDecorator>> decorators, int count) {
        LockUtils.executeWithLock(lock, () -> {
            orderDomainService.addPancakes(orderId, decorators, count);
        });
    }

    /**
     * Removes a specified number of pancakes from an order based on their description.
     *
     * @param orderId     The UUID of the order.
     * @param ingredients The description of the pancakes to remove.
     * @param count       The number of pancakes to remove.
     */
    public void removePancake(UUID orderId, Set<String> ingredients, int count) {
        LockUtils.executeWithLock(lock, () -> {
            orderDomainService.removePancake(orderId, ingredients, count);
        });
    }

    /**
     * Cancels an order and removes it from the repository.
     *
     * @param orderId The UUID of the order.
     */
    public void cancelOrder(UUID orderId) {
        LockUtils.executeWithLock(lock, () -> {
            orderDomainService.cancelOrder(orderId);
        });
    }

    /**
     * Marks an order as complete and publishes a completion event.
     *
     * @param orderId The UUID of the order.
     */
    public void completeOrder(UUID orderId) {
        LockUtils.executeWithLock(lock, () -> {
            orderDomainService.completeOrder(orderId);
        });
    }

    /**
     * Prepares an order for delivery and publishes a preparation event.
     *
     * @param orderId The UUID of the order.
     */
    public void prepareOrder(UUID orderId) {
        LockUtils.executeWithLock(lock, () -> {
            orderDomainService.prepareOrder(orderId);
        });
    }

    /**
     * Marks an order as delivered, removes it from the repository,
     * and publishes a delivery event.
     *
     * @param orderId The UUID of the order.
     * @return An array containing the order and a list of pancake descriptions.
     */
    public Object[] deliverOrder(UUID orderId) {
        return LockUtils.executeWithLock(lock, () -> {
            return orderDomainService.deliverOrder(orderId);
        });
    }


    /**
     * Lists all completed orders.
     *
     * @return A Set of UUIDs representing completed orders.
     */
    public Set<UUID> listCompletedOrders() {
        return orderDomainService.listCompletedOrders();
    }

    /**
     * Lists all prepared orders.
     *
     * @return A Set of UUIDs representing prepared orders.
     */
    public Set<UUID> listPreparedOrders() {
        return orderDomainService.listPreparedOrders();
    }

}

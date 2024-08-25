package org.pancakelab;

import lombok.extern.slf4j.Slf4j;
import org.pancakelab.application.service.PancakeOrderService;
import org.pancakelab.domain.PancakeOrderEntity;
import org.pancakelab.domain.model.pancakes.DarkChocolateDecorator;
import org.pancakelab.domain.model.pancakes.PancakeDecorator;
import org.pancakelab.domain.model.pancakes.WhippedCreamDecorator;
import org.pancakelab.domain.repository.OrderRepository;
import org.pancakelab.domain.repository.PancakeRepository;
import org.pancakelab.domain.services.OrderDomainService;
import org.pancakelab.domain.vo.Building;
import org.pancakelab.domain.vo.RoomNumber;
import org.pancakelab.infrastructure.persistence.InMemoryOrderRepository;
import org.pancakelab.infrastructure.persistence.InMemoryPancakeRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
public class Main {
    public static void main(String[] args) {

        OrderRepository orderRepository = new InMemoryOrderRepository();
        PancakeRepository pancakeRepository = new InMemoryPancakeRepository();
        OrderDomainService orderDomainService = new OrderDomainService(orderRepository, pancakeRepository);

        PancakeOrderService pancakeOrderService = new PancakeOrderService(orderDomainService);

        // 1. Create an order
        PancakeOrderEntity pancakeOrderEntity = pancakeOrderService.createOrder(new Building("2"), new RoomNumber("343"));
        log.info("Order created with ID: " + pancakeOrderEntity.getOrderId());

        // 2. Add pancakes with Dark Chocolate and Whipped Cream
        List<Class<? extends PancakeDecorator>> decorators = Arrays.asList(DarkChocolateDecorator.class, WhippedCreamDecorator.class);
        pancakeOrderService.addPancakes(pancakeOrderEntity.getOrderId(), decorators, 3);
        log.info("Added 3 pancakes with Dark Chocolate and Whipped Cream.");

        // 3. Complete the order
        pancakeOrderService.completeOrder(pancakeOrderEntity.getOrderId());


        // 4. Prepare the order
        pancakeOrderService.prepareOrder(pancakeOrderEntity.getOrderId());


        // 5. Deliver the order
        Object[] deliveryInfo = pancakeOrderService.deliverOrder(pancakeOrderEntity.getOrderId());
        if (deliveryInfo != null) {
            UUID deliveredOrderId = ((PancakeOrderEntity) deliveryInfo[0]).getOrderId();
            List<String> deliveredPancakes = (List<String>) deliveryInfo[1];
            log.info("Delivered order ID: " + deliveredOrderId);
            log.info("Delivered pancakes: " + deliveredPancakes);
        } else {
            log.info("Order not ready for delivery.");
        }
    }
}

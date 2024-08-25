package org.pancakelab.application.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.pancakelab.domain.PancakeOrderEntity;
import org.pancakelab.domain.model.pancakes.DarkChocolateDecorator;
import org.pancakelab.domain.model.pancakes.HazelnutsDecorator;
import org.pancakelab.domain.model.pancakes.MilkChocolateDecorator;
import org.pancakelab.domain.services.OrderDomainService;
import org.pancakelab.domain.vo.Building;
import org.pancakelab.domain.vo.RoomNumber;
import org.pancakelab.infrastructure.persistence.InMemoryOrderRepository;
import org.pancakelab.infrastructure.persistence.InMemoryPancakeRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PancakeOrderServiceTest {

    private OrderDomainService orderDomainService =  new OrderDomainService(new InMemoryOrderRepository(), new InMemoryPancakeRepository());

    private PancakeOrderService pancakeOrderService = new PancakeOrderService(orderDomainService);
    private PancakeOrderEntity pancakeOrderEntity = null;
    private final static String DARK_CHOCOLATE_PANCAKE_DESCRIPTION           = "Delicious pancake with dark chocolate!";
    private final static String MILK_CHOCOLATE_PANCAKE_DESCRIPTION           = "Delicious pancake with milk chocolate!";
    private final static String MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION = "Delicious pancake with milk chocolate, hazelnuts!";

    @Test
    @org.junit.jupiter.api.Order(10)
    public void GivenOrderDoesNotExist_WhenCreatingOrder_ThenOrderCreatedWithCorrectData_Test() {
        // setup

        // exercise
        pancakeOrderEntity = pancakeOrderService.createOrder(new Building("10"), new RoomNumber("20"));

        assertEquals("10", pancakeOrderEntity.getBuilding().getName());
        assertEquals("20", pancakeOrderEntity.getRoomNumber().getNumber());

        // verify

        // tear down
    }

    @Test
    @org.junit.jupiter.api.Order(20)
    public void GivenOrderExists_WhenAddingPancakes_ThenCorrectNumberOfPancakesAdded_Test() {
        // setup

        // exercise
        addPancakes();

        // verify
        List<String> ordersPancakes = orderDomainService.viewOrder(pancakeOrderEntity.getOrderId());

        assertEquals(List.of(DARK_CHOCOLATE_PANCAKE_DESCRIPTION,
                             DARK_CHOCOLATE_PANCAKE_DESCRIPTION,
                             DARK_CHOCOLATE_PANCAKE_DESCRIPTION,
                             MILK_CHOCOLATE_PANCAKE_DESCRIPTION,
                             MILK_CHOCOLATE_PANCAKE_DESCRIPTION,
                             MILK_CHOCOLATE_PANCAKE_DESCRIPTION,
                             MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION,
                             MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION,
                             MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION), ordersPancakes);

        // tear down
    }

    @Test
    @org.junit.jupiter.api.Order(30)
    public void GivenPancakesExists_WhenRemovingPancakes_ThenCorrectNumberOfPancakesRemoved_Test() {
        // setup

        // exercise
        pancakeOrderService.removePancake(pancakeOrderEntity.getOrderId(), Set.of(DARK_CHOCOLATE_PANCAKE_DESCRIPTION), 2);
        pancakeOrderService.removePancake( pancakeOrderEntity.getOrderId(), Set.of(MILK_CHOCOLATE_PANCAKE_DESCRIPTION),3);
        pancakeOrderService.removePancake(pancakeOrderEntity.getOrderId(), Set.of(MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION), 1);

        // verify
        List<String> ordersPancakes = orderDomainService.viewOrder(pancakeOrderEntity.getOrderId());

        assertEquals(List.of(DARK_CHOCOLATE_PANCAKE_DESCRIPTION,
                             MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION,
                             MILK_CHOCOLATE_HAZELNUTS_PANCAKE_DESCRIPTION), ordersPancakes);

        // tear down
    }

    @Test
    @org.junit.jupiter.api.Order(40)
    public void GivenOrderExists_WhenCompletingOrder_ThenOrderCompleted_Test() {
        // setup

        // exercise
        pancakeOrderService.completeOrder(pancakeOrderEntity.getOrderId());

        // verify
        Set<UUID> completedOrdersOrders = pancakeOrderService.listCompletedOrders();
        assertTrue(completedOrdersOrders.contains(pancakeOrderEntity.getOrderId()));

        // tear down
    }

    @Test
    @org.junit.jupiter.api.Order(50)
    public void GivenOrderExists_WhenPreparingOrder_ThenOrderPrepared_Test() {
        // setup

        // exercise
        pancakeOrderService.prepareOrder(pancakeOrderEntity.getOrderId());

        // verify
        Set<UUID> completedOrders = pancakeOrderService.listCompletedOrders();
        assertFalse(completedOrders.contains(pancakeOrderEntity.getOrderId()));

        Set<UUID> preparedOrders = pancakeOrderService.listPreparedOrders();
        assertTrue(preparedOrders.contains(pancakeOrderEntity.getOrderId()));

        // tear down
    }

    @Test
    @org.junit.jupiter.api.Order(60)
    public void GivenOrderExists_WhenDeliveringOrder_ThenCorrectOrderReturnedAndOrderRemovedFromTheDatabase_Test() {
        // setup
        List<String> pancakesToDeliver = orderDomainService.viewOrder(pancakeOrderEntity.getOrderId());

        // exercise
        Object[] deliveredOrder = pancakeOrderService.deliverOrder(pancakeOrderEntity.getOrderId());

        // verify
        Set<UUID> completedOrders = pancakeOrderService.listCompletedOrders();
        assertFalse(completedOrders.contains(pancakeOrderEntity.getOrderId()));

        Set<UUID> preparedOrders = pancakeOrderService.listPreparedOrders();
        assertFalse(preparedOrders.contains(pancakeOrderEntity.getOrderId()));

        List<String> ordersPancakes = orderDomainService.viewOrder(pancakeOrderEntity.getOrderId());

        assertEquals(List.of(), ordersPancakes);
        assertEquals(pancakeOrderEntity.getOrderId(), ((PancakeOrderEntity) deliveredOrder[0]).getOrderId());
        assertEquals(pancakesToDeliver, (List<String>) deliveredOrder[1]);

        // tear down
        pancakeOrderEntity = null;
    }

    @Test
    @org.junit.jupiter.api.Order(70)
    public void GivenOrderExists_WhenCancellingOrder_ThenOrderAndPancakesRemoved_Test() {
        // setup
        pancakeOrderEntity = pancakeOrderService.createOrder(new Building("10"), new RoomNumber("20"));
        addPancakes();

        // exercise
        pancakeOrderService.cancelOrder(pancakeOrderEntity.getOrderId());

        // verify
        Set<UUID> completedOrders = pancakeOrderService.listCompletedOrders();
        assertFalse(completedOrders.contains(pancakeOrderEntity.getOrderId()));

        Set<UUID> preparedOrders = pancakeOrderService.listPreparedOrders();
        assertFalse(preparedOrders.contains(pancakeOrderEntity.getOrderId()));

        List<String> ordersPancakes = orderDomainService.viewOrder(pancakeOrderEntity.getOrderId());

        assertEquals(List.of(), ordersPancakes);

        // tear down
    }

    private void addPancakes() {
        pancakeOrderService.addPancakes(pancakeOrderEntity.getOrderId(), List.of(DarkChocolateDecorator.class), 3);
        pancakeOrderService.addPancakes(pancakeOrderEntity.getOrderId(), List.of(MilkChocolateDecorator.class), 3);
        pancakeOrderService.addPancakes(pancakeOrderEntity.getOrderId(), List.of(MilkChocolateDecorator.class, HazelnutsDecorator.class), 3);
    }
}

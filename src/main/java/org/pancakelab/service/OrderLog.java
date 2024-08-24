package org.pancakelab.service;

import lombok.extern.slf4j.Slf4j;
import org.pancakelab.model.Order;
import org.pancakelab.model.pancakes.PancakeRecipe;

import java.util.List;

@Slf4j
public class OrderLog {

    public static void main(String[] args) {
        log.info("{} {} pancake(s) with description '{}' from order {} now containing {} pancakes, for building {}, room {}.");
    }

    public static void logAddPancake(Order order, String description, List<PancakeRecipe> pancakes) {
        logOrderAction("Added", order, description, pancakes.size(), pancakes);
    }

    public static void logRemovePancakes(Order order, String description, int count, List<PancakeRecipe> pancakes) {
        logOrderAction("Removed", order, description, count, pancakes);
    }

    public static void logCancelOrder(Order order, List<PancakeRecipe> pancakes) {
        logOrderSummary("Cancelled", order, pancakes);
    }

    public static void logDeliverOrder(Order order, List<PancakeRecipe> pancakes) {
        logOrderSummary("out for delivery", order, pancakes);
    }

    private static void logOrderAction(String action, Order order, String description, int count, List<PancakeRecipe> pancakes) {
        long pancakesInOrder = countPancakesInOrder(order, pancakes);
        log.info("{} {} pancake(s) with description '{}' from order {} now containing {} pancakes, for building {}, room {}.",
                action, count, description, order.getId(), pancakesInOrder, order.getBuilding(), order.getRoom());
    }

    private static void logOrderSummary(String action, Order order, List<PancakeRecipe> pancakes) {
        long pancakesInOrder = countPancakesInOrder(order, pancakes);
        log.info("Order {} with {} pancakes for building {}, room {} {}.",
                order.getId(), pancakesInOrder, order.getBuilding(), order.getRoom(), action);
    }

    private static long countPancakesInOrder(Order order, List<PancakeRecipe> pancakes) {
        return pancakes.stream()
                .filter(p -> p.getOrderId().equals(order.getId()))
                .count();
    }
}

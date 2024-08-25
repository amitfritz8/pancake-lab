package org.pancakelab.domain.util;

import lombok.extern.slf4j.Slf4j;
import org.pancakelab.domain.PancakeOrderEntity;
import org.pancakelab.domain.model.pancakes.Pancake;

import java.util.List;

@Slf4j
public class OrderLog {

    public static void logAddPancake(PancakeOrderEntity pancakeOrderEntity, String description, List<Pancake> pancakes) {
        logOrderAction("Added", pancakeOrderEntity, description, pancakes.size(), pancakes);
    }

    public static void logRemovePancakes(PancakeOrderEntity pancakeOrderEntity, String description, int count, List<Pancake> pancakes) {
        logOrderAction("Removed", pancakeOrderEntity, description, count, pancakes);
    }

    public static void logCancelOrder(PancakeOrderEntity pancakeOrderEntity, List<Pancake> pancakes) {
        logOrderSummary("Cancelled", pancakeOrderEntity, pancakes);
    }

    public static void logDeliverOrder(PancakeOrderEntity pancakeOrderEntity, List<Pancake> pancakes) {
        logOrderSummary("out for delivery", pancakeOrderEntity, pancakes);
    }

    private static void logOrderAction(String action, PancakeOrderEntity pancakeOrderEntity, String description, int count, List<Pancake> pancakes) {
        long pancakesInOrder = countPancakesInOrder(pancakeOrderEntity, pancakes);
        log.info("{} {} pancake(s) with description '{}' from order {} now containing {} pancakes, for building {}, room {}.",
                action, count, description, pancakeOrderEntity.getOrderId(), pancakesInOrder, pancakeOrderEntity.getBuilding(), pancakeOrderEntity.getRoomNumber().getNumber());
    }

    private static void logOrderSummary(String action, PancakeOrderEntity pancakeOrderEntity, List<Pancake> pancakes) {
        long pancakesInOrder = countPancakesInOrder(pancakeOrderEntity, pancakes);
        log.info("Order {} with {} pancakes for building {}, room {} {}.",
                pancakeOrderEntity.getOrderId(), pancakesInOrder, pancakeOrderEntity.getBuilding(), pancakeOrderEntity.getRoomNumber().getNumber(), action);
    }

    private static long countPancakesInOrder(PancakeOrderEntity pancakeOrderEntity, List<Pancake> pancakes) {
        return pancakes.stream()
                .filter(p -> p.getOrderId().equals(pancakeOrderEntity.getOrderId()))
                .count();
    }
}

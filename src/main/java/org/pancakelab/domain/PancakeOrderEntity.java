package org.pancakelab.domain;

import lombok.Data;
import org.pancakelab.domain.exception.PancakeLabException;
import org.pancakelab.domain.exception.PancakeLabExceptionEnum;
import org.pancakelab.domain.model.pancakes.DefaultPancakeDecorator;
import org.pancakelab.domain.model.pancakes.Pancake;
import org.pancakelab.domain.model.pancakes.PancakeDecorator;
import org.pancakelab.domain.vo.Building;
import org.pancakelab.domain.vo.RoomNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Represents a pancake order, managing its lifecycle and ensuring business
 * rules are enforced.
 */
@Data
public class PancakeOrderEntity {
    private final UUID orderId;
    private final Building building;
    private final RoomNumber roomNumber;
    private final List<Pancake> pancakes;

    /**
     * Creates a new Order.
     *
     * @param building   The building where the order is to be delivered.
     * @param roomNumber The room number where the order is to be delivered.
     */
    public PancakeOrderEntity(Building building, RoomNumber roomNumber) {
        if (building == null || roomNumber == null) {
            throw new PancakeLabException(PancakeLabExceptionEnum.MISSING_DATA, "Building or room number cannot be null.");
        }
        this.orderId = UUID.randomUUID();
        this.building = building;
        this.roomNumber = roomNumber;
        this.pancakes = new ArrayList<>();
    }

    // Static factory method to create an order
    public static PancakeOrderEntity createOrder(Building building, RoomNumber roomNumber) {
        return new PancakeOrderEntity(building, roomNumber);
    }

    public List<Pancake> getPancakes() {
        return Collections.unmodifiableList(pancakes);
    }

    /**
     * Applies the provided decorators to the pancake.
     *
     * @param pancake    The pancake to decorate.
     * @param decorators The list of decorator classes to apply.
     * @return The decorated pancake.
     */
    public Pancake decoratePancake(Pancake pancake, List<Class<? extends PancakeDecorator>> decorators) {
        Pancake decoratedPancake = pancake;
        if (decorators == null || decorators.isEmpty()) {
            decoratedPancake = new DefaultPancakeDecorator(pancake);
        } else {
            for (Class<? extends PancakeDecorator> decorator : decorators) {
                decoratedPancake = decoratePancake(decoratedPancake, decorator);
            }
        }
        return decoratedPancake;
    }

    /**
     * Applies a single decorator to the pancake.
     *
     * @param pancake   The pancake to decorate.
     * @param decorator The decorator class to apply.
     * @return The decorated pancake.
     */
    private Pancake decoratePancake(Pancake pancake, Class<? extends PancakeDecorator> decorator) {
        try {
            return decorator.getConstructor(Pancake.class).newInstance(pancake);
        } catch (Exception e) {
            throw new PancakeLabException(PancakeLabExceptionEnum.PANCAKE_DECORATION_ERROR, "Failed to decorate pancake", e);
        }
    }
}

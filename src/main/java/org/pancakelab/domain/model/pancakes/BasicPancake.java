package org.pancakelab.domain.model.pancakes;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class BasicPancake implements Pancake {

    private final UUID orderId;

    public BasicPancake(UUID orderId) {
        this.orderId = orderId;
    }

    @Override
    public String description() {
        return "";
    }

    @Override
    public Set<Ingredient> ingredients() {
        return Set.of();
    }
}

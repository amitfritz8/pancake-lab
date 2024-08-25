package org.pancakelab.domain.model.pancakes;

import java.util.Set;
import java.util.UUID;

public interface Pancake {

    UUID getOrderId();

    String description();

    Set<Ingredient> ingredients();
}
package org.pancakelab.domain.model.pancakes;

import java.util.Collections;
import java.util.Set;

public class DefaultPancakeDecorator extends PancakeDecorator {
    public DefaultPancakeDecorator(Pancake pancake) {
        super(pancake);
    }

    @Override
    public String description() {
        return "Delicious pancake with %s!".formatted(String.join(", ", ingredients().stream()
                .map(Ingredient::getDescription)
                .toList()));
    }

    @Override
    public Set<Ingredient> ingredients() {
        return Collections.singleton(Ingredient.PLAIN_CAKE);
    }
}
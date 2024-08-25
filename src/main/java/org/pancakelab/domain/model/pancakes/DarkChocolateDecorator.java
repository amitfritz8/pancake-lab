package org.pancakelab.domain.model.pancakes;

import java.util.HashSet;
import java.util.Set;

public class DarkChocolateDecorator extends PancakeDecorator {

    public DarkChocolateDecorator(Pancake pancake) {
        super(pancake);
    }

    @Override
    public Set<Ingredient> ingredients() {
        Set<Ingredient> ingredients = new HashSet<>(super.ingredients());
        ingredients.add(Ingredient.DARK_CHOCOLATE);
        return ingredients;
    }
}


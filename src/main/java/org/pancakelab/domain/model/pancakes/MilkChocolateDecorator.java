package org.pancakelab.domain.model.pancakes;

import java.util.HashSet;
import java.util.Set;

public class MilkChocolateDecorator extends PancakeDecorator {

    public MilkChocolateDecorator(Pancake pancake) {
        super(pancake);
    }

    @Override
    public Set<Ingredient> ingredients() {
        Set<Ingredient> ingredients = new HashSet<>(super.ingredients());
        ingredients.add(Ingredient.MILK_CHOCOLATE);
        return ingredients;
    }
}
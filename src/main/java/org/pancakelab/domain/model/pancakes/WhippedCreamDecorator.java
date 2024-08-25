package org.pancakelab.domain.model.pancakes;

import java.util.HashSet;
import java.util.Set;
public class WhippedCreamDecorator extends PancakeDecorator {

    public WhippedCreamDecorator(Pancake pancake) {
        super(pancake);
    }

    @Override
    public Set<Ingredient> ingredients() {
        Set<Ingredient> ingredients = new HashSet<>(super.ingredients());
        ingredients.add(Ingredient.WHIPPED_CREAM);
        return ingredients;
    }
}
package org.pancakelab.domain.model.pancakes;

import org.pancakelab.domain.exception.PancakeLabException;
import org.pancakelab.domain.exception.PancakeLabExceptionEnum;

import java.util.Set;
import java.util.UUID;

public abstract class PancakeDecorator implements Pancake {
    protected final Pancake decoratedPancake;

    public PancakeDecorator(Pancake pancake) {
        this.decoratedPancake = pancake;
        validateIngredients(ingredients());
    }

    @Override
    public UUID getOrderId() {
        return decoratedPancake.getOrderId();
    }


    @Override
    public String description() {
        return "Delicious pancake with %s!".formatted(String.join(", ", ingredients().stream()
                .map(Ingredient::getDescription)
                .toList()));
    }

    @Override
    public Set<Ingredient> ingredients() {
        return decoratedPancake.ingredients();
    }

    private void validateIngredients(Set<Ingredient> ingredients) {
        if (ingredients.contains(Ingredient.MUSTARD) && ingredients.contains(Ingredient.MILK_CHOCOLATE)) {
            throw new PancakeLabException(PancakeLabExceptionEnum.INVALID_DATA, "Mustard and Milk Chocolate cannot be combined.");
        }
    }
}

package org.pancakelab.domain.model.pancakes;

public enum Ingredient {
    DARK_CHOCOLATE("dark chocolate"),
    MILK_CHOCOLATE("milk chocolate"),
    HAZELNUTS("hazelnuts"),
    WHIPPED_CREAM("whipped cream"),
    PLAIN_CAKE("plain cake"),
    MUSTARD("mustard");

    private final String description;

    Ingredient(String description) {
        this.description = description;
    }

    /**
     * Gets the description of the ingredient.
     *
     * @return The ingredient's description.
     */
    public String getDescription() {
        return description;
    }
}

package cs.cvut.fel.pjv.demo.view;

import java.util.HashMap;
import java.util.Map;

public enum Recipes {
    WOODEN_PICAXE(new Item("Picaxe", "wooden_picaxe.png", "wooden"),
            new Item("Material", "stick.png", "stick"), 5),
    WOODEN_PLANK(new Item("Block", "wooden_block.jpg", "wooden_plank"),
            new Item("Material", "stick.png", "stick"), 4),
    STONE_BLOCK(new Item("Block", "stone_block.jpg", "stone_block"),
            new Item("Material", "stone_material.png", "stone"), 4),
    WOODEN_SHOWEL(new Item("Showel", "wooden_shovel.png", "wooden"),
            new Item("Material", "stick.png", "stick"), 3),
    WOODEN_SWORD(new Item("Sword", "wooden_sword.png", "wooden"),
            new Item("Material", "stick.png", "stick"), 1),
    STONE_PICAXE(new Item("Picaxe", "stone_picaxe.png", "stone"),
            new Item("Material", "stick.png", "stick"), 2,
            new Item("Material", "stone_material.png", "stone"), 3),
    STONE_SHOWEL(new Item("Showel", "stone_shovel.png", "stone"),
            new Item("Material", "stick.png", "stick"), 2,
            new Item("Material", "stone_material.png", "stone"), 1),
    STONE_SWORD(new Item("Sword", "stone_sword.png", "stone"),
            new Item("Material", "stick.png", "stick"), 1,
            new Item("Material", "stone_material.png", "stone"), 2),
    IRON_PICAXE(new Item("Picaxe", "iron_picaxe.png", "iron"),
            new Item("Material", "stick.png", "stick"), 2,
            new Item("Material", "iron_ingot.png", "iron"), 3),
    IRON_SHOWEL(new Item("Showel", "iron_shovel.png", "iron"),
            new Item("Material", "stick.png", "stick"), 2,
            new Item("Material", "iron_ingot.png", "iron"), 1),
    IRON_SWORD(new Item("Sword", "iron_sword.png", "iron"),
            new Item("Material", "stick.png", "stick"), 1,
            new Item("Material", "iron_ingot.png", "iron"), 2);

    private final Item result;
    private final Map<Item, Integer> ingredients;

    Recipes(Item result, Item ingredient, int ingredientCount) {
        this.result = result;
        this.ingredients = new HashMap<>();
        this.ingredients.put(ingredient, ingredientCount);
    }

    Recipes(Item result, Item ingredient1, int ingredientCount1, Item ingredient2, int ingredientCount2) {
        this.result = result;
        this.ingredients = new HashMap<>();
        this.ingredients.put(ingredient1, ingredientCount1);
        this.ingredients.put(ingredient2, ingredientCount2);
    }

    public Map<Item, Integer> getIngredients() {
        return ingredients;
    }

    public Item getResult() {
        return result;
    }
}

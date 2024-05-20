package cs.cvut.fel.pjv.demo.view;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Map;

public class SpecialBlock extends Block {
    @Expose
    ArrayList<Item> inventory = new ArrayList<>();

    public SpecialBlock(boolean isCraftable, boolean canFall, String type , String imagePath, String group) {
        super(isCraftable, canFall, type, imagePath, group);
    }

    @Override
    public void setCoords(int xCoord, int yCoord) {
        super.setCoords(xCoord, yCoord);
    }

    @Override
    public int getXCoord() {
        return super.getXCoord();
    }

    @Override
    public int getYCoord() {
        return super.getYCoord();
    }

    @Override
    public boolean isPlaced() {
        return super.isPlaced();
    }

    @Override
    public boolean isCraftable() {
        return super.isCraftable();
    }

    @Override
    public boolean isCanFall() {
        return super.isCanFall();
    }

    @Override
    public int getSize() {
        return super.getSize();
    }

    /**
     * Attempts to craft an item based on the available recipes and the current inventory.
     *
     * @return the crafted item if the recipe's ingredients are available, otherwise null
     */
    public Item craft() {
        for (Recipes recipe : Recipes.values()) {
            boolean hasIngredients = true;
            for (Map.Entry<Item, Integer> entry : recipe.getIngredients().entrySet()) {
                Item ingredient = entry.getKey();
                int requiredAmount = entry.getValue();
                int availableAmount = 0;

                for (Item item : inventory) {
                    if (item.equals(ingredient)) {
                        availableAmount++;
                    }
                }

                if (availableAmount < requiredAmount) {
                    hasIngredients = false;
                    break;
                }
            }

            if (hasIngredients) {
                for (Map.Entry<Item, Integer> entry : recipe.getIngredients().entrySet()) {
                    Item ingredient = entry.getKey();
                    int requiredAmount = entry.getValue();

                    for (int i = 0; i < requiredAmount; i++) {
                        inventory.remove(ingredient);
                    }
                }
                return recipe.getResult();
            }
        }

        return null;
    }

    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public void clearInventory() {
        inventory.clear();
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }
}

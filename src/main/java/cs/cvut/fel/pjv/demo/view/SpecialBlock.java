package cs.cvut.fel.pjv.demo.view;

import java.util.ArrayList;

public class SpecialBlock extends Block {
    ArrayList<Object> inventory = new ArrayList<>();

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

    public void addToInventory(Item item) {
        inventory.add(item);
    }

    public void clearInventory() {
        inventory.clear();
    }

    public ArrayList<Object> getInventory() {
        return inventory;
    }
}

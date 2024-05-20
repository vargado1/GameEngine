package cs.cvut.fel.pjv.demo.view;

import com.google.gson.annotations.Expose;

public class Key extends Item{
    @Expose
    private boolean isDropped = false;

    public Key(String name, String group, String imagePath) {
        super(group, name, imagePath);
    }

    public boolean isDropped() {
        return isDropped;
    }

    public void setDropped(boolean dropped) {
        isDropped = dropped;
    }
}

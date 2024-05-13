package cs.cvut.fel.pjv.demo.view;

import com.google.gson.annotations.Expose;

public class Item {
    @Expose
    protected String group;
    @Expose
    protected String imagePath;
    @Expose
    protected String type;

    public Item(String group, String imagePath, String type) {
        this.group = group;
        this.imagePath = imagePath;
        this.type = type;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getType() {
        return type;
    }

    public String getGroup() {
        return group;
    }
}

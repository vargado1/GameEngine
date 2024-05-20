package cs.cvut.fel.pjv.demo.view;

import com.google.gson.annotations.Expose;
import javafx.scene.image.Image;

public class Item {
    @Expose
    protected String group;
    @Expose
    protected String imagePath;
    @Expose
    protected String type;
    @Expose
    private int sceneXCoord;
    @Expose
    private int sceneYCoord;

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

    public int getSceneXCoord() {
        return sceneXCoord;
    }

    public int getSceneYCoord() {
        return sceneYCoord;
    }

    public void setSceneXCoord(int sceneXCoord) {
        this.sceneXCoord = sceneXCoord;
    }

    public void setSceneYCoord(int sceneYCoord) {
        this.sceneYCoord = sceneYCoord;
    }
}

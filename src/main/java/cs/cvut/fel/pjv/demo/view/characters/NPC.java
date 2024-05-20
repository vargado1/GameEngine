package cs.cvut.fel.pjv.demo.view.characters;

import com.google.gson.annotations.Expose;
import cs.cvut.fel.pjv.demo.view.Material;
import javafx.scene.image.ImageView;

public class NPC {
    private String riddle;
    private Material reward;
    private String NpcName;
    private int xCoords;
    private int yCoords;
    private String img;
    private ImageView imageView;

    public NPC(String riddle, String npcName, int xCoords, int yCoords, String img) {
        this.xCoords = xCoords;
        this.yCoords = yCoords;
        this.riddle = riddle;
        this.NpcName = npcName;
        this.img = img;
    }


    public String getRiddle() {
        return riddle;
    }

    public Material getReward() {
        return reward;
    }

    public String getNpcName() {
        return NpcName;
    }

    public int getxCoords() {
        return xCoords;
    }

    public int getyCoords() {
        return yCoords;
    }

    public String getImg() {
        return img;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setRiddle(String riddle) {
        this.riddle = riddle;
    }

    public void setReward(Material reward) {
        this.reward = reward;
    }

    public void setxCoords(int xCoords) {
        this.xCoords = xCoords;
    }

    public void setyCoords(int yCoords) {
        this.yCoords = yCoords;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public String interact(){
        return riddle;
    }
}

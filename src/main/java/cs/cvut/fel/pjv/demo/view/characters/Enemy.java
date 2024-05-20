package cs.cvut.fel.pjv.demo.view.characters;

import com.google.gson.annotations.Expose;
import javafx.scene.image.ImageView;

public class Enemy {
    @Expose
    private int speed;
    @Expose
    private int damage;
    @Expose
    private String type;
    @Expose
    private int xCoords;
    @Expose
    private int yCoords;
    @Expose
    private int HP;
    @Expose
    private String imagePath;
    private ImageView imageView;

    public Enemy(int damage, String type, int xCoords, int yCoords, int HP, String imagePath) {
        this.xCoords = xCoords;
        this.yCoords = yCoords;
        this.damage = damage;
        this.type = type;
        this.HP = HP;
        this.imagePath = imagePath;
    }

    public void setEnemySpeed(int speed) {
        this.speed = speed;
    }

    public String getEnemyType() {
        return type;
    }

    /**
     * Takes 20 HP (one heart) from player.
     *
     * @param character player
     */
    public void attack(Player character) {
        int HP = character.getHP();

        character.setHP(HP-damage);
    }

    public int getDamage() {
        return damage;
    }

    public String getType() {
        return type;
    }

    public int getxCoords() {
        return xCoords;
    }

    public int getyCoords() {
        return yCoords;
    }

    public int getHP() {
        return HP;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }
}

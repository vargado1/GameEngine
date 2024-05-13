package cs.cvut.fel.pjv.demo.view.characters;

import com.google.gson.annotations.Expose;

public class Enemy {
    private int speed;
    private int damage;
    private String type;
    @Expose
    private int xCoords;
    @Expose
    private int yCoords;
    @Expose
    private int HP;

    public Enemy(int damage, String type, int xCoords, int yCoords, int HP) {
        this.xCoords = xCoords;
        this.yCoords = yCoords;
        this.damage = damage;
        this.type = type;
        this.HP = HP;
    }

    /**
     * Metóda na nastavenie rýchlosti nepriateľa.
     *
     * @param speed Rýchlosť nepriateľa.
     */
    public void setEnemySpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Metóda na získanie typu nepriateľa.
     *
     * @return Typ nepriateľa.
     */
    public String getEnemyType() {
        return type;
    }

    /**
     * Metóda pre útok nepriateľa na postavu.
     *
     * @param character Postava, na ktorú sa má vykonať útok.
     */
    public void attack(Player character) {}

    /**
     * Metóda pre pohyb nepriateľa doprava.
     */

    void moveRIGHT() {

    }

    /**
     * Metóda pre pohyb nepriateľa doľava.
     */

    void moveLEFT() {

    }
}

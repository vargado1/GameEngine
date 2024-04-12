package cs.cvut.fel.pjv.demo.view.characters;

public class Enemy extends Avatar {
    private int speed;
    private int damage;
    private String type;

    public Enemy(int damage, String type) {
        this.damage = damage;
        this.type = type;
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
    public void attack(Avatar character) {}

    /**
     * Metóda pre pohyb nepriateľa doprava.
     */
    @Override
    void moveRIGHT() {

    }

    /**
     * Metóda pre pohyb nepriateľa doľava.
     */
    @Override
    void moveLEFT() {

    }
}

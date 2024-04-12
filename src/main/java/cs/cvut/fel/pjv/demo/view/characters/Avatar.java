package cs.cvut.fel.pjv.demo.view.characters;

abstract class Avatar {
    private double xCoords;
    private double yCoords;
    private int HP;

    /**
     * Metóda pre prijatie poškodenia postavou.
     */
    public void takeDamage(){}

    /**
     * Abstraktná metóda pre pohyb postavy doprava.
     */

    /**
     * Abstraktná metóda pre pohyb postavy doľava.
     */
    abstract void moveRIGHT();
    abstract void moveLEFT();

}

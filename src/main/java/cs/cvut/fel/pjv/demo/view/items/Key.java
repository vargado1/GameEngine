package cs.cvut.fel.pjv.demo.view.items;

public class Key extends Item{
    private boolean isDropped = false;

    private String name;

    /**
     * Konštruktor pre vytvorenie inštancie kľúča.
     *
     * @param name Názov kľúča.
     */
    public Key(String name) {
        this.name = name;
    }

    /**
     * Metóda vracejúca názov kľúča.
     *
     * @return Názov kľúča.
     */
    public String getName() {
        return name;
    }
}

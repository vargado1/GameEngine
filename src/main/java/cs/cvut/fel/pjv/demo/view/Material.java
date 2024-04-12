package cs.cvut.fel.pjv.demo.view;

public class Material {
    private String type;

    /**
     * Konštruktor pre vytvorenie inštancie materiálu.
     *
     * @param type Typ materiálu.
     */
    public Material(String type) {
        this.type = type;
    }

    /**
     * Metóda vracejúca typ materiálu.
     *
     * @return Typ materiálu.
     */
    public String getType() {
        return type;
    }
}

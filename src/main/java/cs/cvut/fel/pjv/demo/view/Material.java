package cs.cvut.fel.pjv.demo.view;

import com.google.gson.annotations.Expose;

public class Material extends Item {

    /**
     * Konštruktor pre vytvorenie inštancie materiálu.
     *
     * @param type Typ materiálu.
     */
    public Material(String type, String group, String imagePath) {
        super(group, imagePath, type);
    }
}

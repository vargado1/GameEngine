package cs.cvut.fel.pjv.demo.view;

public enum Direction {
    LEFT(-1, 0),
    RIGHT(1, 0),
    JUMP(0, 1);

    /**
     * Konštruktor pre vytvorenie inštancie smeru.
     *
     * @param i Hodnota X smeru.
     * @param i1 Hodnota Y smeru.
     */
    Direction(int i, int i1) {
    }
}

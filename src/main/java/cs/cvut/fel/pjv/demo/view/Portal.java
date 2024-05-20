package cs.cvut.fel.pjv.demo.view;

public class Portal {
    private boolean isUnlocked = false;
    private String name;
    private int xCoors;
    private int yCoors;

    public Portal(String name, int xCoors, int yCoors) {
        this.name = name;
        this.xCoors = xCoors;
        this.yCoors = yCoors;
    }

    public void unlockPortal(Key key){}
}

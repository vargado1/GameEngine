package cs.cvut.fel.pjv.demo.view;

import com.google.gson.annotations.Expose;
import cs.cvut.fel.pjv.demo.RealmTypes;
import cs.cvut.fel.pjv.demo.view.characters.Enemy;
import cs.cvut.fel.pjv.demo.view.characters.NPC;
import cs.cvut.fel.pjv.demo.view.characters.Player;

import java.util.ArrayList;

public class Realm {
    @Expose
    private int xMaxCoords;
    @Expose
    private int yMaxCoords;
    @Expose
    private RealmTypes name;
    @Expose
    private String bossName;
    @Expose
    private int difficulty;
    @Expose
    private ArrayList<Block> blocks = new ArrayList<>();
    @Expose
    public ArrayList<Object> mobs = new ArrayList<>();
    public Block[][] map;
    @Expose
    private String backgroundImagePath;


    /**
     * Konštruktor pre vytvorenie inštancie realmov.
     *
     * @param name       Názov realmov.
     * @param bossName   Názov bossa v danom realme.
     * @param difficulty Obtiažnosť daného realmu.
     */
    public Realm(RealmTypes name, String bossName, int difficulty, String background_image, int xMaxCoords, int yMaxCoords) {
        this.name = name;
        this.bossName = bossName;
        this.difficulty = difficulty;
        this.backgroundImagePath = background_image;
        this.map = new Block[xMaxCoords][yMaxCoords];
        this.xMaxCoords = xMaxCoords;
        this.yMaxCoords = yMaxCoords;
    }

    /**
     * DONE
     * Metóda vracejúca maximálne súradnice v osi X.
     *
     * @return Maximálne súradnice v osi X.
     */
    public int getXMaxCoords() {
        return xMaxCoords;
    }

    /**
     * DONE
     * Metóda vracejúca maximálne súradnice v osi Y.
     *
     * @return Maximálne súradnice v osi Y.
     */
    public int getYMaxCoords() {
        return yMaxCoords;
    }

    /**DONE
     * Metóda vracejúca názov realmov.
     *
     * @return Názov realmu.
     */
    public RealmTypes getName() {
        return name;
    }

    /**DONE
     * Metóda vracejúca názov bossa v danom realme.
     *
     * @return Názov bossa v danom realmu.
     */
    public String getBossName() {
        return bossName;
    }

    /**DONE
     * Metóda vracejúca obtiažnosť daného realmu.
     *
     * @return Obtiažnosť daného realmu.
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**DONE
     * Metoda na pridanie characteru do mapy
     * @param mob character na pridanie
     * @return uspesnost pridania
     */
    public void addMob(Object mob){
        mobs.add(mob);
    }

    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }

    public void addBlock(Block block){
        this.blocks.add(block);
        map[block.getXCoord()][block.getYCoord()] = block;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }
}

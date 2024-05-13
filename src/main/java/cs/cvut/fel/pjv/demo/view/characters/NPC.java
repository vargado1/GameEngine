package cs.cvut.fel.pjv.demo.view.characters;

import com.google.gson.annotations.Expose;
import cs.cvut.fel.pjv.demo.view.Material;

public class NPC{
    private String riddle;
    private Material reward;
    private String NpcName;
    @Expose
    private int xCoords;
    @Expose
    private int yCoords;


    public NPC(String riddle, Material reward, String npcName, int xCoords, int yCoords) {
        this.xCoords = xCoords;
        this.yCoords = yCoords;
        this.riddle = riddle;
        this.reward = reward;
        NpcName = npcName;
    }

    /**
     * Metóda na získanie riešenej hádanky NPC postavy.
     *
     * @return Riešená hádanka NPC postavy.
     */
    public String getRiddle() {
        return riddle;
    }

    /**
     * Metóda na získanie odmeny za úspešné riešenie hádanky.
     *
     * @return Odmena za úspešné riešenie hádanky.
     */
    public Material getReward() {
        return reward;
    }

    /**
     * Metóda na získanie mena NPC postavy.
     *
     * @return Meno NPC postavy.
     */
    public String getNpcName() {
        return NpcName;
    }

    /**
     * NPC sa nebude môcť hýbať
     */

    void moveRIGHT() {

    }


    void moveLEFT() {

    }
}

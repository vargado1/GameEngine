package cs.cvut.fel.pjv.demo.view.characters;

import cs.cvut.fel.pjv.demo.view.Material;
import cs.cvut.fel.pjv.demo.view.characters.Avatar;

public class NPC extends Avatar {
    private String riddle;
    private Material reward;
    private String NpcName;

    public NPC(String riddle, Material reward, String npcName) {
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
    @Override
    void moveRIGHT() {

    }

    @Override
    void moveLEFT() {

    }
}

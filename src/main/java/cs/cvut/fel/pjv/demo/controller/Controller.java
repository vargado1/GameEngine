package cs.cvut.fel.pjv.demo.controller;

import cs.cvut.fel.pjv.demo.model.Model;
import cs.cvut.fel.pjv.demo.view.Block;
import cs.cvut.fel.pjv.demo.view.Item;
import cs.cvut.fel.pjv.demo.view.Realm;
import cs.cvut.fel.pjv.demo.view.characters.NPC;
import cs.cvut.fel.pjv.demo.view.characters.Player;
import cs.cvut.fel.pjv.demo.view.SpecialBlock;

public class Controller {
    Player player;
    Model model;
    Realm realm;

    public Controller(Player player, Model model, Realm realm) {
        this.player = player;
        this.model = model;
        this.realm = realm;
    }

    /**
     * tells player to move up
     * @return new coordination's of player which has been already calculated for screen
     */
    public int[] moveUp() {
        int[] coords = player.moveUP();

        return model.getCoordsFromListToScreen(coords[0], coords[1], 30, realm);
    }

    /**
     * tells player to move down
     * @return new coordination's of player which has been already calculated for screen
     */
    public int[] moveDown() {
        int[] coords = player.moveDOWN();

        return model.getCoordsFromListToScreen(coords[0], coords[1], 30, realm);
    }

    /**
     * tells player to move right
     * @return new coordination's of player which has been already calculated for screen
     */
    public int[] moveRight(){
        int[] coords = player.moveRIGHT();

        return model.getCoordsFromListToScreen(coords[0], coords[1], 30, realm);
    }

    /**
     * tells player to move left
     * @return new coordination's of player which has been already calculated for screen
     */
    public int[] moveLeft(){
        int[] coords = player.moveLEFT();
        return model.getCoordsFromListToScreen(coords[0], coords[1], 30, realm);
    }

    /**
     * tells player to destroy a block
     * @param block block to destroy
     * @return if it was successful
     */
    public boolean removeBlock(Block block){
        if (player.breakBlock(block)) {
            return true;
        }
        return false;
    }

    /**
     * asks model if player is sufficiently near NPC to interact and if yes then asks NPC to interact
     * @param npc NPC to interact
     * @return if it was successful ten returns NPC's riddle if not then returns empty string
     */
    public String interactWithObject(NPC npc) {
        if (model.isNearObject(player, npc.getxCoords(), npc.getyCoords())) {
            return npc.interact();
        }
        return "";
    }

    /**
     * asks model if player is sufficiently near block to interact
     * @param block block to interact
     * @return if player is near the block returns crafted item if not then returns null
     */
    public Item interactWithObject(SpecialBlock block) {
        if (model.isNearObject(player, block.getXCoord(), block.getYCoord())) {
            return block.craft();
        }
        return null;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

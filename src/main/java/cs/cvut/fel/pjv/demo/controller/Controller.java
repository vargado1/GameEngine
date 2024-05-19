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

    public int[] moveUp() {
        int[] coords = player.moveUP();

        return model.getCoordsFromListToScreen(coords[0], coords[1], 30, realm);
    }

    public int[] moveDown() {
        int[] coords = player.moveDOWN();

        return model.getCoordsFromListToScreen(coords[0], coords[1], 30, realm);
    }

    public int[] moveRight(){
        int[] coords = player.moveRIGHT();

        return model.getCoordsFromListToScreen(coords[0], coords[1], 30, realm);
    }

    public int[] moveLeft(){
        int[] coords = player.moveLEFT();
        return model.getCoordsFromListToScreen(coords[0], coords[1], 30, realm);
    }

    public void craftItem(){}

    public void attacEnemy(){}

    public boolean removeBlock(Block block){
        if (player.breakBlock(block)) {
            return true;
        }
        return false;
    }

    public String interactWithObject(NPC npc) {
        if (model.isNearObject(player, npc.getxCoords(), npc.getyCoords())) {
            return npc.interact();
        }
        return "";
    }

    public Item interactWithObject(SpecialBlock block) {
        if (model.isNearObject(player, block.getXCoord(), block.getYCoord())) {
            return block.craft();
        }
        return null;
    }
}

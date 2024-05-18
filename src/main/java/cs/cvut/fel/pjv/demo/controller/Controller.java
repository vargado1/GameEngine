package cs.cvut.fel.pjv.demo.controller;

import cs.cvut.fel.pjv.demo.model.Model;
import cs.cvut.fel.pjv.demo.view.Block;
import cs.cvut.fel.pjv.demo.view.Realm;
import cs.cvut.fel.pjv.demo.view.characters.NPC;
import cs.cvut.fel.pjv.demo.view.characters.Player;


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

    /**
     * Metóda pre obsluhu udalosti pohybu doprava.
     *
     */
    public int[] moveRight(){
        int[] coords = player.moveRIGHT();

        return model.getCoordsFromListToScreen(coords[0], coords[1], 30, realm);
    }

    /**
     * Metóda pre obsluhu udalosti pohybu doľava.
     */
    public int[] moveLeft(){
        int[] coords = player.moveLEFT();
        return model.getCoordsFromListToScreen(coords[0], coords[1], 30, realm);
    }


    /**
     * Metóda pre obsluhu udalosti výroby predmetu.
     */
    public void craftItem(){}

    /**
     * Metóda pre obsluhu udalosti útoku na nepriateľa.
     */
    public void attacEnemy(){}

    /**
     * Metóda pre obsluhu udalosti odstránenia bloku.
     */
    public boolean removeBlock(Block block){
        if (player.breakBlock(block)) {
            return true;
        }
        return false;
    }

    /**
     * Metóda pre obsluhu udalosti odomknutia portálu.
     */
    public void unlockPortal(){}

    public String interactWithObject(NPC npc) {
        if (model.isNearObject(player, npc.getxCoords(), npc.getyCoords())) {
            return npc.interact();
        }
        return "";
    }

    public boolean interactWithObject(SpecialBlock block) {

    }
}

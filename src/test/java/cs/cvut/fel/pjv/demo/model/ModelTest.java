package cs.cvut.fel.pjv.demo.model;

import cs.cvut.fel.pjv.demo.RealmTypes;
import cs.cvut.fel.pjv.demo.controller.Controller;
import cs.cvut.fel.pjv.demo.view.Block;
import cs.cvut.fel.pjv.demo.view.Realm;
import cs.cvut.fel.pjv.demo.view.characters.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {
    private Model model;
    private Player player;
    private Realm realm;

    @BeforeEach
    void setUp() {
        player = new Player(24, 13, 100);
        model = new Model();
        realm = new Realm(RealmTypes.Overworld, "Boss", 1, "background.png", 49, 28);
        player.setPlayerSpeed(1);
        Block block = new Block(false, false, "wooden_block", "wooden_block.jpg", "Block");
        block.setCoords(24, 14);
        realm.map[24][14] = block;
        Block block1 = new Block(false, false, "wooden_block", "wooden_block.jpg", "Block");
        block1.setCoords(25, 13);
        realm.map[25][13] = block1;

    }

    @Test
    void isBlockUnder() {
        boolean result = model.isBlockUnder(realm, player);
        assertTrue(result);
    }

    @Test
    void isCollision() {
        boolean result = model.isCollision(realm, player);
        assertTrue(result);
    }

    @Test
    void getCoordsFromListToScreen() {
        int[] result = model.getCoordsFromListToScreen(24, 14, 30, realm);
        assertArrayEquals(new int[]{0, 0}, result);
    }

    @Test
    void getCoordsFromScreenToList() {
        int[] result = model.getCoordsFromScreenToList(0, 0, 30, realm);
        assertArrayEquals(new int[]{24, 14}, result);

    }

    @Test
    void isNearObject() {
        boolean result = model.isNearObject(player, 8, 8);
        assertFalse(result);
    }
}
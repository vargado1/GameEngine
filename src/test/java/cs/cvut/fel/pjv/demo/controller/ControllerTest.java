package cs.cvut.fel.pjv.demo.controller;

import cs.cvut.fel.pjv.demo.RealmTypes;
import cs.cvut.fel.pjv.demo.model.Model;
import cs.cvut.fel.pjv.demo.view.Block;
import cs.cvut.fel.pjv.demo.view.Item;
import cs.cvut.fel.pjv.demo.view.Realm;
import cs.cvut.fel.pjv.demo.view.SpecialBlock;
import cs.cvut.fel.pjv.demo.view.characters.NPC;
import cs.cvut.fel.pjv.demo.view.characters.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;


class ControllerTest {
    private Controller controller;

    @BeforeEach
    void setUp() {
        Player player = new Player(24, 13, 100);
        Model model = new Model();
        Realm realm = new Realm(RealmTypes.Overworld, "Boss", 1, "background.png", 49, 28);
        controller = new Controller(player, model, realm);
        player.setPlayerSpeed(1);
    }


    @Test
    void moveUp() {
        int[] result = controller.moveUp();
        assertEquals(0, result[0]);
        assertEquals(-30, result[1]);
    }

    @Test
    void removeBlock() {
        Block block = new Block(false,true,"wooden_block","wooden_block.jpg","Block");
        block.setCoords(25, 13);
        assertTrue(controller.removeBlock(block));
    }

    @Test
    void interactWithObject() {
        NPC npc = new NPC("Hello", "NPC", 25, 13, "npc.png");
        String result = controller.interactWithObject(npc);
        assertEquals("Hello", result);

    }

    @Test
    void testInteractWithObject() {
        SpecialBlock block = new SpecialBlock(false, false, "specialBlock", "crafting_table.png","Block");
        block.setCoords(0,0);
        Item result = controller.interactWithObject(block);
        assertNull(result);
    }
}
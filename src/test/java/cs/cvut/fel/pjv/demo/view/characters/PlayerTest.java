package cs.cvut.fel.pjv.demo.view.characters;

import cs.cvut.fel.pjv.demo.view.Block;
import cs.cvut.fel.pjv.demo.view.tools.Sword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;
    private Enemy enemy;
    private Block block;
    private Sword sword;

    @BeforeEach
    void setUp() {
        player = new Player(24, 14, 100);
        sword = new Sword("iron_sword.png", "Sword","iron");
        player.setPlayerSpeed(1);
        player.setSelectetItem(sword);
        enemy = new Enemy(20, "enemy", 25,14, 20, "enemy.png");
        block = new Block(false, false, "wooden", "wooden_block.jpg", "Block");

    }

    @Test
    void punch() {
        int enemyHP = enemy.getHP();
        player.punch(enemy);
        assertEquals(enemyHP - 17, enemy.getHP());
    }

    @Test
    void breakBlock() {
        player.breakBlock(block);
        assertNotNull(player.getHotBar()[0]);
    }

    @Test
    void moveLEFT() {
        int[] result = player.moveLEFT();
        assertEquals(23, result[0]);
        assertEquals(14, result[1]);
    }
}
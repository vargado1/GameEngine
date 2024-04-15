package cs.cvut.fel.pjv.demo.view.characters;

import cs.cvut.fel.pjv.demo.view.Block;
import cs.cvut.fel.pjv.demo.view.Direction;
import cs.cvut.fel.pjv.demo.view.Material;
import cs.cvut.fel.pjv.demo.view.SpecialBlock;
import cs.cvut.fel.pjv.demo.view.items.Key;
import cs.cvut.fel.pjv.demo.view.items.tools.Tool;

import java.util.ArrayList;

public class Player extends Avatar {
    private boolean isMoving = false;
    private Direction direction = Direction.RIGHT;
    private int speed;
    private ArrayList<Object> inventory = new ArrayList<>();
    private ArrayList<Object> hotBar = new ArrayList<>();
    private Object selectetItem = null;

    public Player() {
    }

    /**
     * Metóda na nastavenie itemu ktorý ma hráč v ruke
     *
     * @param selectetItem item na nastavenie
     */
    public void setSelectetItem(Object selectetItem) {
        this.selectetItem = selectetItem;
    }

    /**
     * Metóda vracejúca informáciu o pohybe hráča.
     *
     * @return True, ak sa hráč pohybuje, inak false.
     */
    public boolean isPlayerMoving(){
        return isMoving;
    }

    /**
     * Metóda na zastavenie pohybu hráča.
     */
    public void stopMoving(){}

    /**
     * Metóda na nastavenie smeru pohybu hráča.
     *
     * @param direction Smer pohybu hráča.
     */
    public void setDirection(Direction direction){}

    /**
     * Metóda na nastavenie rýchlosti hráča.
     *
     * @param speed Rýchlosť hráča.
     */
    public void setPlayerSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Metóda na pridanie položky do inventára hráča.
     *
     * @param key Kľúč, ktorý sa má pridať do inventára.
     */
    public void addToInvenory(Key key){}

    /**
     * Metóda na pridanie bloku do inventára hráča.
     *
     * @param block Blok, ktorý sa má pridať do inventára.
     */
    public void addToInvenory(Block block){}

    /**
     * Metóda na pridanie nástroju do inventára hráča.
     *
     * @param tool Nástroj, ktorý sa má pridať do inventára.
     */
    public void addToInvenory(Tool tool){}

    /**
     * Metóda na pridanie matriálu do inventára hráča.
     *
     * @param material Material, ktorý sa má pridať do inventára.
     */
    public void addToInvenory(Material material){}

    /**
     * Metóda na pridanie položky do HotBaru hráča.
     *
     * @param key Kľúč, ktorý sa má pridať do HotBaru.
     */
    public void addToHotBar(Key key){}

    /**
     * Metóda na pridanie položky do HotBaru hráča.
     *
     * @param block Položka, ktorá sa má pridať do HotBaru.
     */
    public void addToHotBar(Block block){}

    /**
     * Metóda na pridanie položky do HotBaru hráča.
     *
     * @param tool Položka, ktorá sa má pridať do HotBaru.
     */
    public void addToHotBar(Tool tool){}

    /**
     * Metóda na pridanie položky do HotBaru hráča.
     *
     * @param material Položka, ktorá sa má pridať do HotBaru.
     */
    public void addToHotBar(Material material){}

    /**
     * Metóda na útok nástrojom.
     *
     * @param usedTool Použitý nástroj na útok.
     */
    public void punch(Tool usedTool){}

    /**
     * Metóda na umiestnenie bloku na zadaných súradniciach.
     *
     * @param block    Blok, ktorý sa má umiestniť.
     * @param xCoords  X-ová súradnica umiestnenia.
     * @param yCoords  Y-ová súradnica umiestnenia.
     */
    public void placeBlock(Block block, int xCoords, int yCoords){}

    /**
     * Metóda na rozbíjanie bloku s použitím nástroja.
     *
     * @param usedTool         Použitý nástroj na rozbíjanie.
     * @param blockToDestroy   Blok, ktorý sa má rozbíjať.
     */
    public void breakBlock(Tool usedTool, Block blockToDestroy){}

    /**
     * Metóda na interakciu s nehráčovskou postavou.
     *
     * @param npc NPC postava, s ktorou hráč interaguje.
     */
    public void interactWithNPC(NPC npc){}

    /**
     * Metóda na použitie špeciálneho bloku.
     *
     * @param block Špeciálny blok, ktorý sa má použiť.
     */
    public void useBlock(SpecialBlock block){}

    /**
     * Metóda na vytvorenie bloku zo zadaných materiálov.
     *
     * @param materials Pole materiálov potrebných na vytvorenie bloku.
     * @return Vytvorený blok.
     */
    public Block craft(Material[] materials){
        return null;
    }

    /**
     * Metóda na vytvorenie nástroja zo zadaných materiálov.
     *
     * @param materials Pole materiálov potrebných na vytvorenie nástroja.
     * @return Vytvorený nástroj.
     */
    public Tool craft(Material[] materials){
        return null;
    }

    /**
     * Metóda na pohyb hráča doľava.
     */
    public void moveLEFT() {

    }

    /**
     * Metóda na pohyb hráča doprava.
     */
    @Override
    public void moveRIGHT() {

    }

    /**
     * Metóda na skok hráča.
     */
    public void Jump() {

    }
}

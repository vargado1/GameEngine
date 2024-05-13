package cs.cvut.fel.pjv.demo.view.characters;

import com.google.gson.annotations.Expose;
import cs.cvut.fel.pjv.demo.view.*;
import cs.cvut.fel.pjv.demo.view.tools.Tool;

public class Player {
    @Expose
    private boolean isMoving = false;
    @Expose
    private Direction direction = Direction.RIGHT;
    @Expose
    private int speed;
    @Expose
    private Item[] inventory;
    @Expose
    private Item[] hotBar;
//    @Expose
//    ArrayList<Block> inventoryBlocks = new ArrayList<>();
//    @Expose
//    ArrayList<Key> inventoryKeys = new ArrayList<>();
//    @Expose
//    ArrayList<Material> inventoryMaterials = new ArrayList<>();
//    @Expose
//    ArrayList<Picaxe> inventoryPicaxes = new ArrayList<>();
//    @Expose
//    ArrayList<Showel> inventoryShowels = new ArrayList<>();
//    @Expose
//    ArrayList<Sword> inventorySwords = new ArrayList<>();
//    @Expose
//    int lastIndex = 0;
    @Expose
    private Item selectetItem = null;
    @Expose
    private String playerImage = "player.png";
    @Expose
    private int xCoords;
    @Expose
    private int yCoords;
    @Expose
    private int HP;
//    @SerializedName("className")
//    private String className = "Player";

    public Player(int xCoords, int yCoords, int HP) {
        this.xCoords = xCoords;
        this.yCoords = yCoords;
        this.HP = HP;
        this.inventory = new Item[32];
        this.hotBar = new Item[10];
    }

    /**
     * Metóda na nastavenie itemu ktorý ma hráč v ruke
     *
     * @param selectetItem item na nastavenie
     */
    public void setSelectetItem(Item selectetItem) {
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
     * @param item
     */
    public void addToInvenory(Item item, int position){
        inventory[position] = item;
    }
//
//    /**
//     * Metóda na pridanie bloku do inventára hráča.
//     *
//     * @param block Blok, ktorý sa má pridať do inventára.
//     */
//    public void addToInvenory(Block block, int position){
//        if (lastIndex > inventory.length) {
//            inventoryBlocks.add(block);
//            inventory[position] = block;
//            lastIndex++;
//        }
//
//    }
//
//    /**
//     * Metóda na pridanie nástroju do inventára hráča.
//     *
//     * @param picaxe Nástroj, ktorý sa má pridať do inventára.
//     */
//    public void addToInvenory(Picaxe picaxe, int position){
//        if (lastIndex > inventory.length) {
//            inventoryPicaxes.add(picaxe);
//            inventory[position] = picaxe;
//            lastIndex++;
//        }
//    }
//
//    public void addToInvenory(Showel showel, int position){
//        if (lastIndex > inventory.length) {
//            inventoryShowels.add(showel);
//            inventory[position] = showel;
//            lastIndex++;
//        }
//
//    }
//
//    public void addToInvenory(Sword sword, int position){
//        if (lastIndex > inventory.length) {
//            inventorySwords.add(sword);
//            inventory[position] = sword;
//            lastIndex++;
//        }
//
//    }
//
//
//    /**
//     * Metóda na pridanie matriálu do inventára hráča.
//     *
//     * @param material Material, ktorý sa má pridať do inventára.
//     */
//    public void addToInvenory(Material material, int position){
//        if (lastIndex > inventory.length) {
//            inventoryMaterials.add(material);
//            inventory[position] = material;
//            lastIndex++;
//        }
//
//    }

    /**
     * Metóda na pridanie položky do HotBaru hráča.
     *
     * @param item Kľúč, ktorý sa má pridať do HotBaru.
     */
    public void addToHotBar(Item item, int position){
        hotBar[position] = item;
    }

//    /**
//     * Metóda na pridanie položky do HotBaru hráča.
//     *
//     * @param block Položka, ktorá sa má pridať do HotBaru.
//     */
//    public void addToHotBar(Block block, int position){
//        hotBar[position] = block;
//    }
//
//    /**
//     * Metóda na pridanie položky do HotBaru hráča.
//     *
//     * @param tool Položka, ktorá sa má pridať do HotBaru.
//     */
//    public void addToHotBar(Tool tool, int position){
//        hotBar[position] = tool;
//    }
//
//    /**
//     * Metóda na pridanie položky do HotBaru hráča.
//     *
//     * @param material Položka, ktorá sa má pridať do HotBaru.
//     */
//    public void addToHotBar(Material material, int position){
//        hotBar[position] = material;
//    }
//
//    /**
//     * Metóda na útok nástrojom.
//     *
//     * @param usedTool Použitý nástroj na útok.
//     */
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
//    public Tool craft(Material[] materials){
//        return null;
//    }

    /**
     * Metóda na pohyb hráča doľava.
     */
    public int[] moveLEFT() {
        xCoords = xCoords - speed;

        int[] coords = new int[]{xCoords, yCoords};

        return coords;

    }

    /**
     * Metóda na pohyb hráča doprava.
     *
     * @return
     */

    public int[] moveRIGHT() {
        xCoords = xCoords + speed;

        int[] coords = new int[]{xCoords, yCoords};

        return coords;
    }

    /**
     * Metóda na skok hráča.
     */
    public int[] moveUP() {
        yCoords = yCoords - speed;
        int[] coords = new int[]{xCoords, yCoords};

        return coords;
    }

    public int[] moveDOWN() {
        yCoords = yCoords + speed;
        int[] coords = new int[]{xCoords, yCoords};

        return coords;

    }
    public String getPlayerImage() {
        return playerImage;
    }

    public int getxCoord() {
        return xCoords;
    }

    public int getyCoord() {
        return yCoords;
    }

    public int getHP() {
        return HP;
    }

    public Item[] getInventory() {
        return inventory;
    }

    public Item[] getHotBar() {
        return hotBar;
    }

    public Item getSelectetItem() {
        return selectetItem;
    }

//    public ArrayList<Block> getInventoryBlocks() {
//        return inventoryBlocks;
//    }
//
//    public ArrayList<Key> getInventoryKeys() {
//        return inventoryKeys;
//    }
//
//    public ArrayList<Material> getInventoryMaterials() {
//        return inventoryMaterials;
//    }
//
//    public ArrayList<Picaxe> getInventoryPicaxes() {
//        return inventoryPicaxes;
//    }
//
//    public ArrayList<Showel> getInventoryShowels() {
//        return inventoryShowels;
//    }
//
//    public ArrayList<Sword> getInventorySwords() {
//        return inventorySwords;
//    }
//
//    public int getLastIndex() {
//        return lastIndex;
//    }


    public int getSpeed() {
        return speed;
    }
}

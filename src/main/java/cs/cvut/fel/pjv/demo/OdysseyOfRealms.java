package cs.cvut.fel.pjv.demo;


import cs.cvut.fel.pjv.demo.controller.Controller;
import cs.cvut.fel.pjv.demo.model.DataSerializer;
import cs.cvut.fel.pjv.demo.model.Model;
import cs.cvut.fel.pjv.demo.view.*;
import cs.cvut.fel.pjv.demo.view.characters.Enemy;
import cs.cvut.fel.pjv.demo.view.characters.NPC;
import cs.cvut.fel.pjv.demo.view.characters.Player;
import cs.cvut.fel.pjv.demo.view.tools.Picaxe;
import cs.cvut.fel.pjv.demo.view.tools.Showel;
import cs.cvut.fel.pjv.demo.view.tools.Sword;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Duration;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;


public class OdysseyOfRealms extends Application {
    private static final Logger LOGGER = Logger.getLogger(OdysseyOfRealms.class.getName());
    private boolean loggingEnabled = true;
    Realm world;
    Player player;
    Model model = new Model();
    DataSerializer serializer = new DataSerializer();
    Controller controller;
    ImageView playerIMG;
    ImageView hotbarFocus;
    ArrayList<ImageView> hotbarNodes = new ArrayList<>();
    ArrayList<ImageView> worldNodes = new ArrayList<>();
    int hotBarNumber = 0;
    boolean isPaused = false;
    int[] backgroundDimensions = new int[2];
    ArrayList<NPC> NPCs = new ArrayList<>();
    ImageView selectedItemView;
    Item resultItem;
    ImageView[] healtImageView = new ImageView[5];


    /**
     * This method places block on screen. Firstly it converts coordination's from class StackPane to my coordinate system
     * where are blocks stored. If calculation is successful, new block will be added to by coordination system. Then it will
     * create new ImageView and set coordinates and place the block to the front.
     * @param block block to place
     * @param root instance of StackPane and the main scene
     * @param xIndex x coordination's of place where the block should be placed
     * @param yIndex y coordination's of place where the block should be placed
     */
    private void addBlockToScreen(Block block, StackPane root, int xIndex, int yIndex) {
        //rounds coords to nearest multiple of 30
        xIndex = 30 * Math.round(xIndex / 30.0f);
        yIndex = 30 * Math.round(yIndex / 30.0f);

        // gets coords for realm system
        int[] coords = model.getCoordsFromScreenToList(xIndex, yIndex, block.getSize(), world);

        if (world.map[coords[0]][coords[1]] != null) {
            return;
        }

        //if block is not null then it will be added to realm map
        world.map[coords[0]][coords[1]] = block;

        //creates new ImageView sets up coords
        Image blockImage = new Image(block.getImagePath());
        ImageView blockImageView = new ImageView(blockImage);
        blockImageView.setTranslateX(xIndex);
        blockImageView.setTranslateY(yIndex);
        root.getChildren().add(blockImageView);
        blockImageView.toFront();

        //sets up coords in block and adds block to world list of blocks
        block.setCoords(coords[0], coords[1]);
        block.isPlaced = true;
        world.addBlock(block);
        worldNodes.add(blockImageView);

        LOGGER.info("Block added to screen to positon" + coords[0] + coords[1]);
    }

    /**
     * When player runs out of HP he will be revived by this method. Firstly it removes player and his ImageView.
     * Then new player will be created in the middle of screen. Last but not least controller will be updated wth new player.
     * @param root main scene
     */
    private void revivePlayer(StackPane root) {
        int[] coords;

        //removes players imageView
        root.getChildren().remove(playerIMG);
        player = null;

        //creates new player and its ImageView
        player = new Player(24, 12, 100);
        player.setPlayerSpeed(1);
        ImageView pImageView = new ImageView("player.png");
        playerIMG = pImageView;

        //calculates coords from world map to screen
        coords = model.getCoordsFromListToScreen(player.getxCoord(), player.getyCoord(), 30, world);

        //sets up screen coords
        playerIMG.setTranslateX(coords[0]);
        playerIMG.setTranslateY(coords[1] - 15);

        root.getChildren().add(playerIMG);

        updateHP(root);

        controller.setPlayer(player);
        fillHotbar(root);

        LOGGER.info("revived player after loosing all his HP");
    }

    /**
     * Method to see the movement of player on the screen. Basically it creates new ImageView to simulate the movement.
     * @param root main scene
     * @param xCoord x coordination where player should go
     * @param yCoords y coordination where player should go
     */
    private void viewPlayer(StackPane root, int xCoord, int yCoords) {

        Image playerImage = new Image(player.getPlayerImage());
        ImageView playerImageView = new ImageView(playerImage);
        playerImageView.setTranslateX(xCoord);
        playerImageView.setTranslateY(yCoords - 15);

        this.playerIMG = playerImageView;
        root.getChildren().add(playerImageView);

        LOGGER.info("changed player's view to" + xCoord + yCoords);
    }

    /**
     * Removes all ImageViews of hearts.
     * @param root main scene
     */
    private void clearHP(StackPane root) {
        for (ImageView imageView: healtImageView) {
            root.getChildren().remove(imageView);
        }

        LOGGER.info("cleared all hearts from screen");
    }

    /**
     * Creates new hearts based on players HP.
     * @param root main scene
     */
    private void updateHP(StackPane root) {
        //clear ImageViews of hearts
        clearHP(root);

        //creates new ImageViews of hearts
        for (int i = 0; i < (player.getHP()/20); i++) {
            ImageView heartImageView = new ImageView("heart.png");

            heartImageView.setTranslateX(-705 + i*30);
            heartImageView.setTranslateY(-390);

            root.getChildren().add(heartImageView);

            healtImageView[i] = heartImageView;

        }

        LOGGER.info("updated hearts based on players HP");
    }

    /**
     * Checks if enemy is not null and if he is not then will remove said enemy.
     * @param enemy enemy to remove
     * @param root main scene
     */

    private void banishEnemy(Enemy enemy, StackPane root) {
        if (enemy == null) {
            return;
        }
        root.getChildren().remove(enemy.getImageView());
        world.mobs.remove(enemy);
        enemy = null;

        LOGGER.info("banished enemy");
    }

    /**
     * Loads overworld from json file. Firstly it loads data by GSON library and the creates a new world.
     * Next it loads all blocks and if the block is special block then loads its inventory. Then loads all mobs.
     * @param root main scene
     * @throws IOException when loading from json fail then it throws this exception
     */
    private void loadOverworldFromSave(StackPane root) throws IOException {
        //deserialized realm from json
        Realm data = serializer.deserializeFromFile("overworld.json", Realm.class);

        //creating new realm
        this.world = new Realm(data.getName(), data.getBossName(), data.getDifficulty(), data.getBackgroundImagePath(), data.getXMaxCoords(), data.getYMaxCoords());
        int[] coords;
        int x;
        int y;

        //loads blocks from realms list of blocks
        for (Block i: data.getBlocks()) {

            if (i.getType().equals("specialBlock")) {
                x = i.getXCoord();
                y = i.getYCoord();

                ArrayList<Item> items = i.getInventory();

                i = new SpecialBlock(false, false, i.getType(), i.getImagePath(), i.getGroup());

                if (items != null) {
                    for (Item item: items) {
                        i.addToInventory(item);
                    }
                }



                i.setCoords(x,y);
            }

            world.addBlock(i);

            world.map[i.getXCoord()][i.getYCoord()] = i;

            int xCoords = i.getXCoord();
            int yCoords = i.getYCoord();

            coords = model.getCoordsFromListToScreen(xCoords, yCoords, i.getSize(), world);

            Image blockImage = new Image(i.getImagePath());
            ImageView blockImageView = new ImageView(blockImage);
            blockImageView.setTranslateX(coords[0]);
            blockImageView.setTranslateY(coords[1]);
            root.getChildren().add(blockImageView);

            worldNodes.add(blockImageView);
        }

        //loads enemies from list of enemies
        for (Enemy enemy: data.mobs) {
            Enemy enemy1 = new Enemy(enemy.getDamage(), enemy.getType(), enemy.getxCoords(), enemy.getyCoords(), enemy.getHP(), enemy.getImagePath());

            ImageView enImageView = new ImageView(enemy1.getImagePath());
            enemy1.setImageView(enImageView);

            coords = model.getCoordsFromListToScreen(enemy1.getxCoords(), enemy1.getyCoords(), 30, world);

            enImageView.setTranslateX(coords[0]);
            enImageView.setTranslateY(coords[1] - 15);

            root.getChildren().add(enImageView);

            world.addMob(enemy1);

        }

        LOGGER.info("loaded overworld successfully");
    }

    /**
     * This method loads player from json file. For each item checks its group and based on the group creates new item
     * by using json pointer.
     * @param root main scene
     * @throws IOException when loading form json fails then it throws this exception
     */
    private void loadPlayer(StackPane root) throws IOException {
        int[] coords;
        Item[] inventory;
        Item item;

        //creates new ObjectMapper and JsonNode
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(Files.newBufferedReader(Paths.get("player.json")));

        //deserialize players data
        Player playerData = serializer.deserializeFromFile("player.json", Player.class);

        //creates new player
        Player loadedPlayer = new Player(playerData.getxCoord(), playerData.getyCoord(), playerData.getHP());
        coords = model.getCoordsFromListToScreen(loadedPlayer.getxCoord(), loadedPlayer.getyCoord(), 30, world);

        inventory = playerData.getHotBar();

        //fills up inventory
        for (int i = 0; i < inventory.length; i++) {
            item = inventory[i];
            if (item == null) {
                continue;
            }

            //based on items group new item will be created
            switch (item.getGroup()) {
                case "Block":
                    //json pointer to specific data of item
                    boolean isCraftable = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/isCraftable")).asBoolean();
                    boolean canFall = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/canFall")).asBoolean();
                    String typeBL = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/type")).asText();
                    String imagePathBL = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/imagePath")).asText();
                    String groupBL = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/group")).asText();

                    if (typeBL.equals("specialBlock")) {
                        SpecialBlock block = new SpecialBlock(isCraftable,canFall, typeBL, imagePathBL, groupBL);
                        loadedPlayer.addToHotBar(block, i);
                    } else {
                        Block block = new Block(isCraftable, canFall, typeBL, imagePathBL, groupBL);
                        loadedPlayer.addToHotBar(block, i);
                    }

                    break;

                case "Key":
                    String typeKey = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/type")).asText();
                    String groupKey = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/group")).asText();
                    String imagePathKey = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/imagePath")).asText();

                    Key key = new Key(typeKey, groupKey, imagePathKey);

                    loadedPlayer.addToHotBar(key, i);

                    break;

                case "Material":
                    String typeMT = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/type")).asText();
                    String groupMT = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/group")).asText();
                    String imagePathMT = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/imagePath")).asText();

                    Material material = new Material(typeMT, groupMT, imagePathMT);

                    loadedPlayer.addToHotBar(material, i);

                    break;
                case "Sword":
                    String typeSW = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/type")).asText();
                    String groupSW = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/group")).asText();
                    String imagePathSW = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/imagePath")).asText();

                    Sword sword = new Sword(imagePathSW, groupSW, typeSW);

                    loadedPlayer.addToHotBar(sword, i);

                    break;

                case "Picaxe":
                    String typePI = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/type")).asText();
                    String groupPI = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/group")).asText();
                    String imagePathPI = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/imagePath")).asText();

                    Picaxe picaxe = new Picaxe(imagePathPI, groupPI, typePI);

                    loadedPlayer.addToHotBar(picaxe, i);

                    break;

                case "Showel":
                    String typeSH = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/type")).asText();
                    String groupSH = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/group")).asText();
                    String imagePathSH = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/imagePath")).asText();

                    Showel showel = new Showel(imagePathSH, groupSH, typeSH);

                    loadedPlayer.addToHotBar(showel, i);

                    break;

                default:
                    break;
            }


        }

        loadedPlayer.setPlayerSpeed(playerData.getSpeed());
        loadedPlayer.setSelectetItem(loadedPlayer.getHotBar()[0]);

        //new players ImageView
        ImageView playerImageView = new ImageView(loadedPlayer.getPlayerImage());
        playerImageView.setTranslateX(coords[0]);
        playerImageView.setTranslateY(coords[1] - 15);
        root.getChildren().add((playerImageView));

        this.playerIMG = playerImageView;
        this.player = loadedPlayer;

        updateHP(root);

        LOGGER.info("loaded player successfully");
    }

    /**
     * Loads a NPC and ads a riddle in this case a tutorial. In a standard way it loads NPC to screen.
     * @param root main scene
     */
    private void loadNPC(StackPane root) {
        int[] coords = new int[2];

        //creates new NPC and sets up text which is show when interacted with
        NPC NPC = new NPC("","Player's Helper", 30, 24, "npc_helper.png");
        NPC.setRiddle("Tutorial: to move use A or D, to go over block use W + A, to jump over use SPACE + A + A. " +
                "To select item on your hot bar press any number. " +
                "To place a block form your hot bar select a block and right-click where you want " +
                "to place a block, left-click on block you desire to remove. " +
                "To craft something you need to stay near crafting table and press E. " +
                "Then place correct combination of materials to crafting table and press ENTER. " +
                "If you are successful new item will appear on the empty bottom slot where you wil be able to right-click said item " +
                "to claim it. " +
                "To escape from crafting table press ESCAPE");

        Image NPCimage = new Image(NPC.getImg());
        ImageView NPCimageView = new ImageView(NPCimage);

        NPC.setImageView(NPCimageView);

        coords = model.getCoordsFromListToScreen(NPC.getxCoords(), NPC.getyCoords(), 30, world);

        NPCimageView.setTranslateX(coords[0]);
        NPCimageView.setTranslateY(coords[1] - 15);

        root.getChildren().add(NPCimageView);

        //creates new label of NPC's head to let player know how to interact
        Label pressELabel = new Label("Press E");

        pressELabel.setFont(new Font("Arial", 16));

        pressELabel.setTranslateX(NPCimageView.getTranslateX());
        pressELabel.setTranslateY(NPCimageView.getTranslateY() - 50);

        pressELabel.setTextFill(Color.valueOf("Black"));

        root.getChildren().add(pressELabel);

        NPCs.add(NPC);

        LOGGER.info("loaded NPC successfully");
    }

    /**
     * Clears all ImageViews in hot bar.
     * @param root main scene
     */
    private void clearHotbar(StackPane root) {
        for (ImageView node : hotbarNodes) {
            root.getChildren().remove(node);
        }
        hotbarNodes.clear();

        LOGGER.info("cleared images from hot bar");
    }

    /**
     * Shows how much is players hot bar full. For every item in hot bar creates new ImageView.
     * @param root main scene
     */
    private void fillHotbar(StackPane root) {
        String texture;
        int count = -200;

        clearHotbar(root);

        //creates new ImageViews for each item in hot bar
        for (Item item : player.getHotBar()) {
            if (item == null) {
                count = count + 50;
                continue;
            }
            texture = item.getImagePath();
            Image block = new Image(texture);
            ImageView blockView = new ImageView(block);

            root.getChildren().add(blockView);

            blockView.setTranslateX(count);
            blockView.setTranslateY(381);

            blockView.toFront();
            count = count + 50;

            hotbarNodes.add(blockView);
        }

        LOGGER.info("filled hot bar with images of player's hot bar");
    }

    /**
     * When player interacts with NPC this method shows bubble of NPC's riddle.
     * @param npc NPC to engage
     * @param root main scene
     */
    private void showTextBubble(NPC npc, StackPane root) {
        String text = npc.getRiddle();
        Label textLabel = new Label(text);
        Insets insets = new Insets(10,10,10,10);
        Font font = new Font("Arial", 14);

        //styles for bubble
        textLabel.setPadding(insets);
        textLabel.setFont(font);
        textLabel.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-radius: 10; -fx-background-radius: 10;");
        textLabel.setWrapText(true);

        textLabel.setMinWidth(100);
        textLabel.setMaxWidth(300);

        //position set up
        double x = npc.getImageView().getTranslateX();
        double y = npc.getImageView().getTranslateY() - npc.getImageView().getBoundsInParent().getHeight() - 30;

        textLabel.setTranslateX(x);
        textLabel.setTranslateY(y);

        root.getChildren().add(textLabel);

        PauseTransition pause = new PauseTransition(Duration.seconds(30));
        pause.setOnFinished(event -> root.getChildren().remove(textLabel));
        pause.play();

        LOGGER.info("showed a text bubble");
    }

    /**
     * Saves whole game with GSON library.
     * @throws IOException when writing to json fails
     */
    private void saveGame() throws IOException {
        serializer.serializeToFile(world, "overworld.json");
        serializer.serializeToFile(player, "player.json");

        LOGGER.info("saved game");
    }

    /**
     * Method that combines each loading part of the game.
     * @param root main scene
     * @throws IOException when loading form json fails
     */
    private void loadGame(StackPane root) throws IOException {
        loadOverworldFromSave(root);
        loadPlayer(root);
        loadNPC(root);

        Image hotbarImage = new Image("hotbar.png");
        ImageView hotbarImageView = new ImageView(hotbarImage);
        root.getChildren().add(hotbarImageView);
        root.setAlignment(hotbarImageView, Pos.BOTTOM_CENTER);

        fillHotbar(root);

        Image selector = new Image("selector.png");
        ImageView selectorView = new ImageView(selector);
        root.getChildren().add(selectorView);

        selectorView.setTranslateX(-200);
        selectorView.setTranslateY(381);

        selectorView.toFront();

        this.hotbarFocus = selectorView;

        LOGGER.info("loaded whole game");
    }

    /**
     * Method that checks if is a block under a player and let player fall till there is block beneath.
     * @param root main scene
     */
    private void isFalling(StackPane root) {
        int[] coords;
        while (!model.isBlockUnder(world, player)) {
            root.getChildren().remove(playerIMG);
            coords = controller.moveDown();
            viewPlayer(root, coords[0], coords[1]);
        }

        LOGGER.info("player just falled");
    }

    /**
     * When player selects a new item in hot bar this will move the focus rectangle to correct place on hot bar.
     * @param root main scene
     * @param hotNum number which is selected on hot bar
     */
    private void setFocusOnHotbar(StackPane root, int hotNum) {
        root.getChildren().remove(hotbarFocus);

        hotbarFocus.setTranslateX(-200 + (50 * hotNum));
        hotbarFocus.setTranslateY(381);

        root.getChildren().add(hotbarFocus);

        player.setSelectetItem(player.getHotBar()[hotNum]);

        this.hotBarNumber = hotNum;

        LOGGER.info("new focus is set on hot bar");
    }

    /**
     * When player selects item on hot bar this will change cursor to further show player which item did he chose.
     * @param root main scene
     */
    private void setSelectedItemIcon(StackPane root) {
        if (player.getSelectetItem() == null) {
            root.setCursor(Cursor.DEFAULT);
            player.setSelectetItem(null);
            return;
        }

        Image image = new Image(player.getSelectetItem().getImagePath());
        ImageView imageView = new ImageView(image);

        root.setCursor(new ImageCursor(image));

        selectedItemView = imageView;

        LOGGER.info("new cursor is set");
    }

    /**
     * This method will count how many different materials are needed for recipe.
     * @param recipe recipe to chceck
     * @return return how many different materials are needed for recipe
     */
    public int countDifferentMaterials(Recipes recipe) {
        int count = 0;
        Map<Item, Integer> ingredients = recipe.getIngredients();

        for (Item ingredient : ingredients.keySet()) {
            count++;
        }

        LOGGER.info("counted how many different materials are there");
        return count;
    }

    /**
     * When there are some items in crafting table, this method will check if there is suitable recipe. It uses different
     * checkmarks to not use wrong recipe.
     * @param specialBlock crafting table
     * @return item which is outcome of the recipe
     */
    public Item checkForRecipe(SpecialBlock specialBlock) {
        //list of booleans tha needs to be true to find the correct recipe
        boolean firstCheckmark = false;
        boolean secondCheckemark = false;
        Boolean[] checkmarks = new Boolean[]{firstCheckmark, secondCheckemark};
        int count = 0;
        boolean isMoreThanOne = false;
        Item temp = null;

        //checks if there are two or more same materials
        for (Item item: specialBlock.getInventory()) {
            if (temp != null && temp.getImagePath() != item.getImagePath()){
                isMoreThanOne = true;
            }

            temp = item;
        }

        //lists through all recipes
        for (Recipes recipe : Recipes.values()) {
            count = 0;
            checkmarks[0] = false;
            checkmarks[1] = false;
            if (countDifferentMaterials(recipe) <= 1 && !isMoreThanOne){
                checkmarks[1] = true;
            }


            for (Map.Entry<Item, Integer> entry : recipe.getIngredients().entrySet()) {

                Item ingredient = entry.getKey();
                int requiredCount = entry.getValue();
                int actualCount = countItem(specialBlock.getInventory(), ingredient);
                if (actualCount == requiredCount) {
                    checkmarks[count] = true;
                    count++;
                }
            }

            //if both checkmarks are true return recipe
            if (checkmarks[0] && checkmarks[1]) {
                return recipe.getResult();
            }
        }


        LOGGER.info("checked if there is suitable recipe");
        return null;
    }

    /**
     * This should fill chest based on its inventory. WIP.
     * @param root main scene
     * @param specialBlock chest to fill
     */
    public void fillChest(StackPane root, SpecialBlock specialBlock) {
        int count = 0;
        int x = (0 - (3 * 50) + 25);
        int y = -200;

        for (int i = 0; i < specialBlock.getInventory().size(); i++) {
            if (count == 6) {
                count = 0;
                y = y + 50;
            }
            ImageView imageView = new ImageView(specialBlock.getInventory().get(i).getImagePath());

            imageView.setTranslateX(x + (count * 50));
            imageView.setTranslateY(y);

            specialBlock.getInventory().get(i).setSceneYCoord(x + (count * 50));
            specialBlock.getInventory().get(i).setSceneYCoord(y);

            imageView.setId("chestItem");

            root.getChildren().add(imageView);

            count++;
        }

        LOGGER.info("filled chest");
    }

    /**
     * Counts how many of the same items are in crafting table's inventory.
     * @param items list of items (crafting table's inventory)
     * @param item item to compare with
     * @return count of how many are there same items
     */
    private int countItem(List<Item> items, Item item) {
        int count = 0;
        for (Item currentItem : items) {
            if (currentItem.getImagePath().equals(item.getImagePath())) {
                count++;
            }
        }

        LOGGER.info("counted how many material are same");
        return count;
    }

    /**
     * Makes interface for special block. Creates empty slots where player can place items.
     * @param root main scene
     * @param numberOfSlots how many slot are needed
     */
    public void fillEmptySlots(StackPane root, double numberOfSlots){
        //square root of number of slots so its always square
        numberOfSlots = Math.sqrt(numberOfSlots);
        int x = (int) (0 - ((numberOfSlots/2) * 50) + 25);
        int y = -200;

        //creating empty slots
        for (int i = 0; i < numberOfSlots; i++) {
            for (int j = 0; j < numberOfSlots; j++) {

                ImageView emptySlotView = new ImageView("empty_slot.png");

                emptySlotView.setFitWidth(50);
                emptySlotView.setFitHeight(50);

                emptySlotView.setTranslateX(x + (j * 50));
                emptySlotView.setTranslateY(y + (i * 50));

                emptySlotView.setId("emptySlot");

                root.getChildren().add(emptySlotView);

            }

        }

        LOGGER.info("made empty slots for special block interface");
    }

    /**
     * Puts whole crafting interface together. If it is a crafting table creates extra slot for output.
     * Also uses two event handlers to interact with crafting table.
     * @param root main scene
     * @param specialBlock special block that is interacted with
     */
    public void showCraftingGUI(StackPane root, SpecialBlock specialBlock) {
        int slots;
        String specialBlockImagePath = specialBlock.getImagePath();
        ImageView resultSlotImageView = new ImageView("empty_slot.png");


        switch (specialBlockImagePath) {
            case "crafting_table.png":
                slots = 9;

                // creates spare empty slot for result item
                resultSlotImageView.setFitHeight(50);
                resultSlotImageView.setFitWidth(50);

                resultSlotImageView.setTranslateX(0);
                resultSlotImageView.setTranslateY(0);

                resultSlotImageView.setId("resultSlot");

                root.getChildren().add(resultSlotImageView);

                worldNodes.add(resultSlotImageView);

                fillEmptySlots(root, slots);
                break;

//                TODO
            case "chest.png":
                slots = 36;
                fillEmptySlots(root, slots);
                fillChest(root, specialBlock);

        }

        LOGGER.info("showed special block interface");

        root.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                //enables player to place a material in crafting
                LOGGER.info("primary button was pressed on mause");
                double sceneX = event.getX();
                double sceneY = event.getY();

                //rounds players click to multiply of 50
                sceneX = sceneX - (backgroundDimensions[0]/2);
                sceneY = sceneY - (backgroundDimensions[1]/2);

                sceneX = 50 * Math.round(sceneX / 50.0f);
                sceneY = 50 * Math.round(sceneY / 50.0f);

                if (specialBlockImagePath.equals("chest.png")) {
                    sceneX = sceneX + 25;
                }

                if (player.getSelectetItem() != null) {
                    //process of inserting a material in crafting
                    Item item = player.getSelectetItem();

                    item.setSceneXCoord((int) sceneX);
                    item.setSceneYCoord((int) sceneY);

                    player.addToHotBar(null, hotBarNumber);
                    fillHotbar(root);

                    ImageView itemImageView = new ImageView(item.getImagePath());

                    itemImageView.setTranslateX(sceneX);
                    itemImageView.setTranslateY(sceneY);

                    itemImageView.setId("usedItem");

                    root.getChildren().add(itemImageView);

                    root.setCursor(Cursor.DEFAULT);

                    specialBlock.addToInventory(item);

                    player.setSelectetItem(null);

                }
                LOGGER.info("added item to crafting");
            }
            if (event.getButton() == MouseButton.SECONDARY && specialBlockImagePath.equals("crafting_table.png")) {
                LOGGER.info("secondary mose button was pressed");

                //enables player to collect new item
                ImageView resultImageView = null;
                double sceneX = event.getX();
                double sceneY = event.getY();

                sceneX = sceneX - (backgroundDimensions[0]/2);
                sceneY = sceneY - (backgroundDimensions[1]/2);

                sceneX = 50 * Math.round(sceneX / 50.0f);
                sceneY = 50 * Math.round(sceneY / 50.0f);

                for (ImageView imageView: worldNodes) {
                    if (imageView.getId() != null) {
                        if (imageView.getId().equals("itemResult")) {
                            resultImageView = imageView;
                            break;
                        }
                    }
                }

                //based on group creates new item
                switch (resultItem.getGroup()) {
                    case "Block":
                        resultItem = new Block(true, false, resultItem.getType(), resultItem.getImagePath(), resultItem.getGroup());
                        break;
                    case "Picaxe":
                        resultItem = new Picaxe(resultItem.getImagePath(), resultItem.getGroup(), resultItem.getType());
                        break;
                    case "Showel":
                        resultItem = new Showel(resultItem.getImagePath(), resultItem.getGroup(), resultItem.getType());
                        break;
                    case "Sword":
                        resultItem = new Sword(resultItem.getImagePath(), resultItem.getGroup(), resultItem.getType());

                }

                //checks if hot bar is empty
                if ((sceneX == 0 && sceneY == 0) && resultImageView != null) {
                    for (int i = 0; i < player.getHotBar().length; i++) {
                        if (player.getHotBar()[i] == null){
                            player.addToHotBar(resultItem, i);
                            break;
                        }
                    }
                }

                fillHotbar(root);

                root.getChildren().remove(resultImageView);
                worldNodes.remove(resultImageView);

                LOGGER.info("player took new item from crafting");
            }
            if (event.getButton() == MouseButton.SECONDARY && specialBlockImagePath.equals("chest.png")) {
                ImageView itemImageView = null;
                Item desiredItem = null;

                double sceneX = event.getX();
                double sceneY = event.getY();

                sceneX = sceneX - (backgroundDimensions[0]/2);
                sceneY = sceneY - (backgroundDimensions[1]/2);

                sceneX = 50 * Math.round(sceneX / 50.0f);
                sceneY = 50 * Math.round(sceneY / 50.0f);

                for (Item item: specialBlock.getInventory()) {
                    if (item == null) {
                        continue;
                    }
                    if (item.getSceneXCoord() == (sceneX + 25) && item.getSceneYCoord() == sceneY) {
                        desiredItem = item;
                    }
                }

                for (Node imageView: root.getChildren()) {
                    if (imageView.getId() != null) {
                        if (imageView.getId().equals("chestItem") && imageView.getTranslateY() == sceneY && imageView.getTranslateX() == sceneX) {
                            itemImageView = (ImageView) imageView;
                        }
                    }
                }

                root.getChildren().remove(itemImageView);
                specialBlock.getInventory().remove(desiredItem);

                for (int i = 0; i < player.getHotBar().length; i++) {
                    if (player.getHotBar()[i] == null) {
                        player.addToHotBar(desiredItem, i);
                        break;
                    }
                }

                fillHotbar(root);

            }
        });

        // Closing special block interface on escape
        root.getScene().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE && specialBlockImagePath.equals("crafting_table.png")) {
                specialBlock.getInventory();
                int count = 0;

                root.getChildren().remove(resultSlotImageView);

                //Iterator so items can be deleted during iteration
                Iterator<ImageView> worldNodesIterator = worldNodes.iterator();
                while (worldNodesIterator.hasNext()) {
                    ImageView imageView = worldNodesIterator.next();
                    if (imageView.getId() != null) {
                        if (imageView.getId().equals("resultSlot") || imageView.getId().equals("emptySlot") || imageView.getId().equals("usedItem")) {
                            worldNodesIterator.remove();
                        }
                    }
                }

                Iterator<Node> rootChildrenIterator = root.getChildren().iterator();
                while (rootChildrenIterator.hasNext()) {
                    Node node = rootChildrenIterator.next();
                    if (node.getId() != null) {
                        if (node.getId().equals("resultSlot") || node.getId().equals("emptySlot") || node.getId().equals("usedItem")) {
                            rootChildrenIterator.remove();
                        }
                    }
                }

                //checks if hot bar has enough space
                for (int i = 0; i < player.getHotBar().length; i++) {
                    if (count >= specialBlock.getInventory().size()) {
                        break;
                    }

                    Item item = specialBlock.getInventory().get(count);

                    if (player.getHotBar()[i] == null) {
                        player.addToHotBar(item, i);
                        count++;
                    }

                }

                fillHotbar(root);

                specialBlock.getInventory().clear();

                root.setOnMouseClicked(null);
                LOGGER.info("player exited crafting");
            }
            if (event.getCode() == KeyCode.ESCAPE && specialBlockImagePath.equals("chest.png")) {
                Iterator<Node> rootChildrenIterator = root.getChildren().iterator();
                while (rootChildrenIterator.hasNext()) {
                    Node node = rootChildrenIterator.next();
                    if (node.getId() != null) {
                        if (node.getId().equals("emptySlot") || node.getId().equals("usedItem")) {
                            rootChildrenIterator.remove();
                        }
                    }
                }

            }
            if (event.getCode() == KeyCode.ENTER && specialBlockImagePath.equals("crafting_table.png")) {
                Item item = checkForRecipe(specialBlock);

                //creates new item
                if (item != null) {

                    resultItem = item;

                    ImageView imageView = new ImageView(item.getImagePath());
                    imageView.setTranslateX(0);
                    imageView.setTranslateY(0);

                    imageView.setId("itemResult");

                    root.getChildren().add(imageView);
                    worldNodes.add(imageView);

                    specialBlock.getInventory().clear();



                }
                LOGGER.info("player crafted an item");
            }
        });
    }

    private void toggleLogging() {
        loggingEnabled = !loggingEnabled;
        if (loggingEnabled) {
            LOGGER.setLevel(Level.INFO);
            LOGGER.info("Logging enabled");
        } else {
            LOGGER.info("Logging disabled");
            LOGGER.setLevel(Level.OFF);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {

        Image backgroundImage = new Image("overworld_background.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);

        StackPane root = new StackPane(backgroundImageView);

        backgroundDimensions[0] = (int) backgroundImage.getWidth();
        backgroundDimensions[1] = (int) backgroundImage.getHeight();



        Scene scene = new Scene(root, 1440,810);


        loadGame(root);



        stage.setTitle("Odyssey Of Realms");
        stage.setScene(scene);
        stage.show();

        controller = new Controller(player, model, world);

        //transition so player falls after jumping
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.setOnFinished(jump -> {
            root.getChildren().remove(playerIMG);
            int[] finalCoords = controller.moveDown();
            viewPlayer(root, finalCoords[0], finalCoords[1]);
            isPaused = false;
        });

        //transition so player falls after moving
        PauseTransition fall = new PauseTransition(Duration.seconds(0.3));
        fall.setOnFinished(event -> {
            isFalling(root);
            player.setMoving(false);
        });

        /**
         * one of two main event handlers
         * used for moving the player
         */
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int[] coords;
                switch (event.getCode()) {
                    case A:
                        LOGGER.info("A was pressed");
                        if (player.getHP() <= 0) {
                            revivePlayer(root);
                            return;
                        }

                        player.setDirection(Direction.LEFT);
                        player.setMoving(true);

                        if (model.isCollision(world, player)) {
                            break;
                        }

                        //moves player visually
                        root.getChildren().remove(playerIMG);
                        coords = controller.moveLeft();
                        viewPlayer(root, coords[0], coords[1]);

                        fall.play();

                        //if player is near enemies he will take damage
                        for (Enemy enemy: world.mobs) {
                            if (model.isNearObject(player, enemy.getxCoords(), enemy.getyCoords())) {
                                enemy.attack(player);
                                updateHP(root);
                                LOGGER.info("player took damage from enemy");
                            }
                        }

                        LOGGER.info("player moved left");
                        break;
                    case D:
                        LOGGER.info("D was pressed");
                        if (player.getHP() <= 0) {
                            revivePlayer(root);
                            return;
                        }
                        player.setDirection(Direction.RIGHT);
                        player.setMoving(true);

                        if (model.isCollision(world, player)) {
                            break;
                        }

                        //moves player visually
                        root.getChildren().remove(playerIMG);
                        coords = controller.moveRight();
                        viewPlayer(root, coords[0], coords[1]);

                        fall.play();

                        //if player is near enemies he will take damage
                        for (Enemy enemy: world.mobs) {
                            if (model.isNearObject(player, enemy.getxCoords(), enemy.getyCoords())) {
                                enemy.attack(player);
                                updateHP(root);
                                LOGGER.info("player took damage from enemy");
                            }
                        }
                        LOGGER.info("player moved right");
                        break;
                    case SPACE:
                        LOGGER.info("SPACE was pressed");
                        if (player.getHP() <= 0) {
                            revivePlayer(root);

                            return;
                        }
                        player.setDirection(Direction.JUMP);

                        if (model.isCollision(world, player)) {
                            break;
                        }

                        //inverts isPaused so user can not spam
                        if (!isPaused) {
                            root.getChildren().remove(playerIMG);
                            coords = controller.moveUp();
                            viewPlayer(root, coords[0], coords[1]);
                            player.setMoving(true);
                            if (model.isCollision(world, player)) {
                                isPaused = false;
                                player.setMoving(false);
                                break;
                            }
                            isPaused = true;
                            pause.play();
                            player.setMoving(false);
                        }
                        LOGGER.info("player jumped");
                        break;
                    case W:
                        LOGGER.info("W was pressed");

                        if (player.getHP() <= 0) {
                            revivePlayer(root);
                            return;
                        }
                        root.getChildren().remove(playerIMG);
                        coords = controller.moveUp();
                        viewPlayer(root, coords[0], coords[1]);

                        break;
                    case E:
                        //set up necessary variables
                        NPC npc = NPCs.get(0);
                        String riddle = controller.interactWithObject(npc);
                        SpecialBlock nearestSpecialBlock = world.findNearestSpecialBlock(player);
                        Item item = controller.interactWithObject(nearestSpecialBlock);

                        if (!riddle.isEmpty() && !isPaused) {
                            isPaused = true;
                            showTextBubble(npc, root);
                        }

                        if (model.isNearObject(player, nearestSpecialBlock.getXCoord(), nearestSpecialBlock.getYCoord()) && !isPaused) {
                            isPaused = true;
                            showCraftingGUI(root, nearestSpecialBlock);
                        }

                        isPaused = false;

                        LOGGER.info("player moved up");
                        break;
                    case DIGIT1:
                        LOGGER.info("1 was pressed");
                        setFocusOnHotbar(root, 0);
                        setSelectedItemIcon(root);
                        break;
                    case DIGIT2:
                        LOGGER.info("2 was pressed");
                        setFocusOnHotbar(root, 1);
                        setSelectedItemIcon(root);
                        break;
                    case DIGIT3:
                        LOGGER.info("3 was pressed");
                        setFocusOnHotbar(root, 2);
                        setSelectedItemIcon(root);
                        break;
                    case DIGIT4:
                        LOGGER.info("4 was pressed");
                        setFocusOnHotbar(root, 3);
                        setSelectedItemIcon(root);
                        break;
                    case DIGIT5:
                        LOGGER.info("5 was pressed");
                        setFocusOnHotbar(root, 4);
                        setSelectedItemIcon(root);
                        break;
                    case DIGIT6:
                        LOGGER.info("6 was pressed");
                        setFocusOnHotbar(root, 5);
                        setSelectedItemIcon(root);
                        break;
                    case DIGIT7:
                        LOGGER.info("7 was pressed");
                        setFocusOnHotbar(root, 6);
                        setSelectedItemIcon(root);
                        break;
                    case DIGIT8:
                        LOGGER.info("8 was pressed");
                        setFocusOnHotbar(root, 7);
                        setSelectedItemIcon(root);
                        break;
                    case DIGIT9:
                        LOGGER.info("9 was pressed");
                        setFocusOnHotbar(root, 8);
                        setSelectedItemIcon(root);
                        break;
                    case DIGIT0:
                        LOGGER.info("0 was pressed");
                        root.setCursor(Cursor.DEFAULT);
                        player.setSelectetItem(null);
                    case L:
                        toggleLogging();
                    default:

                }
            }
        });

        /**
         * second main event handler
         * used for building, removing blocks and fighting
         */
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                boolean checker = false;
                if (player.getSelectetItem() != null){
                    if (player.getSelectetItem().getGroup().equals("Sword")) {
                        checker = true;
                    }
                }
                if (event.getButton() == MouseButton.SECONDARY && player.getSelectetItem() != null && "Block".equals(player.getSelectetItem().getGroup())) {

                    //places block from hot bar
                    double sceneX = event.getX();
                    double sceneY = event.getY();

                    sceneX = sceneX - (backgroundDimensions[0]/2);
                    sceneY = sceneY - (backgroundDimensions[1]/2);

                    Block block = (Block) player.getSelectetItem();

                    if (block.getType().equals("specialBlock")) {
                        block = new SpecialBlock(block.isCraftable(), block.isCanFall(), block.getType(), block.getImagePath(), block.getGroup());
                    }

                    addBlockToScreen(block, root,(int) sceneX, (int) sceneY);

                    player.setSelectetItem(null);
                    player.addToHotBar(null, hotBarNumber);
                    fillHotbar(root);
                    root.setCursor(Cursor.DEFAULT);

                    LOGGER.info("a block was built");
                }
                if (event.getButton() == MouseButton.PRIMARY && checker) {
                    //if player has sword in hand he wil attack
                    Enemy enemyToKill = null;

                    for (Enemy enemy: world.mobs) {
                        if (model.isNearObject(player, enemy.getxCoords(), enemy.getyCoords())) {
                            player.punch(enemy);
                            if (enemy.getHP() <= 0) {
                                enemyToKill = enemy;

                            }
                        }
                    }

                    banishEnemy(enemyToKill, root);

                    LOGGER.info("player have attacked an enemy");
                }
                if (event.getButton() == MouseButton.PRIMARY && !checker) {
                    //enables player to destroy block
                    double sceneX = event.getX();
                    double sceneY = event.getY();

                    sceneX = sceneX - (backgroundDimensions[0]/2);
                    sceneY = sceneY - (backgroundDimensions[1]/2);

                    sceneX = 30 * Math.round(sceneX / 30.0f);
                    sceneY = 30 * Math.round(sceneY / 30.0f);


                    int[] coords = model.getCoordsFromScreenToList((int) sceneX, (int) sceneY, 30, world);

                    if (world.map[coords[0]][coords[1]] == null) {
                        return;
                    }

                    Block block = world.map[coords[0]][coords[1]];
                    if (controller.removeBlock(block)) {
                        for (ImageView imageView: worldNodes) {
                            double indeX = imageView.getTranslateX();
                            double indexY = imageView.getTranslateY();

                            if (indeX == sceneX && indexY == sceneY){
                                root.getChildren().remove(imageView);

                                fillHotbar(root);

                                if (worldNodes.remove(imageView)){

                                    world.map[coords[0]][coords[1]] = null;
                                    world.removeBlock(block);
                                }

                                break;
                            }
                        }
                    }

                    LOGGER.info("a block was destroyed");
                }
            }
        });
    }


    public void stop() throws IOException {
        //after game is closed everything will be saved
        saveGame();
    }

    public static void main(String[] args) {
        LoggerSetup.setupLogger(LOGGER);
        launch();
    }
}
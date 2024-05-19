package cs.cvut.fel.pjv.demo;


import cs.cvut.fel.pjv.demo.controller.Controller;
import cs.cvut.fel.pjv.demo.model.DataSerializer;
import cs.cvut.fel.pjv.demo.model.Model;
import cs.cvut.fel.pjv.demo.view.*;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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

import javafx.scene.control.Label;
import javafx.scene.text.Text;


public class OdysseyOfRealms extends Application {
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

    /**
     * adds block to screen and to the realms map
     * @param block desired block
     * @param root
     * @param xIndex x coords of the block on scene
     * @param yIndex y coords of the block on sceneq
     */
    private void addBlockToScreen(Block block, StackPane root, int xIndex, int yIndex) {
        xIndex = 30 * Math.round(xIndex / 30.0f);
        yIndex = 30 * Math.round(yIndex / 30.0f);

        int[] coords = model.getCoordsFromScreenToList(xIndex, yIndex, block.getSize(), world);

        if (world.map[coords[0]][coords[1]] != null) {
            return;
        }

        world.map[coords[0]][coords[1]] = block;

        Image blockImage = new Image(block.getImagePath());
        ImageView blockImageView = new ImageView(blockImage);
        blockImageView.setTranslateX(xIndex);
        blockImageView.setTranslateY(yIndex);
        root.getChildren().add(blockImageView);
        blockImageView.toFront();

        block.setCoords(coords[0], coords[1]);
        block.isPlaced = true;
        world.addBlock(block);
        worldNodes.add(blockImageView);
    }

    private void viewPlayer(StackPane root, int xCoord, int yCoords) {

        Image playerImage = new Image(player.getPlayerImage());
        ImageView playerImageView = new ImageView(playerImage);
        playerImageView.setTranslateX(xCoord);
        playerImageView.setTranslateY(yCoords - 15);

        this.playerIMG = playerImageView;
        root.getChildren().add(playerImageView);
    }

    private void loadOverworldFromSave(StackPane root) throws IOException {
        Realm data = serializer.deserializeFromFile("overworld.json", Realm.class);
        this.world = new Realm(data.getName(), data.getBossName(), data.getDifficulty(), data.getBackgroundImagePath(), data.getXMaxCoords(), data.getYMaxCoords());
        int[] coords;
        int x;
        int y;

        for (Block i: data.getBlocks()) {

            if (i.getType().equals("specialBlock")) {
                x = i.getXCoord();
                y = i.getYCoord();

                i = new SpecialBlock(false, false, i.getType(), i.getImagePath(), i.getGroup());

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

    }

    private void loadPlayer(StackPane root) throws IOException {
        int[] coords;
        Item[] inventory;
        Item item;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(Files.newBufferedReader(Paths.get("player.json")));

        Player playerData = serializer.deserializeFromFile("player.json", Player.class);


        Player loadedPlayer = new Player(playerData.getxCoord(), playerData.getyCoord(), playerData.getHP());
        coords = model.getCoordsFromListToScreen(loadedPlayer.getxCoord(), loadedPlayer.getyCoord(), 30, world);

        inventory = playerData.getHotBar();

        for (int i = 0; i < inventory.length; i++) {
            item = inventory[i];
            if (item == null) {
                continue;
            }

            switch (item.getGroup()) {
                case "Block":
                    boolean isCraftable = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/isCraftable")).asBoolean();
//                    JsonPointer xCoord =JsonPointer.compile("/inventory/" + i + "/xCoords");
//                    JsonPointer yCoord =JsonPointer.compile("/inventory/" + i + "/yCoords");
//                    JsonPointer size =JsonPointer.compile("/inventory/" + i + "/size");
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

                    Sword sword = new Sword(typeSW, groupSW, imagePathSW);

                    loadedPlayer.addToHotBar(sword, i);

                    break;

                case "Picaxe":
                    String typePI = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/type")).asText();
                    String groupPI = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/group")).asText();
                    String imagePathPI = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/imagePath")).asText();

                    Picaxe picaxe = new Picaxe(typePI, groupPI, imagePathPI);

                    loadedPlayer.addToHotBar(picaxe, i);

                    break;

                case "Showel":
                    String typeSH = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/type")).asText();
                    String groupSH = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/group")).asText();
                    String imagePathSH = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/imagePath")).asText();

                    Showel showel = new Showel(typeSH, groupSH, imagePathSH);

                    loadedPlayer.addToHotBar(showel, i);

                    break;

                default:
                    break;
            }


        }

        loadedPlayer.setPlayerSpeed(playerData.getSpeed());
        loadedPlayer.setSelectetItem(loadedPlayer.getHotBar()[0]);

        ImageView playerImageView = new ImageView(loadedPlayer.getPlayerImage());
        playerImageView.setTranslateX(coords[0]);
        playerImageView.setTranslateY(coords[1] - 15);
        root.getChildren().add((playerImageView));

        this.playerIMG = playerImageView;
        this.player = loadedPlayer;
    }

    private void loadNPC(StackPane root) {
        int[] coords = new int[2];

        NPC NPC = new NPC("","Player's Helper", 30, 24, "npc_helper.png");
        NPC.setRiddle("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris ut imperdiet risus. Nunc laoreet lectus a dictum aliquet. Suspendisse vel ultricies mauris, at consectetur lectus. Nulla elementum elit eget tincidunt interdum. Maecenas elementum eget elit non efficitur. Morbi id finibus lorem. Proin facilisis nisl lacinia arcu suscipit fermentum. Nunc vitae risus nec enim euismod dignissim id vitae lacus. Nam interdum accumsan dignissim. Vivamus nec enim in ex hendrerit sodales. Nulla congue bibendum velit at efficitur. Donec fermentum sit amet risus id vulputate. Fusce lobortis sollicitudin lacus. Phasellus nibh enim, tempor ac purus in, ultricies euismod mi.");

        Image NPCimage = new Image(NPC.getImg());
        ImageView NPCimageView = new ImageView(NPCimage);

        NPC.setImageView(NPCimageView);

        coords = model.getCoordsFromListToScreen(NPC.getxCoords(), NPC.getyCoords(), 30, world);

        NPCimageView.setTranslateX(coords[0]);
        NPCimageView.setTranslateY(coords[1] - 15);

        root.getChildren().add(NPCimageView);

        Label pressELabel = new Label("Press E");

        pressELabel.setFont(new Font("Arial", 16));

        pressELabel.setTranslateX(NPCimageView.getTranslateX());
        pressELabel.setTranslateY(NPCimageView.getTranslateY() - 50);

        pressELabel.setTextFill(Color.valueOf("Black"));

        root.getChildren().add(pressELabel);

        NPCs.add(NPC);

    }

    private void clearHotbar(StackPane root) {
        for (ImageView node : hotbarNodes) {
            root.getChildren().remove(node);
        }
        hotbarNodes.clear();
    }

    private void fillHotbar(StackPane root) {
        String texture;
        int count = -200;

        clearHotbar(root);

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
    }

    private void showTextBubble(NPC npc, StackPane root) {
        String text = npc.getRiddle();
        Label textLabel = new Label(text);
        Insets insets = new Insets(10,10,10,10);
        Font font = new Font("Arial", 14);

        textLabel.setPadding(insets);
        textLabel.setFont(font);
        textLabel.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-radius: 10; -fx-background-radius: 10;");
        textLabel.setWrapText(true);

        textLabel.setMinWidth(100);
        textLabel.setMaxWidth(300);

        double x = npc.getImageView().getTranslateX();
        double y = npc.getImageView().getTranslateY() - npc.getImageView().getBoundsInParent().getHeight() - 30;

        textLabel.setTranslateX(x);
        textLabel.setTranslateY(y);

        root.getChildren().add(textLabel);

        PauseTransition pause = new PauseTransition(Duration.seconds(8));
        pause.setOnFinished(event -> root.getChildren().remove(textLabel));
        pause.play();
    }

    private void saveGame() throws IOException {
        serializer.serializeToFile(world, "overworld.json");
        serializer.serializeToFile(player, "player.json");

    }

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

    }

    private void isFalling(StackPane root) {
        int[] coords;
        while (!model.isBlockUnder(world, player)) {
            root.getChildren().remove(playerIMG);
            coords = controller.moveDown();
            viewPlayer(root, coords[0], coords[1]);
        }
    }

    private void setFocusOnHotbar(StackPane root, int hotNum) {
        root.getChildren().remove(hotbarFocus);

        hotbarFocus.setTranslateX(-200 + (50 * hotNum));
        hotbarFocus.setTranslateY(381);

        root.getChildren().add(hotbarFocus);

        player.setSelectetItem(player.getHotBar()[hotNum]);

        this.hotBarNumber = hotNum;
    }

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
    }

//    private void checkRecipesAndCraft(Material[] craftingInventory, ImageView resultSlot) {
//        for (Recipes recipe : Recipes.values()) {
//            boolean recipeMatch = true;
//            for (Map.Entry<Item, Integer> entry : recipe.getIngredients().entrySet()) {
//                Item ingredient = entry.getKey();
//                int requiredCount = entry.getValue();
//                int actualCount = countItem(craftingInventory, ingredient);
//                if (actualCount < requiredCount) {
//                    recipeMatch = false;
//                    System.out.println("recipes doesnt match");
//                    break;
//                }
//            }
//            if (recipeMatch) {
//                // Vytvoríme výstupný predmet podľa receptu
//                Item result = recipe.getResult();
//                // Zobrazíme výstupný predmet výstupnom slote
//                resultSlot.setImage(new Image(result.getImagePath()));
//                System.out.println("recipes match");
//                break; // Ak sme našli recept, nemusíme kontrolovať ďalšie recepty
//            }
//        }
//    }
//
//    private int countItem(Material[] craftingInventory, Item item) {
//        int count = 0;
//        for (Material material : craftingInventory) {
//            if (material != null && material.equals(item)) {
//                count++;
//            }
//        }
//        return count;
//    }
//    private void addRightClickFunctionality(ImageView slot, Material[] craftingInventory, int slotIndex, StackPane root, ImageView resultSlot) {
//        slot.setOnMouseClicked(event -> {
//            if (event.getButton() == MouseButton.SECONDARY) {
//                if (craftingInventory[slotIndex] == null) {
//                    for (int i = 0; i < craftingInventory.length; i++) {
//                        if (craftingInventory[i] != null && craftingInventory[i].equals(player.getSelectetItem())) {
//                            System.out.println("Tento materiál je už pridaný na iný slot v craftingu!");
//                            return;
//                        }
//                    }
//
//                    craftingInventory[slotIndex] = (Material) player.getSelectetItem();
//                    slot.setImage(new Image(player.getSelectetItem().getImagePath()));
//
//                    System.out.println("Materiál pridaný do craftingu: " + player.getSelectetItem().getType());
//                    System.out.println("Index slotu craftingu: " + slotIndex);
//
//                    player.addToHotBar(null, hotBarNumber);
//                    fillHotbar(root);
//                    root.setCursor(Cursor.DEFAULT);
//
//                    // Kontrola receptov a vytvorenie výstupného predmetu
//                    checkRecipesAndCraft(craftingInventory, resultSlot);
//                } else {
//                    System.out.println("Slot v craftingu je už obsadený!");
//                }
//            }
//        });
//    }
//
//    private ImageView createSlot() {
//        ImageView slot = new ImageView(new Image("empty_slot.png"));
//        slot.setFitWidth(50);
//        slot.setFitHeight(50);
//        return slot;
//    }
//    private void showCraftingGUI(StackPane root) {
//        StackPane craftingRoot = new StackPane();
//
//        craftingRoot.setAlignment(Pos.CENTER);
//
//        craftingRoot.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 20;");
//
//        GridPane craftingGrid = new GridPane();
//        craftingGrid.setHgap(10);
//        craftingGrid.setVgap(10);
//        Material[] craftingInventory = new Material[9];
//        ImageView resultSlot = createSlot();
//
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                ImageView slot = createSlot();
//                addRightClickFunctionality(slot, craftingInventory, i * 3 + j, root, resultSlot);
//                craftingGrid.add(slot, j, i);
//            }
//        }
//
//
//
//        craftingRoot.getChildren().addAll(craftingGrid, resultSlot);
//
//        root.getChildren().add(craftingRoot);
//
//        craftingRoot.setMaxWidth(root.getWidth());
//        craftingRoot.setMaxHeight(root.getHeight());
//
//        craftingRoot.setOnMouseClicked(event -> {
//            if (event.getButton() == MouseButton.PRIMARY) {
//                root.getChildren().remove(craftingRoot);
//            }
//        });
//    }

    public int countDifferentMaterials(Recipes recipe) {
        int count = 0;
        Map<Item, Integer> ingredients = recipe.getIngredients();

        for (Item ingredient : ingredients.keySet()) {
            count++;
        }

        return count;
    }
    public Item checkForRecipe(SpecialBlock specialBlock) {
        boolean firstCheckmark = false;
        boolean secondCheckemark = false;
        Boolean[] checkmarks = new Boolean[]{firstCheckmark, secondCheckemark};
        int count = 0;

        for (Recipes recipe : Recipes.values()) {
            count = 0;
            checkmarks[0] = false;
            checkmarks[1] = false;
            if (countDifferentMaterials(recipe) <= 1){
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


            if (checkmarks[0] && checkmarks[1]) {
                return recipe.getResult();
            }
        }


        return null;
    }


    private int countItem(List<Item> items, Item item) {
        int count = 0;
        for (Item currentItem : items) {
            if (currentItem.getImagePath().equals(item.getImagePath())) {
                count++;
            }
        }
        return count;
    }
    public void fillEmptySlots(StackPane root, double numberOfSlots){
        numberOfSlots = Math.sqrt(numberOfSlots);
        int x = (int) (0 - ((numberOfSlots/2) * 50) + 25);
        int y = -200;


        for (int i = 0; i < numberOfSlots; i++) {
            for (int j = 0; j < numberOfSlots; j++) {

                ImageView emptySlotView = new ImageView("empty_slot.png");

                emptySlotView.setFitWidth(50);
                emptySlotView.setFitHeight(50);

                emptySlotView.setTranslateX(x + (j * 50));
                emptySlotView.setTranslateY(-200 + (i * 50));

                emptySlotView.setId("emptySlot");

                root.getChildren().add(emptySlotView);

            }

        }

    }

    public void showCraftingGUI(StackPane root, SpecialBlock specialBlock) {
        int slots;
        String specialBlockImagePath = specialBlock.getImagePath();
        ImageView resultSlotImageView = new ImageView("empty_slot.png");


        switch (specialBlockImagePath) {
            case "crafting_table.png":
                slots = 9;

                resultSlotImageView.setFitHeight(50);
                resultSlotImageView.setFitWidth(50);

                resultSlotImageView.setTranslateX(0);
                resultSlotImageView.setTranslateY(0);

                resultSlotImageView.setId("resultSlot");

                root.getChildren().add(resultSlotImageView);

                worldNodes.add(resultSlotImageView);

                fillEmptySlots(root, slots);
                break;

            case "chest.png":
                slots = 36;
                fillEmptySlots(root, slots);
        }


        root.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                double sceneX = event.getX();
                double sceneY = event.getY();

                sceneX = sceneX - (backgroundDimensions[0]/2);
                sceneY = sceneY - (backgroundDimensions[1]/2);

                sceneX = 50 * Math.round(sceneX / 50.0f);
                sceneY = 50 * Math.round(sceneY / 50.0f);

                if (player.getSelectetItem() != null) {
                    Item item = player.getSelectetItem();

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

            }
            if (event.getButton() == MouseButton.SECONDARY) {
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
            }
        });

// Zatvorenie special Blocku na esc
        root.getScene().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE && specialBlockImagePath.equals("crafting_table.png")) {
                specialBlock.getInventory();
                int count = 0;

                root.getChildren().remove(resultSlotImageView);

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
            }
            if (event.getCode() == KeyCode.ENTER && specialBlockImagePath.equals("crafting_table.png")) {
                Item item = checkForRecipe(specialBlock);

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
            }
        });
    }

    @Override
    public void start(Stage stage) throws IOException {

//        gsonBuilder.registerTypeAdapter(Avatar.class, new AvatarAdapter());

//        overworld = new Realm(RealmTypes.Overworld, "none", 1, "overworld_background.jpg", 49,28);

        Image backgroundImage = new Image("overworld_background.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);

        StackPane root = new StackPane(backgroundImageView);

        backgroundDimensions[0] = (int) backgroundImage.getWidth();
        backgroundDimensions[1] = (int) backgroundImage.getHeight();

        Scene scene = new Scene(root, 1440,810);


        loadGame(root);

//        this.player = new Player(0,0, 100);
//        player.setPlayerSpeed(1);
//        world.addMob(player);
//
//
//        Block wooden_block = new Block(true, false, "Plank", "wooden_block.jpg", "Block");
//        Block dirt_block = new Block(false, false, "Dirt", "dirt_block.jpg", "Block");
//        Block stone_block = new Block(true, false, "Stone", "gravel_block.jpg", "Block");
//        Block gravel_block = new Block(false, true, "Gravel", "stone_block.jpg", "Block");
//
//        player.addToInvenory(wooden_block, 0);
//        player.addToInvenory(dirt_block, 1);
//        player.addToInvenory(stone_block,2);
//        player.addToInvenory(gravel_block,3);

//        addBlockToScreen(wooden_block, root, 0, 0, 30 , overworld);
//        addBlockToScreen(dirt_block, root, 40, 0, 30, overworld);
//        addBlockToScreen(stone_block, root, 80, 0, 30, overworld);
//        addBlockToScreen(gravel_block, root, 120, 0, 30, overworld);

//        Sword sword = new Sword("Diamond", "Sword", "");
//        Showel showel = new Showel("Diamond", "Showel", "");
//        Picaxe picaxe = new Picaxe("Diamond", "Picaxe", "");
//        Picaxe picaxe2 = new Picaxe("Diamond", "Picaxe", "");
//        player.addToInvenory(sword, 5);
//        player.addToInvenory(showel, 6);
//        player.addToInvenory(picaxe, 7);
//        player.addToInvenory(picaxe2, 8);
//
//        for (int i = 1; i < 48; i++) {
//            Block block = new Block(true, false, "Dirt", "dirt_block.jpg", "Block");
//            block.setCoords(i,25);
//            world.addBlock(block);
//            serializer.serializeToFile(world, "overworld.json");
//        }
//
//        Material stone = new Material("stone", "Material", "stone_material.png");
//        player.addToHotBar(stone,3);
//
//        Material stick = new Material("stick", "Material", "stick.png");
//        player.addToHotBar(stick, 4);
//        Material iron = new Material("iron", "Material", "iron_ingot.png");
//        player.addToHotBar(iron, 5);
//
//        SpecialBlock crafting = new SpecialBlock(false, false, "craftingTable", "crafting_table.png", "Block");
//        addBlockToScreen(crafting, root, 420, 300);

        stage.setTitle("Odyssey Of Realms");
        stage.setScene(scene);
        stage.show();

        controller = new Controller(player, model, world);

        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.setOnFinished(jump -> {
            root.getChildren().remove(playerIMG);
            int[] finalCoords = controller.moveDown();
            viewPlayer(root, finalCoords[0], finalCoords[1]);
            isPaused = false;
        });

        PauseTransition fall = new PauseTransition(Duration.seconds(0.3));
        fall.setOnFinished(event -> {
            isFalling(root);
            player.setMoving(false);
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int[] coords;
                switch (event.getCode()) {
                    case A:
                        player.setDirection(Direction.LEFT);
                        player.setMoving(true);

                        if (model.isCollision(world, player)) {
                            break;
                        }

                        root.getChildren().remove(playerIMG);
                        coords = controller.moveLeft();
                        viewPlayer(root, coords[0], coords[1]);

                        fall.play();

                        break;
                    case D:
                        player.setDirection(Direction.RIGHT);
                        player.setMoving(true);

                        if (model.isCollision(world, player)) {
                            break;
                        }

                        root.getChildren().remove(playerIMG);
                        coords = controller.moveRight();
                        viewPlayer(root, coords[0], coords[1]);

                        fall.play();

                        break;
                    case SPACE:
                        player.setDirection(Direction.JUMP);

                        if (model.isCollision(world, player)) {
                            break;
                        }

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

                        break;
                    case W:
                        root.getChildren().remove(playerIMG);
                        coords = controller.moveUp();
                        viewPlayer(root, coords[0], coords[1]);

                        break;
                    case E:
                        NPC npc = NPCs.get(0);
                        String riddle = controller.interactWithObject(npc);
                        SpecialBlock nearestSpecialBlock = world.findNearestSpecialBlock(player);
                        Item item = controller.interactWithObject(nearestSpecialBlock);

                        if (!riddle.isEmpty() && !isPaused) {
                            isPaused = true;
                            showTextBubble(npc, root);
                        }

                        if (model.isNearObject(player, nearestSpecialBlock.getXCoord(), nearestSpecialBlock.getYCoord()) && !isPaused) {
                            //Vymysli interface ktory sa zobrazi a ako tam hrac nahadze matros
                            isPaused = true;
                            showCraftingGUI(root, nearestSpecialBlock);
                        }

                        isPaused = false;

                        break;
                    case DIGIT1:
                        setFocusOnHotbar(root, 0);
                        setSelectedItemIcon(root);
                        break;
                    case DIGIT2:
                        setFocusOnHotbar(root, 1);
                        setSelectedItemIcon(root);
                        break;
                    case DIGIT3:
                        setFocusOnHotbar(root, 2);
                        setSelectedItemIcon(root);
                        break;
                    case DIGIT4:
                        setFocusOnHotbar(root, 3);
                        setSelectedItemIcon(root);
                        break;
                    case DIGIT5:
                        setFocusOnHotbar(root, 4);
                        setSelectedItemIcon(root);
                        break;
                    case DIGIT6:
                        setFocusOnHotbar(root, 5);
                        setSelectedItemIcon(root);
                        break;
                    case DIGIT7:
                        setFocusOnHotbar(root, 6);
                        setSelectedItemIcon(root);
                        break;
                    case DIGIT8:
                        setFocusOnHotbar(root, 7);
                        setSelectedItemIcon(root);
                        break;
                    case DIGIT9:
                        setFocusOnHotbar(root, 8);
                        setSelectedItemIcon(root);
                        break;
                    case DIGIT0:
                        root.setCursor(Cursor.DEFAULT);
                        player.setSelectetItem(null);
                    default:

                }
            }
        });


        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY && player.getSelectetItem() != null && "Block".equals(player.getSelectetItem().getGroup())) {

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
                }
                if (event.getButton() == MouseButton.PRIMARY) {

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

                }
            }
        });

    }

    public void stop() throws IOException {
        saveGame();
    }

    public static void main(String[] args) {
        launch();
    }
}
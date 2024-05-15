package cs.cvut.fel.pjv.demo;


import cs.cvut.fel.pjv.demo.controller.Controller;
import cs.cvut.fel.pjv.demo.model.DataSerializer;
import cs.cvut.fel.pjv.demo.model.Model;
import cs.cvut.fel.pjv.demo.view.*;
import cs.cvut.fel.pjv.demo.view.characters.Player;
import cs.cvut.fel.pjv.demo.view.tools.Picaxe;
import cs.cvut.fel.pjv.demo.view.tools.Showel;
import cs.cvut.fel.pjv.demo.view.tools.Sword;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OdysseyOfRealms extends Application {
    Realm world;
    Player player;
    Model model = new Model();
    DataSerializer serializer = new DataSerializer();
    Controller controller;
    ImageView playerIMG;
    ImageView hotbarFocus;
    ImageView selectedItemIcon;
    boolean isPaused = false;

    /**
     * adds block to screen and to the realms map
     * @param block desired block
     * @param root
     * @param xIndex x coords of the block on scene
     * @param yIndex y coords of the block on sceneq
     * @param blockSize size of block
     * @param map map of realm
     */
    private void addBlockToScreen(Block block, StackPane root, int xIndex, int yIndex, int blockSize, Realm map) {
        int[] coords = model.getCoordsFromScreenToList(xIndex, yIndex, block.getSize(), map);

        if (map.map[coords[0]][coords[1]] != null) {
            return;
        }

        map.map[coords[0]][coords[1]] = block;

        Image blockImage = new Image(block.getImagePath());
        ImageView blockImageView = new ImageView(blockImage);
        blockImageView.setTranslateX(xIndex);
        blockImageView.setTranslateY(yIndex);
        root.getChildren().add(blockImageView);

        block.setCoords(coords[0], coords[1]);
        block.isPlaced = true;
        map.addBlock(block);
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

        for (Block i: data.getBlocks()) {
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

                    Block block = new Block(isCraftable, canFall, typeBL, imagePathBL, groupBL);

                    loadedPlayer.addToHotBar(block, i);

                    break;

                case "Key":
                    String typeKey = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/imagePath")).asText();
                    String groupKey = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/group")).asText();
                    String imagePathKey = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/imagePath")).asText();

                    Key key = new Key(typeKey, groupKey, imagePathKey);

                    loadedPlayer.addToHotBar(key, i);

                    break;

                case "Material":
                    String typeMT = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/imagePath")).asText();
                    String groupMT = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/group")).asText();
                    String imagePathMT = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/imagePath")).asText();

                    Material material = new Material(typeMT, groupMT,imagePathMT);

                    loadedPlayer.addToHotBar(material, i);

                    break;
                case "Sword":
                    String typeSW = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/imagePath")).asText();
                    String groupSW = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/group")).asText();
                    String imagePathSW = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/imagePath")).asText();

                    Sword sword = new Sword(typeSW, groupSW, imagePathSW);

                    loadedPlayer.addToHotBar(sword, i);

                    break;

                case "Picaxe":
                    String typePI = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/imagePath")).asText();
                    String groupPI = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/group")).asText();
                    String imagePathPI = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/imagePath")).asText();

                    Picaxe picaxe = new Picaxe(typePI, groupPI, imagePathPI);

                    loadedPlayer.addToHotBar(picaxe, i);

                    break;

                case "Showel":
                    String typeSH = rootNode.at(JsonPointer.compile("/hotBar/" + i + "/imagePath")).asText();
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
        playerImageView.setTranslateY(coords[1]);
        root.getChildren().add((playerImageView));

        this.playerIMG = playerImageView;
        this.player = loadedPlayer;
    }

    private void fillHotbar(StackPane root) {
        String texture;
        int count = -200;

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
        }
    }

    private void saveGame() throws IOException {
        serializer.serializeToFile(world, "overworld.json");
        serializer.serializeToFile(player, "player.json");

    }

    private void loadGame(StackPane root) throws IOException {
        loadOverworldFromSave(root);
        loadPlayer(root);

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
    }

    private void setSelectedItemIcon(StackPane root) {
        if (player.getSelectetItem() == null) {
            root.setCursor(Cursor.DEFAULT);
            player.setSelectetItem(null);
            return;
        }

        Image image = new Image(player.getSelectetItem().getImagePath());
        ImageView imageView = new ImageView(image);

        root.getChildren().add(imageView);
        root.setCursor(new ImageCursor(image));

    }

    @Override
    public void start(Stage stage) throws IOException {

//        gsonBuilder.registerTypeAdapter(Avatar.class, new AvatarAdapter());

//        overworld = new Realm(RealmTypes.Overworld, "none", 1, "overworld_background.jpg", 49,28);

        Image backgroundImage = new Image("overworld_background.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);

        StackPane root = new StackPane(backgroundImageView);

        Scene scene = new Scene(root, 1440,810);


        loadGame(root);



//        this.player = new Player(0,0, 100);
//        player.setPlayerSpeed(1);
//        world.addMob(player);
//
//
//        Block wooden_block = new Block(true, false, "Plank", "wooden_block.jpg", "Block");
//        Block dirt_block = new Block(false, false, "Dirt", "dirt_block.jpg", "Block");
//        Block stone_block = new Block(true, false, "Stone", "stone_block.jpg", "Block");
//        Block gravel_block = new Block(false, true, "Gravel", "gravel_block.jpg", "Block");
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
        fall.setOnFinished(event -> isFalling(root));

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                int[] coords;
                switch (event.getCode()) {
                    case A:
                        player.setDirection(Direction.LEFT);

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
                            isPaused = true;
                            pause.play();
                        }
                        break;
                    case W:
                        root.getChildren().remove(playerIMG);
                        coords = controller.moveUp();
                        viewPlayer(root, coords[0], coords[1]);

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
                if (event.getButton() == MouseButton.SECONDARY & player.getSelectetItem() != null & player.getSelectetItem().getGroup() == "Block") {
                    double sceneX = event.getSceneX();
                    double sceneY = event.getSceneY();
                    Item item = player.getSelectetItem();



//                    addBlockToScreen(, root,(int) sceneX, (int) sceneY, 30, world);
                }
            }
        });

//        scene.setOnMouseClicked(event -> {
//            double x = event.getSceneX();
//            double y = event.getSceneY();
//            System.out.println("Pozícia kurzora: x = " + x + ", y = " + y);
//        });
    }

    public void stop() throws IOException {
        saveGame();
    }

    public static void main(String[] args) {
        launch();
    }
}
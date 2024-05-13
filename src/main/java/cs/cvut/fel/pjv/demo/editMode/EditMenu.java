package cs.cvut.fel.pjv.demo.editMode;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditMenu extends Application {

    private VBox overworldScene;
    private VBox subterraneanMystoriaScene;
    private VBox etherialRealmScene;
    private VBox currentScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initializeScenes();

        Button overworldButton = new Button("Overworld");
        Button subterraneanMystoriaButton = new Button("Subterranean Mystoria");
        Button etherialRealmButton = new Button("Etherial Realm");
        Text heading = new Text("Choose realm to edit:");

        overworldButton.setOnAction(event -> {
            showScene(overworldScene);
        });

        subterraneanMystoriaButton.setOnAction(event -> {
            showScene(subterraneanMystoriaScene);
        });

        etherialRealmButton.setOnAction(event -> {
            showScene(etherialRealmScene);
        });

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(overworldButton, subterraneanMystoriaButton, etherialRealmButton);

        VBox root = new VBox(10);
        root.getChildren().addAll(heading, buttonBox, currentScene);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Level Editor Menu");
        primaryStage.show();
    }

    private void initializeScenes() {
        overworldScene = new VBox();



        subterraneanMystoriaScene = new VBox();
        subterraneanMystoriaScene.getChildren().addAll(new TextArea("Subterranean Mystoria Scene"));

        etherialRealmScene = new VBox();
        etherialRealmScene.getChildren().addAll(new TextArea("Etherial Realm Scene"));

        currentScene = new VBox();
    }

    private void showScene(VBox scene) {
        currentScene.getChildren().clear();
        currentScene.getChildren().add(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

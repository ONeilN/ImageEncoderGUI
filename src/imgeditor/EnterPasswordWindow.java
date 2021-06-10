package imgeditor;

import com.nugumanov.wavelettransform.encryptors.EncryptionType;
import com.nugumanov.wavelettransform.encryptors.KeyHelper;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.util.Date;

public class EnterPasswordWindow extends Application {

    BufferedImage bufferedImage = null;
    Date date = new Date();

    public void startWindow(Stage stage, BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
        try {
            start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage enterPassStage = new Stage();
        BorderPane root = new BorderPane();
        VBox vBox = new VBox();
        HBox hBoxTop = new HBox();
        HBox hBoxBottom = new HBox();
        Scene exitScene = new Scene(root, 300, 100);

        PasswordField passwordField = new PasswordField();

        Button okButton = new Button("OK");
        Button cancelButton = new Button("Cancel");


        root.setStyle("-fx-background-color: #3c3f41;");

        passwordField.setStyle("-fx-min-width: 300px;\n" +
                " -fx-min-height: 50px;");

        okButton.setStyle(" linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                " linear-gradient(#20262b, #191d22),\n" +
                " radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                " -fx-background-radius: 5,4,3,5;\n" +
                " -fx-background-insets: 0,1,2,0;\n" +
                " -fx-text-fill: white;\n" +
                " -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                " -fx-font-family: \"Arial\";\n" +
                "  -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                " -fx-font-size: 18px;\n" +
                " -fx-padding: 5 35 5 35;\n" +
                " -fx-font-weight: bold;\n" +
                " -fx-min-width: 150px;\n" +
                " -fx-background-color: green;\n" +
                " -fx-min-height: 50px;");

        cancelButton.setStyle(" linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                " linear-gradient(#20262b, #191d22),\n" +
                " radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                " -fx-background-radius: 5,4,3,5;\n" +
                " -fx-background-insets: 0,1,2,0;\n" +
                " -fx-text-fill: white;\n" +
                " -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                " -fx-font-family: \"Arial\";\n" +
                "  -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                " -fx-font-size: 18px;\n" +
                " -fx-padding: 5 35 5 35;\n" +
                " -fx-font-weight: bold;\n" +
                " -fx-min-width: 150px;\n" +
                " -fx-background-color: red;\n" +
                " -fx-min-height: 50px;");

        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                KeyHelper keyHelper = KeyHelper.getInstance(EncryptionType.AES);
                keyHelper.setKey(passwordField.getText(), EncryptionType.AES);
                date = new Date();
                System.out.println(date.toString() + " | Пароль введен!");
                enterPassStage.close();
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                enterPassStage.close();
            }
        });

        vBox.getChildren().add(hBoxTop);
        vBox.getChildren().add(hBoxBottom);

        hBoxTop.getChildren().add(passwordField);

        hBoxBottom.getChildren().add(okButton);
        hBoxBottom.getChildren().add(cancelButton);

        root.setCenter(vBox);

        enterPassStage.setTitle("Enter password");
        enterPassStage.getIcons().add(new Image("././icons/buttons/encode.png"));
        enterPassStage.setScene(exitScene);
        enterPassStage.show();

    } // start
} //class

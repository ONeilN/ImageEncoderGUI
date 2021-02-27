package imgeditor;

import com.nugumanov.wavelettransform.ImageTransformation;
import com.nugumanov.wavelettransform.transforms.TransformType;
import com.nugumanov.wavelettransform.transforms.WaveletType;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageEditor extends Application {

    boolean isTransformed = false;
    ImageKeeper imageKeeper = new ImageKeeper();
    ImageTransformation imageTransformation = new ImageTransformation();

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #3c3f41");
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #2b2b2b");
        Scene mainScene = new Scene(root, 800, 650);
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-text-fill: white");
        ToolBar toolBar = new ToolBar();
        toolBar.setStyle("-fx-background-color: #2b2b2b");

        ImageActions imageActions = new ImageActions();
        ImageViewHelper imageViewHelper = new ImageViewHelper();
        FileChooserHelper fileChooserHelper = new FileChooserHelper();


        Menu fileMenu = new Menu("Файл");
        Menu actionsMenu = new Menu("Изображение");
        Menu helpMenu = new Menu("Помощь");

        // Для "Файл"
        MenuItem openImageMenuItem = new MenuItem("Открыть");
        MenuItem saveImageMenuItem = new MenuItem("Сохранить");
        MenuItem exitMenuItem = new MenuItem("Выход");

        // Для "Действия"
        MenuItem encodeImageMenuItem = new MenuItem("Зашифровать");
        MenuItem decodeImageMenuItem = new MenuItem("Расшифровать");


        exitMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        saveImageMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        openImageMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));

        File fileOpenImage = new File("./src/icons/buttons/openimage.png");
        Image iconOpenImageButton = new Image(fileOpenImage.toURI().toString());
        Button buttonOpenImage = new Button("", new ImageView(iconOpenImageButton));
        buttonOpenImage.setStyle("    -fx-background-color: \n" +
                "        #090a0c,\n" +
                "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                "        linear-gradient(#20262b, #191d22),\n" +
                "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "    -fx-font-size: 12px;\n" +
                "    -fx-padding: 5 5 5 5;");

        File fileEncodeImage = new File("./src/icons/buttons/encode.png");
        Image iconEncodeImageButton = new Image(fileEncodeImage.toURI().toString());
        Button buttonEncodeImage = new Button("", new ImageView(iconEncodeImageButton));
        buttonEncodeImage.setStyle(" -fx-background-color: \n" +
                " #090a0c,\n" +
                " linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                " linear-gradient(#20262b, #191d22),\n" +
                " radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                " -fx-background-radius: 5,4,3,5;\n" +
                " -fx-background-insets: 0,1,2,0;\n" +
                " -fx-text-fill: white;\n" +
                " -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                " -fx-font-family: \"Arial\";\n" +
                "  -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "  -fx-font-size: 12px;\n" +
                "  -fx-padding: 5 5 5 5;");

        File fileDecodeImage = new File("./src/icons/buttons/decode.png");
        Image iconDecodeImageButton = new Image(fileDecodeImage.toURI().toString());
        Button buttonDecodeImage = new Button("", new ImageView(iconDecodeImageButton));
        buttonDecodeImage.setStyle(" -fx-background-color: \n" +
                " #090a0c,\n" +
                " linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                " linear-gradient(#20262b, #191d22),\n" +
                " radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                " -fx-background-radius: 5,4,3,5;\n" +
                " -fx-background-insets: 0,1,2,0;\n" +
                " -fx-text-fill: white;\n" +
                " -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                " -fx-font-family: \"Arial\";\n" +
                "  -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "  -fx-font-size: 12px;\n" +
                "  -fx-padding: 5 5 5 5;");

        File fileSaveImage = new File("./src/icons/buttons/saveimage.png");
        Image iconSaveImageButton = new Image(fileSaveImage.toURI().toString());
        Button buttonSaveImage = new Button("", new ImageView(iconSaveImageButton));
        buttonSaveImage.setStyle("    -fx-background-color: \n" +
                "        #090a0c,\n" +
                "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                "        linear-gradient(#20262b, #191d22),\n" +
                "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "    -fx-font-size: 12px;\n" +
                "    -fx-padding: 5 5 5 5;");

        File fileClearButton = new File("./src/icons/buttons/clear.png");
        Image iconClearButton = new Image(fileClearButton.toURI().toString());
        Button buttonClear = new Button("", new ImageView(iconClearButton));
        buttonClear.setStyle(" -fx-background-color: \n" +
                " #090a0c,\n" +
                " linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                " linear-gradient(#20262b, #191d22),\n" +
                " radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                " -fx-background-radius: 5,4,3,5;\n" +
                " -fx-background-insets: 0,1,2,0;\n" +
                " -fx-text-fill: white;\n" +
                " -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                " -fx-font-family: \"Arial\";\n" +
                "  -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "  -fx-font-size: 12px;\n" +
                "  -fx-padding: 5 5 5 5;");

        File fileExitButton = new File("./src/icons/buttons/exit.png");
        Image iconExitButton = new Image(fileExitButton.toURI().toString());
        Button buttonExit = new Button("", new ImageView(iconExitButton));
        buttonExit.setLayoutY(265);
        buttonExit.setStyle(" -fx-background-color: \n" +
                " #090a0c,\n" +
                " linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                " linear-gradient(#20262b, #191d22),\n" +
                " radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                " -fx-background-radius: 5,4,3,5;\n" +
                " -fx-background-insets: 0,1,2,0;\n" +
                " -fx-text-fill: white;\n" +
                " -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                " -fx-font-family: \"Arial\";\n" +
                "  -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "  -fx-font-size: 12px;\n" +
                "  -fx-padding: 5 5 5 5;");

        // MENU ITEMS ACTIONS

        openImageMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                imageKeeper.setBufImage(imageActions.openImage(primaryStage));
                imageViewHelper.setImageView(imageKeeper.getImage());
                root.setCenter(imageViewHelper.getImgView());
            } // handle
        }); // openImageMenuItem

        encodeImageMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                CompressWindow compress = new CompressWindow();
                if (imageKeeper.getImage() == null) {
                    imageKeeper.setBufImage(imageActions.openImage(primaryStage));
                    imageViewHelper.setImageView(imageKeeper.getImage());
                    root.setCenter(imageViewHelper.getImgView());
                }

                /*
                    try {
                        compress.startWindow(primaryStage, imageKeeper.getBufImage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                 */

                imageKeeper.setBufImage(imageTransformation.forwardImage(imageKeeper.getBufImage(), WaveletType.HAAR, 2));

                isTransformed = true;

                imageViewHelper.setImageView(imageKeeper.getImage());
                root.setCenter(imageViewHelper.getImgView());
                //imageKeeper.clear();
            } // handle
        }); // encodeImageMenuItem

        decodeImageMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                BufferedImage bufferedImage = null;

                if (!isTransformed) {
                    if (imageKeeper.getBufImage() != null) {
                        imageKeeper.clear();
                        imageViewHelper.getImgView().setImage(null);
                    }
                    FileChooserHelper zipFileChooser = new FileChooserHelper(FileChooserHelper.Type.PNG_COMPRESS);
                    File inputFile = zipFileChooser.getFileChooser().showOpenDialog(primaryStage);

                    try {
                        bufferedImage = ImageIO.read(inputFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    bufferedImage = imageKeeper.getBufImage();
                }

                bufferedImage = imageTransformation.transform(bufferedImage, TransformType.REVERSE, WaveletType.HAAR, 2);

                imageKeeper.setBufImage(bufferedImage);
                imageViewHelper.setImageView(imageKeeper.getImage());
                root.setCenter(imageViewHelper.getImgView());
            } // handle
        }); // decodeImageMenuItem

        saveImageMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (imageKeeper.getImage() == null) {
                    Label error = new Label("Изображение не найдено!");
                    error.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 30px;");
                    root.setCenter(error);
                } else {

                    File selectImage = fileChooserHelper.getFileChooser().showSaveDialog(primaryStage);

                    try {
                        imageActions.save(imageKeeper.getImage(), selectImage, "png");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } // if else
            } // handle
        }); // saveImageMenuItem

        exitMenuItem.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (imageKeeper.getImage() != null) {
                    ExitWindow exit = new ExitWindow();
                    try {
                        exit.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.exit(0);
                }
            } // handle
        }); // exitMenuItem

        /**
         *   BUTTONS ACTIONS
         */

        buttonOpenImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                imageKeeper.setBufImage(imageActions.openImage(primaryStage));
                imageViewHelper.setImageView(imageKeeper.getImage());
                root.setCenter(imageViewHelper.getImgView());
            } // handle
        }); // buttonOpenImage

        buttonEncodeImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                CompressWindow compress = new CompressWindow();
                if (imageKeeper.getImage() == null) {
                    imageKeeper.setBufImage(imageActions.openImage(primaryStage));
                    imageViewHelper.setImageView(imageKeeper.getImage());
                    root.setCenter(imageViewHelper.getImgView());
                }

                /*
                    try {
                        compress.startWindow(primaryStage, imageKeeper.getBufImage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                 */

                imageKeeper.setBufImage(imageTransformation.forwardImage(imageKeeper.getBufImage(), WaveletType.HAAR, 2));

                isTransformed = true;

                imageViewHelper.setImageView(imageKeeper.getImage());
                root.setCenter(imageViewHelper.getImgView());
                //imageKeeper.clear();
            } // handle
        }); // buttonEncodeImage

        buttonDecodeImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                BufferedImage bufferedImage = null;

                if (!isTransformed) {
                    if (imageKeeper.getBufImage() != null) {
                        imageKeeper.clear();
                        imageViewHelper.getImgView().setImage(null);
                    }
                    FileChooserHelper zipFileChooser = new FileChooserHelper(FileChooserHelper.Type.PNG_COMPRESS);
                    File inputFile = zipFileChooser.getFileChooser().showOpenDialog(primaryStage);

                    try {
                        bufferedImage = ImageIO.read(inputFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    bufferedImage = imageKeeper.getBufImage();
                }

                bufferedImage = imageTransformation.transform(bufferedImage, TransformType.REVERSE, WaveletType.HAAR, 2);

                imageKeeper.setBufImage(bufferedImage);
                imageViewHelper.setImageView(imageKeeper.getImage());
                root.setCenter(imageViewHelper.getImgView());
            } // handle
        }); // buttonDecodeImage

        buttonSaveImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (imageKeeper.getImage() == null) {
                    Label error = new Label("Изображение не найдено!");
                    error.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-font-size: 30px;");
                    root.setCenter(error);
                } else {

                    File selectImage = fileChooserHelper.getFileChooser().showSaveDialog(primaryStage);

                    try {
                        imageActions.save(imageKeeper.getImage(), selectImage, "png");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } // if else
            } // handle
        }); // buttonSaveImage

        buttonExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (imageKeeper.getImage() != null) {
                    ExitWindow exit = new ExitWindow();
                    try {
                        exit.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.exit(0);
                }
            } // handle
        }); // buttonExit

        buttonClear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                imageKeeper.clear();
                imageViewHelper.getImgView().setImage(null);
            } // handle
        }); // buttonClear



        // Добавление элементов на MenuBar
        fileMenu.getItems().addAll(openImageMenuItem, saveImageMenuItem, exitMenuItem);
        actionsMenu.getItems().addAll(encodeImageMenuItem, decodeImageMenuItem);
        menuBar.getMenus().addAll(fileMenu, actionsMenu, helpMenu);
        root.setTop(menuBar);


        // Добавление кнопок на ToolBar
        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.getItems().add(buttonOpenImage);
        toolBar.getItems().add(buttonEncodeImage);
        toolBar.getItems().add(buttonDecodeImage);
        toolBar.getItems().add(buttonSaveImage);
        toolBar.getItems().add(buttonClear);
        toolBar.getItems().add(buttonExit);
        root.setLeft(toolBar);


        primaryStage.setTitle("Image Encoder");
        primaryStage.getIcons().add(new Image("icons/mainIcon.png"));
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(300);
        primaryStage.setScene(mainScene);
        primaryStage.show();

    } // void start - end
} // class ImageEditor - end

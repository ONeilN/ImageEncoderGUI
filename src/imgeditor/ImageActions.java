package imgeditor;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.*;

public class ImageActions {

    /**
     * Метод для загрузки изображения из памяти компьютера
     * @param stage - окно на котором откроется диалог для выбора файла
     * @return объект класса BufferedImage
     */
    BufferedImage openImage(Stage stage) {
        FileChooserHelper fileChooserHelper = new FileChooserHelper();
        FileChooser fileChooser = fileChooserHelper.getFileChooser();
        File selectImage = fileChooser.showOpenDialog(stage);
        BufferedImage bufImage = null;
        try {
            bufImage = ImageIO.read(selectImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufImage;
    } // openImage

    /**
     * Метод для сохранения изображения в память компьютера
     * @param image - объект класса Image
     * @param outputFile - объект класса File
     * @param format - переменная типа String хранящая название формата
     * @throws IOException
     */
    void save(Image image, File outputFile, String format) throws IOException {

        ImageIO.write(SwingFXUtils.fromFXImage(image, null), format, outputFile);
    } // save
} // class

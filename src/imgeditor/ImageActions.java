package imgeditor;

import com.nugumanov.wavelettransform.Encryption;
import com.nugumanov.wavelettransform.ImageEncryption;
import com.nugumanov.wavelettransform.encryptors.EncryptionType;
import com.nugumanov.wavelettransform.transforms.WaveletType;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.io.*;
import java.util.Date;

public class ImageActions {

    Encryption encryption = new ImageEncryption();
    InputStream is = null;
    File selectImage = null;
    byte[] encryptedImage = null;
    Date date = null;

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

    File openEncryptedImage(Stage stage) {

        byte[] encryptedImage = null;
        FileChooserHelper fileChooserHelper = new FileChooserHelper();
        FileChooser fileChooser = fileChooserHelper.getFileChooser();
        selectImage = fileChooser.showOpenDialog(stage);


        return selectImage;
    }

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

    BufferedImage decryptImage(ImageKeeper imageKeeper, ImageViewHelper imageViewHelper, Stage stage) {

        BufferedImage dencryptedImage = null;

        if (imageKeeper.getBufImage() != null) {
            imageKeeper.clear();
            imageViewHelper.getImgView().setImage(null);
        }

        selectImage = this.openEncryptedImage(stage);

        try {
            is = new FileInputStream(selectImage);
        } catch (FileNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        try {
            encryptedImage = new byte[is.available()];
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            is.read(encryptedImage);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        dencryptedImage = encryption.decrypt(encryptedImage, EncryptionType.AES, WaveletType.HAAR);

        date = new Date();
        System.out.println(date.toString() + " | Изображение расшифровано!");

        return dencryptedImage;
    }
} // class

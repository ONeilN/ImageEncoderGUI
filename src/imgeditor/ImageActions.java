package imgeditor;

import com.nugumanov.wavelettransform.Encryption;
import com.nugumanov.wavelettransform.ImageEncryption;
import com.nugumanov.wavelettransform.encryptors.EncryptionType;
import com.nugumanov.wavelettransform.encryptors.KeyHelper;
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
    File keyFile = null;
    byte[] encryptedImage = null;
    Date date = null;
    KeyHelper keyHelper = KeyHelper.getInstance(EncryptionType.AES);
    FileChooserHelper fileChooserHelper = new FileChooserHelper();

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

    void encryptImage(ImageKeeper imageKeeper, Stage stage) {

        byte[] enctypted = encryption.encrypt(imageKeeper.getBufImage(), EncryptionType.AES, WaveletType.HAAR);

        selectImage = fileChooserHelper.getFileChooser().showSaveDialog(stage);

        try {
            FileOutputStream fos = new FileOutputStream(selectImage);
            fos.write(enctypted);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        date = new Date();
        System.out.println(date.toString() + " | Изображение зашифровано!");
    }

    BufferedImage decryptImage(ImageKeeper imageKeeper, ImageViewHelper imageViewHelper, Stage stage) {

        BufferedImage decryptedImage = null;

        if (imageKeeper.getBufImage() != null) {
            imageKeeper.clear();
            imageViewHelper.getImgView().setImage(null);
        }

        selectImage = this.openEncryptedImage(stage);

        try {
            is = new FileInputStream(selectImage);
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        try {
            encryptedImage = new byte[is.available()];
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            is.read(encryptedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        decryptedImage = encryption.decrypt(encryptedImage, EncryptionType.AES, WaveletType.HAAR);

        date = new Date();
        System.out.println(date.toString() + " | Изображение расшифровано!");

        return decryptedImage;
    }

    void saveKeyToFile(Stage stage) {
        keyFile = fileChooserHelper.getFileChooser().showSaveDialog(stage);
        byte[] key = keyHelper.getByteKey();
        try {
            FileOutputStream fos = new FileOutputStream(keyFile);
            fos.write(key);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        date = new Date();
        System.out.println(date.toString() + " | Ключ сохранен!");
    }

    void getKeyFromFile(Stage stage, EncryptionType type) {

        FileChooser fileChooser = fileChooserHelper.getFileChooser();
        keyFile = fileChooser.showOpenDialog(stage);

        byte[] key = null;

        try {
            is = new FileInputStream(keyFile);
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }

        try {
            key = new byte[is.available()];
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            is.read(key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        keyHelper.setKey(key, type);
        date = new Date();
        System.out.println(date.toString() + " | Ключ загружен!");
    }
} // class

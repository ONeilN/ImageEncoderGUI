package imgeditor;

import javafx.stage.FileChooser;

import java.io.File;

public class FileChooserHelper {
    private FileChooser fileChooser;
    enum Type { ZIP_COMPRESS, PNG_COMPRESS, NO_COMPRESS}

    FileChooserHelper() {
        fileChooser = new FileChooser();
        configuringFileChooser(fileChooser);
    }

    FileChooserHelper(Type type) {
        fileChooser = new FileChooser();
        configuringFileChooser(fileChooser);
    }

    void setFileChooser(FileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    FileChooser getFileChooser() {
        return fileChooser;
    }

    /**
     * Настройка объекта класса FileChooser
     * @param fileChooser - объект класса FileChooser
     */
    static void configuringFileChooser(FileChooser fileChooser) {

        //Set title for FileChooser
        fileChooser.setTitle("Select Picture");
        //Set Initial Directory
        fileChooser.setInitialDirectory(new File("./Pictures"));
        //Add Extension Filters
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));


    } // configuringFileChooser
} // class

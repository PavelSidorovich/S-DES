package com.sidorovich.tatarinov.sdes.controller;

import com.sidorovich.tatarinov.sdes.exception.TextFileNotFoundException;
import com.sidorovich.tatarinov.sdes.service.FileCipherDecipher;
import com.sidorovich.tatarinov.sdes.service.impl.FileCipherDecipherImpl;
import com.sidorovich.tatarinov.sdes.service.impl.SDesCipherDecipher;
import com.sidorovich.tatarinov.sdes.util.Validator;
import com.sidorovich.tatarinov.sdes.util.impl.BitSetValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

public class SDesCipherDecipherController {

    private static final String SOURCE_FILE_CHOOSER_TITLE = "Choose source file";
    private static final String TARGET_FILE_CHOOSER_TITLE = "Save target file";
    private static final String FILE_CHOOSER_TEXT = "TXT files (*.txt)";
    private static final String FILE_EXTENSION = "*.txt";
    private static final String INITIAL_FILENAME_TO_READ = "";
    private static final String INITIAL_FILENAME_CIPHERED = "ciphered";
    private static final String INITIAL_FILENAME_DECIPHERED = "deciphered";
    private static final String INVALID_KEY_MESSAGE = "Invalid key (should contain 10 bits)";
    private static final String INPUT_FILE_ERROR_MSG = "Input file was not chosen (or it doesn't exists anymore)";
    private static final String EMPTY_STRING = "";
    private static final String DELIMITER = ";\n";
    private static final String STRING_PREFIX = "- ";

    @FXML
    private Button fileButton;

    @FXML
    private TextField keyTextField;

    @FXML
    private Label filenameTextField;

    @FXML
    private Button cipherButton;

    @FXML
    private Button decipherButton;

    @FXML
    private Label errorLabel;

    @FXML
    private ProgressIndicator progressIndicator;

    private File inputFile;

    private final FileCipherDecipher fileCipherDecipher = new FileCipherDecipherImpl(new SDesCipherDecipher());
    private final Validator validator = new BitSetValidator(10);

    @FXML
    void chooseFile(ActionEvent event) {
        inputFile = createFileChooser(
                SOURCE_FILE_CHOOSER_TITLE,
                INITIAL_FILENAME_TO_READ
        ).showOpenDialog(fileButton.getScene().getWindow());
        if (inputFile != null) {
            filenameTextField.setText(inputFile.getAbsolutePath());
        }
    }

    @FXML
    void cipher(ActionEvent event) {
        if (inputIsValid()) {
            progressIndicator.setProgress(0.0);
            File output = createFileChooser(
                    TARGET_FILE_CHOOSER_TITLE,
                    INITIAL_FILENAME_CIPHERED
            ).showSaveDialog(fileButton.getScene().getWindow());
            try {
                if (output != null) {
                    executeThread(() -> {
                        disableButtons(true);
                        fileCipherDecipher.cipher(inputFile, output, keyTextField.getText(), progressIndicator);
                        disableButtons(false);
                    });
                }
            } catch (TextFileNotFoundException e) {
                errorLabel.setText(INPUT_FILE_ERROR_MSG);
            }
        }
    }

    @FXML
    void decipher(ActionEvent event) {
        if (inputIsValid()) {
            progressIndicator.setProgress(0.0);
            File output = createFileChooser(
                    TARGET_FILE_CHOOSER_TITLE,
                    INITIAL_FILENAME_DECIPHERED
            ).showSaveDialog(fileButton.getScene().getWindow());
            try {
                if (output != null) {
                    executeThread(() -> {
                        disableButtons(true);
                        fileCipherDecipher.decipher(inputFile, output, keyTextField.getText(), progressIndicator);
                        disableButtons(false);
                    });
                }
            } catch (TextFileNotFoundException e) {
                errorLabel.setText(INPUT_FILE_ERROR_MSG);
            }
        }
    }

    boolean inputIsValid() {
        String errorMsg = EMPTY_STRING;

        if (!validator.validate(keyTextField.getText())) {
            errorMsg += STRING_PREFIX + INVALID_KEY_MESSAGE + DELIMITER;
        }
        if (inputFile == null || !inputFile.exists()) {
            errorMsg += STRING_PREFIX + INPUT_FILE_ERROR_MSG + DELIMITER;
        }
        errorLabel.setText(errorMsg.trim());
        return errorMsg.isEmpty();
    }

    private FileChooser createFileChooser(String title, String initialName) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle(title);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                FILE_CHOOSER_TEXT, FILE_EXTENSION
        );
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName(initialName);

        return fileChooser;
    }

    private void disableButtons(boolean state) {
        cipherButton.setDisable(state);
        decipherButton.setDisable(state);
        fileButton.setDisable(state);
    }

    private void executeThread(Runnable runnable) {
        Thread thread = new Thread(runnable);

        thread.setDaemon(true);
        thread.start();
    }

}

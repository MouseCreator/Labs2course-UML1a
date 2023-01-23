package com.example.lab23a;

import com.example.lab23a.PDF.PDFConvertor;
import com.example.lab23a.PDF.PDFParams;
import com.example.lab23a.model.SetIndex;
import com.example.lab23a.model.TermList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

public class ExportController {
    @FXML
    private Slider fontSizeSlider;
    PDFParams params = new PDFParams();
    @FXML
    private TextField filenameField;

    @FXML
    private Rectangle footer;

    @FXML
    private Rectangle header;

    @FXML
    private HBox legendColor;

    @FXML
    private ColorPicker legendColorPicker;

    @FXML
    private VBox termsBox;

    @FXML
    private ColorPicker termsColorPicker;

    @FXML
    private ColorPicker termsColorPicker1;

    @FXML
    private CheckBox textCheckBox;

    @FXML
    private ColorPicker titleColorPicker;

    private SetIndex index;
    private TermList terms;

    private Stage stage;
    @FXML
    void changeFooterColor() {
       footer.setFill(termsColorPicker1.getValue());
       params.getPalette().setFooter(fxToAwtColor(termsColorPicker1.getValue()));
    }
    private java.awt.Color fxToAwtColor(Color color) {
        float red = (float) color.getRed();
        float green = (float) color.getGreen();
        float blue = (float) color.getBlue();
        return new java.awt.Color(red, green, blue);
    }

    @FXML
    void changeHeaderColor() {
        header.setFill(titleColorPicker.getValue());
        params.getPalette().setHeader(fxToAwtColor(titleColorPicker.getValue()));
    }

    @FXML
    void changeLegendColor() {
        for (Object obj : legendColor.getChildren().toArray()) {
            Rectangle rect = (Rectangle) obj;
            rect.setFill(legendColorPicker.getValue());
        }
        params.getPalette().setLegend(fxToAwtColor(legendColorPicker.getValue()));
    }

    @FXML
    void changeTermsColor() {
        for (Object outer : termsBox.getChildren().toArray()) {
            HBox box = (HBox) outer;
            for (Object obj : box.getChildren().toArray()) {
                Rectangle rect = (Rectangle) obj;
                rect.setFill(termsColorPicker.getValue());
            }
        }
        params.getPalette().setBackground(fxToAwtColor(termsColorPicker.getValue()));
    }
    private void beforeExport() {
        params.getPalette().setTextColor(textCheckBox.isSelected() ? java.awt.Color.white : java.awt.Color.black);
    }
    @FXML
    void export() {
        beforeExport();
        savePDF();
        stage.close();
    }
    @FXML
    void cancel() {
        stage.close();
    }
    @FXML
    private Label fontLabel;

    public void init(SetIndex index, TermList list) {
        this.index = index;
        this.terms = list;
        filenameField.setText(index.getNotEmptyName());
        stage = (Stage) filenameField.getScene().getWindow();
        initSlider();
    }
    private void initSlider() {
        fontSizeSlider.valueProperty().addListener((obs, old, newVal) -> {
                int size = newVal.intValue();
                fontSizeSlider.setValue(size);
                displaySlider();
                params.setFontSize(size);
        });
    }
    private void displaySlider() {
        fontLabel.setText("Font size: " +  (int)fontSizeSlider.getValue());
    }
    public SetInfoController getParent() {
        return parent;
    }
    private SetInfoController parent;
    public void setParent(SetInfoController setInfoController) {
       this.parent = setInfoController;
    }

    private void savePDF() {
        try {
            savePDFFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void savePDFFile() throws IOException {
        FileChooser fileChooser = initFileChooser();
        Window stage = parent.getParent().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            parent.getParent().getUserData().getStyle().updateLastSavedFileAbsolutePath(file.getParent());
            parent.getParent().getUserData().saveData();
            getPDF(file);
        }
    }
    private FileChooser initFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(this.getParent().getParent().getUserData().getStyle().getLastSavedFileAbsolutePath()));
        fileChooser.setTitle("Save PDF");
        fileChooser.setInitialFileName(filenameField.getText());
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF document","*.pdf"));
        return fileChooser;
    }
    private void getPDF(File file) throws IOException {
        PDFConvertor convertor = new PDFConvertor();
        PDDocument document = convertor.convert(params, index, terms);
        document.save(file);
        document.close();
    }
}

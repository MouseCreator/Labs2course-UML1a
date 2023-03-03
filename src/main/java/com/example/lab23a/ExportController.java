package com.example.lab23a;

import com.example.lab23a.PDF.PDFConvertor;
import com.example.lab23a.PDF.PDFParams;
import com.example.lab23a.model.FileBuilder;
import com.example.lab23a.model.SetIndex;
import com.example.lab23a.model.TermList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

public class ExportController extends AdditionalWindowController{
    @FXML
    private Slider fontSizeSlider;
    private final PDFParams parameters = new PDFParams();
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
    private ColorPicker footerColorPicker;

    @FXML
    private CheckBox textCheckBox;

    @FXML
    private ColorPicker titleColorPicker;

    private SetIndex index;
    private TermList terms;
    @FXML
    private Label fontLabel;

    /**
     * Sets footer color to the color in specified color picker
     */
    @FXML
    public void changeFooterColor() {
       footer.setFill(footerColorPicker.getValue());
       parameters.getPalette().setFooter(fxToAwtColor(footerColorPicker.getValue()));
    }
    private java.awt.Color fxToAwtColor(Color color) {
        float red = (float) color.getRed();
        float green = (float) color.getGreen();
        float blue = (float) color.getBlue();
        return new java.awt.Color(red, green, blue);
    }
    /**
     * Sets header color to the color in specified color picker
     */
    @FXML
    void changeHeaderColor() {
        header.setFill(titleColorPicker.getValue());
        parameters.getPalette().setHeader(fxToAwtColor(titleColorPicker.getValue()));
    }
    /**
     * Sets table legend (heading) color to the color in specified color picker
     */
    @FXML
    void changeLegendColor() {
        for (Object obj : legendColor.getChildren().toArray()) {
            Rectangle rect = (Rectangle) obj;
            rect.setFill(legendColorPicker.getValue());
        }
        parameters.getPalette().setLegend(fxToAwtColor(legendColorPicker.getValue()));
    }
    /**
     * Sets table cells color to the color in specified color picker
     */
    @FXML
    void changeTermsColor() {
        for (Object outer : termsBox.getChildren().toArray()) {
            HBox box = (HBox) outer;
            for (Object obj : box.getChildren().toArray()) {
                Rectangle rect = (Rectangle) obj;
                rect.setFill(termsColorPicker.getValue());
            }
        }
        parameters.getPalette().setBackground(fxToAwtColor(termsColorPicker.getValue()));
    }
    private void setTextColor() {
        parameters.getPalette().setTextColor(textCheckBox.isSelected() ? java.awt.Color.white : java.awt.Color.black);
    }
    /**
     * Exports PDF file and closes the scene
     */
    @FXML
    void export() {
        setTextColor();
        savePDF();
        stage.close();
    }
    /**
     * Closes export window without exporting the file
     */
    @FXML
    void cancel() {
        stage.close();
    }

    private void setExportData(SetIndex index, TermList list) {
        this.index = index;
        this.terms = list;
        filenameField.setText(index.getNotEmptyName());

        initSlider();
    }
    private void initSlider() {
        fontSizeSlider.valueProperty().addListener((obs, old, newVal) -> {
                int size = newVal.intValue();
                fontSizeSlider.setValue(size);
                displaySlider();
                parameters.setFontSize(size);
        });
    }
    private void displaySlider() {
        fontLabel.setText("Font size: " +  (int)fontSizeSlider.getValue());
    }

    @Override
    public String getFilename() {
        return FileBuilder.FXMLDestination("PDFPopUp");
    }

    @Override
    public String getTitle() {
        return "Export PDF";
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
        Window stage = parent.getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            parent.getUserData().getUserInfo().updateLastSavedFileAbsolutePath(file.getParent());
            parent.getUserData().saveData();
            getPDF(file);
        }
    }
    private FileChooser initFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(this.getParent().getUserData().getUserInfo().getLastSavedFileAbsolutePath()));
        fileChooser.setTitle("Save PDF");
        fileChooser.setInitialFileName(filenameField.getText());
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF document","*.pdf"));
        return fileChooser;
    }
    private void getPDF(File file) throws IOException {
        PDFConvertor convertor = new PDFConvertor();
        PDDocument document = convertor.convert(parameters, index, terms);
        document.save(file);
        document.close();
    }

    /**
     *
     * @param parentController - the workspace controller to create window
     * @param index - index to export
     * @param studyTerms - terms of the exporting index
     * @return initialized instance of export controller
     */
    public ExportController load(WorkspaceController parentController, SetIndex index, TermList studyTerms) {
        ExportController exportController = (ExportController) super.load(parentController);
        exportController.setExportData(index, studyTerms);
        exportController.setHeight(384);
        exportController.setWidth(512);
        return exportController;
    }
}

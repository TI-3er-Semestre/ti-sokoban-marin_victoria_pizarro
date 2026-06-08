package control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class ManualController {

    @FXML private ToggleButton btnWhatIs;
    @FXML private ToggleButton btnElements;
    @FXML private ToggleButton btnControls;
    @FXML private ToggleButton btnIndicators;
    @FXML private ToggleButton btnLevels;
    @FXML private ToggleGroup sectionGroup;

    @FXML private VBox sectionWhatIs;
    @FXML private VBox sectionElements;
    @FXML private VBox sectionControls;
    @FXML private VBox sectionIndicators;
    @FXML private VBox sectionLevels;

    private Stage stage;
    private GameController gameController;

    /**
     * Inyecta dependencias y configura los listeners de navegacion del manual.
     * @param stage          Stage principal de JavaFX
     * @param gameController controlador del modelo
     */
    public void init(Stage stage, GameController gameController) {
        this.stage = stage;
        this.gameController = gameController;
        setupSectionListeners();
        showSection(sectionWhatIs);
    }

    /**
     * Configura los listeners para mostrar la seccion activa segun el boton seleccionado.
     */
    private void setupSectionListeners() {
        sectionGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> obs,
                                Toggle oldVal, Toggle newVal) {
                if (newVal == btnWhatIs)          showSection(sectionWhatIs);
                else if (newVal == btnElements)   showSection(sectionElements);
                else if (newVal == btnControls)   showSection(sectionControls);
                else if (newVal == btnIndicators) showSection(sectionIndicators);
                else if (newVal == btnLevels)     showSection(sectionLevels);
            }
        });
    }

    /**
     * Muestra la seccion indicada y oculta las demas.
     * @param section VBox de la seccion a mostrar
     */
    private void showSection(VBox section) {
        sectionWhatIs.setVisible(false);
        sectionWhatIs.setManaged(false);
        sectionElements.setVisible(false);
        sectionElements.setManaged(false);
        sectionControls.setVisible(false);
        sectionControls.setManaged(false);
        sectionIndicators.setVisible(false);
        sectionIndicators.setManaged(false);
        sectionLevels.setVisible(false);
        sectionLevels.setManaged(false);

        section.setVisible(true);
        section.setManaged(true);
    }

    /**
     * Navega de vuelta al menu principal.
     */
    @FXML
    public void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/MainMenuView.fxml"));
            Parent root = loader.load();

            MainMenuController controller = loader.getController();
            controller.init(stage, gameController);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
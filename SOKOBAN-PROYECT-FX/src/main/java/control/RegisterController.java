package control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Difficulty;
import model.User;

import java.io.IOException;
import java.util.List;

public class RegisterController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField usernameField;
    @FXML private ToggleGroup difficultyGroup;
    @FXML private Label errorLabel;

    @FXML private ToggleButton diffBeginner;
    @FXML private ToggleButton diffAdvanced;
    @FXML private ToggleButton diffExpert;

    private Stage stage;
    private GameController gameController;

    private static final String DIFF_BASE =
            "-fx-border-width: 2;" +
                    "-fx-border-radius: 5; -fx-background-radius: 5;" +
                    "-fx-font-size: 10px; -fx-font-weight: bold;" +
                    "-fx-font-family: 'Courier New'; -fx-pref-height: 30px; -fx-cursor: hand;";

    /**
     * Inyecta dependencias y configura los estilos iniciales.
     */
    public void init(Stage stage, GameController gameController) {
        this.stage = stage;
        this.gameController = gameController;
        applyInitialDiffStyles();
        setupDifficultyListeners();
    }

    /**
     * Aplica los estilos iniciales a los botones de dificultad.
     */
    private void applyInitialDiffStyles() {
        diffBeginner.setStyle(DIFF_BASE +
                "-fx-background-color: #c8862a; -fx-border-color: #f0e060; -fx-text-fill: white;");
        diffAdvanced.setStyle(DIFF_BASE +
                "-fx-background-color: #1a1a1a; -fx-border-color: #444444; -fx-text-fill: #aaaaaa;");
        diffExpert.setStyle(DIFF_BASE +
                "-fx-background-color: #1a1a1a; -fx-border-color: #444444; -fx-text-fill: #aaaaaa;");
    }

    /**
     * Listener del ToggleGroup de dificultad — actualiza el estilo del boton activo.
     */
    private void setupDifficultyListeners() {
        difficultyGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> obs,
                                Toggle oldVal, Toggle newVal) {
                if (newVal == null) {
                    oldVal.setSelected(true);
                    return;
                }
                List<ToggleButton> all = List.of(diffBeginner, diffAdvanced, diffExpert);
                for (ToggleButton btn : all) {
                    if (btn == newVal) {
                        btn.setStyle(DIFF_BASE +
                                "-fx-background-color: #c8862a; -fx-border-color: #f0e060; -fx-text-fill: white;");
                    } else {
                        btn.setStyle(DIFF_BASE +
                                "-fx-background-color: #1a1a1a; -fx-border-color: #444444; -fx-text-fill: #aaaaaa;");
                    }
                }
            }
        });
    }

    /**
     * Valida el formulario, crea el usuario y navega al menu principal.
     */
    @FXML
    public void handleSubmit() {
        String name     = nameField.getText().trim();
        String email    = emailField.getText().trim();
        String username = usernameField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || username.isEmpty()) {
            errorLabel.setText("Completa todos los campos antes de continuar.");
            return;
        }

        // Avatar siempre fijo
        String avatar = "player_05.png";

        // Dificultad segun seleccion
        Difficulty difficulty = Difficulty.EASY;
        Toggle selectedDiff = difficultyGroup.getSelectedToggle();
        if (selectedDiff != null) {
            switch (selectedDiff.getUserData().toString()) {
                case "ADVANCED": difficulty = Difficulty.MEDIUM; break;
                case "EXPERT":   difficulty = Difficulty.HARD;   break;
                default:         difficulty = Difficulty.EASY;   break;
            }
        }

        User user = new User(name, email, username, avatar, difficulty);
        gameController.addUser(user);
        gameController.setCurrentUser(user);

        goToMenu(user);
    }

    /**
     * Navega al menu principal con el usuario recien registrado.
     */
    private void goToMenu(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/assets/views/MainMenuView.fxml"));
            Parent root = loader.load();

            MainMenuController controller = loader.getController();
            controller.init(stage, gameController);
            controller.setPlayer(user);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Difficulty;
import model.User;

import java.io.IOException;
import java.util.List;

public class RegisterController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField usernameField;
    @FXML private ToggleGroup avatarGroup;
    @FXML private ToggleGroup difficultyGroup;
    @FXML private Label errorLabel;
    @FXML private ImageView avatarPreview;
    @FXML private Label avatarNameLabel;

    // fx:id de los botones para poder cambiarles el estilo
    @FXML private ToggleButton avatar1;
    @FXML private ToggleButton avatar2;
    @FXML private ToggleButton avatar3;
    @FXML private ToggleButton avatar4;

    @FXML private ToggleButton diffBeginner;
    @FXML private ToggleButton diffAdvanced;
    @FXML private ToggleButton diffExpert;

    private Stage stage;
    private GameController gameController;

    // Estilos base para los botones (sin style inline en el FXML, los aplica el controller)
    private static final String AVATAR_BASE =
            "-fx-background-color: #1a1a1a;" +
                    "-fx-border-width: 3;" +
                    "-fx-border-radius: 8; -fx-background-radius: 8;" +
                    "-fx-padding: 3; -fx-cursor: hand;";

    private static final String DIFF_BASE =
            "-fx-border-width: 2;" +
                    "-fx-border-radius: 5; -fx-background-radius: 5;" +
                    "-fx-font-size: 10px; -fx-font-weight: bold;" +
                    "-fx-font-family: 'Courier New'; -fx-pref-height: 30px; -fx-cursor: hand;";

    public void init(Stage stage, GameController gameController) {
        this.stage = stage;
        this.gameController = gameController;
        applyInitialStyles();
        setupAvatarListeners();
        setupDifficultyListeners();
        updateAvatarPreview("player_05.png");
    }

    /**
     * Aplica los estilos iniciales a todos los botones al cargar la vista.
     */
    private void applyInitialStyles() {
        // Avatares: el primero seleccionado (amarillo), los demás gris
        avatar1.setStyle(AVATAR_BASE + "-fx-border-color: #f0e060;");
        avatar2.setStyle(AVATAR_BASE + "-fx-border-color: #444444;");
        avatar3.setStyle(AVATAR_BASE + "-fx-border-color: #444444;");
        avatar4.setStyle(AVATAR_BASE + "-fx-border-color: #444444;");

        // Dificultad: el primero seleccionado (naranja), los demás oscuro
        diffBeginner.setStyle(DIFF_BASE +
                "-fx-background-color: #c8862a; -fx-border-color: #f0e060; -fx-text-fill: white;");
        diffAdvanced.setStyle(DIFF_BASE +
                "-fx-background-color: #1a1a1a; -fx-border-color: #444444; -fx-text-fill: #aaaaaa;");
        diffExpert.setStyle(DIFF_BASE +
                "-fx-background-color: #1a1a1a; -fx-border-color: #444444; -fx-text-fill: #aaaaaa;");
    }

    /**
     * Listener del ToggleGroup de avatares — actualiza borde y preview.
     */
    private void setupAvatarListeners() {
        avatarGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> obs,
                                Toggle oldVal, Toggle newVal) {
                if (newVal == null) {
                    // Evita que se deseleccionen todos
                    oldVal.setSelected(true);
                    return;
                }
                // Actualiza bordes
                List<ToggleButton> all = List.of(avatar1, avatar2, avatar3, avatar4);
                for (ToggleButton btn : all) {
                    if (btn == newVal) {
                        btn.setStyle(AVATAR_BASE + "-fx-border-color: #f0e060;");
                    } else {
                        btn.setStyle(AVATAR_BASE + "-fx-border-color: #444444;");
                    }
                }
                // Actualiza preview
                updateAvatarPreview(newVal.getUserData().toString());
            }
        });
    }

    /**
     * Listener del ToggleGroup de dificultad — actualiza color del botón activo.
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
     * Actualiza la imagen grande del preview y el label con el nombre del avatar.
     */
    private void updateAvatarPreview(String avatarFile) {
        try {
            Image img = new Image(
                    getClass().getResourceAsStream("/assets/sprites/" + avatarFile));
            avatarPreview.setImage(img);

            String playerName = avatarFile
                    .replace("player_0", "JUGADOR ")
                    .replace(".png", "");
            avatarNameLabel.setText(playerName.toUpperCase());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Valida el formulario, crea el usuario y navega al menú principal.
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

        String avatar = "player_05.png";
        Toggle selectedAvatar = avatarGroup.getSelectedToggle();
        if (selectedAvatar != null) {
            avatar = selectedAvatar.getUserData().toString();
        }

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

        goToMenu(user);
    }

    /**
     * Navega al menú principal con el usuario recién registrado.
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
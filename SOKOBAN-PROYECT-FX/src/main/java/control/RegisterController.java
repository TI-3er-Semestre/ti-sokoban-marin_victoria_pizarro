package control;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Difficulty;
import model.User;

import java.io.IOException;

/**
 * Controlador de la pantalla de registro del jugador.
 */
public class RegisterController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField usernameField;
    @FXML private ToggleGroup avatarGroup;
    @FXML private ToggleGroup difficultyGroup;
    @FXML private Label errorLabel;
    @FXML private ImageView avatarPreview;
    @FXML private Label avatarNameLabel;

    private Stage stage;
    private GameController gameController;

    /**
     * Inyecta las dependencias necesarias desde el controlador que navega aqui.
     * @param stage          Stage principal de JavaFX
     * @param gameController controlador del modelo
     */
    public void init(Stage stage, GameController gameController) {
        this.stage = stage;
        this.gameController = gameController;
        setupAvatarListeners();
    }

    /**
     * Configura los listeners para actualizar el preview del avatar al seleccionar.
     */
    private void setupAvatarListeners() {
        if (avatarGroup == null) return;

        avatarGroup.selectedToggleProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                String avatarFile = newVal.getUserData().toString();
                updateAvatarPreview(avatarFile);
            }
        });
    }

    /**
     * Actualiza la imagen de preview segun el archivo de avatar seleccionado.
     * @param avatarFile nombre del archivo del avatar
     */
    private void updateAvatarPreview(String avatarFile) {
        try {
            Image img = new Image(
                    getClass().getResourceAsStream("/sprites/" + avatarFile));
            avatarPreview.setImage(img);

            String playerName = avatarFile.replace("player_", "JUGADOR ")
                    .replace(".png", "");
            avatarNameLabel.setText(playerName.toUpperCase());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Procesa el formulario y registra al usuario si los datos son validos.
     */
    @FXML
    public void handleSubmit() {
        String name     = nameField.getText().trim();
        String email    = emailField.getText().trim();
        String username = usernameField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || username.isEmpty()) {
            errorLabel.setText("Por favor completa todos los campos obligatorios.");
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
            String diffData = selectedDiff.getUserData().toString();
            switch (diffData) {
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
     * Navega de vuelta al menu principal con el usuario registrado.
     * @param user usuario recien registrado
     */
    private void goToMenu(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/MainMenuView.fxml"));
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
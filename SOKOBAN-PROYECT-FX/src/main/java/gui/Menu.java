package gui;

import control.SokobanController;
import model.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Vista principal del patrón MVC. Gestiona todas las pantallas de la UI
 * y delega las acciones del usuario al SokobanController.
 */
public class Menu {

    private final SokobanController controller;
    private final Stage primaryStage;

    /**
     * Crea la Vista con el controlador MVC y el Stage de JavaFX.
     * @param controller   controlador MVC que recibe las acciones del usuario
     * @param primaryStage Stage principal de JavaFX
     */
    public Menu(SokobanController controller, Stage primaryStage) {
        this.controller = controller;
        this.primaryStage = primaryStage;
    }

    /**
     * Muestra el menú principal con opciones de juego, registro y estadísticas.
     */
    public void showMenu() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #1a1a2e;");

        Label title = new Label("SOKOBAN");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        title.setTextFill(Color.web("#e0e0e0"));

        Button btnRegister = createMenuButton("Registrar Jugador");
        Button btnLevel1   = createMenuButton("Nivel 1 - Básico");
        Button btnLevel2   = createMenuButton("Nivel 2 - Avanzado");
        Button btnLevel3   = createMenuButton("Nivel 3 - Experto (Auto)");
        Button btnStats    = createMenuButton("Estadísticas");

        btnRegister.setOnAction(e -> showRegistrationScreen());
        btnLevel1.setOnAction(e -> showGameScreen(1));
        btnLevel2.setOnAction(e -> showGameScreen(2));
        btnLevel3.setOnAction(e -> showGameScreen(3));
        btnStats.setOnAction(e -> showStatsScreen());

        root.getChildren().addAll(
                title, new Label(" "),
                btnRegister, btnLevel1, btnLevel2, btnLevel3, btnStats
        );

        Scene scene = new Scene(root, 500, 600);
        primaryStage.setTitle("Sokoban - Menú Principal");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Muestra la pantalla de registro del jugador.
     */
    public void showRegistrationScreen() {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #1a1a2e;");

        Label title = new Label("Registro de Jugador");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#e0e0e0"));

        TextField fieldName     = createTextField("Nombre completo");
        TextField fieldEmail    = createTextField("Correo electrónico");
        TextField fieldUsername = createTextField("Username");
        TextField fieldAvatar   = createTextField("Avatar (texto o URL)");

        Label labelDiff = createLabel("Nivel de experiencia:");
        ToggleGroup difficultyGroup = new ToggleGroup();
        RadioButton radioEasy   = new RadioButton("Principiante");
        RadioButton radioMedium = new RadioButton("Avanzado");
        RadioButton radioHard   = new RadioButton("Experto");
        radioEasy.setToggleGroup(difficultyGroup);
        radioMedium.setToggleGroup(difficultyGroup);
        radioHard.setToggleGroup(difficultyGroup);
        radioEasy.setSelected(true);
        styleRadioButton(radioEasy);
        styleRadioButton(radioMedium);
        styleRadioButton(radioHard);

        HBox difficultyBox = new HBox(20, radioEasy, radioMedium, radioHard);
        difficultyBox.setAlignment(Pos.CENTER);

        Label labelError = new Label("");
        labelError.setTextFill(Color.web("#ff6b6b"));

        Button btnRegister = createMenuButton("Registrar");
        Button btnBack     = createMenuButton("Volver");

        btnBack.setOnAction(e -> showMenu());
        btnRegister.setOnAction(e -> {
            String name     = fieldName.getText().trim();
            String email    = fieldEmail.getText().trim();
            String username = fieldUsername.getText().trim();
            String avatar   = fieldAvatar.getText().trim();

            if (name.isEmpty() || email.isEmpty() || username.isEmpty()) {
                labelError.setText("Por favor completa todos los campos obligatorios.");
                return;
            }

            Difficulty difficulty;
            if (radioMedium.isSelected())    difficulty = Difficulty.MEDIUM;
            else if (radioHard.isSelected()) difficulty = Difficulty.HARD;
            else                             difficulty = Difficulty.EASY;

            User user = new User(name, email, username,
                    avatar.isEmpty() ? "default" : avatar, difficulty);
            controller.registerUser(user);
            showAlert("Registro exitoso", "¡Bienvenido, " + name + "!");
        });

        root.getChildren().addAll(
                title, fieldName, fieldEmail, fieldUsername, fieldAvatar,
                labelDiff, difficultyBox, labelError, btnRegister, btnBack
        );

        Scene scene = new Scene(root, 500, 600);
        primaryStage.setTitle("Sokoban - Registro");
        primaryStage.setScene(scene);
    }

    /**
     * Muestra la pantalla de juego para el nivel indicado y conecta los callbacks MVC.
     * @param levelId ID del nivel a cargar (1, 2 o 3)
     */
    public void showGameScreen(int levelId) {
        // Pendiente de implementar
        showAlert("En desarrollo", "Pantalla de juego próximamente.");
    }

    /**
     * Muestra la pantalla de estadísticas con el historial del árbol binario.
     */
    public void showStatsScreen() {
        // Pendiente de implementar
        showAlert("En desarrollo", "Pantalla de estadísticas próximamente.");
    }

    /**
     * Selecciona un nivel directamente por ID.
     * @param levelId ID del nivel a cargar
     */
    public void selectLevel(int levelId) {
        showGameScreen(levelId);
    }

    // ── Helpers de UI ─────────────────────────────────────────────────────────

    private Button createMenuButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(280);
        btn.setPrefHeight(45);
        btn.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        btn.setStyle(
                "-fx-background-color: #16213e; -fx-text-fill: #e0e0e0; "
                        + "-fx-border-color: #4a90d9; -fx-border-width: 2; "
                        + "-fx-border-radius: 5; -fx-background-radius: 5;"
        );
        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: #4a90d9; -fx-text-fill: #ffffff; "
                        + "-fx-border-color: #4a90d9; -fx-border-width: 2; "
                        + "-fx-border-radius: 5; -fx-background-radius: 5;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: #16213e; -fx-text-fill: #e0e0e0; "
                        + "-fx-border-color: #4a90d9; -fx-border-width: 2; "
                        + "-fx-border-radius: 5; -fx-background-radius: 5;"
        ));
        return btn;
    }

    private TextField createTextField(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setPrefWidth(320);
        field.setStyle(
                "-fx-background-color: #16213e; -fx-text-fill: #e0e0e0; "
                        + "-fx-prompt-text-fill: #888888; -fx-border-color: #4a90d9; "
                        + "-fx-border-radius: 4; -fx-background-radius: 4;"
        );
        return field;
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setTextFill(Color.web("#e0e0e0"));
        label.setFont(Font.font("Arial", 14));
        return label;
    }

    private void styleRadioButton(RadioButton rb) {
        rb.setTextFill(Color.web("#e0e0e0"));
        rb.setFont(Font.font("Arial", 14));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
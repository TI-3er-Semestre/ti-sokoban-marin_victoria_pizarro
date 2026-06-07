package gui;

import control.SokobanController;
import model.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.Cell;


public class Menu {

    private final SokobanController controller;
    private final Stage primaryStage;

    private static final int CELL_SIZE = 60;

    private static final Color COLOR_WALL     = Color.web("#3d3d3d");
    private static final Color COLOR_FLOOR    = Color.web("#f5e6c8");
    private static final Color COLOR_GOAL     = Color.web("#a8d8a8");
    private static final Color COLOR_BOX      = Color.web("#c8a464");
    private static final Color COLOR_BOX_GOAL = Color.web("#6aaa6a");
    private static final Color COLOR_PLAYER   = Color.web("#4a90d9");

    /** Canvas donde se dibuja el tablero durante el juego. */
    private Canvas boardCanvas;

    private Label labelMoves;
    private Label labelPushes;
    private Label labelLevel;

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
        controller.loadLevel(levelId);
        Game game = controller.getGame();
        if (game == null || game.getBoard() == null) return;

        Board board = game.getBoard();
        boardCanvas = new Canvas(board.getWidth() * CELL_SIZE, board.getHeight() * CELL_SIZE);

        labelMoves  = createLabel("Movimientos: 0");
        labelPushes = createLabel("Empujes: 0");
        labelLevel  = createLabel("Nivel: " + levelId);

        HBox statsBar = new HBox(30, labelLevel, labelMoves, labelPushes);
        statsBar.setAlignment(Pos.CENTER);
        statsBar.setPadding(new Insets(10));
        statsBar.setStyle("-fx-background-color: #16213e;");

        Button btnUndo     = createGameButton("Deshacer (U)");
        Button btnReset    = createGameButton("Reiniciar (R)");
        Button btnMainMenu = createGameButton("Menú");

        btnUndo.setOnAction(e     -> controller.undoMove());
        btnReset.setOnAction(e    -> controller.resetLevel());
        btnMainMenu.setOnAction(e -> { controller.stopAutoSolve(); showMenu(); });

        HBox actionBar = new HBox(15, btnUndo, btnReset, btnMainMenu);
        actionBar.setAlignment(Pos.CENTER);
        actionBar.setPadding(new Insets(10));

        VBox root = new VBox(0, statsBar, boardCanvas, actionBar);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #0f3460;");

        controller.setOnBoardChanged(() -> { drawBoard(); updateStats(); });
        controller.setOnGameWon(() -> showVictoryScreen(levelId));
        controller.setOnNoSolutionFound(() ->
                showAlert("Sin solución", "No se encontró solución para este estado."));

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            switch (code) {
                case UP:    case W: controller.handleKeyInput("UP");    break;
                case DOWN:  case S: controller.handleKeyInput("DOWN");  break;
                case LEFT:  case A: controller.handleKeyInput("LEFT");  break;
                case RIGHT: case D: controller.handleKeyInput("RIGHT"); break;
                case Z:     case U: controller.undoMove();              break;
                case R:             controller.resetLevel();            break;
                default: break;
            }
        });

        primaryStage.setTitle("Sokoban - Nivel " + levelId);
        primaryStage.setScene(scene);
        drawBoard();
        updateStats();
        boardCanvas.setFocusTraversable(true);
        boardCanvas.requestFocus();
    }

    /**
     * Dibuja el estado actual del tablero en el canvas: fondo, cajas y jugador.
     */
    private void drawBoard() {
        Game game = controller.getGame();
        if (game == null || game.getBoard() == null || boardCanvas == null) return;

        Board board   = game.getBoard();
        Player player = game.getPlayer();
        GraphicsContext gc = boardCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, boardCanvas.getWidth(), boardCanvas.getHeight());

        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                Cell cell = board.getCell(x, y);
                if (cell == null) continue;

                double px = x * CELL_SIZE;
                double py = y * CELL_SIZE;

                if (cell.getType() == CellType.WALL) gc.setFill(COLOR_WALL);
                else if (cell.isGoal())              gc.setFill(COLOR_GOAL);
                else                                 gc.setFill(COLOR_FLOOR);
                gc.fillRect(px, py, CELL_SIZE, CELL_SIZE);
                gc.setStroke(Color.web("#2a2a2a"));
                gc.setLineWidth(1);
                gc.strokeRect(px, py, CELL_SIZE, CELL_SIZE);

                if (cell.hasBox()) {
                    gc.setFill(cell.isGoal() ? COLOR_BOX_GOAL : COLOR_BOX);
                    gc.fillRoundRect(px + 5, py + 5, CELL_SIZE - 10, CELL_SIZE - 10, 8, 8);
                    gc.setStroke(Color.web("#5a3a10"));
                    gc.setLineWidth(2);
                    gc.strokeRoundRect(px + 5, py + 5, CELL_SIZE - 10, CELL_SIZE - 10, 8, 8);
                }

                if (player != null
                        && player.getPosition().getX() == x
                        && player.getPosition().getY() == y) {
                    gc.setFill(COLOR_PLAYER);
                    gc.fillOval(px + 8, py + 8, CELL_SIZE - 16, CELL_SIZE - 16);
                    gc.setStroke(Color.web("#1a5fa0"));
                    gc.setLineWidth(2);
                    gc.strokeOval(px + 8, py + 8, CELL_SIZE - 16, CELL_SIZE - 16);
                }
            }
        }
    }

    /**
     * Actualiza las etiquetas de estadísticas desde el modelo.
     */
    private void updateStats() {
        GameStats stats = controller.getStats();
        if (stats == null || labelMoves == null) return;
        labelMoves.setText("Movimientos: " + stats.getMoves());
        labelPushes.setText("Empujes: " + stats.getPushes());
    }

    /**
     * Muestra la pantalla de victoria cuando se completa un nivel.
     * @param levelId ID del nivel completado
     */
    public void showVictoryScreen(int levelId) {
        GameStats stats = controller.getStats();

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #0d4f0d;");

        Label labelWin = new Label("¡Nivel completado!");
        labelWin.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        labelWin.setTextFill(Color.web("#a8ff78"));

        Label labelStats = new Label(stats != null
                ? "Movimientos: " + stats.getMoves() + "  |  Empujes: " + stats.getPushes()
                : "");
        labelStats.setFont(Font.font("Arial", 18));
        labelStats.setTextFill(Color.web("#e0e0e0"));

        Button btnNext     = createMenuButton(levelId < 3 ? "Siguiente nivel" : "Menú principal");
        Button btnMainMenu = createMenuButton("Menú principal");

        btnNext.setOnAction(e     -> { if (levelId < 3) showGameScreen(levelId + 1); else showMenu(); });
        btnMainMenu.setOnAction(e -> showMenu());

        root.getChildren().addAll(labelWin, labelStats, btnNext, btnMainMenu);

        Scene scene = new Scene(root, 500, 350);
        primaryStage.setTitle("Sokoban - ¡Victoria!");
        primaryStage.setScene(scene);
    }

    /**
     * Muestra la pantalla de estadísticas con el historial del árbol binario.
     */
    public void showStatsScreen() {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #1a1a2e;");

        Label title = new Label("Estadísticas");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        title.setTextFill(Color.web("#e0e0e0"));

        Game game = controller.getGame();
        String statsText = "No hay partidas registradas.";
        if (game != null && game.getStatsTree() != null
                && game.getStatsTree().getTotalNodes() > 0) {
            statsText = "Partidas (mejor a peor):\n" + game.getStatsTree().inorder();
        }

        Label labelStats = new Label(statsText);
        labelStats.setTextFill(Color.web("#b0b0b0"));
        labelStats.setFont(Font.font("Arial", 14));
        labelStats.setWrapText(true);

        Button btnBack = createMenuButton("Volver");
        btnBack.setOnAction(e -> showMenu());

        root.getChildren().addAll(title, labelStats, btnBack);

        Scene scene = new Scene(root, 500, 400);
        primaryStage.setTitle("Sokoban - Estadísticas");
        primaryStage.setScene(scene);
    }

    /**
     * Selecciona un nivel directamente por ID.
     * @param levelId ID del nivel a cargar
     */
    public void selectLevel(int levelId) {
        showGameScreen(levelId);
    }



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

    private Button createGameButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(130);
        btn.setPrefHeight(35);
        btn.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        btn.setStyle(
                "-fx-background-color: #0f3460; -fx-text-fill: #e0e0e0; "
                        + "-fx-border-color: #4a90d9; -fx-border-width: 1.5; "
                        + "-fx-border-radius: 4; -fx-background-radius: 4;"
        );
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
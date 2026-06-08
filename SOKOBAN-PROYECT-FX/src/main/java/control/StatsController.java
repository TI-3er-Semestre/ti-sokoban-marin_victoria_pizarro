package control;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.TableColumn.CellDataFeatures;
import model.GameStats;
import structures.BSTNode;

import java.io.IOException;


public class StatsController {

    @FXML private ToggleButton tabLevel1;
    @FXML private ToggleButton tabLevel2;
    @FXML private ToggleButton tabLevel3;
    @FXML private ToggleGroup levelTabGroup;
    @FXML private TableView<GameStats> rankingTable;
    @FXML private TableColumn<GameStats, Integer> colRank;
    @FXML private TableColumn<GameStats, Integer> colMoves;
    @FXML private TableColumn<GameStats, Integer> colPushes;
    @FXML private TableColumn<GameStats, String>  colTime;
    @FXML private Label personalBestLabel;

    private Stage stage;
    private GameController gameController;

    /**
     * Inyecta dependencias e inicializa la tabla de ranking.
     * @param stage          Stage principal de JavaFX
     * @param gameController controlador del modelo
     */
    public void init(Stage stage, GameController gameController) {
        this.stage = stage;
        this.gameController = gameController;

        setupTable();
        setupTabListeners();
        loadStats(1);
    }

    /**
     * Configura las columnas de la tabla de ranking.
     */
    private void setupTable() {
        colRank.setCellValueFactory(new Callback<CellDataFeatures<GameStats, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(CellDataFeatures<GameStats, Integer> data) {
                return new SimpleIntegerProperty(
                        rankingTable.getItems().indexOf(data.getValue()) + 1).asObject();
            }
        });

        colMoves.setCellValueFactory(new Callback<CellDataFeatures<GameStats, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(CellDataFeatures<GameStats, Integer> data) {
                return new SimpleIntegerProperty(data.getValue().getMoves()).asObject();
            }
        });

        colPushes.setCellValueFactory(new Callback<CellDataFeatures<GameStats, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(CellDataFeatures<GameStats, Integer> data) {
                return new SimpleIntegerProperty(data.getValue().getPushes()).asObject();
            }
        });

        colTime.setCellValueFactory(new Callback<CellDataFeatures<GameStats, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<GameStats, String> data) {
                int secs = (int) data.getValue().getTime();
                return new SimpleStringProperty(
                        String.format("%02d:%02d", secs / 60, secs % 60));
            }
        });
    }

    /**
     * Configura los listeners de las pestanas de nivel.
     */
    private void setupTabListeners() {
        levelTabGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> obs, Toggle oldVal, Toggle newVal) {
                if (newVal != null) {
                    int levelId = Integer.parseInt(newVal.getUserData().toString());
                    loadStats(levelId);
                }
            }
        });
    }

    /**
     * Carga las estadisticas del nivel indicado en la tabla.
     * @param levelId ID del nivel a mostrar
     */
    private void loadStats(int levelId) {
        ObservableList<GameStats> data = FXCollections.observableArrayList();

        if (gameController.getGame().getStatsTree() != null) {
            collectInorder(
                    gameController.getGame().getStatsTree().getRoot(),
                    levelId,
                    data);
        }

        rankingTable.setItems(data);

        if (!data.isEmpty()) {
            GameStats best = data.get(0);
            personalBestLabel.setText(
                    best.getMoves() + " movs  |  " + best.getPushes() + " empujes");
        } else {
            personalBestLabel.setText("—");
        }
    }

    /**
     * Recorre el arbol en inorden y agrega al observable list los stats del nivel indicado.
     * @param node    nodo actual del recorrido
     * @param levelId nivel a filtrar
     * @param data    lista donde se agregan los resultados
     */
    private void collectInorder(BSTNode<GameStats> node, int levelId,
                                ObservableList<GameStats> data) {
        if (node == null) return;

        collectInorder(node.getLeft(), levelId, data);

        if (node.getData().getLevelId() == levelId) {
            data.add(node.getData());
        }

        collectInorder(node.getRight(), levelId, data);
    }

    /**
     * Navega de vuelta al menu principal.
     */
    @FXML
    public void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/assets/views/MainMenuView.fxml"));
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
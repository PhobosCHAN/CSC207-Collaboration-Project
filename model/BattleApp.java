package model;
import view.viewStart;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * A Tetris Application, in JavaFX
 *
 * Based on the Tetris assignment in the Nifty Assignments Database, authored by Nick Parlante
 */
public class BattleApp extends Application {
    BattleModel model;
    viewStart startView;

    /**
     * Main method
     *
     * @param args argument, if any
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start method.  Control of application flow is dictated by JavaFX framework
     *
     * @param primaryStage stage upon which to load GUI elements
     */
    @Override
    public void start(Stage primaryStage) {
        viewStart start = new viewStart(primaryStage);
    }

}
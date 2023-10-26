package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Classe principal que inicia o player de música.
 */
public class MusicPlayer extends Application {

    /**
     * Método de inicialização da aplicação JavaFX.
     * @param stage O palco principal da aplicação.
     * @throws Exception Exceção lançada em caso de erro durante a inicialização.
     */
    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/view/MainView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent arg0) {
                Platform.exit();
                System.exit(0);
            }
        });
    }

    /**
     * Método principal que inicia a aplicação.
     * @param args Argumentos de linha de comando.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

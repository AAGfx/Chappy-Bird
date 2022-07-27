package application;

import java.io.IOException;
import java.net.URISyntaxException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("Frame.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Chappy Bird");
		primaryStage.setResizable(false);

		try {
			primaryStage.getIcons().add(new Image(getClass().getResource("/resources/bird.png").toURI().toString()));
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}

		primaryStage.centerOnScreen();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent e) {

				Platform.exit();
				System.exit(0);
			}
		});

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Input {

	private Pane pane;
	private Button btn;
	private TextField tf;
	private Label info;
	File table;
	PrintWriter pw;
	Scanner scan;
	Scene scene;
	Stage stage;

	public Input(double score) throws IOException {

		this.pane = new Pane();
		this.pane.setPrefHeight(200);
		this.pane.setPrefWidth(300);

		this.btn = new Button("OK");
		this.btn.setPrefHeight(25);
		this.btn.setPrefWidth(50);
		this.btn.setLayoutX(this.pane.getPrefWidth() / 2 - this.btn.getPrefWidth() / 2);
		this.btn.setLayoutY(this.pane.getPrefHeight() - this.btn.getPrefHeight() * 2);

		this.tf = new TextField();
		this.tf.setPromptText("ex. SuperBird");
		this.tf.setPrefHeight(25);
		this.tf.setPrefWidth(150);
		this.tf.setLayoutX(this.pane.getPrefWidth() / 2 - this.tf.getPrefWidth() / 2);
		this.tf.setLayoutY(this.pane.getPrefHeight() / 2 - this.tf.getPrefHeight() / 2);

		this.info = new Label("Enter Your Nickname");
		this.info.setPrefWidth(190);
		this.info.setFont(Font.font(20));
		this.info.setLayoutX(this.pane.getPrefWidth() / 2 - this.info.getPrefWidth() / 2);
		this.info.setLayoutY(20);

		this.pane.getChildren().addAll(btn, tf, info);

		scene = new Scene(pane, 300, 200);
		stage = new Stage();
		stage.setScene(scene);
		stage.setResizable(true);
		stage.show();

		this.btn.setOnMousePressed(event -> {
			try {
				String nick = new String();
				if (!tf.getText().isEmpty()) {
					nick = tf.getText();
					addToList(score, nick);
					stage.close();
				} else {
					Alert alertFile = new Alert(AlertType.ERROR);
					alertFile.setTitle("ERROR");
					alertFile.setHeaderText("Enter Your Nick!");
					alertFile.showAndWait();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	}

	public void addToList(double score, String nick) throws IOException {

		table = new File("Scores.txt");
		pw = new PrintWriter(new FileWriter(table, true));
		scan = new Scanner(table);

		if (!scan.hasNext()) {
			table.createNewFile();
			pw.printf("%d %s \n", (int)score, nick);

			Alert alertFile = new Alert(AlertType.INFORMATION);
			alertFile.setTitle("INFO");
			alertFile.setHeaderText("Board created! " + table.getName());
			alertFile.showAndWait();
			System.out.println("File created: " + table.getName());

		} else {
			pw.printf("%d %s \n", (int)score, nick);
			System.out.println("File already exists." + table.getName());
		}
		pw.close();
		scan.close();

		new LeaderBoard();
	}

}

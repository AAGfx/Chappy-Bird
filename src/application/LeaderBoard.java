package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LeaderBoard {

	File table;
	Scanner scan;
	PrintWriter pw;
	VBox pane;
	Text text;
	Text textLeader;
	ArrayList<Text> scores;
	Button bt;
	Scene scene;
	Stage stage;

	public LeaderBoard() throws IOException {

		table = new File("Scores.txt");
		scan = new Scanner(table);
		
		pane = new VBox();
		textLeader = new Text("BEST 10");
		textLeader.setFont(Font.font(30));
		pane.getChildren().add(textLeader);

		scores = new ArrayList<Text>();
		while(scan.hasNext()) {
			String temp = scan.nextLine();
			String score = (temp.split(" "))[0];
			String nickname = (temp.split(" "))[1];
			
			text = new Text();
			text.setText("[" + score + "]" + " ---> \" " + nickname + " \"");
			text.setFont(Font.font(15));
			scores.add(text);
		}

		sort();
		addToPane();

		bt = new Button("Clear All");
		bt.setOnMouseClicked(e -> {
			try {
				clear();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		pane.getChildren().add(bt);
		
		scan.close();

		pane.setPadding(new Insets(30));
		pane.setStyle("-fx-background-color:orange");
		scene = new Scene(pane, 250, 500);
		stage = new Stage();
		stage.setTitle("Leaderboard");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	private void addToPane() {
		for (int i = 0; i < scores.size(); i++) {
			if(i < 10) {
				pane.getChildren().add(scores.get(i));	
			}
		}
	}

	public void sort() {
		for (int i = 0; i < scores.size(); i++) {
			
			String scorePart1 = ((Text)(scores.get(i))).getText().split(" ")[0];
			String score1 = ((scorePart1).substring(1, scorePart1.length() - 1));
			int sc1 = Integer.parseInt(score1);
			
			for (int j = 1 + i; j < scores.size(); j++) {
				
				String scorePart2 = ((Text)(scores.get(j))).getText().split(" ")[0];
				String score2 = ((scorePart2).substring(1, scorePart2.length() - 1));
				int sc2 = Integer.parseInt(score2);

				if(sc1 < sc2) {
					Collections.swap(scores, i, j);
					sc1 = sc2;
					/*
					Text temp = scores.get(j);
					scores.set(j, scores.get(i));
					scores.set(i, temp);
					*/
				}
			}
		}
	}

	public void clear() throws IOException {
		table = new File("Scores.txt");
		pw = new PrintWriter(new FileWriter(table, false));
		pw.print("");
		pw.close();
		stage.close();
	}
}

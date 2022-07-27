package application;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Frame implements Initializable {

	@FXML
	private AnchorPane gameFrame;
	@FXML
	private ImageView bird;
	@FXML
	private Label label;
	@FXML
	private Label labelScore;

	static final double earthG = 0.00098;
	static final double velocity = 5.0;
	int duration;
	double score;
	double initBirdX;
	double initBirdY;
	double birdX;
	double birdY;
	double VeloY;
	double AcceY;
	boolean run;
	Random random;
	Timeline tl;
	Timeline tl2;
	Timeline tl3;
	Timeline tl4;
	Timeline tl5;
	ArrayList<ImageView> iwList;
	File file;
	File files[];
	String directory;
	//File file1;
	MediaPlayer mp;
	Media media;
	//MediaPlayer mp1;
	Image image;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		image = new Image("/resources/bird.png");
		bird.setImage(image);
		initBirdX = bird.getX();
		initBirdY = bird.getY();
		birdX = bird.getX();
		birdY = bird.getY();
		VeloY = 0;
		AcceY = earthG;
		run = false;
		score = 0;
		duration = 1000;
		random = new Random();
		iwList = new ArrayList<ImageView>();

		file = new File("src/resources");
		
		/*
		try {
			mp = new MediaPlayer(new Media(file.toURI().toString()));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		*/
		
		files = file.listFiles();
		if(files != null) {
			
			for (File file : files) {
				if(file.getName().contains("sound")) {
					try {
						directory = file.toURI().toString();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		media = new Media(directory);
		mp = new MediaPlayer(media);
		
		//file1 = new File("src/resources/jump.mp3");
		//mp1 = new MediaPlayer(new Media(file1.toURI().toString()));

		//changeColor();
		tl = new Timeline(new KeyFrame(Duration.millis(1), e -> {
			try {
				game();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}));
		tl.setCycleCount(Timeline.INDEFINITE);

		tl2 = new Timeline(new KeyFrame(Duration.millis(200), e -> changeColor()));
		tl2.setCycleCount(Timeline.INDEFINITE);
		tl2.play();

		gameFrame.setOnMousePressed(event -> start());
	}

	public void changeColor() {
		label.setTextFill(Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble()));
	}

	public void start() {	

		gameFrame.setDisable(true);
		mp.stop();
		deleteAll();
		
		run = true;
		label.setVisible(false);
		bird.setVisible(true);
		tl.play();

		tl3 = new Timeline(new KeyFrame(Duration.millis(duration / 4), e -> callcloud()));
		tl3.setCycleCount(Timeline.INDEFINITE);
		tl3.play();

		score = 0;
		tl5 = new Timeline(new KeyFrame(Duration.millis(10), e -> {
			score++;
			labelScore.setText("Score : " + (int)score);
		}));

		tl5.setCycleCount(Timeline.INDEFINITE);
		tl5.play();
	}

	public void callcloud() {

		ImageView iw = new ImageView(new Image("/resources/cloud1.png"));
		gameFrame.getChildren().add(iw);
		iw.setLayoutX(1000);
		iw.setLayoutY(0 + random.nextInt((int) (600 - iw.getFitHeight())));
		iw.setFitHeight(37);
		iw.setFitWidth(50);
		iwList.add(iw);
		iw.setVisible(true);

		tl4 = new Timeline(new KeyFrame(Duration.millis(8), e -> translateCloud(iw)));
		tl4.setCycleCount(Timeline.INDEFINITE);
		tl4.play();
	}

	public void translateCloud(ImageView iw) {
		iw.setLayoutX(iw.getLayoutX() - velocity);
	}

	public void game() throws IOException {

		if(run) {
			bird.setVisible(true);
			VeloY += AcceY;
			bird.setY(bird.getY() + VeloY);
		}

		double birdR = bird.getX() + bird.getFitWidth();
		double birdL = bird.getX();
		double birdU = bird.getY();
		double birdD = bird.getY() + bird.getFitHeight();

		for (int i = 0; i < iwList.size(); i++) {

			double cloudR = iwList.get(i).getLayoutX() + iwList.get(i).getFitWidth() - 1;
			double cloudL = iwList.get(i).getLayoutX() + 1;
			double cloudU = iwList.get(i).getLayoutY() + 5;
			double cloudD = iwList.get(i).getLayoutY() + iwList.get(i).getFitHeight() - 5;
			
			if(     ((birdR > cloudL && birdR < cloudR) && (birdD > cloudU && birdD < cloudD)) 
					|| ((birdL > cloudL && birdL < cloudR) && (birdD > cloudU && birdD < cloudD)) 
					|| ((birdR > cloudL && birdR < cloudR) && (birdU > cloudU && birdU < cloudD)) 
					|| ((birdL > cloudL && birdL < cloudR) && (birdU > cloudU && birdU < cloudD))
					) 
			{gameOver();}
		}

		if(bird.getY() > gameFrame.getHeight() || bird.getY() + bird.getFitHeight() < 0) {
			gameOver();
		}

		gameFrame.getScene().setOnKeyPressed(event -> {
			//mp1.seek(Duration.seconds(0));
			toUp();
		});
	}

	public void toUp() {
		VeloY = -0.4;
		//mp1.play();
	}

	public void gameOver() throws IOException {
		tl.stop();
		tl2.stop();
		tl3.stop();
		tl4.stop();
		tl5.stop();
		
		gameFrame.setDisable(false);
		run = false;
		label.setText("Game Over");
		label.setVisible(true);
		bird.setX(initBirdX);
		bird.setY(initBirdY);
		VeloY = 0;

		mp.play();
		deleteAll();

		new Input(score);
	}

	public void deleteAll() {

		for (int i = 0; i < gameFrame.getChildren().size(); i++) {
			if(gameFrame.getChildren().get(i).getClass().getSimpleName().equals("ImageView") && gameFrame.getChildren().get(i).getId() == null) {
				gameFrame.getChildren().remove(i);
				i--;
			}
		}

		iwList.clear();
	}
}


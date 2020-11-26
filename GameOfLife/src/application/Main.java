package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Main extends Application {
	Timeline timeline = new Timeline();


	private double frameDuration = 100;


	private Text gridSizeLabel = new Text();// changed to text for better control
	private Text frameDurationLabel = new Text();// changed to text for better control


	private int frameCount;


	private CircleList<CircleList<Life>> cellsList = new CircleList<CircleList<Life>>();
	private Grid grid = new Grid(cellsList);
	boolean running = false;

	// holds all the containers for the various screen nodes
	VBox displayPane = new VBox();
	// game title and effects
	HBox titleBox = new HBox();
	Text gameTitle = new Text();
	DropShadow titleShadow = new DropShadow();
	// holds the HBox that holds the game
	HBox gameBox = new HBox();
	// holds the HBox that holds the sliders
	HBox slideBox = new HBox();
	// holds the nodes for the controls for the game
	HBox controlBox = new HBox();
	// game buttons
	Button btnStart = new Button();
	Button btnPause = new Button();
	Button btnClear = new Button();
	Button btnSave = new Button();
	Button btnLoad = new Button();
	Button btnCounter = new Button();

//	variables to the data folder and file - update folder directory
	private String DATAFOLDER = "C:\\Users\\levan3\\git\\diehard2\\GameOfLife\\src\\data\\";
//	holds the game grid
	private ScrollPane scrollPane = new ScrollPane();
	private GridPane gridPane = new GridPane();


	private Slider gridSizeSlider;
	private Slider frameDurationSlider;
	private int gridSize = 1;

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			frameCount = 0;
			grid.makeGrid();

//			slideHeight and slideWidth variables to constrain proportions and stop scroll bar being visible
			int slideWidth = 5;
			int slideHeight = 70;
// 			set the slider text, position, colour and font
			gridSizeLabel.setText("Grid Size");
			gridSizeLabel.setFill(Color.CYAN);
			gridSizeLabel.setFont(Font.font("Helvetica", 12));
			gridSizeLabel.setTranslateX(gridSizeLabel.getX() - 35);
//			set values for display and function
			gridSizeSlider = new Slider(1, 10, gridSize);
			gridSizeSlider.setValue(1);
			gridSizeSlider.setPrefSize(slideWidth, slideHeight);
			gridSizeSlider.setMinSize(slideWidth, slideHeight);
			gridSizeSlider.setMaxSize(slideWidth, slideHeight);
			gridSizeSlider.setShowTickLabels(false);
			gridSizeSlider.setShowTickMarks(false);
			gridSizeSlider.setSnapToTicks(true);
			gridSizeSlider.setMajorTickUnit(1);
			gridSizeSlider.setMinorTickCount(1);
			gridSizeSlider.setBlockIncrement(1);
			gridSizeSlider.setPadding(new Insets(0, 40, 3, 10));
			gridSizeSlider.setOrientation(Orientation.VERTICAL);
			gridSizeSlider.valueProperty().addListener(sizeChange());
//			 set the slider text, position, colour and font
			frameDurationLabel.setText("Speed");
			frameDurationLabel.setFill(Color.CYAN);
			frameDurationLabel.setFont(Font.font("Helvetica", 12));
			frameDurationLabel.setTranslateX(frameDurationLabel.getX() - 15);
//			set values for display and function
			frameDurationSlider = new Slider(50, 300, frameDuration);
			frameDurationSlider.setValue(50);
			frameDurationSlider.setPrefSize(slideWidth, slideHeight);
			frameDurationSlider.setMinSize(slideWidth, slideHeight);
			frameDurationSlider.setMaxSize(slideWidth, slideHeight);
			frameDurationSlider.setShowTickLabels(false);
			frameDurationSlider.setShowTickMarks(false);
			frameDurationSlider.setSnapToTicks(true);
			frameDurationSlider.setMajorTickUnit(100);
			frameDurationSlider.setMinorTickCount(100);
			frameDurationSlider.setBlockIncrement(100);
			frameDurationSlider.setPadding(new Insets(0, 10, 3, 10));
			frameDurationSlider.setOrientation(Orientation.VERTICAL);
			frameDurationSlider.valueProperty().addListener(speedChange());
//			***********************************************************************************************************************
//			background Images
			FileInputStream loadBGimg = new FileInputStream(DATAFOLDER + "geoOverlay.jpg");
			FileInputStream loadGradient = new FileInputStream(DATAFOLDER + "bgGrad.jpg");
//			main background pattern
			BackgroundImage backgroundImage = new BackgroundImage(new Image(loadBGimg, 150, 150, true, true),
					BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
//			background gradient image loaded in the gridPane to be exposed by the live cells
			BackgroundImage gradientBG = new BackgroundImage(
					new Image(loadGradient, gameBox.getWidth(), gameBox.getHeight(), true, true),
					BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
//			background image for the sliders
			BackgroundImage slide = new BackgroundImage(new Image(loadGradient, 902, 105, false, false),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
					BackgroundSize.DEFAULT);
//			***********************************************************************************************************************
//			set images for buttons
			FileInputStream loadStart = new FileInputStream(DATAFOLDER + "play.png");
			FileInputStream loadPause = new FileInputStream(DATAFOLDER + "pause.png");
			FileInputStream loadSave = new FileInputStream(DATAFOLDER + "save.png");
			FileInputStream loadClear = new FileInputStream(DATAFOLDER + "clear.png");
			FileInputStream loadLoad = new FileInputStream(DATAFOLDER + "load.png");

			Image tmpStart = new Image(loadStart, 50, 50, true, true);
			Image tmpPause = new Image(loadPause, 50, 50, true, true);
			Image tmpSave = new Image(loadSave, 50, 50, true, true);
			Image tmpClear = new Image(loadClear, 50, 50, true, true);
			Image tmpLoad = new Image(loadLoad, 50, 50, true, true);

			ImageView start = new ImageView(tmpStart);
			ImageView pause = new ImageView(tmpPause);
			ImageView save = new ImageView(tmpSave);
			ImageView clear = new ImageView(tmpClear);
			ImageView load = new ImageView(tmpLoad);

			HBox.setMargin(btnStart, new Insets(5, 5, 25, 5));
			HBox.setMargin(btnPause, new Insets(5, 5, 25, 5));
			HBox.setMargin(btnClear, new Insets(5, 5, 25, 5));
			HBox.setMargin(btnSave, new Insets(5, 5, 25, 5));
			HBox.setMargin(btnLoad, new Insets(5, 5, 25, 5));
			HBox.setMargin(btnCounter, new Insets(5, 5, 25, 115));
			HBox.setMargin(slideBox, new Insets(5, 115, 25, 5));
//			start button replace the built in button for the image
			btnStart.setGraphic(start);
			btnStart.setStyle("-fx-border-color: transparent;\n" + "-fx-border-width: 0;\n"
					+ "-fx-background-radius: 0;\n" + "-fx-background-color: transparent;");
			btnStart.setPrefSize(50, 50);
			btnStart.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> startGame());
//			pause button replace the built in button for the image
			btnPause.setGraphic(pause);
			btnPause.setStyle("-fx-border-color: transparent;\n" + "-fx-border-width: 0;\n"
					+ "-fx-background-radius: 0;\n" + "-fx-background-color: transparent;");
			btnPause.setPrefSize(50, 50);
			btnPause.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> stopGame());
//			save button replace the built in button for the image
			btnSave.setGraphic(save);
			btnSave.setStyle("-fx-border-color: transparent;\n" + "-fx-border-width: 0;\n"
					+ "-fx-background-radius: 0;\n" + "-fx-background-color: transparent;");
			btnSave.setPrefSize(50, 50);
			btnSave.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> saveShape(primaryStage));
//			load button replace the built in button for the image
			btnLoad.setGraphic(load);
			btnLoad.setStyle("-fx-border-color: transparent;\n" + "-fx-border-width: 0;\n"
					+ "-fx-background-radius: 0;\n" + "-fx-background-color: transparent;");
			btnLoad.setPrefSize(50, 50);
			btnLoad.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> loadShape(primaryStage));
//			clear button replace the built in button for the image
			btnClear.setGraphic(clear);
			btnClear.setStyle("-fx-border-color: transparent;\n" + "-fx-border-width: 0;\n"
					+ "-fx-background-radius: 0;\n" + "-fx-background-color: transparent;");
			btnClear.setPrefSize(50, 50);
			btnClear.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> clear());
//			set the button to transparent and set the frameCount variable as the displayed text 
			btnCounter.setText("" + frameCount + "");
			btnCounter.setTextFill(Color.CYAN);
			btnCounter.setAlignment(Pos.CENTER);
			btnCounter.setPadding(new Insets(10, 0, 10, 0));
			btnCounter.setMaxSize(130, 90);
			btnCounter.setMinSize(130, 90);
			btnCounter.setPrefSize(130, 90);
			btnCounter.setStyle("-fx-font: 20px Helvetica;\n" + "-fx-stroke: 1px;\n"
					+ "    -fx-background-color: black;\n"
					+ "    -fx-border-color: linear-gradient(from 50% 100% to 0% 200%, repeat, yellow 0%, cyan 30%, blue 40% , purple 80%);\n"
					+ "    -fx-border-width: 0.3px;\n");
//			hBox holding the sliders
			slideBox.setStyle("-fx-font: 20px Helvetica;\n" + "-fx-stroke: 1px;\n"
					+ "    -fx-background-color: black;\n"
					+ "    -fx-border-color: linear-gradient(from 50% 100% to 0% 200%, repeat, yellow 0%, cyan 30%, blue 40% , purple 80%);\n"
					+ "    -fx-border-width: 0.25px;\n");
			slideBox.setMaxSize(130, 90);
			slideBox.setMinSize(130, 90);
			slideBox.setPrefSize(130, 90);
			slideBox.setAlignment(Pos.TOP_RIGHT);
			slideBox.getChildren().addAll(new HBox(new VBox(gridSizeSlider, gridSizeLabel),
					new VBox(frameDurationSlider, frameDurationLabel)));
//			***********************************************************************************************************************

//			set title outer glow
			titleShadow.setColor(Color.CYAN);
			titleShadow.setOffsetX(0);
			titleShadow.setOffsetY(0);
			titleShadow.setHeight(10);
//			set game title with gradient effect
			gameTitle.setText("GAME OF LIFE");
			gameTitle.setStyle("-fx-font: 60px Tahoma;\n"
					+ "    -fx-fill: linear-gradient(from 100% 100% to 100% 200%, repeat, green 0%, yellow 50%);\n"
					+ "    -fx-stroke: blue;\n" + "    -fx-stroke-width: 2;");
//			apply shadow effect to the title
			gameTitle.setEffect(titleShadow);
//			apply setting to titleBox and add game title to the HBox
			titleBox.setAlignment(Pos.TOP_CENTER);
			titleBox.setPadding(new Insets(0, 0, 20, 0));
			titleBox.getChildren().add(gameTitle);
//			apply the gradient background to the gridPane to be exposed when the cells are alive
			gridPane.setBackground(new Background(gradientBG));
			gridPane.setStyle("" + "-fx-border-color: black;\n" + "-fx-border-width: 1.65;\n");
			resetGridPane();
//			setting to constrain proportions and allow for the gradient image border
			int paneWidth = 902;
			int paneHeight = 402;
			double borderWidth = 1.5;
//			constrain the proportions of the scrollPane 
			scrollPane.setMaxSize(paneWidth, paneHeight);
			scrollPane.setPrefSize(paneWidth, paneHeight);
			scrollPane.setMinSize(paneWidth, paneHeight);
			scrollPane.setContent(gridPane);
//			set the background image to the same gradient used in the gridPane as a border 
			gameBox.setBackground(new Background(gradientBG));
			gameBox.setPadding(new Insets(1.2, 1, 1, 1));
//			constrain the proportions of the gameBox so the scroll bar doesn't become visible
			gameBox.setMaxSize(paneWidth + borderWidth, paneHeight + borderWidth);
			gameBox.setPrefSize(paneWidth + borderWidth, paneHeight + borderWidth);
			gameBox.setMinSize(paneWidth + borderWidth, paneHeight + borderWidth);
			gameBox.getChildren().add(scrollPane);
//			apply settings and background image to slider HBox
			controlBox.setBackground(new Background(slide));
			controlBox.setPadding(new Insets(20, 0, 0, 0));
			controlBox.setAlignment(Pos.TOP_CENTER);
			controlBox.setMaxSize(paneWidth + borderWidth, 130);
			controlBox.setPrefSize(paneWidth + borderWidth, 130);
			controlBox.setMinSize(paneWidth + borderWidth, 130);
//			load all the sliders in a horizontal display
			controlBox.getChildren().addAll(new HBox(slideBox),
					new HBox(btnStart, btnClear, btnPause, btnSave, btnLoad), btnCounter);
//			add background image to displayPane and add nodes
			displayPane.setBackground(new Background(backgroundImage));
			displayPane.setAlignment(Pos.CENTER);
			displayPane.setPadding(new Insets(40, 25, 25, 25));
			displayPane.getChildren().addAll(titleBox, gameBox, controlBox);

//			2909 Van call displayGrid method
			displayGrid();

//			add that stage to the main stage
			primaryStage.initStyle(StageStyle.UTILITY);
			primaryStage.setScene(new Scene(displayPane, 1080, 680));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	 reseting GridPane + Alexia code cleaning 	
	public void resetGridPane() {
		gridPane.getChildren().clear();
		gridPane.setPadding(Insets.EMPTY);
		gridPane.setHgap(0);
		gridPane.setVgap(0);
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setMinSize(900, 400);
		gridPane.setPrefSize(900, 400);
		gridPane.setMaxSize(900, 400);
	}

//	 adding func for start btn
	private void startGame() {
		if (running == false) {
			timeline = new Timeline(new KeyFrame(Duration.millis(frameDuration), e -> {
				resetGridPane();
				displayGrid();
				grid.Update();
				frameCount++;
				btnCounter.setText("" + frameCount + "");
			}));
			timeline.setCycleCount(Animation.INDEFINITE);
			timeline.play();
		}
		running = true;
	}

// 	 adding stop method
	public void stopGame() {
		timeline.stop();
		running = false;
	}

//	 adding display grid func 
	public void displayGrid() {
		resetGridPane();
		CircleList<CircleList<Life>> g = grid.getGrid();

		for (int i = 0; i < 40; i++) {
			for (int j = 0; j < 90; j++) {
				application.Node<CircleList<Life>> nodeY = g.getNode(i);
				CircleList<Life> listY = nodeY.getValue();
				application.Node<Life> nodeL = listY.getNode(j);
				Life l = nodeL.getValue();
				l.getAlive();
				Rectangle rect = new Rectangle(j, i, gridSize * 10, gridSize * 10);
				rect.setStrokeType(StrokeType.INSIDE);
				rect.setStrokeWidth(0.25d);
				rect.setStroke(Color.web("cyan", 0.9));

				if (l.getAlive() == true) {
					rect.setFill(Color.TRANSPARENT);
				} else {
					rect.setFill(Color.BLACK);
				}
				rect.setOnMouseClicked(event -> {
					if (l.getAlive() == true) {
						rect.setFill(Color.BLACK);
						l.setAlive(false);
					} else {
						rect.setFill(Color.TRANSPARENT);
						l.setAlive(true);
					}
				});
				gridPane.add(rect, j, i);
			}
		}
	}

//	adding Save & Load method
	public void saveShape(Stage primaryStage) {

		try {
			FileChooser fileChooser = new FileChooser();

			File outfile = fileChooser.showSaveDialog(primaryStage);
			PrintStream out = new PrintStream(outfile);

//			loop through Circle List and outfile using Printstream
			CircleList<CircleList<Life>> g = grid.getGrid();

			for (int i = 0; i < 40; i++) {
				for (int j = 0; j < 90; j++) {
					application.Node<CircleList<Life>> nodeY = g.getNode(i);
					CircleList<Life> listY = nodeY.getValue();
					application.Node<Life> nodeL = listY.getNode(j);
					Life l = nodeL.getValue();
					out.println(l.getX() + " " + l.getY() + " " + l.getAlive() + " " + l.getChange());
				}
			}
			out.close();
			Alert a = new Alert(AlertType.NONE);
			a.setAlertType(AlertType.CONFIRMATION);
			a.setContentText("File Saved!!");
			a.show();

		} catch (IOException e) {
			System.out.println("File Error.");
		}
	}

	public void loadShape(Stage primaryStage) {
		try {
			frameCount = 0;
			btnCounter.setText("" + frameCount + "");

			FileChooser fileChooser = new FileChooser();

			cellsList = new CircleList<CircleList<Life>>();
			grid = new Grid(cellsList);

			File inFile = fileChooser.showOpenDialog(primaryStage);
			Scanner scan = new Scanner(inFile);

//			loop through circle List and Load file using scanner
			for (int i = 0; i < 40; i++) {

				CircleList<Life> lList = new CircleList<>();
				Node<CircleList<Life>> yList = new Node<CircleList<Life>>(lList, null, null);
				this.grid.getGrid().appendNode(yList);
				for (int j = 0; j < 90; j++) {

					int x = scan.nextInt();
					int y = scan.nextInt();
					boolean alive = scan.nextBoolean();
					boolean change = scan.nextBoolean();
					Life l = new Life(x, y, alive, change);

					Node<Life> n = new Node<Life>(l, null, null);
					lList.appendNode(n);
				}
				lList.circleLink();
			}
			this.grid.getGrid().circleLink();

			scan.close();
			Alert a = new Alert(AlertType.NONE);
			a.setAlertType(AlertType.CONFIRMATION);
			a.setContentText("File loading!!");
			a.show();
			displayGrid();

		} catch (IOException e) {
			System.out.println("File Error.");
		}
	}

// adding clear method
	private void clear() {
		frameCount = 0;
		btnCounter.setText("" + frameCount + "");
		timeline.pause();

		resetGridPane();

		CircleList<CircleList<Life>> g = grid.getGrid();
//		loop through the Circle List
		for (int i = 0; i < 40; i++) {
			for (int j = 0; j < 90; j++) {
				application.Node<CircleList<Life>> nodeY = g.getNode(i);
				CircleList<Life> listY = nodeY.getValue();
				application.Node<Life> nodeL = listY.getNode(j);
				Life l = nodeL.getValue();
				l.getAlive();
				Rectangle rect = new Rectangle(j, i, gridSize * 10, gridSize * 10);
				rect.setStrokeType(StrokeType.INSIDE);
				rect.setStrokeWidth(0.25d);
				rect.setStroke(Color.web("cyan", 0.9));
//				remove all active cell
				l.setAlive(false);
//				add listener
				rect.setOnMouseClicked(event -> {
					if (l.getAlive() == true) {
						rect.setFill(Color.BLACK);
						l.setAlive(false);
					} else {
						rect.setFill(Color.TRANSPARENT);
						l.setAlive(true);
					}
				});

				gridPane.add(rect, j, i);
			}
		}
	}

//	 adding SizeChange Method for Slider
	private ChangeListener<Number> sizeChange() {
		return (observable, oldValue, newValue) -> {
			gridSize = newValue.intValue();
			gridSizeSlider.getValue();
//			set the value observed to gridSize variable
			gridSizeSlider.setValue(gridSize);
			gridPane.getChildren().clear();
			displayGrid();
		};
	}

//	adding SpeedChange Method for Slider
	private ChangeListener<Number> speedChange() {
		return (observable, oldValue, newValue) -> {
			frameDuration = newValue.intValue();
//			set frame duration to the value observed
			frameDurationSlider.setValue(frameDuration);
		};
	}

	public static void main(String[] args) {
		launch(args);
	}
}

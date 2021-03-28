package loc8r;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * This class controls the graphical user interface for the project. A
 * {@code Loc8rDB} object is created, and this object is used to calculate the
 * top 8 locations of a specific type.
 * 
 * @author Kenny Ngo
 * @version 1.1
 * 
 */

public class Loc8rUI extends Application {

	private Loc8rDB locObj = new Loc8rDB();

	public static void main(String[] args) {
		launch(args);
	}

	private Scene dislikesScene(Stage stage) {
		Image im2 = new Image("darkBackground.jpg");
		ImageView i2 = new ImageView(im2);
		i2.setFitHeight(600);
		i2.setFitWidth(800);
		BorderPane disRoot = new BorderPane();
		BorderPane root = new BorderPane();

		Button backButton = new Button("<--");
		backButton.setPrefSize(40, 40);
		backButton.setOnAction(e -> {
			stage.setScene(homeScene(stage));
		});

		Scene disScene = new Scene(root, 800, 600);
		disRoot.getChildren().addAll(i2);

		HBox topBox = new HBox();
		topBox.getChildren().add(backButton);
		topBox.setSpacing(50);
		topBox.setPadding(new Insets(10));

		disRoot.setTop(topBox);

		root.setCenter(disRoot);
		root.setTop(menu());

		List<Location> allDislikes = new ArrayList<>();

		for (ArrayList<Location> a : locObj.getBusiness().values()) {
			for (Location l : a) {
				if (l.getRating() == -1) {
					allDislikes.add(l);
				}
			}
		}

		List<String> locAsString = new ArrayList<String>();
		for (Location ll : allDislikes) {
			locAsString.add(ll.getName() + "\t\t" + ll.getAddress() + "\t\t" + "Distance: N/A" + "\t\t" + "Rating: -");
		}

		ObservableList<String> dislikesLocations = FXCollections.observableArrayList(locAsString);
		ListView<String> list = new ListView<String>();
		list.setItems(dislikesLocations);
		list.setMaxHeight(450);
		list.setMaxWidth(750);
		disRoot.setCenter(list);

		list.setOnMouseClicked(e -> {
			boolean isListEmpty = list.getSelectionModel().isEmpty();
			if (e.getClickCount() == 2 && !isListEmpty)
				stage.setScene(dislikedLocScene(stage, list.getSelectionModel().getSelectedItem()));
		});

		return disScene;

	}

	private Scene dislikedLocScene(Stage stage, String info) {
		Image im2 = new Image("darkBackground.jpg");
		ImageView i2 = new ImageView(im2);
		i2.setFitHeight(600);
		i2.setFitWidth(800);
		BorderPane locRoot = new BorderPane();
		BorderPane root = new BorderPane();
		Scene locScene = new Scene(root, 800, 600);
		locRoot.getChildren().addAll(i2);

		Button backButton = new Button("<--");
		backButton.setPrefSize(40, 40);
		backButton.setOnAction(e -> {
			stage.setScene(dislikesScene(stage));
		});

		HBox topBox = new HBox();
		topBox.getChildren().add(backButton);
		topBox.setSpacing(50);
		topBox.setPadding(new Insets(10));

		String[] entryParts = info.split("\\t\\t");

		GridPane grid = new GridPane();

		Label name = new Label(entryParts[0]);
		name.setFont(Font.font("Gill Sans", 40));
		name.setTextFill(Color.web("ffffff"));

		Label address = new Label(entryParts[1]);
		address.setFont(name.getFont());
		address.setTextFill(name.getTextFill());

		Label rating = new Label("Rating: ");
		rating.setFont(name.getFont());
		rating.setTextFill(name.getTextFill());

		ToggleGroup group = new ToggleGroup();

		Rectangle rect = new Rectangle(40, 40);
		ToggleButton like = new ToggleButton("Like");
		like.setToggleGroup(group);
		like.setStyle("-fx-base: lightgreen;");
		like.setOnAction(e -> {

			for (ArrayList<Location> a : locObj.getBusiness().values()) {
				for (Location l : a) {
					if (l.getName().equals(entryParts[0]) && l.getAddress().equals(entryParts[1])) {
						l.setRating(1);
						rect.setFill(Color.LIGHTGREEN);
					}
				}
			}

		});
		like.setPrefSize(100, 50);
		ToggleButton dislike = new ToggleButton("Dislike");
		dislike.setToggleGroup(group);
		dislike.setStyle("-fx-base: salmon;");
		dislike.setOnAction(e -> {
			for (ArrayList<Location> a : locObj.getBusiness().values()) {
				for (Location l : a) {
					if (l.getName().equals(entryParts[0]) && l.getAddress().equals(entryParts[1])) {
						l.setRating(-1);
						rect.setFill(Color.SALMON);
					}
				}
			}
		});
		dislike.setPrefSize(100, 50);
		ToggleButton neutral = new ToggleButton("Neutral");
		neutral.setToggleGroup(group);
		neutral.setPrefSize(100, 50);
		neutral.setOnAction(e -> {
			for (ArrayList<Location> a : locObj.getBusiness().values()) {
				for (Location l : a) {
					if (l.getName().equals(entryParts[0]) && l.getAddress().equals(entryParts[1])) {
						l.setRating(0);
						rect.setFill(Color.WHITE);
					}
				}
			}
		});

		group.selectToggle(dislike);
		rect.setFill(Color.SALMON);

		HBox h = new HBox();
		h.getChildren().addAll(like, neutral, dislike);

		grid.add(name, 0, 0, 6, 1);
		grid.add(address, 0, 1, 6, 1);
		grid.add(rating, 0, 2);
		grid.add(rect, 1, 2);
		grid.add(h, 2, 2);

		grid.setVgap(30);
		grid.setHgap(50);
		grid.setPadding(new Insets(0, 0, 0, 10));

		locRoot.setTop(topBox);
		locRoot.setCenter(grid);

		root.setTop(menu());
		root.setCenter(locRoot);

		return locScene;

	}

	private Scene locScene(Stage stage, String info, List<Location> location, double x, double y, String businessType) {

		Image im2 = new Image("darkBackground.jpg");
		ImageView i2 = new ImageView(im2);
		i2.setFitHeight(600);
		i2.setFitWidth(800);
		BorderPane locRoot = new BorderPane();
		BorderPane root = new BorderPane();
		Scene locScene = new Scene(root, 800, 600);
		locRoot.getChildren().addAll(i2);

		Button backButton = new Button("<--");
		backButton.setPrefSize(40, 40);
		backButton.setOnAction(e -> {
			List<Location> ll = locObj.determine8(businessType, x, y);
			stage.setScene(resultsScene(stage, ll, x, y, businessType));
		});

		HBox topBox = new HBox();
		topBox.getChildren().add(backButton);
		topBox.setSpacing(50);
		topBox.setPadding(new Insets(10));

		String[] entryParts = info.split("\\t\\t");

		GridPane grid = new GridPane();

		Label name = new Label(entryParts[0]);
		name.setFont(Font.font("Gill Sans", 40));
		name.setTextFill(Color.web("ffffff"));

		Label address = new Label(entryParts[1]);
		address.setFont(name.getFont());
		address.setTextFill(name.getTextFill());

		Label distance = new Label(entryParts[2]);
		distance.setFont(name.getFont());
		distance.setTextFill(name.getTextFill());

		Label rating = new Label("Rating: ");
		rating.setFont(name.getFont());
		rating.setTextFill(name.getTextFill());

		ToggleGroup group = new ToggleGroup();
		Rectangle rect = new Rectangle(40, 40);
		ToggleButton like = new ToggleButton("Like");
		like.setToggleGroup(group);
		like.setStyle("-fx-base: lightgreen;");
		like.setOnAction(e -> {
			for (Location ll : locObj.getBusiness().get(businessType)) {
				if (ll.getName().equals(entryParts[0]) && ll.getAddress().equals(entryParts[1])) {
					ll.setRating(1);
					rect.setFill(Color.LIGHTGREEN);
				}
			}

		});
		like.setPrefSize(100, 50);
		ToggleButton dislike = new ToggleButton("Dislike");
		dislike.setToggleGroup(group);
		dislike.setStyle("-fx-base: salmon;");
		dislike.setOnAction(e -> {
			for (Location ll : locObj.getBusiness().get(businessType)) {
				if (ll.getName().equals(entryParts[0]) && ll.getAddress().equals(entryParts[1])) {
					ll.setRating(-1);
					rect.setFill(Color.SALMON);
				}
			}
		});
		dislike.setPrefSize(100, 50);
		ToggleButton neutral = new ToggleButton("Neutral");
		neutral.setToggleGroup(group);
		neutral.setPrefSize(100, 50);
		neutral.setOnAction(e -> {
			for (Location ll : locObj.getBusiness().get(businessType)) {
				if (ll.getName().equals(entryParts[0]) && ll.getAddress().equals(entryParts[1])) {
					ll.setRating(0);
					rect.setFill(Color.WHITE);

				}
			}
		});

		if (entryParts[3].equals("Rating: 0")) {
			group.selectToggle(neutral);
			rect.setFill(Color.WHITE);
		}
		else if (entryParts[3].equals("Rating: +")) {
			group.selectToggle(like);
			rect.setFill(Color.LIGHTGREEN);
		}
		else {
			group.selectToggle(dislike);
			rect.setFill(Color.SALMON);
		}

		HBox h = new HBox();
		h.getChildren().addAll(like, neutral, dislike);

		grid.add(name, 0, 0, 6, 1);
		grid.add(address, 0, 1, 6, 1);
		grid.add(distance, 0, 2, 6, 1);
		grid.add(rating, 0, 3);
		grid.add(rect, 1, 3);
		grid.add(h, 2, 3);

		grid.setVgap(30);
		grid.setHgap(50);
		grid.setPadding(new Insets(0, 0, 0, 10));

		locRoot.setTop(topBox);
		locRoot.setCenter(grid);

		root.setTop(menu());
		root.setCenter(locRoot);

		return locScene;
	}

	private Scene resultsScene(Stage stage, List<Location> location, double x, double y, String businessType) {
		Image im2 = new Image("darkBackground.jpg");
		ImageView i2 = new ImageView(im2);
		i2.setFitHeight(600);
		i2.setFitWidth(800);
		BorderPane resultsRoot = new BorderPane();
		BorderPane root = new BorderPane();
		Scene resultsScene = new Scene(root, 800, 600);
		resultsRoot.getChildren().addAll(i2);

		List<String> locationAsString = new ArrayList<String>();
		for (Location ll : location) {
			if (ll.getRating() == 1) {
				locationAsString.add(ll.getName() + "\t\t" + ll.getAddress() + "\t\t" + "Distance: "
						+ ll.getDistance(x, y) + "\t\t" + "Rating: +");
			}
			else if (ll.getRating() == 0) {
				locationAsString.add(ll.getName() + "\t\t" + ll.getAddress() + "\t\t" + "Distance: "
						+ ll.getDistance(x, y) + "\t\t" + "Rating: 0");
			}
			else if (ll.getRating() == -1) {
				locationAsString.add(ll.getName() + "\t\t" + ll.getAddress() + "\t\t" + "Distance: "
						+ ll.getDistance(x, y) + "\t\t" + "Rating: -");
			}
		}

		ObservableList<String> locations = FXCollections.observableArrayList(locationAsString);
		ListView<String> list = new ListView<String>();
		list.setItems(locations);
		list.setMaxHeight(450);
		list.setMaxWidth(750);
		resultsRoot.setCenter(list);

		list.setOnMouseClicked(e -> {
			boolean isListEmpty = list.getSelectionModel().isEmpty();
			if (e.getClickCount() == 2 && !isListEmpty)
				stage.setScene(
						locScene(stage, list.getSelectionModel().getSelectedItem(), location, x, y, businessType));
		});

		Button backButton = new Button("<--");
		backButton.setPrefSize(40, 40);
		backButton.setOnAction(e -> {
			stage.setScene(searchScene(stage));
		});

		HBox topBox = new HBox();
		topBox.getChildren().add(backButton);
		topBox.setSpacing(50);
		topBox.setPadding(new Insets(10));

		resultsRoot.setTop(topBox);

		root.setCenter(resultsRoot);
		root.setTop(menu());

		return resultsScene;
	}

	private Scene searchScene(Stage stage) {
		Image im2 = new Image("darkBackground.jpg");
		ImageView i2 = new ImageView(im2);
		i2.setFitHeight(600);
		i2.setFitWidth(800);
		BorderPane searchRoot = new BorderPane();

		BorderPane root = new BorderPane();

		Scene searchScene = new Scene(root, 800, 600);
		searchRoot.getChildren().addAll(i2);

		GridPane grid = new GridPane();

		Label coords = new Label("Coordinates*");
		coords.setFont(Font.font("Gill Sans", 40));
		coords.setTextFill(Color.web("ffffff"));

		Label x = new Label("X");
		x.setTextFill(coords.getTextFill());
		TextField xVal = new TextField();
		xVal.setPrefSize(50, 50);
		xVal.setPromptText("X");
		Label y = new Label("Y");
		y.setTextFill(coords.getTextFill());
		TextField yVal = new TextField();
		yVal.setPrefSize(50, 50);
		yVal.setPromptText("Y");

		Label busType = new Label("Business type");
		busType.setFont(coords.getFont());
		busType.setTextFill(coords.getTextFill());
		TextField busField = new TextField();
		busField.setPromptText("Business Type");
		busField.setPrefSize(200, 50);

		Label note = new Label("*Note: Austin coordinates are within the ranges -98<x<-96 and 29<y<31");
		note.setTextFill(coords.getTextFill());

		grid.add(coords, 0, 0);
		grid.add(busType, 0, 1);
		grid.add(x, 1, 0);
		grid.add(xVal, 2, 0);
		grid.add(y, 3, 0);
		grid.add(yVal, 4, 0);
		grid.add(busField, 1, 1, 4, 1);
		grid.add(note, 0, 3);

		grid.setVgap(50);
		grid.setHgap(50);

		grid.setPadding(new Insets(0, 0, 0, 10));

		Image im3 = new Image("Loc8r_white.png");
		ImageView i3 = new ImageView(im3);
		i3.setFitWidth(150);
		i3.setFitHeight(100);

		Button backButton = new Button("<--");
		backButton.setPrefSize(40, 40);
		backButton.setOnAction(e -> {
			stage.setScene(homeScene(stage));
		});

		HBox topBox = new HBox();
		topBox.getChildren().add(backButton);
		topBox.getChildren().add(i3);
		topBox.setSpacing(50);
		topBox.setPadding(new Insets(10));

		Button searchButton = new Button("Loc8!");
		searchButton.setPrefSize(100, 50);

		searchButton.setOnAction(e -> {
			if (xVal.getText().equals("") || yVal.getText().equals("") || busField.getText().equals("")) {
				String error2 = "ERROR: You must fill out all of the boxes to proceed!";
				alertError(error2);
			}
			else {
				if (xVal.getText().equals("") || yVal.getText().equals("") || busField.getText().equals("")) {
					String error3 = "ERROR: You must fill out all of the boxes to proceed!";
					alertError(error3);
				}
				else {
					try {
						double xVar = Double.valueOf(xVal.getText());
						double yVar = Double.valueOf(yVal.getText());
						if ((!(yVar > 29 && yVar < 31)) || (!(xVar > -98 && xVar < -96))) {
							String error4 = "ERROR: Coordinates not within Austin range!";
							alertError(error4);
						}
						else {
							List<Location> l = locObj.determine8(busField.getText(), xVar, yVar);
							if (!(l == null)) {
								stage.setScene(resultsScene(stage, l, xVar, yVar, busField.getText()));
							}
							else {
								String error5 = "ERROR: Invalid business type or business type misspelled!";
								alertError(error5);
							}
						}
					}
					catch (NumberFormatException ee) {
						String error6 = "ERROR: X and Y fields are not valid numbers!";
						alertError(error6);
					}
				}
			}
		});

		searchScene.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				if (xVal.getText().equals("") || yVal.getText().equals("") || busField.getText().equals("")) {
					String error1 = "ERROR: You must fill out all of the boxes to proceed!";
					alertError(error1);
				}
				else {
					if (xVal.getText().equals("") || yVal.getText().equals("") || busField.getText().equals("")) {
						String error2 = "ERROR: You must fill out all of the boxes to proceed!";
						alertError(error2);
					}
					else {
						if (xVal.getText().equals("") || yVal.getText().equals("") || busField.getText().equals("")) {
							String error3 = "ERROR: You must fill out all of the boxes to proceed!";
							alertError(error3);
						}
						else {
							try {
								double xVar = Double.valueOf(xVal.getText());
								double yVar = Double.valueOf(yVal.getText());
								if ((!(yVar > 29 && yVar < 31)) || (!(xVar > -98 && xVar < -96))) {
									String error4 = "ERROR: Coordinates not within Austin range!";
									alertError(error4);
								}
								else {
									List<Location> l = locObj.determine8(busField.getText(), xVar, yVar);
									if (!(l == null)) {
										stage.setScene(resultsScene(stage, l, xVar, yVar, busField.getText()));
									}
									else {
										String error5 = "ERROR: Invalid business type or business type misspelled!";
										alertError(error5);
									}
								}
							}
							catch (NumberFormatException ee) {
								String error6 = "ERROR: X and Y fields are not valid numbers!";
								alertError(error6);
							}
						}
					}
				}
			}
		});

		HBox bottomBox = new HBox();

		bottomBox.getChildren().add(searchButton);
		bottomBox.setAlignment(Pos.TOP_CENTER);

		searchRoot.setBottom(bottomBox);
		searchRoot.setTop(topBox);
		BorderPane.setMargin(grid, new Insets(100, 0, 0, 0));
		searchRoot.setCenter(grid);

		root.setCenter(searchRoot);
		root.setTop(menu());

		return searchScene;
	}

	private Scene homeScene(Stage stage) {
		Image im = new Image("Loc8rBackground1.png");
		ImageView i = new ImageView(im);
		i.setFitHeight(600);
		i.setFitWidth(800);

		BorderPane rootPane = new BorderPane();
		Button goToSearch = new Button("Search");
		goToSearch.setPrefSize(100, 40);
		goToSearch.setOnAction(e -> {
			stage.setScene(searchScene(stage));
		});

		Button goToDislikes = new Button("Dislikes");
		goToDislikes.setPrefSize(100, 40);

		goToDislikes.setOnAction(e -> {
			stage.setScene(dislikesScene(stage));
		});

		HBox buttonBox = new HBox();
		buttonBox.getChildren().addAll(goToSearch, goToDislikes);

		HBox.setMargin(goToSearch, new Insets(0, 200, 0, 0));
		buttonBox.setAlignment(Pos.TOP_LEFT);
		buttonBox.setPadding(new Insets(50));

		rootPane.getChildren().addAll(i);
		rootPane.setTop(menu());
		rootPane.setBottom(buttonBox);

		Scene scene = new Scene(rootPane, 800, 600);
		stage.setTitle("Loc8r");
		stage.show();
		stage.setResizable(false);
		return scene;
	}

	private MenuBar menu() {
		MenuBar menuBar = new MenuBar();
		Menu helpMenu = new Menu("Help");

		MenuItem aboutItem = new MenuItem("About");
		helpMenu.getItems().add(aboutItem);

		MenuItem typeList = new MenuItem("List of business types");
		helpMenu.getItems().add(typeList);

		typeList.setOnAction(e -> {
			Alert helpAlert = new Alert(AlertType.INFORMATION);
			helpAlert.setTitle("Business Type list");
			String busSet = locObj.getBusiness().keySet().toString();
			helpAlert.setHeaderText("Types:");
			helpAlert.setContentText(busSet);
			helpAlert.showAndWait();
		});

		aboutItem.setOnAction(e -> {
			Alert aboutAlert = new Alert(AlertType.INFORMATION);
			aboutAlert.setTitle("About");
			aboutAlert.setHeaderText(
					"Loc8r\n\nVersion: 1.1\n\nThis product allows a user to search for locations of a specific business type");
			aboutAlert.setContentText("Made by Kenny Ngo");
			aboutAlert.showAndWait();
		});

		menuBar.getMenus().add(helpMenu);
		return menuBar;
	}

	private void alertError(String x) {
		Alert a = new Alert(AlertType.ERROR);
		a.setTitle(x);
		a.setContentText(x);
		a.showAndWait();
	}

	public void start(Stage stage) throws Exception {
		stage.setScene(homeScene(stage));
	}
}

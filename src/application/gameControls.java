package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class gameControls implements Initializable {
	private int index; 
	private controlUNO control = new controlUNO();
	private boolean isWild;
	private boolean isGameWon = false;

	/* Initialize FXML tags */
	@FXML
	private AnchorPane Anchor;
	@FXML 
	private TextArea txtField;
	@FXML
	private StackPane drawPile;
	@FXML
	private StackPane discardPile;
	@FXML
	private HBox controlledPlayerHBox;
	@FXML
	private HBox computerHBox;
	@FXML
	private Button drawButton;
	@FXML
	private Button red;
	@FXML
	private Button green;
	@FXML
	private Button yellow;
	@FXML
	private Button blue;
	@FXML
	private ImageView start;
	@FXML
	private ImageView logo;

	// <Label alignment="CENTER"  layoutX="400.0" layoutY="300.0" opacity="0.58" prefHeight="21.0" prefWidth="130.0" text="Draw Pile" />
//	private Label draw; 
//	<Label alignment="CENTER" layoutX="675.0" layoutY="300.0" opacity="0.58" prefHeight="21.0" prefWidth="120.0" text="Discard Pile" />

//	private Label discard = new Label(layoutX="675.0" );
	/*TODO
	 * Deal the cards \/
	 * shuffle the cards 
	 * check for movement of the mouse \/
	 * differentiate between drawing between computers and controlledPlayers \/
	 * Have a discard function for when the player puts down a card \/
	 * */
	
	/* Draw cards for the players */
	public void controlledPlayerDraw() {
		ImageView temp = new ImageView(control.cards().getDrawPile().peek().getIcon());
		drawPile.getChildren().remove(drawPile.getChildren().size()-1);
		control.getControlledPlayer().draw(control.cards().getDrawPile().pop());
		controlledPlayerHBox.getChildren().add(temp);
		txtField.setText("\nYou drew a card, it is now the computer's turn");
	    txtField.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
		control.setCurrentPlayer(control.getComputerPlayer());
	}
	
	public void computerDraw() {
		ImageView temp = new ImageView(control.cards().getDrawPile().peek().getBackIcon());
		drawPile.getChildren().remove(drawPile.getChildren().size()-1);
		control.getComputerPlayer().draw(control.cards().getDrawPile().pop());
		computerHBox.getChildren().add(temp);
		txtField.setText("\nComputer drew a card, it is now your turn");
	    txtField.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
		control.setCurrentPlayer(control.getControlledPlayer());
	}
	
	public void deal() {
		for(int i = 0; i < 5; i++) {
			controlledPlayerDraw();
			computerDraw();
			txtField.setText("\nAll players given five cards");
		}
		control.setCurrentPlayer(control.getControlledPlayer());
	}
	/* Handles mouseEvents */
	public void handle(MouseEvent event) {
		if(isGameWon == false) {
			if(control.getCurrentPlayer() == control.getControlledPlayer()) {
				for(int i = 0; i < 13; i++) {
					if(controlledPlayerHBox.getChildren().get(i) == event.getTarget()) {
						index=i;
						break;
					}
				}
				String playerCard = control.getControlledPlayer().getCards().get(index).getName();
				if(control.getControlledPlayer().getCards().get(index).getName().equals("noneplusfour-1")) {
					controlledPlayerDiscard(index);
					control.setCurrentCard(control.cards().getDiscardPile().peek());
					for(int i = 0; i < 4; i++) {
						computerDraw();
					}
					txtField.setText("\nYou put down a plus four now pick a color");
				    txtField.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
					if(control.getCurrentPlayer().getCards().size() == 0) {
					    txtField.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
	
					}
					isWild = true;
					isVisible(true);
	
					control.setCurrentPlayer(control.getComputerPlayer());
					// Add visibility 
				}else if(control.getControlledPlayer().getCards().get(index).getName().equals("nonewild-1")) {
					txtField.setText("\nYou put down a wild now pick a color");
				    txtField.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
					controlledPlayerDiscard(index);
					control.setCurrentCard(control.cards().getDiscardPile().peek());
					if(control.getCurrentPlayer().getCards().size() == 0) {
						txtField.setText("You win");
					    setGameWon(true);

					}
					isWild = true;
					isVisible(true);
					control.setCurrentPlayer(control.getComputerPlayer());
					// Add visibility 
				}
				System.out.println(playerCard.substring(playerCard.length()-9, playerCard.length()-2));
				if(playerCard.substring(playerCard.length()-9, playerCard.length()-2).equals("plustwo") &&
					control.matches(control.getControlledPlayer().getCards().get(index))) {
					control.cards().getDiscardPile().push(control.getControlledPlayer().getCards().get(index));
					controlledPlayerDiscard(index);
					control.setCurrentCard(control.cards().getDiscardPile().peek());
					for(int i = 0; i < 2; i++) {
						computerDraw();
					}
					if(control.getCurrentPlayer().getCards().size() == 0) {
						txtField.setText("You win");
					    txtField.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
					    setGameWon(true);

					}
					control.setCurrentPlayer(control.getComputerPlayer());
				} else if(control.matches(control.getControlledPlayer().getCards().get(index))){
					control.cards().getDiscardPile().push(control.getControlledPlayer().getCards().get(index));
					controlledPlayerDiscard(index);
					control.setCurrentCard(control.cards().getDiscardPile().peek());
					if(control.getCurrentPlayer().getCards().size() == 0) {
						txtField.setText("You win");
					    txtField.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
					    setGameWon(true);

					}
					control.setCurrentPlayer(control.getComputerPlayer());
				}
			}
		}
	}
	/* Handles computer controls */
	public void computerHandles() {
		if(isGameWon == false) {
			if(control.getCurrentPlayer().getCards().size() == 0) {
				txtField.setText("Computer has won");
			    txtField.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
	
			}
			if(control.cards().getDrawPile().size() == 0) {
				reshuffle();
			}
			String[] color = {"yellow", "blue", "red", "green"};
			String computerChoice  = "";
			for(int i = 0; i < control.getComputerPlayer().getCards().size(); i++) {
				IndividualCardView playerCard = control.getComputerPlayer().getCards().get(i);
				if(control.matches((control.getComputerPlayer().getCards().get(i))) == false){
					continue;
				}
				if(playerCard.getName().substring(playerCard.getName().length()-9, playerCard.getName().length()-2).equals("plustwo") &&
					control.matches(control.getComputerPlayer().getCards().get(i))) {
	
				}else if(control.matches(control.getComputerPlayer().getCards().get(i)) == true && !(playerCard.getNumber().equals("-1"))) {
						computerDiscard(i);
						control.setCurrentCard(control.cards().getDiscardPile().peek());
						if(control.getCurrentPlayer().getCards().size() == 0) {
							txtField.setText("Computer has won");
						    txtField.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
						    setGameWon(true);
						}
						control.setCurrentPlayer(control.getControlledPlayer());
						return;
				}else if(control.getComputerPlayer().getCards().get(i).getName().equals("noneplusfour-1")) {
					computerChoice = color[(int) (Math.random() * (color.length))];
	
					
					computerDiscard(i);
					
					control.cards().getDiscardPile().peek().setColor(computerChoice);
					control.setCurrentCard(control.cards().getDiscardPile().peek());
					if(control.getCurrentPlayer().getCards().size() == 0) {
						txtField.setText("Computer has won");
					    txtField.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
					    setGameWon(true);

					}
					for(int j = 0; j < 4; j++) {
						controlledPlayerDraw();
					}
					txtField.setText("Computer sets the color to " + computerChoice);
				    txtField.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
	
					control.setCurrentPlayer(control.getControlledPlayer());
					return;
				}else if(control.getComputerPlayer().getCards().get(i).getName().equals("nonewild-1")) {
					computerChoice = color[(int) (Math.random() * (color.length))];
					txtField.setText("Computer sets the color to " + computerChoice);
				    txtField.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
	
					
					computerDiscard(i);
					
					control.cards().getDiscardPile().peek().setColor(computerChoice);
					control.setCurrentCard(control.cards().getDiscardPile().peek());
					if(control.getCurrentPlayer().getCards().size() == 0) {
						txtField.setText("Computer has won");
					    txtField.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
					    setGameWon(true);

					}
					control.setCurrentPlayer(control.getControlledPlayer());
					return;
				}
					
				
				
			}
			computerDraw();
		}
	}
	/* Shuffles the cards */
	public void reshuffle() {
		control.cards().shuffleDrawPile();
		if(discardPile.getChildren().size() > 0) {
			for(int i = 0; i < discardPile.getChildren().size() - 1; i++) {
				discardPile.getChildren().remove(i);
			}
		}
		for(int i =0; i < control.cards().getDrawPile().size(); i++) {
			ImageView temp = new ImageView(control.cards().getDrawPile().get(i).getBackIcon());
			drawPile.getChildren().add(i,temp);
		}
		txtField.setText("Shuffled the draw deck");
	    txtField.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");

	}
	/* Discard the cards for when the player puts down a card*/
	@FXML
	public void select() {
		if(isWild == false) {
			ArrayList<ImageView> arr = new ArrayList<ImageView>();
			for(int i = 0; i < controlledPlayerHBox.getChildren().size(); i++) {
				arr.add((ImageView) controlledPlayerHBox.getChildren().get(i));
			}
			for(int i = 0; i < controlledPlayerHBox.getChildren().size(); i++) {
				arr.get(i).setOnMouseClicked(this::handle);
			}
		}
	}
	public void controlledPlayerDiscard(int n) {
		control.cards().getDiscardPile().push(control.getControlledPlayer().remove(control.getControlledPlayer().getCards().get(n)));
		discardPile.getChildren().add(controlledPlayerHBox.getChildren().remove(n));
	}
	public void computerDiscard(int n) {
		if(control.matches(control.getComputerPlayer().getCards().get(n))) {
			ImageView temp = new ImageView(control.getComputerPlayer().getCards().get(n).getIcon());
			control.cards().getDiscardPile().push(control.getComputerPlayer().getCards().remove(n));
			computerHBox.getChildren().remove(n);
			discardPile.getChildren().add(temp);
		}
	}
	public void setGameWon(boolean b) {
		if(b == true) {
			isGameWon = true;
		}else if(b == false) {
			isGameWon = false;
			
		}
	}
	public String getTehStyle(String color) {
		return  ("			     -fx-background-radius: 5em;\n" + 
				"	             -fx-min-width: 50px;\n" + 
				"	             -fx-min-height: 50px;\n" + 
				"	             -fx-max-width: 50px; \n" + 
				"	             -fx-max-height: 50px;\n" + 
				"	             -fx-background-color:" + color + ";\n" + 
				"	             -fx-border-color: black;\n" + 
				"	             -fx-border-radius: 30;\n" + 
				"	             visibility: hidden;");
	}
	public void visibilty(boolean b) {
		if(b == true) {
			txtField.setStyle(txtField.getStyle() + "visibility: visible");
			drawPile.setStyle(drawPile.getStyle() + "visibility: visible");
			discardPile.setStyle(discardPile.getStyle() + "visibility: visible");
			controlledPlayerHBox.setStyle(controlledPlayerHBox.getStyle() + "visibility: visible");
			computerHBox.setStyle(computerHBox.getStyle() + "visibility: visible");
			drawButton.setStyle(drawButton.getStyle() + "visibility: visible");
		}else if(b == false) {
			txtField.setStyle("visibility: hidden");
			drawPile.setStyle("visibility: hidden");
			discardPile.setStyle("visibility: hidden");
			controlledPlayerHBox.setStyle("visibility: hidden");
			computerHBox.setStyle("visibility: hidden");
			drawButton.setStyle("visibility: hidden");
			logo.setStyle("visibility:hidden");

		}
	}
	/* CHecks mouse movement */
	@FXML
	public void mouseMoved(MouseEvent event) {
		if(isWild == false) {
			if(control.getCurrentPlayer() == control.getComputerPlayer()) {
				computerHandles();
			}
		}
	}
	@FXML
	public void drawClicked() {
		if(control.getCurrentPlayer() == control.getControlledPlayer()) {
			controlledPlayerDraw();
			control.setCurrentPlayer(control.getComputerPlayer());
		}
	}
	public void isVisible(boolean b) {
		if(b == true) {
			red.setStyle(getTehStyle("red") + "visibility: visible;");
			blue.setStyle(getTehStyle("blue") + "visibility: visible;");

			yellow.setStyle(getTehStyle("yellow") + "visibility: visible;");
			green.setStyle(getTehStyle("green")+ "visibility: visible;");
		}else if(b == false) {
			red.setStyle("visibility: hidden");
			blue.setStyle("visibility: hidden");
			green.setStyle("visibility: hidden");
			yellow.setStyle("visibility: hidden");
		}
	}
	@FXML
	public void redClicked() {
		control.getCurrentCard().setColor("red");
		isWild = false;
		isVisible(false);
		txtField.setText("\nThe color is now red");
	    txtField.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
	}
	@FXML
	public void blueClicked() {
		control.getCurrentCard().setColor("blue");
		isWild = false;
		isVisible(false);
		txtField.setText("\nThe color is now blue");
	    txtField.setStyle("-fx-text-fill: blue; -fx-font-size: 16px;");
	}
	@FXML
	public void greenClicked() {
		control.getCurrentCard().setColor("green");
		isWild = false;
		isVisible(false);
		txtField.setText("\nThe color is now green");
	    txtField.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
	}
	@FXML
	public void yellowClicked() {
		control.getCurrentCard().setColor("yellow");
		isWild = false;
		isVisible(false);
		txtField.setText("\nThe color is now yellow");
	    txtField.setStyle("-fx-text-fill: yellow; -fx-font-size: 16px;");
	}
	
	@FXML
	public void handleStartButton() {
		visibilty(true);
		for(int i = 0; i < control.cards().getDrawPile().size(); i++) {
			ImageView temp = new ImageView(control.cards().getDrawPile().get(i).getBackIcon());
			drawPile.getChildren().add(temp);
		}
		reshuffle();
		control.setCurrentPlayer(control.getControlledPlayer());
		deal();
		IndividualCardView top = control.cards().getDrawPile().peek();
		top.setFaceDown(true);
		IndividualCardView tempCard = control.cards().getDrawPile().pop();
		ImageView temp = new ImageView(tempCard.getIcon());
		control.cards().getDiscardPile().add(tempCard);
		drawPile.getChildren().remove(drawPile.getChildren().size()-1);
		discardPile.getChildren().add(temp);
		Anchor.getChildren().remove(start);
		//Anchor.getChildren().add(draw);
		//Anchor.getChildren().add(discard);
		control.setCurrentCard(control.cards().getDiscardPile().peek());
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
}

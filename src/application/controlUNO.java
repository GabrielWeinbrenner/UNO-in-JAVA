package application;

public class controlUNO {
	private IndividualCardView currentCard; 
	private CardsView unoDeck;
	private Player controlledPlayer;
	private Player computerPlayer;
	private Player currentPlayer;
	
	public controlUNO() {
		this.controlledPlayer = new Player("Gabe");
		this.computerPlayer = new Player("Computer");
		this.unoDeck = new CardsView();
	}
	public Player getComputerPlayer() {
		return computerPlayer;
	}
	public Player getControlledPlayer() {
		return controlledPlayer;
	}
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(Player p) {
		this.currentPlayer = p;
	}
	public void setCurrentCard(IndividualCardView c) {
		this.currentCard = c;
	}
	public CardsView cards() {
		return unoDeck;
	}
	public void draw() {
		currentPlayer.draw(unoDeck.getDrawPile().pop());
	}
	public IndividualCardView getCurrentCard() {
		return currentCard;
	}
	public boolean matches(IndividualCardView c) {
		System.out.println(
				"------------------\n"+
				currentCard.getColor() + " + " + c.getColor() + "=" +
				currentCard.getColor().equals(c.getColor()) 
		);
		System.out.println(
				currentCard.getNumber() + " + " + c.getNumber() + "=" +
				currentCard.getNumber().equals(c.getNumber())
		);
		System.out.println(c.getName());
		if(currentCard.getNumber().equals(c.getNumber()) || 
		   currentCard.getColor().equals(c.getColor()) || 
		   currentCard.getName().equals(c.getName())) {
			return true;
		}else {
			return false;
		}
	}
}
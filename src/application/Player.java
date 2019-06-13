package application;

import java.util.ArrayList;

import javafx.scene.image.ImageView;

public class Player {
	private ArrayList<IndividualCardView> cards;
	private ArrayList<ImageView> cardsView;
	
	public Player(String name) {
		this.cards = new ArrayList<IndividualCardView>();
		this.cardsView = new ArrayList<ImageView>();
	}
	
	public void draw(IndividualCardView drawnCard) {
		cards.add(drawnCard);
	}
	public ArrayList<IndividualCardView> getCards(){
		return this.cards;
	}
	
	public ArrayList<ImageView> getCardsView(){
		return this.cardsView;
	}
	public IndividualCardView remove(IndividualCardView removedCard) {
		for(int i = 0; i < cards.size(); i++) {
			if(cards.get(i).equals(removedCard)) {
				return cards.remove(i);
			}
		}
		
		// IF they don't have that card???
		return cards.get(0);
	}
}

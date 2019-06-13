package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardsView {
	private Stack<IndividualCardView> drawPile;
	private Stack<IndividualCardView> discardPile;
	private Stack<ImageView> discardPileView;
	private Stack<ImageView> drawPileView;
	public Stack<IndividualCardView> cards = new Stack<>();
	public String[] colors = {"red", "yellow", "green", "blue", "none"};
	public CardsView() {
		int indexOfColors = 0;
		int indexOfCard = 0;
		do {
			if(indexOfColors <= 3) {
				for(int j = 0; j < 12; j++) {
					if(j == 0) {
						cards.add(new IndividualCardView("0","number",colors[indexOfColors]));
						indexOfCard++;
					}else if(j>0 && j < 10) {
						cards.add(new IndividualCardView(Integer.toString(j),"number",colors[indexOfColors]));
						indexOfCard++;
						cards.add(new IndividualCardView(Integer.toString(j),"number",colors[indexOfColors]));
						indexOfCard++;
					}else if(j == 10) {
						cards.add(new IndividualCardView("-1","skip",colors[indexOfColors]));
						indexOfCard++;
						cards.add(new IndividualCardView("-1","skip",colors[indexOfColors]));
						indexOfCard++;
					}else if(j == 11) {
						cards.add(new IndividualCardView("-1","plustwo",colors[indexOfColors]));
						indexOfCard++;
						cards.add(new IndividualCardView("-1","plustwo",colors[indexOfColors]));
						indexOfCard++;
					}
				}

			}
			if(indexOfColors > 3) {
				for(int i = 0; i < 4; i++) {
					cards.add(new IndividualCardView("-1", "plusfour", colors[4]));
					indexOfCard++;
				}
				for(int i = 0; i < 4; i++) {
					cards.add(new IndividualCardView("-1", "wild", colors[4]));
					indexOfCard++;
				}
			}
			indexOfColors++;
		}while(indexOfCard < 100);
		
		drawPile = cards;
		discardPile = new Stack<IndividualCardView>();
		drawPileView = new Stack<ImageView>();
		discardPileView = new Stack<ImageView>();
		Collections.shuffle(drawPile);
	}
	public Stack<ImageView> getDiscardPileView(){
		return discardPileView;
	}
	public void setDiscardPileView(Stack<ImageView> discardPileView) {
		this.discardPileView = discardPileView;
	}
	public Stack<ImageView> getDrawPileView(){
		return drawPileView;
	}
	public void setDrawPileView(Stack<ImageView> drawPileView) {
		this.drawPileView = drawPileView;
	}
	public Stack<IndividualCardView> getDrawPile(){
		return drawPile;
	}
	public Stack<IndividualCardView> getDiscardPile(){
		return discardPile;
	}
	
	public void setDrawPileFaceDown() {
		for(int i = 0; i < drawPile.size(); i++) {
			drawPile.get(i).setFaceDown(true);
		}
	}
	public void shuffleDrawPile() {
		if(discardPile.size() > 0) {
			for(int i =0; i< discardPile.size(); i++) {
				drawPile.push(discardPile.pop());
			}
		}
		Collections.shuffle(drawPile);
		setDrawPileFaceDown();
	}
	public String toString() {
		String s = "";
		for(int i = 0; i < cards.size() - 1; i++) {
			s += cards.get(i) + "\n";
		}
		return s;
	}
}

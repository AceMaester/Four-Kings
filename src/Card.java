/*
 * Class which stores information about the card object.
 * 
 */

/*
 8 Marks
 */

import java.awt.*; 
import javax.swing.*;


public class Card {    
    private int suit;
    private int value;
    private Rectangle cardArea;
    private boolean hasBeenRemoved;
    private boolean isFaceUp;
    private boolean isActive;
    
    
    
      
    public Card(int cardValue, int suit) {  
    
    	this.value = cardValue;
    
    
    
    
    
    
    
    this.suit = suit;
    
    cardArea = new Rectangle(0, 0, 0, 0);
    hasBeenRemoved = false;
    isFaceUp = false;
    setActive(false);
    
    }  
//-------------------------------------------------------------------
//-------- Accessor and mutator methods -----------------------------
//-------------------------------------------------------------------  
    public int getSuit() {    
        return suit;    
    }
    
    public int getValue() {  
        return value;   
    }
    
    public void setSuit(int suit) {  
        this.suit = suit;
    }
    
    public void setValue(int value) {  
        this.value = value;
    }
    
    public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public boolean getHasBeenRemoved() {  
        return hasBeenRemoved;     
    }
    
    public void setHasBeenRemoved(boolean removed) {  
        hasBeenRemoved = removed;
    }
    
    public boolean getIsFaceUp() {  
        return isFaceUp;     
    }
    
    public void setIsFaceUp(boolean faceUp) {  
        isFaceUp = faceUp;
    }
    
    public void setCardArea(int x, int y, int w, int h) {  
        cardArea.x = x;
        cardArea.y = y;
        cardArea.width = w;
        cardArea.height = h;
    }
    
    public Rectangle getCardArea() {  
        return cardArea;     
    }
//-------------------------------------------------------------------
//-------- Returns true if the parameter Point object ---------------
//-------- is inside the Card area. --------------------------------
//-------------------------------------------------------------------  
    public boolean pressPointIsInsideCard(Point pressPt) {  
        
      if(hasBeenRemoved){
        return false;
      
      }else if(cardArea.contains(pressPt)){
        return true;
      }
      
        return false;     
    }
//-------------------------------------------------------------------
//-------- Get String describing the card suit and value ------------
//-------------------------------------------------------------------
    public String getCardStatusInformation() { 
        String cardInfo = "";
        
        cardInfo = cardInfo + value + " " + suit + " ";
        cardInfo = cardInfo + cardArea.x + " " + cardArea.y + " ";
        cardInfo = cardInfo + hasBeenRemoved + " " + isFaceUp;
        
        return cardInfo;
    }
//-------------------------------------------------------------------
//-------- Draw the Card object. ------------------------------------
//-------------------------------------------------------------------  
    public void drawCard(Graphics g, JComponent theJPanelInstance) {  
        Image cardPic;
        int fileNumber;
        
        if (hasBeenRemoved) {
            return;
        } 
        
        if (isFaceUp) {
            fileNumber = suit * KPanel.CARDS_IN_EACH_SUIT + value;
            cardPic = CardImageLoadUp.getSingleCardImage(fileNumber);   
        } else {
            cardPic = CardImageLoadUp.getFaceDownCardImage();
        }
        
        
        g.drawImage(cardPic, cardArea.x, cardArea.y, theJPanelInstance);    
    }  
//-------------------------------------------------------------------
//-------- Get String describing the card suit and value ------------
//-------------------------------------------------------------------
    public String toString() { 
        final String[] SUITS = {"CLUBS", "DIAMONDS", "HEARTS", "SPADES"};
        if (value == 0) {
            return "A" + " " + SUITS[suit] + " active: " + isActive + " removed: " + hasBeenRemoved + "||";
        } else if (value == 12) {
            return "K" + " " + SUITS[suit] + " active: " + isActive + " removed: " + hasBeenRemoved + "||";
        } else if (value == 11) {
            return "Q" + " " + SUITS[suit] + " active: " + isActive + " removed: " + hasBeenRemoved + "||";
        } else if (value == 10) {
            return "J" + " " + SUITS[suit] + " active: " + isActive + " removed: " + hasBeenRemoved + "||";
        }
        
        return (value + 1)  + " " + SUITS[suit] + " active: " + isActive + " removed: " + hasBeenRemoved + "||";
    } 
} 

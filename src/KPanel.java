import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



	/*public KPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	} */



import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class KPanel extends JPanel implements MouseListener, KeyListener {
  public static final int CARDS_IN_EACH_SUIT = A1Constants.CARDS_IN_EACH_SUIT;
  private static final int TOTAL_NUMBER_OF_CARDS = A1Constants.TOTAL_NUMBER_OF_CARDS;
  
  private static final int NUMBER_OF_ROWS = A1Constants.NUMBER_OF_ROWS;
  private static final int NUMBER_OF_COLS = A1Constants.NUMBER_OF_COLS;
  
  private static final int CARD_DISPLAY_TOP = 60;
  private static final int DISPLAY_GAP = 6;
  
  private static final Color BACKGROUND_COLOUR = new Color(211, 211, 25); 
  private static final int CARD_DISPLAY_LEFT = A1Constants.CARD_DISPLAY_LEFT;
  
  private ArrayList<Card> cardStack;
  private Card[][] cards;
  private Integer[] currentRow;
  
  private Card userCard;
  
  private Card aFaceDownCard;
  
  private int cardWidth;
  private int cardHeight;
  
 
  
  private boolean noMoreTableCards;
  private boolean noMoreAvailableMoves;
  
  
  
 
  private int userScore;
  
  
  
  
  
  
  public KPanel() {
    setBackground(BACKGROUND_COLOUR);
    
    loadAllCardImagesAndSetUpCardDimensions();
    
    addKeyListener(this);
    addMouseListener(this);
    
    reset();
    
    
    
  }
  
  public void reset() {
    
    cardStack = createTheFullPack();
    cards = getRandomTableCards(cardStack);
    
    setUpVisibleRowOfCards(cards);
    
  
    
    aFaceDownCard = new Card(0, 0);
    setUpCardPosition(aFaceDownCard, CARD_DISPLAY_LEFT + DISPLAY_GAP*70, CARD_DISPLAY_TOP , false);
    
    
    userScore = 0;
    currentRow = new Integer[]{0,0,0,0};
    
    
    
    noMoreTableCards = false;
    noMoreAvailableMoves = false;
    
  }
  
  private void setUpCardPosition(Card card, int x, int y, boolean isFaceUp) {
    card.setCardArea(x, y, cardWidth, cardHeight);
    card.setIsFaceUp(isFaceUp);  
  }
//--------------------------------------------------------------------- 
// Handle KeyEvents
//--------------------------------------------------------------------- 
  public void keyPressed(KeyEvent e) {
	  
	  
	 
    
    if(e.getKeyCode() == KeyEvent.VK_N){
      reset();
    }
      
    if(e.getKeyCode() == KeyEvent.VK_D && cardStack.size() > 0){
    	  
    	  
    	dealNewRow();
    }
    
    repaint();
  }
  
  public void keyReleased(KeyEvent e) {}
  public void keyTyped(KeyEvent e) {}
//--------------------------------------------------------------------- 
// Handle MouseEvents
//--------------------------------------------------------------------- 
  public void mousePressed(MouseEvent e) {  
    Card selectedCard;
    int selectedCardRow;
    int selectedCardCol;
    
    
    if (noMoreTableCards || noMoreAvailableMoves) {
    	
    	return;
    }
    
    Point pressPt = e.getPoint();
    
    Point rowColOfSelectedCard = getRowColOfSelectedCard(pressPt);
    
    
    if (rowColOfSelectedCard != null) { //
      selectedCardRow = rowColOfSelectedCard.x;
      selectedCardCol = rowColOfSelectedCard.y;
      
      
      selectedCard = cards[selectedCardRow][selectedCardCol];
     
      
      
      if(!selectedCard.isActive()){
    	  
    	  return;
      }else{
    	  
    	  
    	checkCard(selectedCard, selectedCardRow, selectedCardCol ); 
    	
      
      }
      
    }
      
    if (cardStack.size() > 0 && packCardHasBeenPressed(pressPt)) {
     
    	dealNewRow();
       
      
    }
    
    if(userScore == 48){
    	
    	repaint();
    	JOptionPane.showMessageDialog(this, "Congrats you win!!");
    	reset();
    	
    	
    }
    
    repaint();
    }
  
  public void mouseClicked(MouseEvent e) {}
  public void mouseReleased(MouseEvent e) {}
  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {} 
 
//-------------------------------------------------------------------
//-------- Mouse event helper methods --------------------------------
//--------   ------------------------------
//---------------------------------------------------------------------  
  
  private void dealNewRow(){
	  int randomPosition;
	  
	   for(int j = 0; j < A1Constants.NUMBER_OF_COLS; j++){ //set previous cards to inactive
			  
			  
			  if(cards[currentRow[j]][j].equals(null)){
				  
				  currentRow[j]--;
				  
				  
			  }else{
			  cards[currentRow[j]][j].setActive(false);
			  }
		  }
	  
	  
	  for(int i = 0; i < currentRow.length; i++){ //increment current rows
		  
		  
		  
		  if(currentRow[i] == 0 && cards[currentRow[i]][i].getHasBeenRemoved()){
			  
			  
		  }else{
		  
		  currentRow[i] ++;
		  }
		  
	  }
	  
	  for(int i =0; i < A1Constants.NUMBER_OF_COLS; i++){
		  randomPosition = (int) (Math.random() * cardStack.size());
		  userCard = cardStack.remove(randomPosition);
		  setupIndividualCardPosition(userCard,currentRow[i], i );
		  
		  userCard.setIsFaceUp(true);
		  userCard.setActive(true);
		  
		  cards[currentRow[i]][i]  = userCard;
		  userCard.setActive(true); //sets dealt cards to active
		  
		  
	  }
	 
  }
  
  
  public void checkCard(Card c, int selectedCardRow, int selectedCardCol){
	  
	  Card otherActive;
	  //printArray(cards);
	  
	   for(int j = 0; j < A1Constants.NUMBER_OF_COLS; j++){
		    otherActive = cards[currentRow[j]][j];
		    
		    if(otherActive.isActive()){ //only compare cards if they are both active
		      
		    	if(otherActive.getSuit() == c.getSuit() && otherActive.getValue() > c.getValue()){ //same suit and greater value
		    		c.setActive(false);
		    		c.setHasBeenRemoved(true);
		    		userScore = userScore + 1;
		    		
		    		if(selectedCardRow > 0){
		    		cards[selectedCardRow -1 ][selectedCardCol].setActive(true);
		    		}
		    		
		    		if(currentRow[selectedCardCol] > 0){
		    			currentRow[selectedCardCol]--;
		    		}
		    		
		    		
		    		return;
		    	}
		    
		    }}
		  
		  
		
		  
		for(int j = 0; j < A1Constants.NUMBER_OF_COLS; j++){ //check if empty space is available
				  if(cards[0][j].getHasBeenRemoved()){
					 
					  
				
					  
					  cards[0][j] = new Card(c.getValue(), c.getSuit());
					  cards[0][j].setActive(true);
					  cards[0][j].setIsFaceUp(true);
					  setupIndividualCardPosition(cards[0][j], 0, j);
					  currentRow[j] = 0;
					  
					  c.setActive(false);
			    	  c.setHasBeenRemoved(true);
					  
			    	  if(selectedCardRow > 0){
				    		cards[selectedCardRow -1 ][selectedCardCol].setActive(true);
				    		currentRow[selectedCardCol]--;
				    		}
			    	  
			    	 
				    		
				    return;
					  
				  }} 
			  
	  
  }
  private void printArray(Card[][] cards){ //debug method
	  
	  
	  for(int j = 0; j < A1Constants.NUMBER_OF_COLS; j++){
		  System.out.println(cards[0][j] + " ");
	  }
	  System.out.println("");
	  
	  for(int j = 0; j < A1Constants.NUMBER_OF_COLS; j++){
		  System.out.println(cards[1][j] + " ");
	  }
  }
  
  
  
  
  private Point getRowColOfSelectedCard(Point pressPt) {
    
    Point p;
    
    
    for(int i =0; i < A1Constants.NUMBER_OF_ROWS; i++){
      for(int j = 0; j < A1Constants.NUMBER_OF_COLS; j++){
        if(cards[i][j] != null){
          
          
          if(cards[i][j].getCardArea().contains(pressPt) && cards[i][j].isActive()){
           
            p = new Point(i, j);
            
            
            
            return p;
            
          }
          
        }
      }
    }
    return null;  
  }
  
  private boolean packCardHasBeenPressed(Point pressPt) {
    if (aFaceDownCard.pressPointIsInsideCard(pressPt)) {
      return true;
    }
    
    return false;  
  }

//-------------------------------------------------------------------
//-------- Draw all the CARD objects --------------------------------
//-------- Do not draw any null cards  ------------------------------
//--------------------------------------------------------------------- 

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    
    drawTableCards(g);  
    drawRestOfJPanelDisplay(g);  
  }
  
  private void drawTableCards(Graphics g) {
    
    
    
    for(int i = 0; i < cards.length; i++){
      drawRowOfCards(g, i);
      
    }
  } 
  
  private void drawRowOfCards(Graphics g, int whichRow) { 
    
    for(int i = 0; i < cards[whichRow].length; i++){
      if(cards[whichRow][i] != null){
        cards[whichRow][i].drawCard(g, this);
        
        
      }
    }
   
    
  }
  
  private void drawRestOfJPanelDisplay(Graphics g) {
   
    int numberLeftInPack = cardStack.size();
    if (numberLeftInPack > 0) {
      aFaceDownCard.drawCard(g, this);
      drawNumberInsideCardArea(g, aFaceDownCard);   
    }
    
    drawGameInformation(g); 
  }
  
  private void drawNumberInsideCardArea(Graphics g, Card aFaceDownCard) {  
    Rectangle cardArea = aFaceDownCard.getCardArea();
    int numberLeftInPack = cardStack.size();
    g.setFont(new Font("Times", Font.BOLD, 48));
    if (numberLeftInPack < 10) {
      g.drawString("" + numberLeftInPack, cardArea.x + cardArea.width / 3, cardArea.y + cardArea.height * 2 / 3);   
    } else {
      g.drawString("" + numberLeftInPack, cardArea.x + cardArea.width / 8, cardArea.y + cardArea.height * 2 / 3);   
    }
  }
  
  private void drawGameInformation(Graphics g) {
    g.setFont(new Font("Times", Font.BOLD, 36));
    g.setColor(Color.BLACK);
    String scoreMessage = " Score: " + userScore;
    
    
    g.drawString(scoreMessage, A1Constants.SCORE_POSITION.x, A1Constants.SCORE_POSITION.y);
  }
//-------------------------------------------------------------------
//-------- The parameter is a 2D array of CARD objects --------------
//-------- The method sets up the middle row as being visible -------
//--------------------------------------------------------------------- 

  private void setUpVisibleRowOfCards(Card[][] cards) {
    
    
    
    for(int j = 0; j < NUMBER_OF_COLS; j++){
      cards[0][j].setIsFaceUp(true);
      cards[0][j].setActive(true);
    }
  }
    
 //-------------------------------------------------------------------
//-------- Create a 2D array of CARD objects and --------------------
//--- the parameter ArrayList will contain the cards which remain ---
//---  in the pack after the table cards are randomly selected ------
//--------------------------------------------------------------------- 

  private Card[][] getRandomTableCards(ArrayList<Card> cardStack) {
    
    Card card;
    int randomArraylistPosition;
    Card[][] displayCards = new Card[A1Constants.NUMBER_OF_ROWS][A1Constants.NUMBER_OF_COLS];
    
    
      for(int j = 0; j < NUMBER_OF_COLS; j++){
        
        randomArraylistPosition = (int) (Math.random() * cardStack.size());
        
        card = cardStack.remove(randomArraylistPosition);
        
        
        displayCards[0][j] = card;
        
        setupIndividualCardPosition(displayCards[0][j], 0, j);
        
      }
    
    
    
    return displayCards;
  }
  
  private void setupIndividualCardPosition(Card card, int rowNumber, int colNumber) {
    
    
    
    
    int y = CARD_DISPLAY_TOP + (cardHeight * 1 / 4) * rowNumber;
    int x = CARD_DISPLAY_LEFT + (cardWidth + DISPLAY_GAP*5) * colNumber;
    
    
    
    card.setCardArea(x, y, cardWidth, cardHeight);
  }
  
  

//-------------------------------------------------------------------
//------ Create an ArrayList of a full pack of CARD objects ---------
//-------------------------------------------------------------------
  private ArrayList<Card> createTheFullPack() {  
    ArrayList<Card> theCards = new ArrayList <Card> (TOTAL_NUMBER_OF_CARDS);
    int suitNum = A1Constants.CLUBS;
    int cardValue = 0;
    
    for (int i = 0; i < TOTAL_NUMBER_OF_CARDS; i++) {
      theCards.add(new Card(cardValue, suitNum));
      
      if( cardValue >= CARDS_IN_EACH_SUIT - 1) {
        suitNum++;
      }
      
      cardValue = (cardValue + 1) % (CARDS_IN_EACH_SUIT);  
    }
    
    return theCards;
  }
//-------------------------------------------------------------------
//-------- Load all the CARD images ---------------------------------
//-- Set up the width and height instance variables  ----------------
//-------------------------------------------------------------------
  private void loadAllCardImagesAndSetUpCardDimensions() {
    CardImageLoadUp.loadAndSetUpAllCardImages(this);
    
    Dimension d = CardImageLoadUp.getDimensionOfSingleCard();
    cardWidth = d.width;
    cardHeight = d.height;    
  }

}





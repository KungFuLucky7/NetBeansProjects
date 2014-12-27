import java.util.*;

/*
 * Ref: http://en.wikipedia.org/wiki/Video_poker
 * 	http://www.memory-improvement-tips.com/poker.html
 *
 *
 * Short Description and Poker rules:
 *
 * Video poker is also known as draw poker. 
 * The dealer uses a 52-card deck, which is played fresh after each fiveCardsHand. 
 * The player is dealt one five-card poker fiveCardsHand. 
 * After the first draw, which is automatic, you may hold any of the cards and draw 
 * again to replace the cards that you haven't chosen to hold. 
 * Your cards are compared to a table of winning combinations. 
 * The object is to get the best possible combination so that you earn the highest 
 * payout on the bet you placed. 
 *
 * Winning Combinations
 *  
 * 1. Jacks or Better: a pair pays out only if the cards in the pair are Jacks, 
 * 	Queens, Kings, or Aces. Lower pairs do not pay out. 
 * 2. Two Pair: two sets of pairs of the same card denomination. 
 * 3. Three of a Kind: three cards of the same denomination. 
 * 4. Straight: five consecutive denomination cards of different suit. 
 * 5. Flush: five non-consecutive denomination cards of the same suit. 
 * 6. Full House: a set of three cards of the same denomination plus 
 * 	a set of two cards of the same denomination. 
 * 7. Four of a kind: four cards of the same denomination. 
 * 8. Straight Flush: five consecutive denomination cards of the same suit. 
 * 9. Royal Flush: five consecutive denomination cards of the same suit, 
 * 	starting from 10 and ending with an ace
 *
 */


/* This is the main poker game class.
 * Please do not modify any data fields or defined methods
 * You may add new data fields and methods
 * Note: You must implement defined methods
 */

class VideoPoker {

    // default constant values
    private static final int defaultBalance=100;
    private static final int fiveCards=5;

    // default constant payout value and fiveCardsHand types
    private static final int[] multipliers={1,2,3,5,6,9,25,50,250};
    private static final String[] goodHandTypes={ 
	  "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	", 
	  "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };

    // must use only one deck
    private static final Deck deck = new Deck();

    // holding current poker hand, balance, bet    
    private List<Card> fiveCardsHand;
    private int balance;
    private int bet;

    /** default constructor, set balance = defaultBalance */
    public VideoPoker()
    {
	this(defaultBalance);
    }

    /** constructor, set given balance */
    public VideoPoker(int balance)
    {
	this.balance= balance;
    }

    /** This display the payout table based on multipliers and goodHandTypes arrays */
    private void showPayoutTable()
    { 
	System.out.println("\n\n");
	System.out.println("Payout Table   	      Multiplier   ");
	System.out.println("=======================================");
	int size = multipliers.length;
	for (int i=size-1; i >= 0; i--) {
		System.out.println(goodHandTypes[i]+"\t|\t"+multipliers[i]);
	}
	System.out.println("\n\n");
    }

    /** Check current fiveCardsHand using multipliers and goodHandTypes arrays
     *  Must print yourHandType (default="Sorry, you lost") at the end of function.
     *  This can be checked by testCheckHands() and main() method.
     */
    private void checkHands()
    {
	// implement this method!
    }


    /*************************************************
     *   add new private methods here ....
     *
     *************************************************/


    public void play() 
    {

     /** The main algorithm for single player poker game 
     *
     * Steps:
     * 		showPayoutTable()
     *
     * 		++	
     * 		show balance, get bet 
     *		verify bet value, update balance
     *		reset deck, shuffle deck, 
     *		deal cards and display cards
     *		ask for replacement card positions
     *		replace cards
     *		check hands, display proper messages
     *		update balance if there is a payout
     *		if balance = O:
     *			end of program 
     *		else
     *			ask if the player wants to play a new game
     *			if the answer is "no" : end of program
     *			else : showPayoutTable() if user wants to see it
     *			goto ++
     */

	// implement this method!
    }

    /** Do not modify this. It is used to test checkHands() method 
     *  checkHands() should print your current hand type
     */ 
    public void testCheckHands()
    {
    	fiveCardsHand = new ArrayList<Card>();

	// set Royal Flush
	fiveCardsHand.add(new Card("A","Spades"));
	fiveCardsHand.add(new Card("10","Spades"));
	fiveCardsHand.add(new Card("Q","Spades"));
	fiveCardsHand.add(new Card("J","Spades"));
	fiveCardsHand.add(new Card("K","Spades"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");

	// set Straight Flush
	fiveCardsHand.set(0,new Card("9","Spades"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");

	// set Straight
	fiveCardsHand.set(4, new Card("8","Diamonds"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");

	// set Flush 
	fiveCardsHand.set(4, new Card("5","Spades"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");

	//  "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	", 
	 // "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };

	// set Four of a Kind
	fiveCardsHand.clear();
	fiveCardsHand.add(new Card("8","Spades"));
	fiveCardsHand.add(new Card("8","Clubs"));
	fiveCardsHand.add(new Card("Q","Spades"));
	fiveCardsHand.add(new Card("8","Diamonds"));
	fiveCardsHand.add(new Card("8","Hearts"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");

	// set Three of a Kind
	fiveCardsHand.set(4, new Card("J","Spades"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");

	// set Full House
	fiveCardsHand.set(2, new Card("J","Diamonds"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");

	// set Two Pairs
	fiveCardsHand.set(1, new Card("9","Diamonds"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");

	// set Royal Pair
	fiveCardsHand.set(0, new Card("3","Diamonds"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");

	// non Royal Pair
	fiveCardsHand.set(2, new Card("3","Spades"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");
    }

    /* Quick testCheckHands() */
    public static void main(String args[]) 
    {
	VideoPoker mypokergame = new VideoPoker();
	mypokergame.testCheckHands();
    }
}

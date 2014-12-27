import java.util.*;
import java.io.*;

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
	private static boolean status;
	static final String [] Suit = {"Spades","Hearts","Clubs","Diamonds"};
	static final String[] tmp = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};

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
    	sort();
    	int i = checkHandType();	//call checkHandType() for the correct hand type
    	if (i != 0)
    	{
    		String yourHandType = goodHandTypes[i-1];	//store value for yourHandType
    		balance += bet * multipliers[i-1];			//determine the new balance
    		System.out.println(fiveCardsHand + "\t|\t" + yourHandType + "\t|\t" + multipliers[i-1]);
    		System.out.println("You've won! Your current balance is: " + balance);
    	}
	    else
	    {
	    	System.out.println(fiveCardsHand + "\n\nSorry, you lost.");		//print out default message if lost
	    	System.out.println("Here is your current balance: $" + balance);
	    }
	// implement this method!
    }


    /*************************************************
     *   add new private methods here ....
     *
     *************************************************/

    private int checkHandType()
    {
    	//private static final String[] goodHandTypes={ 
    	//	  "Royal Pair" , "Two Pairs" , "Three of a Kind", "Straight", "Flush	", 
    	//	  "Full House", "Four of a Kind", "Straight Flush", "Royal Flush" };
    	boolean straightStatus, flushStatus;
    	straightStatus = straightCards();
    	flushStatus = flushCards();
    	if (straightStatus == true && flushStatus == true)	//check if it's a Straight Flush
    	{
    		if (fiveCardsHand.get(0).getRank() == "10")		//check if it's a Royal Flush
    			return 9;
    		else
    			return 8;
    	}
    	if (straightStatus == true)		//check if it's a Straight
    		return 4;
    	if (flushStatus == true)		//check if it's a Flush
    		return 5;
    	int NumofaKind[] = sameDenominationCards();		//store values in NumofaKind[] to determine a hand
    	if (NumofaKind[0] > 1 || NumofaKind[1] > 1)
    	{
    		if (NumofaKind[0] == 4 || NumofaKind[1] == 4)	//check if it's a Four of a Kind
    			return 7;
    		else if (NumofaKind[0] == 3 || NumofaKind[1] == 3)	//check if it's a Three of a Kind
    		{
    			if (NumofaKind[0] == 2 || NumofaKind[1] == 2)	//check if it's a Full House
    				return 6;
    			else
    				return 3;
    		}
    		else if (NumofaKind[0] == 2 || NumofaKind[1] == 2)	//check if it's a Pair
    		{
    			if (NumofaKind[0] == 2 && NumofaKind[1] == 2)	//check if it's a Two Pairs
    				return 2;
    			else if (RoyalPair() == true)					//check if it's a Royal Pair
    				return 1;
    		}
    	}
    	return 0;
    }
    
    private boolean straightCards()		//test if it's a Straight
    {
    	status = false;
    	for (int i = 0; i < 4; i++)
    	{
    		Card one = fiveCardsHand.get(i);
    		Card other = fiveCardsHand.get(i+1);
    		if (getRankNum(one) != getRankNum(other) - 1)
    			return false;
    		else
    			status = true;
    	}
    	return status;
    }
    
    private boolean flushCards()	//test if it's a Flush
    {
    	status = true;
    	int i = 0;
    	Card one = fiveCardsHand.get(i);
    	for (i = 1; i < 5; i++)
    	{
    		Card other = fiveCardsHand.get(i);
    		if (one.getSuit() != other.getSuit())
    			status = false;
    	}
    	return status;
    }
    
    private int[] sameDenominationCards()	//look for the same-rank cards and store them in an integer array
    {
    	int sameRankCards[] = {1, 1};
    	status = false;
    	int counter = 0;
    	for (int i = 0; i < 4; i++)
    	{
    		counter = i;
    		if (status == false && sameRankCards[0] != 1)
    			break;
    		Card one = fiveCardsHand.get(i);
	    	Card other = fiveCardsHand.get(i+1);
	    	if (one.getRank() == other.getRank())
	    	{
	    		status = true;
	    		sameRankCards[0]++;
	    	}
	    	else
	    		status = false;
	    	}
    	if (counter <= 4 && status == false)
    	{
	    	for (int i = counter; i < 4; i++)
	    	{
	    		if (status == false && sameRankCards[1] != 1)
	    			break;
	    		Card one = fiveCardsHand.get(i);
	    		Card other = fiveCardsHand.get(i+1);
	    		if (one.getRank() == other.getRank())
	    		{
	    			status = true;
	    			sameRankCards[1]++;
	    		}
	    		else
	    			status = false;
	    	}
    	}
    	return sameRankCards;
    }
    
    private boolean RoyalPair()	//test if it has a Royal Pair
    {
    	for (int i = 0; i < 4; i++)
    	{
    		Card one = fiveCardsHand.get(i);
	    	Card other = fiveCardsHand.get(i+1);
	    	if (one.getRank() == other.getRank())
	    	{
	    		if (getRankNum(one) > 9)
	    			return true;    		
	    	}
    	}
    	return false;    	
    }
    
    private void sort()		//sort the hand in increasing order
    {
    	int cmpResult;
    	for (int i = 0; i < fiveCardsHand.size(); i++)
    	{
    		for (int j = i+1; j < fiveCardsHand.size(); j++)
    		{
    			Card one = fiveCardsHand.get(i);
	    		Card other = fiveCardsHand.get(j);
	    		cmpResult = compareRank(one, other);
		    	if (cmpResult == 0)
		    	{
		    		cmpResult = compareSuit(one, other);
		    		if (cmpResult == 1)
		    		{
		    	 		Card tmp = one;
		    	 		fiveCardsHand.set(i, other);
		    	 		fiveCardsHand.set(j, tmp);
		    		}
		    	}
		    	else if (cmpResult == 1)
		    	{
		    		Card tmp = one;
		    		fiveCardsHand.set(i, other);
		    		fiveCardsHand.set(j, tmp);
		    	}
		    }
    	}
    }
    
	private int compareRank(Card one, Card other)	//similar to equals() but it's for rank
	{
		if (getRankNum(one) == getRankNum(other))
			return 0;
		else if (getRankNum(one) < getRankNum(other))
			return -1;
		else
			return 1;
	} 
	
	private int compareSuit(Card one, Card other)	//similar to equals() but it's for Suit
	{
		if (getSuitNum(one) == getSuitNum(other))
			return 0;
		else if (getSuitNum(one) > getSuitNum(other))
			return -1;
		else
			return 1;
	}
    	
    private int getRankNum(Card one)		//get the number of the rank according to values stored in tmp[]
    {
    	for (int i = 0; i < tmp.length; i++)
    	{
    		if (one.getRank() == tmp[i])
    			return i+1;
    	}
    	return 0;
    }
    
    private int getSuitNum(Card one)		//get the number of the suit according to values stored in Suit[]
    {
    	for (int i = 0; i < Suit.length; i++)
    	{
    		if (one.getSuit() == Suit[i])
    			return i+1;
    	}
    	return 0;
    }
    
    public void play() throws Exception
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
    	boolean playAgain = false;
    	showPayoutTable();
    	do
    	{
	    	System.out.println("Your balance is $" + balance);
	    	System.out.println("Please enter your bet:");
	    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));	//use BufferedReader to read user input
	    	String input = null;   		
	    	try
	    	{
	    		input = br.readLine();
	    	}
	    	catch (Exception e)		
	    	{               
	    		System.out.println("Error reading your bet!");  
	    		playAgain = true;								//restart the program if user enters wrong input
	    	}
	    	bet = Integer.parseInt(input);
	    	balance -= bet;
	    	deck.reset();
	    	deck.shuffle();
	    	fiveCardsHand = deck.deal(fiveCards);
	    	List<Card> OriginalHand = new ArrayList<Card>();
	    	for (int i = 0; i < 5; i++)
	    		OriginalHand.add(fiveCardsHand.get(i));		//copy the values in fiveCardsHand to the OriginalHand for display
		    int j = 0;
		    for (int i = 0; i < 5; i++)
		    {
		    	j = i + 1;
		    	System.out.println("Here is your five-card hand" + "\n\n" + OriginalHand);
		    	System.out.println("\nDo you want to hold on to card " + j + " (Y/N)?");	//ask user if they want to hold each card or end replacement
		    	System.out.println("Or you can enter 0 for no replacement cards."); 		
		    	try
		    	{
		    		input = br.readLine();
		        	if (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N") && !input.equalsIgnoreCase("0"))
		        		System.out.println("You've entered an incorrect answer!");
		    	}
		        catch (Exception e)
			    {               
		        	System.out.println( e.getMessage());  
		        	playAgain = true;
			    }
		    	if (input.equalsIgnoreCase("0"))
		    		break;
		    	else
		    	{
		    		if (input.equalsIgnoreCase("N"))
		    		{
		    			fiveCardsHand.remove(i);
		    			deck.shuffle();
		    			fiveCardsHand.add(i, deck.deal(1).get(0));
		    		}
		    	}
		    }
		    System.out.println("You're done with replacing cards");
			System.out.println("Here is your current five-card hand" + "\n\n" + fiveCardsHand + "\n");
			checkHands();
			if (balance == 0)
			{
				System.out.println("You have no more money to bet.");	//if balance reaches 0, end of program
				playAgain = false;
			}
			else
			{
				System.out.println("-----------------------------------\n");
				System.out.println("Would you like to play another game of poker (Y/N)?");
				try
		    	{
		    		input = br.readLine();
		    		if (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N"))
			    		throw new Exception("You've entered an incorrect answer!");
		    	}
		    	catch (Exception e)
		    	{               
		    		System.out.println(e.getMessage());          
		    	}
		    	if (input.equalsIgnoreCase("Y"))
		    	{
		    		playAgain = true;
		    		System.out.println("Would you like to show the Payout Table (Y/N)?");
					try
			    	{
			    		input = br.readLine();
			    		if (!input.equalsIgnoreCase("Y") && !input.equalsIgnoreCase("N"))
				    		throw new Exception("You've entered an incorrect answer!");
			    	}
			    	catch (Exception e)
			    	{               
			    		System.out.println(e.getMessage());          
			    	}
			    	if (input.equalsIgnoreCase("Y"))
			    		showPayoutTable();
		    	}
		    	else
		    		playAgain = false;		    	//if the user input is not "Y" or "y", end of program
			}
    	} while (playAgain == true);
		System.out.println("Thank you for playing!");
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
	fiveCardsHand.set(4,new Card("9","Spades"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");

	// set Straight
	fiveCardsHand.set(4, new Card("8","Diamonds"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");

	// set Flush 
	fiveCardsHand.set(0, new Card("5","Spades"));
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
	fiveCardsHand.set(0, new Card("J","Spades"));
	System.out.println(fiveCardsHand);
    	checkHands();
	System.out.println("-----------------------------------");

	// set Full House
	fiveCardsHand.set(4, new Card("J","Diamonds"));
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
	fiveCardsHand.set(3, new Card("3","Spades"));
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

/*************************************************************************************
 *
 * CSC220 Programming Project #3
 *
 * Due Date: Mid-night, 11/7, Sunday.  
 * Upload PlayingCard.java and VideoPoker.java to ilearn
 * Turn in hard copy on Monday 11/8.
 *
 * Implement poker game program: 
 *
 * 	Part I (30%)  Implement Deck class
 * 	Part II (70%) Implement VideoPoker class
 *
 * See PlayingCard.java and VideoPoker.java for more instruction 
 *
 * PJ3 class allows user to run program as follows:
 *
 *    	java PJ3		// default credit is $100
 *  or 	java PJ3 NNN		// set initial credit to NNN
 *
 *  Do not modify this file!
 *
 *************************************************************************************/

class PJ3 {

    public static void main(String args[]) 
    {
	VideoPoker mypokergame;
	if (args.length > 0)
		mypokergame = new VideoPoker(Integer.parseInt(args[0]));
	else
		mypokergame = new VideoPoker();
	mypokergame.play();
    }
}

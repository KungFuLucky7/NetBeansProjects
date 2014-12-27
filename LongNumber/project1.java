/* ********************************************************************
 *
 *		Project #1  CSC220 Data Structures
 *
 *  Objective: Implement a LongNumber class to hold long positive integer.
 *  Note: Taken from text book, page 35, Chapter 1, Project 6
 *        I have slightly modified the statement from text book.
 *
 *  The outline of LongNumber class is given, You should not modify any
 *  methods and should not add any new fields or public methods
 *  You need to implement all methods with "need to implement!" msg.
 *
 *  LongNumber objects is set to hold upto 50 (MAX_SIZE) digits in
 *  an int array digit[]
 * 
 *  When there is an error, print error msg and set proper digit[] to null
 *  Note: A better way is to throw exception if there is an error.    
 *        Will cover exception handling later.
 *
 *  Due Date: Upload your source file project1.java in ilearn.sfsu.edu 
 *            before mid-night of September 21, 2010. You also need to 
 *            turn in a hard copy of your source file during 9/22/10 class.
 *            
 *  Late Project Penalty : 10% per day
 *
 *  Please read the doc :
 *
 *		http://userwww.sfsu.edu/~jwong/220/General_Information.pdf
 *
 * *************************************************************************
 *

    Project statement from text book:

    The largest positive integer of type int is 2,147,483,647. 
    Another integer type, long, represents integers up to 
    9,223,372,036,854,775,807. Imagine that you want to represent even 
    larger integers.

    Design and implement a class Huge of very large nonnegative integers. 
    The largest integer should contain at least 30 digits. 
    Provide operations for the class that 
    - Set the value of a nonnegative integer (provide both set methods 
      and constructors)
    - Return the value of a nonnegative integer as a string
    - Read a large nonnegative integer (skip leading zeros, but remember 
      that zero is a valid number)
    - Write a large nonnegative integer (do not write leading zeros, 
      but if the integer is zero, write a single zero)
    - Add two nonnegative integers to produce the sum as a third integer
    - Multiply two nonnegative integers to produce the product as a 
      third integer

    You should handle overflow when reading, adding, or multiplying 
    integers. An integer is too large if it exceeds MAX_SIZE digits, 
    where MAX_SIZE is a named constant that you define.

    Write a test program that demonstrates each method.

 */

class LongNumber 
{

	// private field digit[] array to hold 
	// upto MAX_SIZE digits
	private int digit[];
	private final int MAX_SIZE = 50;


        // default constructor
	public LongNumber() 
	{
		digit = new int [MAX_SIZE];
		resetArray();
	} // end default constructor


        // constructor: 
	// copy input number (in array) into digit[] array
	// check for error: too long or invalid digit
  	public LongNumber(int longDigitArray[]) 
	{
		// need to implement! 
	} // end constructor


        // constructor: 
	// copy input number (in string ) into digit[] array
	// check for error: too long or invalid char 
  	public LongNumber(String longNumStr) 
	{
		// need to implement! 
	}


	// replace digit[] array by input number (in array) 
	// check for error: too long or invalid digit
	public void setLongNumber(int longDigitArray[]) 
	{
		// need to implement! 
	} // end setLongNumber
	

	// replace digit[] array by input number (in string) 
	// check for error: too long or invalid char 
	public void setLongNumber(String longNumStr) 
	{
		// need to implement! 
	}


	// static method:
	// create a LongNumber object from input number (in array)
	// return the object
	//
	// check for error: too long or invalid char
	public static LongNumber createLongNumber(String longNumString) 
	{
		// need to implement! 
		// should return proper LongNumber object
		return null;
	} // end createLongNumber


	// static method:
	// create a LongNumber object from input number (in string)
	// return the object
	//
	// check for error: too long or invalid digit 
	public static LongNumber createLongNumber(int longDigitArray[]) 
	{
		// need to implement! 
		// should return proper LongNumber object
		return null;
	} // end createLongNumber
	

	// add this LongNumber with rhs LongNumber 
	// return a result LongNumber object
	//
	// check for error: overflow
	// return null digit[] in result if there is an error
  	public LongNumber addLongNumber(LongNumber rhs)  
	{
		// need to implement! 
		// should return proper LongNumber object
		return null;
	}	


	// multiply this LongNumber with rhs LongNumber 
	// return a result LongNumber object
	//
	// check for error: overflow
	// return null digit[] in result if there is an error
  	public LongNumber multiplyLongNumber(LongNumber rhs) 
	{
		// need to implement! 
		// should return proper LongNumber object
		return null;
	} // end multiplyLongNumber


        // convert digit[] number into string and return it
	public String toString() 
	{
		String tmp = new String("");

		// special case when there was an error, digit[] is null
		if (digit == null) 
			tmp="null";
		else {
			int size = digit.length;
			int i=0;

			// skip front 0 digits
			while((i < size ) && (digit[i]==0)) 
				i++;

			// special case, all 0 in digit[]
  			if (i == size ) tmp += "0";
			else {
		    		for (int j = i; j < size; j++)
				tmp += digit[j];
	        	}	
		}

		// return result string
		return tmp;
	} // end toString


	//============================================================
	// add more private methods here if you like to .....

        private void resetArray()
	{
		for (int i = 0; i < MAX_SIZE; i++)
		    digit[i] = 0;
	}


	//============================================================
	// print msgs for main method 

	private static void printMsg(String msg)
	{
		System.out.println(msg);
		System.out.println("==============================");
	}

	// use main method to create test cases 
	// these are test cases that i will use to test your program
  	public static void main(String[] args) 
	{
		int num1[] = {0,0,0,0,0,0,0,0,0,1};
		int num2[] = {1,2,3,4,5,6,7,8,9,1,2,3,4,5,6,7,8,9};
		int num3[] = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2};
		int num4[] = {9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9,9};

		String str1 = "1234567890123456789012345678901234567890";
		String str2 = "0000123456789012345678901234567890123456";
		String str3 = "98765432109876543210987654321098765432109876543210"; 
		String str4 = "989";
		String str5 = "3211";

		int badNum[] = {2,2,2,2,2,20,2,2,2,2,2,2,2,2,2,2,2,2};
		int longNum[] = {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2};

		String badString =  "12345678901234567890x12345678901234567890";
		String longString = "987654321098765432109876543210987654321098765432108888"; 


		// check constructors
		LongNumber longNum0 = new LongNumber();
		printMsg("Test#1: longNum="+longNum0);

		LongNumber longNum1 = new LongNumber(num1);
		printMsg("Test#2: longNum="+longNum1);

		LongNumber longNum2 = new LongNumber(num2);
		printMsg("Test#3: longNum="+longNum2);

		LongNumber longNum3 = new LongNumber(longNum);
		printMsg("Test#4: longNum="+longNum3);

		LongNumber longNum4 = new LongNumber(badNum);
		printMsg("Test#5: longNum="+longNum4);

		LongNumber longNum5 = new LongNumber(str1);
		printMsg("Test#6: longNum="+longNum5);

		LongNumber longNum6 = new LongNumber(str2);
		printMsg("Test#7: longNum="+longNum6);

		LongNumber longNum7 = new LongNumber(longString);
		printMsg("Test#8: longNum="+longNum7);

		LongNumber longNum8 = new LongNumber(badString);
		printMsg("Test#9: longNum="+longNum8);

		longNum1.setLongNumber(str2);
		printMsg("Test#10: longNum="+longNum1);

		longNum1.setLongNumber(longString);
		printMsg("Test#11: longNum="+longNum1);

		// need to create new storage
		longNum1=new LongNumber();
		longNum1.setLongNumber(badString);
		printMsg("Test#12: longNum="+longNum1);

		longNum1=new LongNumber();
		longNum1.setLongNumber(num2);
		printMsg("Test#13: longNum="+longNum1);
                  
		longNum1.setLongNumber(longNum);
		printMsg("Test#14: longNum="+longNum1);

		longNum1=new LongNumber();
		longNum1.setLongNumber(badNum);
		printMsg("Test#15: longNum="+longNum1);

		LongNumber longNum9 = LongNumber.createLongNumber(num3);
		printMsg("Test#16: longNum="+longNum9);

		LongNumber longNum10 = LongNumber.createLongNumber(badString);
		printMsg("Test#17: longNum="+longNum10);

		LongNumber longNum11 = LongNumber.createLongNumber(str2);
		printMsg("Test#18: longNum="+longNum11);

		LongNumber longNum12 = LongNumber.createLongNumber(longNum);
		printMsg("Test#18: longNum="+longNum12);


		longNum2= new LongNumber(num2);
		longNum3= new LongNumber(num3);
		longNum5= new LongNumber(num4);

		longNum4 = longNum2.addLongNumber(longNum3);
		printMsg("Test#19:"+longNum2 + " + " + longNum3 + " = " + longNum4);

		longNum4 = longNum2.addLongNumber(longNum5);
		printMsg("Test#20:"+longNum2 + " + " + longNum5 + " = " + longNum4);

		longNum4 = longNum2.multiplyLongNumber(longNum3);
		printMsg("Test#21:"+longNum2 + " * " + longNum3 + " = " + longNum4);


		longNum4 = longNum2.multiplyLongNumber(longNum5);
		printMsg("Test#22:"+longNum2 + " * " + longNum5 + " = " + longNum4);

                longNum2.setLongNumber(str4);
		longNum3.setLongNumber(str5);
		longNum4 = longNum2.addLongNumber(longNum3);
		printMsg("Test#23:"+longNum2 + " + " + longNum3 + " = " + longNum4);
		longNum4 = longNum2.multiplyLongNumber(longNum3);
		printMsg("Test#24:"+longNum2 + " + " + longNum3 + " = " + longNum4);
	} // end main

} 

/*************************************************************************************
 *																			// Terry Chun Wong
 * 		CSC220 Programming Project#2										// CSC 220
 *																			// Professor James Wong
 * 		Due Date: Mid-night, 10/10, upload Fraction.java to ilearn
 *			  Turn in hard copy Fraction.java on Monday 10/11 
 *
 * Taken from text book, Chapter#3, project#2 
 *
 * This class represents a fraction whose numerator and denominator are integers.
 *
 * Requirements:
 *      Must implements FractionInterface and Comparable (compareTo())
 *      Should work for both positive and negative fractions
 *      Must always reduce fraction to lowest term.
 *      Must throw exception in case of errors
 *      It is OK to have fraction x/1, e.g. 10/1
 *      Must not add new or modify existing data fields 
 *      Must not add new public methods
 *      May add private methods
 *
 * Notes (i extracted some sentences from text) :
 *
 *   1. To reduce a fraction such as 4/8 to lowest terms, you need to divide both 
 *      the numerator and the denominator by their greatest common denominator. 
 *      The greatest common denominator of 4 and 8 is 4, so when you divide 
 *      the numerator and denominator of 4/8 by 4, you get the fraction 1/2. 
 *      The recursive algorithm to find the greatest common denominator of 
 *      two positive integers is given (see code)
 *
 *   2. It will be easier to determine the correct sign of a fraction if you force 
 *      the fraction's denominator to be positive. However, your implementation must handle 
 *      negative denominators that the client might provide.
 *
 *   3. You need to downcast reference parameter FractionInterface to Fraction before
 *      you can use it as Fraction. See add, subtract, multiply and divide methods
 *
 ************************************************************************************/

public class Fraction implements FractionInterface, Comparable<Fraction>
{
	private	int numerator;
	private	int denominator;

	public Fraction()
	{
		// set number to default = 0/1
		setFraction(0, 1);
	}	// end default constructor

	public Fraction(int initialNumerator, int initialDenominator)
	{
		setFraction(initialNumerator, initialDenominator);	//use setFraction to initialize a new instance of Fraction
		// implement this method!
	}	// end constructor

	public Fraction(int wholeInteger)
	{
		setFraction(wholeInteger, 1); //enter 1 for the denominator for whole integers
		// implement this method!
	}	// end constructor

	public final void setFraction(int newNumerator, int newDenominator)
	{
		numerator = newNumerator;
		denominator = newDenominator;
		if (denominator < 0 && newNumerator > 0)	//if the denominator is negative and the numerator is positive, invoke setSign
			setSign('-');
		reduceToLowestTerms();
		// implement this method!
	}	// end setFraction

	public final void setFraction(int wholeInteger)
	{
		numerator = wholeInteger;
		denominator = 1;	
		// implement this method!
	}	// end setFraction

	public int getNumerator()
	{
		// implement this method!
		return numerator;
	}	// end getNumerator

	public int getDenominator()
	{
		// implement this method!
		return denominator;
	}	// end getDenominator

	public char getSign()			//return the sign of fraction as either + or - char
	{
		if (numerator >= 0)
			return '+';					
		else
		// implement this method!
			return '-';
	}	// end getSign

	public void setSign(char sign)	//set the numerator to negative and denominator to positive if given sign -
	{
		if (sign == '-')
		{
			numerator *= -1;
			denominator *= -1;
		}
		// implement this method!
	}	// end setSign

	public FractionInterface add(FractionInterface operand)
	{
		// a/b + c/d is (ad + cb)/(bd)
		int Resultnumerator = numerator * operand.getDenominator() + operand.getNumerator() * denominator;
		int Resultdenominator = denominator * operand.getDenominator();
		// implement this method!
		FractionInterface result = new Fraction(Resultnumerator, Resultdenominator);
		return result;
	}	// end add

	public FractionInterface subtract(FractionInterface operand)
	{
		// a/b - c/d is (ad - cb)/(bd)
		int Resultnumerator = numerator * operand.getDenominator() - operand.getNumerator() * denominator;
		int Resultdenominator = denominator * operand.getDenominator();
		// implement this method!
		FractionInterface result = new Fraction(Resultnumerator, Resultdenominator);
		return result;
	}	// end subtract

	public FractionInterface multiply(FractionInterface multiplier)
	{
		// a/b * c/d is (ac)/(bd)
		int Resultnumerator = numerator * multiplier.getNumerator();
		int Resultdenominator = denominator * multiplier.getDenominator();
		FractionInterface result = new Fraction(Resultnumerator, Resultdenominator);
		return result;
	}	// end multiply

	public FractionInterface divide(FractionInterface divisor)
	{
		// return ArithmeticException if divisor is 0
		// a/b / c/d is (ad)/(bc)
		try
		{
			int Resultnumerator = numerator * divisor.getDenominator();
			int Resultdenominator = denominator * divisor.getNumerator();
			FractionInterface result = new Fraction(Resultnumerator, Resultdenominator);
			return result;
		}
		catch (Exception e)
		{
			System.out.println("An ArithmeticException is caught!");
			return null;
		}
		// implement this method!
	}	// end divide

	public FractionInterface getReciprocal()	//return the reciprocal of the fraction
	{
		// return ArithmeticException if divisor is 0
		// implement this method!
		FractionInterface result = new Fraction(denominator, numerator);
		return result;
	} // end getReciprocal

	public boolean equals(Object other)	//test to see if two fractions are equal
	{
		if ((other == null) || (getClass() != other.getClass()))
				return false;
		else 
		{
				Fraction otherFraction = (Fraction) other; // need to cast it
				return ((this.numerator == otherFraction.numerator) && (this.denominator == otherFraction.denominator));
		}
		// implement this method!
		//return other.equals(this);
	} // end equals

	public int compareTo(Fraction other)	//test to see if the other fraction is equaled to, greater than or less than this object
	{
		int result;
		if (this.equals(other))
			result = 0;
		else if (numerator/denominator < other.getNumerator()/other.getDenominator())
			result = -1;
		else
			result = 1;
		return result;
		// implement this method!
	} // end compareTo

	public String toString()
	{
		return numerator + "/" + denominator;
	} // end toString

	/** Task: Reduces a fraction to lowest terms. */

	//-----------------------------------------------------------------
	//  private methods start here 
	//-----------------------------------------------------------------
	
	private void reduceToLowestTerms()
	{
		int GCD = greatestCommonDivisor(numerator, denominator);	//store the greatest common divisor to an int variable
		numerator /= GCD;		//reduce numerator and denominator to lowest terms
		denominator /= GCD;
		if (denominator < 0 && numerator > 0)	//force the denominator to be positive
			setSign('-');
		// implement this method!
		
		// Outline:
		// compute GCD of numerator & denominator
		// greatestCommonDivisor works for + numbers.  
		// So, you should eliminate - sign
		// then reduce numbers : numerator/GCD and denominator/GCD
	}	// end reduceToLowestTerms

  	/** Task: Computes the greatest common divisor of two integers.
	 *  @param integerOne	 an integer
	 *  @param integerTwo	 another integer
	 *  @return the greatest common divisor of the two integers */
	private int greatestCommonDivisor(int integerOne, int integerTwo)
	{
		 int result;

		 if (integerOne % integerTwo == 0)
			result = integerTwo;
		 else
			result = greatestCommonDivisor(integerTwo, integerOne % integerTwo);

		 return result;
	}	// end greatestCommonDivisor


	//-----------------------------------------------------------------
	//  Simple test driver is provided here 
	//-----------------------------------------------------------------

	public static void main(String[] args)
	{
		FractionInterface firstOperand = null;
		FractionInterface secondOperand = null;
		FractionInterface result = null;

		Fraction nineSixteenths = new Fraction(9, 16);	// 9/16
		Fraction oneFourth = new Fraction(1, 4);        // 1/4

		// 7/8 + 9/16
		firstOperand = new Fraction(7, 8);
		result = firstOperand.add(nineSixteenths);
		System.out.println("The sum of " + firstOperand + " and " +
				nineSixteenths + " is \t\t" + result);

		// 9/16 - 7/8
		firstOperand = nineSixteenths;
		secondOperand = new Fraction(7, 8);
		result = firstOperand.subtract(secondOperand);
		System.out.println("The difference of " + firstOperand	+
				" and " +	secondOperand + " is \t" + result);

		// 15/-2 * 1/4
		firstOperand.setFraction(15, -2);
		result = firstOperand.multiply(oneFourth);
		System.out.println("The product of " + firstOperand	+
				" and " +	oneFourth + " is \t" + result);

		// (-21/2) / (3/7)
		firstOperand.setFraction(-21, 2);
		secondOperand.setFraction(3, 7);
		result = firstOperand.divide(secondOperand);
		System.out.println("The quotient of " + firstOperand	+
				" and " +	secondOperand + " is \t" + result);

		// -21/2 + 7/8
		firstOperand.setFraction(-21, 2);
		secondOperand.setFraction(7, 8);
		result = firstOperand.add(secondOperand);
		System.out.println("The sum of " + firstOperand	+
				" and " +	secondOperand + " is \t\t" + result);

		System.out.println();

		// equality check
		if (firstOperand.equals(firstOperand))
			System.out.println("Identity of fractions OK");
		else
			System.out.println("ERROR in identity of fractions");

		secondOperand.setFraction(-42, 4);
		if (firstOperand.equals(secondOperand))
			System.out.println("Equality of fractions OK");
		else
			System.out.println("ERROR in equality of fractions");

		// comparison check
		Fraction first  = (Fraction)firstOperand;
		Fraction second = (Fraction)secondOperand;
		
		if (first.compareTo(second) == 0)
			System.out.println("Fractions == operator OK");
		else
			System.out.println("ERROR in fractions == operator");

		second.setFraction(7, 8);
		if (first.compareTo(second) < 0)
			System.out.println("Fractions < operator OK");
		else
			System.out.println("ERROR in fractions < operator");

		if (second.compareTo(first) > 0)
			System.out.println("Fractions > operator OK");
		else
			System.out.println("ERROR in fractions > operator");

		System.out.println();

		try {
			Fraction a1 = new Fraction(1, 0);		    
		}
		catch ( ArithmeticException arithmeticException )
           	{
              		System.err.printf( "\nException: %s\n", arithmeticException );
           	} // end catch

		try {
			Fraction a2 = new Fraction();		    
			Fraction a3 = new Fraction(1, 2);		    
			a3.divide(a2);
		}
		catch ( ArithmeticException arithmeticException )
           	{
              		System.err.printf( "\nException: %s\n", arithmeticException );
           	} // end catch



	}	// end main
} // end Fraction


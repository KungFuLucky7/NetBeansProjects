/* Taken from text book Chapter#3, Project#1  */
/* Modified to include exception requirements */
/* Do not modify this file!!                  */

public interface FractionInterface 
{
	/** Task: Sets a fraction to a given value.
	 *  @param newNumerator  the integer numerator
	 *  @param newDenominator  the integer denominator
         *  @throws ArithmeticException if denominator=0 */
	public void setFraction(int newNumerator, int newDenominator);

	/** Task: Sets a fraction to a whole number.
	 *  @param wholeInteger  the integer numerator */
	public void setFraction(int wholeInteger);

	/** Task: Gets the fraction's numerator.
	 *  @return the fraction's numerator */
	public int getNumerator();

	/** Task: Gets the fraction's denominator.
	 *  @return the fraction's denominator */
	public int getDenominator();

	/** Task: Gets the fraction's sign.
	 *  @return the fraction's sign */
	public char getSign();

	/** Task: Sets the numerator's sign to the fraction's sign,
	 *	and sets the denominator's sign to +.
	 *	@param sign a character that represents the fraction's sign */
	public void setSign(char sign);

	/** Task: Adds two fractions.
	 *  @param operand a fraction that is the second operand of the addition
	 *  @return the sum of the invoking fraction and the second operand */
	public FractionInterface add(FractionInterface operand);

	/** Task: Subtracts two fractions.
	 *  @param operand a fraction that is the second operand of the subtraction
	 *  @return the difference of the invoking fraction and the second operand */
	public FractionInterface subtract(FractionInterface operand);

	/** Task: Multiplies two fractions.
	 *  @param operand a fraction that is the second operand of the multiplication
	 *  @return the product of the invoking fraction and the second operand */
	public FractionInterface multiply(FractionInterface multiplier);

	/** Task: Divides two fractions.
	 *  @param operand a fraction that is the second operand of the division
	 *  @return the quotient of the invoking fraction and the second operand 
         *  @throws ArithmeticException if divisor=0 */
	public FractionInterface divide(FractionInterface divisor);

	/** Task: Get's the fraction's reciprocal
	 *  @return the reciprocal of the invoking fraction 
         *  @throws ArithmeticException if the new number with denominator=0*/
	public FractionInterface getReciprocal();

// The methods equals and toString are defined in and inherited from Object.
// The method compareTo is declared in the interface Comparable.
}

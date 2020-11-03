package bigint;

import org.omg.CORBA.Current;

/**
 * This class encapsulates a BigInteger, i.e. a positive or negative integer with 
 * any number of digits, which overcomes the computer storage length limitation of 
 * an integer.
 * 
 */
public class BigInteger {

	/**
	 * True if this is a negative integer
	 */
	boolean negative;
	
	/**
	 * Number of digits in this integer
	 */
	int numDigits;
	
	/**
	 * Reference to the first node of this integer's linked list representation
	 * NOTE: The linked list stores the Least Significant Digit in the FIRST node.
	 * For instance, the integer 235 would be stored as:
	 *    5 --> 3  --> 2
	 *    
	 * Insignificant digits are not stored. So the integer 00235 will be stored as:
	 *    5 --> 3 --> 2  (No zeros after the last 2)        
	 */
	DigitNode front;
	
	/**
	 * Initializes this integer to a positive number with zero digits, in other
	 * words this is the 0 (zero) valued integer.
	 */
	public BigInteger() {
		negative = false;
		numDigits = 0;
		front = null;
	}
	
	/**
	 * Parses an input integer string into a corresponding BigInteger instance.
	 * A correctly formatted integer would have an optional sign as the first 
	 * character (no sign means positive), and at least one digit character
	 * (including zero). 
	 * Examples of correct format, with corresponding values
	 *      Format     Value
	 *       +0            0
	 *       -0            0
	 *       +123        123
	 *       1023       1023
	 *       0012         12  
	 *       0             0
	 *       -123       -123
	 *       -001         -1
	 *       +000          0
	 *       
	 * Leading and trailing spaces are ignored. So "  +123  " will still parse 
	 * correctly, as +123, after ignoring leading and trailing spaces in the input
	 * string.
	 * 
	 * Spaces between digits are not ignored. So "12  345" will not parse as
	 * an integer - the input is incorrectly formatted.
	 * 
	 * An integer with value 0 will correspond to a null (empty) list - see the BigInteger
	 * constructor
	 * 
	 * @param integer Integer string that is to be parsed
	 * @return BigInteger instance that stores the input integer.
	 * @throws IllegalArgumentException If input is incorrectly formatted
	 */
	public static BigInteger parse(String integer) 
	throws IllegalArgumentException {
		
		/* IMPLEMENT THIS METHOD */
		
		BigInteger bigInt = new BigInteger();
		bigInt.front = null;
		bigInt.numDigits = 0;
		bigInt.negative = false;
		integer = integer.trim();
		
		if (integer.length() == 0) {
			throw new IllegalArgumentException("Input a valid integer");
		}
		
		if (integer.charAt(0) == '+') {
			bigInt.negative = false;
			integer = integer.substring(1);
			if (integer.length() == 0) {
				throw new IllegalArgumentException("Input a valid integer");
			}
		} else if (integer.charAt(0) == '-') {
			bigInt.negative = true;
			integer = integer.substring(1);
			if (integer.length() == 0) {
				throw new IllegalArgumentException("Input a valid integer");
			}
		} else {
			if(Character.isDigit(integer.charAt(0)) == false) { 
				throw new IllegalArgumentException("Input a valid integer");
			}
		}
		
		while (integer.charAt(0) == '0' ) {
			integer = integer.substring(1);
			if(Character.isDigit(integer.charAt(0)) == false) { 
				throw new IllegalArgumentException("Input a valid integer");
			}
			if (integer.length() == 1) {
				break;
			}
		}
		int size = integer.length();
		for (int i = 0; i < size; i++) {
			if (Character.isDigit(integer.charAt(i)) == false) {
				throw new IllegalArgumentException("Input a valid integer");
			}
		}
		
		for (int i = size; i > 0; i--) {
			if (bigInt.front == null) {
				bigInt.front = new DigitNode(Integer.parseInt(integer.substring(i-1,i)), null);
			} else {
				DigitNode temp1 = bigInt.front;
				while (temp1.next != null) {
					temp1 = temp1.next;
				}
				temp1.next = new DigitNode(Integer.parseInt(integer.substring(i-1,i)), null);
				bigInt.numDigits++;
			}
		}
		// following line is a placeholder for compilation
		return bigInt;
	}
	
	/**
	 * Adds the first and second big integers, and returns the result in a NEW BigInteger object. 
	 * DOES NOT MODIFY the input big integers.
	 * 
	 * NOTE that either or both of the input big integers could be negative.
	 * (Which means this method can effectively subtract as well.)
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return Result big integer
	 */
	public static BigInteger add(BigInteger first, BigInteger second) {
		
		/* IMPLEMENT THIS METHOD */
		BigInteger newInt = new BigInteger();
		if (first.numDigits < second.numDigits) {
			BigInteger temp = first;
			first = second; 
			second = temp;
		} 
		newInt.numDigits = 0;
		newInt.front = null;
		boolean extra = false;
		
		DigitNode current1 = first.front;
		DigitNode current2 = second.front;
		DigitNode pointer = newInt.front;
		
		if((first.negative == false && second.negative == false) || (first.negative && second.negative)) {
			while(current2 != null) {
				int sum = current1.digit + current2.digit;
				if (extra) {
					sum++;
					extra = false;
				}
				if (sum >= 10) {
					sum = sum % 10;
					extra = true;
				}
				DigitNode temp = new DigitNode(sum, null);
				if (newInt.front == null) {
					newInt.front = temp;
					pointer = newInt.front;
				} else {
					pointer.next = temp;
					pointer = pointer.next;
				}
				newInt.numDigits++;
				
				current1 = current1.next;
				current2 = current2.next;
			}
			while (current1 != null) {
				int sum = current1.digit;
				if (extra) {
					sum++;
					extra = false;
				}
				if (sum >= 10) {
					sum = sum % 10;
					extra = true;
				}
				DigitNode temp = new DigitNode (sum, null);
				if (newInt.front == null) {
					newInt.front = temp;
					pointer = newInt.front;
				} else {
					pointer.next = temp;
					pointer = pointer.next;
				}
				newInt.numDigits++;
				current1 = current1.next;
			}
			if (extra) {
				DigitNode temp = new DigitNode(1, null);
				pointer.next = temp;
				newInt.numDigits++;
			}
			if (first.negative) {
				newInt.negative = true;
			} else {
				newInt.negative = false;
			}
		} else {
			int sum = 0;
			int firstSum = 0;
			int secondSum = 0;
			if (first.numDigits == second.numDigits) {
				for (int i = first.numDigits; i > 0; i--) {
					for (int j = 0; j < i - 1; j++) {
						current1 = current1.next;
						current2 = current2.next;
					}
					if (current2.digit > current1.digit) {
						
						DigitNode holder = current1;
						current1 = current2;
						current2 = holder;
						break;
					}
					if (current1.digit > current2.digit) {
						break;
					}
					current1 = first.front;
					current2 = second.front;
				}
				
			}
			
			while (current2 != null) {
				if ((current1.digit < current2.digit) || (sum < 0)) {
					current1.digit += 10;
					sum = current1.digit - current2.digit;
					current1.next.digit -= 1;
				}
				else {
					sum = current1.digit - current2.digit;
				}
				
				DigitNode temp = new DigitNode (sum, null);
				if (newInt.front == null) {
					newInt.front = pointer; 
					pointer = newInt.front;
				} else {
					pointer.next = temp;
					pointer = pointer.next;
				}
				newInt.numDigits++;
				
				current1 = current1.next;
				current2 = current2.next;
			}
			while (current1 != null) {
				if (current1.digit < 0) {
					current1.next.digit--;
					current1.digit += 10;
				}
				DigitNode holder = new DigitNode(current1.digit, null);
				if (newInt.front == null) {
					newInt.front = holder;
					pointer = newInt.front;
				}
				else {
					pointer.next = holder;
					pointer = pointer.next;
				}
				current1 = current1.next;
				newInt.numDigits++;
			}
			
			if ((first.numDigits > second.numDigits) || (firstSum > secondSum) && first.negative) {
				newInt.negative = true;
			}
		}
		
		// following line is a placeholder for compilation
		return newInt;
	}
	
	
	
	
	
	/**
	 * Returns the BigInteger obtained by multiplying the first big integer
	 * with the second big integer
	 * 
	 * This method DOES NOT MODIFY either of the input big integers
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return A new BigInteger which is the product of the first and second big integers
	 */
	public static BigInteger multiply(BigInteger first, BigInteger second) {
		
		/* IMPLEMENT THIS METHOD */
		BigInteger bigInt = new BigInteger();
//		if (first.numDigits < second.numDigits){
//			BigInteger temp = first;
//			first = second; 
//			second = temp;
//		} 
		bigInt.numDigits = 0;
		bigInt.front = null;
		
		if ((first.negative && !second.negative) || (!first.negative && second.negative)) {
			bigInt.negative = true;
		} else {
			bigInt.negative = false;
		}
		
		DigitNode current1 = first.front;
		DigitNode current2 = second.front;
		DigitNode productNode = new DigitNode(0, null);
		
			for (int i = 0; i < second.numDigits; i++) {
				int x = 0;
				while(x < i) {
					productNode = productNode.next;
					x++;
					System.out.println("dogs");
				}
				
				if (i != 0) {
					current2 = current2.next;
				}
				x = 0;
				for (int j = 0; j < first.numDigits; j++) {
					int product = (current1.digit * current2.digit) * 10^j;
					int productDigit = product % 10;
					int carryDigit = product/10;
					productNode.digit += productDigit;
					System.out.println("hi");
					if ((productNode.next == null) && carryDigit != 0){
						DigitNode cats = new DigitNode (carryDigit, null);
						productNode.next = cats;
					} else if(carryDigit!=0){
						productNode.next.digit += carryDigit;
					}
					if (j + 1 == first.numDigits) {
						break;
					} else if (productNode.next == null){
						DigitNode dogs = new DigitNode (0, null);
						productNode.next = dogs;
					}
					current1 = current1.next;
					productNode = productNode.next;	
					productNode.digit = 0;
					productNode.next = null;
				}
				current1 = first.front;
				productNode = bigInt.front;
			}
			
			while (productNode != null) {
				System.out.println("hello");
				if (productNode.digit >= 10) {
					int carry = productNode.digit / 10;
					int digit = productNode.digit % 10;
					productNode.digit = digit;
					if (productNode.next != null) {
						productNode.next.digit += carry;
					} else if (carry != 0){
						DigitNode extra = new DigitNode (carry, null);
						productNode.next = extra;
					}
					productNode = productNode.next;
				}
				productNode = productNode.next;
			}
		// following line is a placeholder for compilation
		
		return bigInt;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (front == null) {
			return "0";
		}
		String retval = front.digit + "";
		for (DigitNode curr = front.next; curr != null; curr = curr.next) {
				retval = curr.digit + retval;
		}
		
		if (negative) {
			retval = '-' + retval;
		}
		return retval;
	}
}

package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial         
	 */

	
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		/*
		Node ptr1 = poly1;
		Node ptr2 = poly2;
		Node ptr3 = null;
		Node front;
		
		if (ptr1 != null && ptr2 != null) {
			if (ptr1.term.degree > ptr2.term.degree) {
				front = new Node(poly2.term.coeff, poly2.term.degree, null);
				ptr2 = ptr2.next;
				ptr3 = front;
			} else if (ptr1.term.degree < ptr2.term.degree) {
				front = new Node(poly1.term.coeff, poly1.term.degree, null);
				ptr1 = ptr1.next;
				ptr3 = front;
			} else {
				front = new Node(poly1.term.coeff + poly2.term.coeff, poly1.term.degree, null);
				ptr1 = ptr1.next;
				ptr2 = ptr2.next;
				ptr3 = front;
			}
		} else if (ptr1 != null) {
			front = new Node(poly1.term.coeff, poly1.term.degree, null);
			ptr1 = ptr1.next;
			ptr3 = front;
		} else if (ptr2 != null) {
			front = new Node(poly2.term.coeff, poly2.term.degree, null);
			ptr2 = ptr2.next;
			ptr3 = front;
		} else {
			return null;
		}
		
		while (ptr1 != null || ptr2 != null) {
			if (ptr1 == null) {
				Node newNode = new Node(poly2.term.coeff, poly2.term.degree, null);
				ptr3.next = newNode;
				ptr2 = ptr2.next;
				ptr3 = ptr3.next;
			} else if (ptr2 == null) {
				Node newNode = new Node(poly1.term.coeff, poly1.term.degree, null);
				ptr3.next = newNode;
				ptr3 = ptr3.next;
				ptr1 = ptr1.next;
			} else if (ptr1 != null && ptr2 != null) {
				if (ptr1.term.degree > ptr2.term.degree) {
					Node newNode = new Node (ptr2.term.coeff, ptr2.term.degree, null);
					ptr3.next = newNode;
					ptr3 = ptr3.next;
					ptr2 = ptr2.next;
				} else if (ptr1.term.degree < ptr2.term.degree) {
					Node newNode = new Node (ptr1.term.coeff, ptr1.term.degree, null);
					ptr3.next = newNode;
					ptr3 = ptr3.next;
					ptr1 = ptr1.next;
				} else {
					if (ptr1.term.coeff + ptr2.term.coeff == 0) {
						ptr1 = ptr1.next;
						ptr2 = ptr2.next;
					} else {
						Node newNode = new Node (ptr1.term.coeff + ptr2.term.coeff, ptr1.term.degree, null);
						ptr3.next = newNode;
						ptr3 = ptr3.next;
						ptr1 = ptr1.next;
						ptr2 = ptr2.next;
					}
				}
			}
			if (front.term.coeff == 0 && front.next != null) {
				front = front.next;
			}
			return front;
		}
		
		*/
		
		
		Node ptr1 = poly1;
		Node ptr2 = poly2;
		Node ptr3 = null;
		Node front;
		
		
		if (ptr1 != null && ptr2 != null) {
			if(poly1.term.degree > poly2.term.degree) {
				front = new Node(poly2.term.coeff, poly2.term.degree, null);
				ptr2 = ptr2.next;
				ptr3 = front;
			} else if(poly1.term.degree < poly2.term.degree){
				front = new Node(poly1.term.coeff, poly1.term.degree, null);
				ptr1 = ptr1.next;
				ptr3 = front;
			}else {
				front = new Node(poly2.term.coeff + poly1.term.coeff, poly1.term.degree, null);
				ptr1 = ptr1.next;
				ptr2 = ptr2.next;
				ptr3 = front;
			} 
		} else if(ptr1 != null) {
			front = new Node(poly1.term.coeff, poly1.term.degree, null);
			ptr1 = ptr1.next;
			ptr3 = front;
		} else if (ptr1 == null && ptr2 == null) {
			return null;
		}else {
			front = new Node(poly2.term.coeff, poly2.term.degree, null);
			ptr2 = ptr2.next;
			ptr3 = front;
		}
		
		while(ptr1 != null || ptr2 != null) {
			if(ptr1 == (null)) {
				Node newNode = new Node(ptr2.term.coeff,ptr2.term.degree, null);
				ptr3.next = newNode;
				ptr3 = ptr3.next;
				ptr2 = ptr2.next;
			} else if (ptr2 == (null)) {
				Node newNode = new Node(ptr1.term.coeff,ptr1.term.degree, null);
				ptr3.next = newNode;
				ptr3 = ptr3.next;
				ptr1 = ptr1.next;
			} else {
				if(ptr1.term.degree > ptr2.term.degree) {
					Node newNode = new Node(ptr2.term.coeff, ptr2.term.degree, null);
					ptr2 = ptr2.next;
					ptr3.next = newNode;
					ptr3 = ptr3.next;
				} else if(ptr1.term.degree == ptr2.term.degree) {
					if(ptr2.term.coeff + ptr1.term.coeff == 0) {
						ptr1 = ptr1.next;
						ptr2 = ptr2.next;
					} else {
						Node newNode = new Node(ptr2.term.coeff + ptr1.term.coeff, ptr2.term.degree, null);
						ptr3.next = newNode;
						ptr3 = ptr3.next;
						ptr1 = ptr1.next;
						ptr2 = ptr2.next;
					}

				} else {
					Node newNode = new Node(ptr1.term.coeff, ptr1.term.degree, null);
					ptr1 = ptr1.next;
					ptr3.next = newNode;
					ptr3 = ptr3.next;
				}
			}
		}
		if((front.term.coeff == 0) && (front.next != null)) {
			front = front.next;
		}
		return front;

	}
	

	
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		/*
		Node ptr1 = poly1;
		Node ptr2 = poly2;
		Node result = null;
		
		while (ptr1 != null) {
			while (ptr2 != null) {
				Node newNode = new Node (ptr1.term.coeff * ptr2.term.coeff, ptr1.term.degree + ptr2.term.degree, null);
				ptr2 = ptr2.next;
				result = add(result, newNode);
			}
			ptr1 = ptr1.next;
			ptr2 = poly2;
			
		}
		
		*/		
		
		Node ptr1 = poly1;
		Node ptr2 = poly2;
		Node product = null;
		
		while(ptr1 != null) {
			while (ptr2 != null) {
				Node temp = new Node(ptr1.term.coeff * ptr2.term.coeff, ptr1.term.degree + ptr2.term.degree, null);
				product = add(product, temp);
				ptr2 = ptr2.next;
			}
			ptr1 = ptr1.next;
			ptr2 = poly2;
			
		}
		return product;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		/*
		float total = 0;
		Node ptr = poly;
		while(ptr != null) {
			total += ptr.term.coeff * Math.pow(x, ptr.term.degree);
			ptr++;
		}
		return total;
	}
		
		
		*/
		
		
		
		
		
		float total = 0;
		Node ptr = poly;
		while (ptr != null) {
			total += ptr.term.coeff * Math.pow(x, ptr.term.degree);
			ptr = ptr.next;
		}
		return total;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}

/* 	Node front1 = poly1;
		Node front2 = poly2;
		Node newList = null;
		if (front1 == null && front2 != null) {
			return front2;
		} else if (front1 != null && front2 == null) {
			return front1;
		} else if (front1 == null && front2 == null) {
			return null;
		}
		Node ptr = newList;
		while (front1 != null && front2 != null) {
			if (front1.term.degree > front2.term.degree){
				if (ptr == null) {
					ptr = front2;
				}
				else {
					ptr.next = front2;
					ptr = ptr.next;
				}
				front2 = front2.next;
			} else if (front1.term.degree < front2.term.degree) {
				if(ptr == null) {
					ptr = front1;
				} else {
					ptr.next = front1;
					ptr = ptr.next;
				}
				front1 = front1.next;
			} else {
				Node temp = new Node(front1.term.coeff + front2.term.coeff, front1.term.degree, null);
				System.out.println(temp.term);
				ptr.next = temp;
				ptr = ptr.next;
				front1 = front1.next;
				front2 = front2.next;
			}
		}
		while(front1 != null) {
			ptr.next = front1;
			front1 = front1.next;
			ptr = ptr.next;
		}
		while(front2 != null) {
			ptr.next = front2;
			front2 = front2.next;
			ptr = ptr.next;
		}
		return newList;
		*/

package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

public static String delims = " \t*+-/()[]";

    /**
     * Populates the vars list with simple variables, and arrays lists with arrays
     * in the expression. For every variable (simple or array), a SINGLE instance is created
     * and stored, even if it appears more than once in the expression.
     * At this time, values for all variables and all array items are set to
     * zero - they will be loaded from a file in the loadVariableValues method.
     *
     * @param expr The expression
     * @param vars The variables array list - already created by the caller
     * @param arrays The arrays array list - already created by the caller
     */
    public static void
    makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    /** COMPLETE THIS METHOD **/
    /** DO NOT create new vars and arrays - they are already created before being sent in
    ** to this method - you just need to fill them in.
    **/

    expr = expr.replaceAll(" ", "");
    StringTokenizer str = new StringTokenizer(expr, delims, true);
    Stack<String> word = new Stack<String>();
    String token = null;
    while(str.hasMoreTokens()) {
    	token = str.nextToken();
    	System.out.println(token);
    	if (Character.isLetter(token.charAt(0)) || token.charAt(0) == '[')  {
    		word.push(token);
    	}
    }
    String holdPop = null;
    while (word.isEmpty() != true) {
    	holdPop = word.pop();
    	System.out.print("Current popped character:");
    	System.out.println(holdPop);
    	if (holdPop.equals("[")) { 
    			Array newArray = new Array(word.pop()); 
    			if (arrays.contains(newArray) == true) {
    				continue;
    			}
    			else {
    				arrays.add(newArray); 
    			}
    	} else {
    		Variable newVar = new Variable(holdPop);
    		if (vars.contains(newVar) == true) {
    			continue;
    		}else {
    			vars.add(newVar);
    		}
    	}
    
    }
}

    /**
     * Loads values for variables and arrays in the expression
     *
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input
     * @param vars The variables array list, previously populated by makeVariableLists
     * @param arrays The arrays array list - previously populated by makeVariableLists
     */
    public static void
    loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays)
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numberTokens = st.countTokens();
            String token = st.nextToken();
            Variable newVar = new Variable(token);
            Array newArray = new Array(token);
            int newVar2 = vars.indexOf(newVar);
            int secondArray = arrays.indexOf(newArray);
            if (newVar2 == -1 && secondArray == -1) {
            continue;
            }
            int count = Integer.parseInt(st.nextToken());
            if (numberTokens == 2) {
                vars.get(newVar2).value = count;
            } else {
            	newArray = arrays.get(secondArray);
            	newArray.values = new int[count];
                while (st.hasMoreTokens()) {
                    token = st.nextToken();
                    StringTokenizer tokenizer = new StringTokenizer(token," (,)");
                    int index = Integer.parseInt(tokenizer.nextToken());
                    int val = Integer.parseInt(tokenizer.nextToken());
                    newArray.values[index] = val;
                }
            }
        }
    }

    /**
     * Evaluates the expression.
     *
     * @param vars The variables array list, with values for all variables in the expression
     * @param arrays The arrays array list, with values for all array items
     * @return Result of evaluation
     */
    private static int getVariableValue(String name, ArrayList <Variable> vars, ArrayList<Array> arrays){
        for (int i = 0; i < vars.size(); i++){
            if (vars.get(i).name.equals(name)){
                return vars.get(i).value;
            }
        }
        return 0;
    }

    private static boolean isMultiplicationChar(char c) {
        return c == '*';
    }
    private static boolean isDivisionCharacter(char c) {
        return c == '/';
    }
    private static boolean isAdditionCharacter(char c) {
        return c == '+';
    }
    private static boolean isSubtractionCharacter(char c) {
        return c == '-';
    }
    /**
     * Evaluates the expression, using RECURSION to evaluate subexpressions and to evaluate array
     * subscript expressions.
     *
     * @return Result of evaluation
     */
    public static float evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
        /** COMPLETE THIS METHOD **/
        // following line just a placeholder for compilation
        System.out.println(vars + "    " + arrays);
        float doob = 0;
        String myExpr = expr;

        if(expr.length() == 1){
            if(Character.isDigit(expr.charAt(0)) == true){
            	doob = Float.parseFloat("" + expr.charAt(0));;
                return doob;
            }
            if(Character.isLetter(expr.charAt(0)) == true){
            	doob = getVariableValue(expr, vars, arrays);
                System.out.println(doob);
                return doob;
            }
        }

        for (int i = 0; i < vars.size(); i++) {
        	myExpr = myExpr.replace(vars.get(i).name, "" + vars.get(i).value);
        }
        System.out.println(myExpr);
        myExpr = evaluate(myExpr, arrays);
        System.out.println(myExpr);
        try {
            return Float.parseFloat(myExpr);
        } catch (Exception e) {
            return 0;
        }
    }
    private static String evaluate (String expression, ArrayList<Array> arrays){

        float firstNumber;
        float secondNumber;

        int firstNumIndex;
        int secondNumIndex;

        boolean isNegative;


        if (expression == null || expression.length() == 0) {
            System.out.println("Returned zero");
            return "0";
        }
        if (expression.indexOf('[') == -1) {

            if (expression.indexOf('(') == -1) {
                expression = expression.replace(" ", "");
                expression = " " + expression;


                isNegative = false;

                int numOperators = 0;

                for (int i = 2; i < expression.length(); i++) {
                    if (isAdditionCharacter(expression.charAt(i)))
                    	numOperators++;
                    if (isSubtractionCharacter(expression.charAt(i)))
                    	numOperators++;
                    if (isMultiplicationChar(expression.charAt(i)))
                    	numOperators++;
                    if (isDivisionCharacter(expression.charAt(i))) {
                    	numOperators++;
                    }
                }
                System.out.println("Number of Operators is: " + numOperators);

                if (numOperators == 0)
                    return expression;

                System.out.println(expression);
                for (int i = 0; i < expression.length(); i++) {
                    if (i == 1 && isSubtractionCharacter(expression.charAt(i))) {
                        isNegative = true;
                    } else if (isMultiplicationChar(expression.charAt(i)) || isDivisionCharacter(expression.charAt(i))) {
                    	firstNumIndex = secondNumIndex = i;
                        while (firstNumIndex > 0 && (Character.isDigit(expression.charAt(firstNumIndex - 1)) || expression.charAt(firstNumIndex - 1) == '.')) {
                        	firstNumIndex--;
                        }
                        while (secondNumIndex < expression.length() - 1 && (Character.isDigit(expression.charAt(secondNumIndex + 1)) || expression.charAt(secondNumIndex + 1) == '.')) {
                        	secondNumIndex++;
                        }

                        try {
                        	firstNumber = Float.parseFloat(expression.substring(firstNumIndex, i));
                        } catch (Exception e){
                        	firstNumber = 0;
                        }
                        try {
                        	secondNumber = Float.parseFloat(expression.substring(i + 1, secondNumIndex + 1));
                        } catch (Exception e) {
                        	secondNumber = 0;
                        }
                        String result = "";
                        if (isNegative)
                        	firstNumber = -1 * firstNumber;
                        if (isMultiplicationChar(expression.charAt(i))) {
                            result = "" + firstNumber * secondNumber;
                        }else{
                            result = "" + firstNumber / secondNumber;
                        }

                        int stadex = 0;
                        if (isNegative)
                            stadex = 2;
                        expression = expression.substring(stadex, firstNumIndex) + result + expression.substring(secondNumIndex+ 1);
                        i = 0;
                        isNegative = false;
                    }
                }
                isNegative = false;

                for (int i = 0; i < expression.length(); i++) {
                    if (i == 1 && isSubtractionCharacter(expression.charAt(i))) {
                        isNegative = true;
                    } else if (isAdditionCharacter(expression.charAt(i)) || isSubtractionCharacter(expression.charAt(i))) {
                    	firstNumIndex = secondNumIndex = i;

                        while (firstNumIndex > 0 && (Character.isDigit(expression.charAt(firstNumIndex - 1)) || expression.charAt(firstNumIndex - 1) == '.')) {
                        	firstNumIndex--;
                        }
                        while (secondNumIndex < expression.length() - 1 && (Character.isDigit(expression.charAt(secondNumIndex + 1)) || expression.charAt(secondNumIndex + 1) == '.')) {
                        	secondNumIndex++;
                        }
                        try {
                        	firstNumber = Float.parseFloat(expression.substring(firstNumIndex, i));
                        } catch (Exception e) {
                        	firstNumber = 0;
                        }
                        try {
                        	secondNumber = Float.parseFloat(expression.substring(i + 1, secondNumIndex + 1));
                        } catch (Exception e) {
                        	secondNumber = 0;
                        }
                        String result = "";
                        if (isNegative)
                        	firstNumber = -1 * firstNumber;
                        if (isAdditionCharacter(expression.charAt(i))) {
                            result = "" + (firstNumber + secondNumber);
                        } else {
                            result = "" + (firstNumber - secondNumber);
                        }
                        int stadex = 0;
                        if (isNegative)
                            stadex = 2;
                        expression = expression.substring(stadex, firstNumIndex) + result + expression.substring(secondNumIndex+1);
                        i = 0;
                        isNegative = false;
                    }
                }
                expression = expression.replace(" ", "");
                return expression;
            } else {
                for (int index = 0; index < expression.length(); index++){
                    if (isOpeningBracket(expression.charAt(index))) {
                        int begex = index;
                        int skips = 1;
                        index++;
                        int endIndex = index;
                        for (; endIndex < expression.length(); endIndex++) {
                            if (expression.charAt(endIndex) == '(')
                                skips++; 
                            if (expression.charAt(endIndex) == ')')
                                skips--;
                            if (skips == 0 && expression.charAt(endIndex) == ')')
                                break;
                        }

                        String first = expression.substring(0, begex);
                        String second = evaluate(expression.substring(index, endIndex), arrays);
                        String last = expression.substring(endIndex + 1);
                        return evaluate(first + second + last, arrays);
                    }
                }
            }
        } else {
            for (int index = 0; index < expression.length(); index++){
                if (Character.isLetter(expression.charAt(index))) {
                    String atyarnoSymbol = "";
                    boolean isArray = false;

                    int beginningIndex = index;

                    while (index < expression.length() && Character.isLetter(expression.charAt(index))){
                    	atyarnoSymbol += expression.charAt(index);
                        index++;
                    }
                    if (index < expression.length() && expression.charAt(index) == '[')
                        isArray = true;

                    if (isArray) {
                        int skips = 1;
                        index++;
                        int endIndex = index;

                        for (; endIndex < expression.length(); endIndex++) {
                            if (expression.charAt(endIndex) == '[')
                                skips++;
                            if (expression.charAt(endIndex) == ']')
                                skips--;
                            if (skips == 0 && expression.charAt(endIndex) == ']')
                                break;
                        }

                        String originalArray = expression.substring(0, beginningIndex);
                        String moddedArray = expression.substring(endIndex+1);

                        String evaluatedAddress = evaluate(expression.substring(index, endIndex), arrays);

                        String evaluatedArray = "" + arrayWithName(atyarnoSymbol, arrays).values[(int)Float.parseFloat(evaluatedAddress)];

                        return evaluate(originalArray + evaluatedArray + moddedArray, arrays); //Recursion
                    }
                }
            }
        }
        return "";
    }
    private static boolean isOpeningBracket(char checkOpeningBracket) {
        if (checkOpeningBracket == '(') {
            return true;
        } else if (checkOpeningBracket == '[') {
            return true;
        } else return false;
    }

    @SuppressWarnings("unused")
    private static boolean isClosingBracket(char checkClosingBracket) {
        if (checkClosingBracket == ']') {
            return true;
        } else if (checkClosingBracket == ')') {
            return true;
        } else return false;
    }
    private static Array arrayWithName(String name, ArrayList<Array> arrays) {
        for (int i = 0; i < arrays.size(); i++) {
            if (arrays.get(i).name.equals(name)){
                return arrays.get(i);
            }
        }

        return null;
    }

}
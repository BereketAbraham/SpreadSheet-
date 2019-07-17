package edu.mum.cs.cs525.spreadsheet;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Expression extends Operator {

	private float value;

	List<Operator> mOperations = new ArrayList<Operator>();
	List<String> myDataList = new ArrayList<String>();

	public List<Operator> getNumericOperation() {
		return mOperations;
	}

	public Expression(String string, SpreadSheet spreadSheet) {
		this.value = evaluate(string, spreadSheet);
	}

	public Expression() {

	}

	List<Content> ref = new ArrayList<Content>();

	@Override
	public String value() {
		// TODO Auto-generated method stub
		return Float.toString(value);
	}

	//@Override
	public String formula2() {
		// TODO Auto-generated method stub

		StringBuffer sBuffer = new StringBuffer("[");
		for (String string : myDataList) {
			sBuffer.append(string);
		}
		sBuffer.append("] --> ");

		for (int i = 0; i < (myDataList.size() - 1) / 2; i++) {
			sBuffer.append("(");
		}

		int count = 0;
		sBuffer.append(myDataList.get(0));

		for (int i = 1; i < myDataList.size(); i++) {
			sBuffer.append(myDataList.get(i));
			count++;
			if (count >= 2) {
				count = 0;

				sBuffer.append(")");
			}
		}

		return sBuffer.toString();
	}


	@Override
	public String formula() {

		// Stack for numbers: 'values'
		Stack<String> values = new Stack<String>();

		// Stack for Operators: 'ops'
		Stack<String> ops = new Stack<String>();

		for (String token : myDataList) {
			// Current token is a whitespace, skip it
			if (token.trim().isEmpty())
				continue;


			if (isNumeric(token) || token.matches("\\[[0-9]+,[0-9]+\\]")) {
				values.push(token);
			}

			// Current token is an opening brace, push it to 'ops'
			else if (token.trim().equals("(")) {
				ops.push(token);
			}

			// Closing brace encountered, solve entire brace
			else if (token.trim().equals(")"))
			{
				while (!ops.peek().trim().equals("("))
					values.push("(" + createOperationString(ops.pop().trim().charAt(0), values.pop(), values.pop()) + ")");
				ops.pop();
			}

			else if (token.trim().equals("+") || token.trim().equals("-") || token.trim().equals("*") || token.trim().equals("/")) {
				// While top of 'ops' has same or greater precedence to current
				// token, which is an operator. Apply operator on top of 'ops'
				// to top two elements in values stack
				while (!ops.empty() && hasPrecedence(token.trim().charAt(0), ops.peek().trim().charAt(0)))
					values.push("(" + createOperationString(ops.pop().trim().charAt(0), values.pop(), values.pop()) + ")");

				// Push current token to 'ops'.
				ops.push(token);
			}


		}

		while (!ops.empty())
			values.push("(" + createOperationString(ops.pop().trim().charAt(0), values.pop(), values.pop())+ ")");

		// Top of 'values' contains result, return it
		return values.pop();

	}

	public String createOperationString(char op, String b, String a) {

		switch (op) {
			case '+':
				return a + " + " + b;
			case '-':
				return a + " - " + b;
			case '*':
				return a + " * " + b;
			case '/':
				return a + " / " + b;
		}
		return "";
	}

	@Override
	public float data() {
		// TODO Auto-generated method stub
		return value;
	}

	public float evaluate(String expression, SpreadSheet spreadSheet) {

		char[] tokens = expression.toCharArray();

		// Stack for numbers: 'values'
		Stack<Float> values = new Stack<Float>();

		// Stack for Operators: 'ops'
		Stack<Character> ops = new Stack<Character>();

		for (int i = 0; i < tokens.length;)  {
			// Current token is a whitespace, skip it
			if (tokens[i] == ' ')
				continue;

			if (tokens[i] == '[') {
				int indexClosingBracket = expression.indexOf(']', i);
				String cell = expression.substring(i + 1, indexClosingBracket);
				//System.out.println(cell);
				String[] cellCoordinate = cell.split(",");
				int xCor = Integer.parseInt(cellCoordinate[0]);
				int yCor = Integer.parseInt(cellCoordinate[1]);
				Cell cellRef = spreadSheet.cell(xCor, yCor);
				//System.out.println(cellRef.value());
				if (cellRef.value() == null || cellRef.value().trim().isEmpty()) {
					values.push(0f);
				} else {
					values.push(Float.parseFloat(cellRef.value()));
				}
				myDataList.add("[" + cell + "]");

				i = indexClosingBracket + 1;
			}

			if (tokens[i] >= '0' && tokens[i] <= '9') {
				StringBuilder sb = new StringBuilder();
				sb.append(tokens[i]);
				// There may be more than one digits in number
				while (i + 1 < tokens.length && tokens[i + 1] >= '0' && tokens[i + 1] <= '9') {
					sb.append(tokens[++i]);
				}
				values.push(Float.parseFloat(sb.toString()));
				myDataList.add(sb.toString());
			}

			// Current token is an opening brace, push it to 'ops'
			else if (tokens[i] == '(') {
				ops.push(tokens[i]);
				myDataList.add(tokens[i] + "");
			}

			// Closing brace encountered, solve entire brace
			else if (tokens[i] == ')')
			{
				myDataList.add(tokens[i] + "");
				while (ops.peek() != '(')
					values.push(calculate(ops.pop(), values.pop(), values.pop()));
				ops.pop();
			}

			else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/') {
				// While top of 'ops' has same or greater precedence to current
				// token, which is an operator. Apply operator on top of 'ops'
				// to top two elements in values stack
				while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
					values.push(calculate(ops.pop(), values.pop(), values.pop()));

				// Push current token to 'ops'.
				ops.push(tokens[i]);
				myDataList.add(tokens[i] + "");
			}

			i++;
		}

		// Entire expression has been parsed at this point, apply remaining
		// ops to remaining values
		while (!ops.empty())
			values.push(calculate(ops.pop(), values.pop(), values.pop()));

		// Top of 'values' contains result, return it
		return values.pop();
	}

	// Returns true if 'op2' has higher or same precedence as 'op1',
	// otherwise returns false.
	public static boolean hasPrecedence(char op1, char op2) {
		if (op2 == '(' || op2 == ')')
			return false;
		if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
			return false;
		else
			return true;
	}

	// A utility method to apply an operator 'op' on operands 'a'
	// and 'b'. Return the result.

	public float calculate(char op, float b, float a) {

		switch (op) {
			case '+':
				Operator add = new Add();
				mOperations.add(add);
				return add.operate(a, b);
			case '-':
				Operator sub = new Subtract();
				mOperations.add(sub);
				return sub.operate(a, b);
			case '*':
				Operator multi = new Multiply();
				mOperations.add(multi);
				return multi.operate(a, b);
			case '/':
				Operator div = new Division();
				mOperations.add(div);
				return div.operate(a, b);
		}
		return 0;
	}

	@Override
	public float operate(float a, float b) {
		return 0;
	}

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}

package edu.mum.cs.cs525.spreadsheet;

public class Division extends Operator {

	private float quotient = 0.0f;
	private int counter = 0;


	@Override
	public String value() {

		return Float.toString(quotient);
	}

	@Override
	public String formula() {

		StringBuffer sBuffer = new StringBuffer("(");
		for (int i = 0; i < ref.size(); i++) {
			if (i != ref.size() - 1) {
				sBuffer.append(ref.get(i).formula() + "/");
			} else {
				sBuffer.append(ref.get(i).formula() + ")");

			}

		}

		return sBuffer.toString();
	}

	@Override
	public float data() {

		return quotient;
	}

	public void divideContent(Content content) {

		if (content.data() != 0) {
			if (counter == 0) {
				quotient = content.data();
				counter++;
				ref.add((Reference) content);
			} else {
				quotient /= content.data();
				ref.add((Reference) (content));
			}

		}
	}

	@Override
	public float operate( float a, float b) {
		if (b == 0)
			throw new UnsupportedOperationException("Cannot divide by zero");
		return a / b;
	}
}

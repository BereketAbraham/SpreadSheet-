package edu.mum.cs.cs525.spreadsheet;

public class Subtract extends Operator {

	//List<Reference> ref = new ArrayList<Reference>();
	private float subResult = 0.0f;
	private int counter = 0;

	@Override
	public String value() {
		return Float.toString(subResult);
	}

	@Override
	public String formula() {
		StringBuffer sBuffer = new StringBuffer("(");
		// int count = 0;
		for (int i = 0; i < ref.size(); i++) {
			if (i != ref.size() - 1) {
				sBuffer.append(ref.get(i).formula() + "-");
			} else {
				sBuffer.append(ref.get(i).formula() + ")");

			}

		}

		return sBuffer.toString();
	}

	@Override
	public float data() {
		return subResult;
	}

	public void subContent(Content content) {

		if (counter == 0) {
			subResult += content.data();
			ref.add((Reference) content);
			counter++;
		} else {
			subResult -= content.data();
			if (content instanceof Reference) {
				ref.add((Reference) content);
			}
		}

	}
}

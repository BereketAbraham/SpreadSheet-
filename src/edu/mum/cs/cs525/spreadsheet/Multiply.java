package edu.mum.cs.cs525.spreadsheet;

import java.util.List;

public class Multiply extends Operator {

	private float products = 1.0f;

	public List<Reference> getRef() {
		return ref;
	}

	@Override
	public String value() {

		return Float.toString(products);
	}

	@Override
	public String formula() {


		StringBuffer sBuffer = new StringBuffer("(");
		for (int i = 0; i < ref.size(); i++) {
			if (i != ref.size() - 1) {
				sBuffer.append(ref.get(i).formula() + "*");
			} else {
				sBuffer.append(ref.get(i).formula() + ")");

			}

		}

		return sBuffer.toString();
	}

	@Override
	public float data() {

		return products;
	}

	public void multContent(Content content) {

		products *= content.data();
		if (content instanceof Reference) {
			ref.add((Reference) content);
		}
	}

	@Override
	public float operate(float a, float b) {
		return a * b;
	}
}

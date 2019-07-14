package mum.edu.cs.cs525.spreadsheet;

import java.util.ArrayList;
import java.util.List;

public class Multiply extends NumericOperation {

	private float products = 1.0f;
	List<Reference> ref = new ArrayList<Reference>();

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

}

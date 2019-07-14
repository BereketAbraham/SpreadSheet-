package mum.edu.cs.cs525.spreadsheet;

import java.util.ArrayList;
import java.util.List;

public class Add extends NumericOperation {

	List<Reference> ref = new ArrayList<Reference>();
	private float addNumber = 0.0f;

	@Override
	public String value() {

		return Float.toString(addNumber);
	}

	// get the reference of the added cells
	@Override
	public String formula() {


		StringBuffer sBuffer = new StringBuffer("(");
		int count = 0;
		for (int i = 0; i < ref.size(); i++) {
			if (i != ref.size() - 1) {
				sBuffer.append(ref.get(i).formula() + "+");
			} else {
				sBuffer.append(ref.get(i).formula() + ")");

			}

		}

		return sBuffer.toString();
	}

	@Override
	public float data() {

		return addNumber;
	}

	public void addContent(Content content) {
		addNumber += content.data();
		if (content instanceof Reference) {
			ref.add((Reference) content);
		}
	}

}

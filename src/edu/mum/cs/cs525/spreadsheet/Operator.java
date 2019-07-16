package edu.mum.cs.cs525.spreadsheet;

import java.util.ArrayList;
import java.util.List;

public abstract class Operator extends Content{
	List<Reference> ref = new ArrayList<Reference>();
	public float template(char c, float a, float b) {

		return operate(a, b);

	}

	public abstract float operate(float a, float b);

}

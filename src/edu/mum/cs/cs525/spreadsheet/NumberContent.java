package edu.mum.cs.cs525.spreadsheet;

public class NumberContent extends Content{
	
	
	private float num;
	
	public Boolean isNumber() {
		return true;
	}
	
	public NumberContent(float num) {
		this.num = num;
	}
	
	@Override
	public String value() {
		
		return Float.toString(num);
	}

	@Override
	public String formula() {
		
		return Float.toString(num);
	}

	@Override
	public float data() {
		
		return num;
	}

}

package edu.mum.cs.cs525.spreadsheet;

public class Reference extends Content{

	private Cell cell ;
	public Reference(Cell cell) {
		//super(cell);		
		this.cell = cell;
	}

	@Override
	public String value() {
		return Float.toString(cell.data());
	}

	@Override
	public String formula() {

		return cell.getCoordinates();
	}

	@Override
	public float data() {

		return (cell.data());
	}

}

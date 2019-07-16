package edu.mum.cs.cs525.spreadsheet;

public class Cell extends Element 
{
	private int row;
	private int column;
	private String formulaString = new String();								// The formula, if any, as "typed in" by the end user

	public Cell()
	{
		row = 0; 
		column = 0;
	}

	public Cell(int r, int c)
	{
		row = r;
		column = c;
	}
	public Cell(int r, int c, Content ct)
	{
		this(r,c);
		setContent(ct);
	}
	
	public Cell(int r, int c, String formula)
	{
		this(r,c);
		setFormula(formula);
	}
	
	public void setCoordinates(int r, int c)
	{
		row  = r;
		column = c;
	}
	
	public String getCoordinates()
	{
		String coord = new String();
		coord += ("[" + row + "," + column + "]");
		
		return coord;
	}

	public int getRow() { return row; }
	public int getColumn() { return column; }

	public void setContent(Content cont)
	{
		if (cont != null)
			Associations.cellIsMadeOfContents.link(this,  cont);
	}
	
	public Content getContent()
	{
		return Associations.cellIsMadeOfContents.leftToRight(this);
	}
	
	public void clear()										// Deallocate the cell's contents
	{
		Associations.cellIsMadeOfContents.unlink(this,  getContent());
	}

	public Boolean isNull() { return row == 0 && column == 0; }	// A fake cell not belonging to the spreadsheet
	
	public Boolean isEmpty()									// It's an empty cell if there is no contents
	{
		return getContent() == null;
	}

	public void setFormula(String form)
	{
		formulaString = form;
	}
	
	public String getFormulaString()
	{
		return formulaString;
	}

	public String value()										// The result of what's in a cell or its contents
	{
		String val = new String();
		
		Content contents = getContent();
		if(contents != null)
			val = contents.value();
		else
			val = "";
		
		return val;
	}
	
	public float data()										// THe numerical values in that cell
	{
		float ret = 0;

		Content contents = getContent();

		if (contents != null)
			ret = contents.data();

		return ret;
		
	}
	
	public String formula()									// The mathematical description of a cell's contents
	{
		String forml;

		Content contents = getContent();

		if (contents != null)
			forml = contents.formula();
		else
			forml = ": null";

		return forml;
		
	}
	
	public String examine()									// Provide a textual description of what's in this cell
	{
		StringBuilder examination = new StringBuilder();

		examination.append(getCoordinates()).append(" = ").append(formula()).append("\n");

		return examination.toString();
	
	}
	// getting number
	public String examineNumber()									// Provide a textual description of what's in this cell
	{
		StringBuilder examination = new StringBuilder();
		
		examination.append(data()).append("\n");
		
		return examination.toString();
		
	}
}
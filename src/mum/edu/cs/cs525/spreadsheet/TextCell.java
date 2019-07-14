package mum.edu.cs.cs525.spreadsheet;

public class TextCell extends Content
{
	private StringBuilder text = new StringBuilder();

	public TextCell(String str){ append(str);}
	
	public Boolean isText() { return true; }

	public 	String value() { return text.toString(); }

	public TextCell append(String txt)
	{
		if (txt != null)
			text.append(txt);
		return this;
	}

	public String formula()
	{
		StringBuilder ret = new StringBuilder();

		ret.append('\"').append(text.toString()).append('\"');
		
		
		return ret.toString();
	}
	
	public float data() { return 0; }

	
}
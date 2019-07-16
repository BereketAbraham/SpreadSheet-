package edu.mum.cs.cs525.spreadsheet;

public class TextContent extends Content
{
	private StringBuilder text = new StringBuilder();

	public TextContent(String str){ append(str);}
	
	public Boolean isText() { return true; }

	public 	String value() { return text.toString(); }

	public TextContent append(String txt)
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
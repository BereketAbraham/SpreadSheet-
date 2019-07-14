package mum.edu.cs.cs525.spreadsheet;

public class Application
{

	public static void main(String[] args) 
	{
	
		SpreadSheet spreadsheet = new SpreadSheet(15);		// Create the spreadsheet we'll work on, with a column width to display
		Director director = new Director(spreadsheet);		// Give it to the facade

		director.buildSample();								// Build sample cells, for testing

		System.out.println(director.contents());			// Quick view of contents
		
		System.out.println("Examine");
		System.out.println(director.examine());				// Quick view of formulas
		
	}

}
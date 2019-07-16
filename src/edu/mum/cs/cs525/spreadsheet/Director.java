package edu.mum.cs.cs525.spreadsheet;

public class Director {
	private SpreadSheet spreadsheet;

	public Director(SpreadSheet sps) {
		spreadsheet = sps;
	}

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public SpreadSheet getSpreadsheet() {
		return spreadsheet;
	}

	public Cell cell(int row, int col) // Get a reference to cell at (row,col)
	{
		return spreadsheet.cell(row, col);
	}

	private void setCell(int row, int col, String string) {
		if (isNumeric(string)) {
			spreadsheet.cell(row, col).setContent(new NumberContent(Float.parseFloat(string)));
		} else if (isNumericExpression(string)) {
			spreadsheet.cell(row, col).setContent(new Expression(string));
		} else {
			spreadsheet.cell(row, col).setContent(new TextContent(string));
		}
	}

	public static boolean isNumericExpression(String string) {
		boolean isNumericExpression = false;
		String[] operands = null;

		if(string.contains("+") || string.contains("-") || string.contains("*") || string.contains("/")) {
			operands = string.split("[-+*/]");
			isNumericExpression = true;
		}

		if (isNumericExpression) {
			for (String s : operands) {
				if (!isNumeric(s)) {
					return false;
				}
			}
		}

		return isNumericExpression;
	}

	public String contents() {
		return spreadsheet.show();
	}

	public String describe() {
		return spreadsheet.describe();
	}

	public String examine() {
		return spreadsheet.examine();
	}

	public void buildSample() // Build sample data for development purpose
	{
		setCell(1, 1, "Airfare:");

		setCell(1, 2, "485.15");

		setCell(1, 3, "");

		setCell(1, 4, "What we pay to the airlines");

		setCell(2, 1, "Taxi:");

		setCell(2, 2, "118");

		setCell(3, 1, "Rental Car:");

		// adding the total of rental car
		Add add = new Add();
		NumberContent num1 = new NumberContent(295);
		NumberContent num2 = new NumberContent(0.85f);
		add.addContent(num1);
		add.addContent(num2);

		spreadsheet.cell(3, 2).setContent(add);
		cell(3, 2).setContent(add);
		// writeInCellNumber(3, 2, add);

		setCell(4, 1, "Hotel:");

		setCell(4, 2, "431");

		setCell(5, 1, "Meals:");

		setCell(5, 2, "115+2+3+4*5*6/3-7/8");

		setCell(5, 3, "");

		setCell(5, 4, "All meals combined");

		setCell(7, 1, "Sub-total:");

		// getting the subtotal
		Add subTotal = new Add();

		for (int i = 1; i <= 5; i++) {
			subTotal.addContent(new Reference(spreadsheet.cell(i, 2)));
		}
		spreadsheet.cell(7, 2).setContent(subTotal);
		cell(7, 2).setContent(subTotal);

		// subtracting values
		Subtract subtract = new Subtract();
		for (int i = 1; i <= 5; i++) {
			subtract.subContent(new Reference(spreadsheet.cell(i, 2)));
		}

		// multiplying values
		Multiply multiply = new Multiply();
		for (int i = 1; i <= 5; i++) {
			multiply.multContent(new Reference(spreadsheet.cell(i, 2)));
		}

		setCell(8, 1, "Tax:"); // Tax factor label

		setCell(8, 2, "0.15");

		setCell(9, 1, "Total:");

		// calculating total

		Multiply multiply2 = new Multiply();
		multiply2.multContent(new Reference(cell(7, 2)));
		multiply2.multContent(new Reference(cell(8, 2)));

		Subtract subtract2 = new Subtract();
		subtract2.subContent(new Reference(cell(7, 2)));
		subtract2.subContent(new NumberContent(multiply2.data()));

		spreadsheet.cell(9, 2).setContent(subtract2);
		cell(9, 2).setContent(subtract2);

		setCell(10, 1, "Partners: ");

		setCell(10, 2, "4");

		setCell(11, 1, "Months: ");

		setCell(11, 2, "12");

		setCell(12, 1, "Installments:");

		Division division = new Division();
		division.divideContent(new Reference(cell(9, 2)));
		division.divideContent(new Reference(cell(10, 2)));
		division.divideContent(new Reference(cell(11, 2)));

		spreadsheet.cell(12, 2).setContent(division);
		cell(12, 2).setContent(division);

	}

}
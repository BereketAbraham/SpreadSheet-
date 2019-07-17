package edu.mum.cs.cs525.spreadsheet;

public class Director {

	private SpreadSheet spreadsheet;

	public Director(SpreadSheet sps) {
		spreadsheet = sps;
	}

	public SpreadSheet getSpreadsheet() {
		return spreadsheet;
	}

	public void writeInCellText(int r, int c, String text) {
		if (text != null)
			spreadsheet.cell(r, c).setContent(new TextContent(text));
	}

	// Get a reference to cell at (row, col)
	public Cell cell(int row, int col) {
		return spreadsheet.cell(row, col);
	}

	public String contents() {
		return spreadsheet.show();
	}

	private void writeInCellNumber(int i, int j, float f) {
		// TODO Auto-generated method stub

		spreadsheet.cell(i, j).setContent(new NumberContent(f));

	}

	// numerical expression sprint3
	public void WriteInCellExpression(int r, int c, Operator obj) {
		// spreadsheet.cell(r, c).setContent(new NumericOperation());
	}

	public String describe() {
		return spreadsheet.describe();
	}

	public String examine() {
		return spreadsheet.examine();
	}

	// Build sample data for development purpose
	public void buildSample() {
		setCell(1, 1, "Airfare:");

		setCell(1, 2, "485.15f");

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

		setCell(5, 2, "150");

		setCell(5, 3, "");

		setCell(5, 4, "This is all our meals");

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

		// spreadsheet.cell(7, 3).setContent(subtract);
		// cell(7, 3).setContent(subtract);

		// muliplying values
		Multiply multiply = new Multiply();
		for (int i = 1; i <= 5; i++) {
			multiply.multContent(new Reference(spreadsheet.cell(i, 2)));
		}

		// spreadsheet.cell(7, 4).setContent(multiply);
		/// cell(7, 4).setContent(multiply);

		// writeInCellNumber(7, 2, spreadsheet.cell(1, 2).data());

		// reference goes here
		writeInCellText(7, 4, "This is just a reference to [1, 2], to test the \"Reference\" class and mechanism");
		Reference ref = new Reference(cell(1, 2));

		// cell(7,2).setContent(ref);

		writeInCellText(8, 1, "Tax:"); // Tax factor label

		writeInCellNumber(8, 2, 0.15f);

		writeInCellText(9, 1, "Total:");

		// calculating total

		Multiply multiply2 = new Multiply();
		multiply2.multContent(new Reference(cell(7, 2)));
		multiply2.multContent(new Reference(cell(8, 2)));

		Subtract subtract2 = new Subtract();
		subtract2.subContent(new Reference(cell(7, 2)));
		subtract2.subContent(new NumberContent(multiply2.data()));

		spreadsheet.cell(9, 2).setContent(subtract2);
		cell(9, 2).setContent(subtract2);

		writeInCellText(10, 1, "Partners: ");

		writeInCellNumber(10, 2, 4);

		writeInCellText(11, 1, "Months: ");

		writeInCellNumber(11, 2, 12);

		writeInCellText(12, 1, "Installments:");
		Division division = new Division();
		division.divideContent(new Reference(cell(9, 2)));
		division.divideContent(new Reference(cell(10, 2)));
		division.divideContent(new Reference(cell(11, 2)));

		spreadsheet.cell(12, 2).setContent(division);
		cell(12, 2).setContent(division);

//		setCell(13, 1, "Meals: ");
//		setCell(13, 2, "115+2+3+4*5*6/3-7/8");
		setCell(5, 2, "115+2+3+4*5*6/3-7/8");
		setCell(13, 1, "[7,2]*(1-[8,2])");

	}

	private void setCell(int row, int col, String string) {
		if (isNumeric(string)) {
			spreadsheet.cell(row, col).setContent(new NumberContent(Float.parseFloat(string)));
		} else if (isNumericExpression(string)) {
			spreadsheet.cell(row, col).setContent(new Expression(string, spreadsheet));
		} else {
			spreadsheet.cell(row, col).setContent(new TextContent(string));
		}
	}

	public static boolean isNumericExpression(String string) {
		boolean isNumericExpression = false;
		String[] operands = null;

		if (string.contains("+") || string.contains("-") || string.contains("*") || string.contains("/")
				|| string.contains(")") || string.contains("(")) {
			operands = string.split("[-+*/()]");
			isNumericExpression = true;
		}

		if (isNumericExpression) {
			for (String s : operands) {
				if(s.trim().isEmpty()) {
					continue;
				}
				if (!isNumeric(s) && !s.matches("\\[[0-9]+,[0-9]+\\]")) {
					return false;
				}
			}
		}

		return isNumericExpression;
	}

	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
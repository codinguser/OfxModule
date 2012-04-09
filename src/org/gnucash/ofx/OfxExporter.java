package org.gnucash.ofx;

import javax.xml.parsers.ParserConfigurationException;

import org.gnucash.ofx.data.Account;
import org.gnucash.ofx.data.Expenses;
import org.gnucash.ofx.data.Transaction;
import org.gnucash.ofx.io.OfxWriter;

public class OfxExporter {

	/**
	 * Program entry point
	 * @param args
	 */
	public static void main(String[] args) {
		Expenses expenses = new Expenses();
		
		Account householdAcc = new Account("Household");
		Account vacationAcc = new Account("Vacation");
		
		householdAcc.addTransaction(new Transaction(-22, "Groceries"));
		householdAcc.addTransaction(new Transaction(-200, "Shelf"));
		
		vacationAcc.addTransaction(new Transaction(-15, "Ice cream"));
		vacationAcc.addTransaction(new Transaction(-250, "Hotel"));
		
		expenses.addAccount(householdAcc);
		expenses.addAccount(vacationAcc);
		
		try {
			OfxWriter.write(expenses, "output.ofx");
		} catch (ParserConfigurationException e) {			
			e.printStackTrace();
		}

	}

}

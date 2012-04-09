package org.gnucash.ofx.io;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.gnucash.ofx.data.Account;
import org.gnucash.ofx.data.Expenses;
import org.gnucash.ofx.data.Transaction;
import org.junit.Test;

public class OfxWriterTest {

	public final String OUTPUT = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<?OFX OFXHEADER=\"200\" VERSION=\"211\" SECURITY=\"NONE\" OLDFILEUID=\"NONE\" NEWFILEUID=\"NONE\"?>\n\n<OFX>\n  <STMTTRN>\n    <TRNTYPE>DEBIT</TRNTYPE>\n    <DTPOSTED>2012-04-09:23-58-52</DTPOSTED>\n    <DTUSER>2012-04-09:23-58-52</DTUSER>\n    <TRNAMT>-22.0</TRNAMT>\n    <FITID>65e95e6e:13698c08325:-8000</FITID>\n    <NAME>Groceries</NAME>\n    <MEMO/>\n  </STMTTRN>\n  <STMTTRN>\n    <TRNTYPE>DEBIT</TRNTYPE>\n    <DTPOSTED>2012-04-09:23-58-52</DTPOSTED>\n    <DTUSER>2012-04-09:23-58-52</DTUSER>\n    <TRNAMT>-200.0</TRNAMT>\n    <FITID>65e95e6e:13698c08325:-7fff</FITID>\n    <NAME>Shelf</NAME>\n    <MEMO/>\n  </STMTTRN>\n  <STMTTRN>\n    <TRNTYPE>DEBIT</TRNTYPE>\n    <DTPOSTED>2012-04-09:23-58-52</DTPOSTED>\n    <DTUSER>2012-04-09:23-58-52</DTUSER>\n    <TRNAMT>-15.0</TRNAMT>\n    <FITID>65e95e6e:13698c08325:-7ffe</FITID>\n    <NAME>Ice cream</NAME>\n    <MEMO/>\n  </STMTTRN>\n  <STMTTRN>\n    <TRNTYPE>DEBIT</TRNTYPE>\n    <DTPOSTED>2012-04-09:23-58-52</DTPOSTED>\n    <DTUSER>2012-04-09:23-58-52</DTUSER>\n    <TRNAMT>-250.0</TRNAMT>\n    <FITID>65e95e6e:13698c08325:-7ffd</FITID>\n    <NAME>Hotel</NAME>\n    <MEMO/>\n  </STMTTRN>\n</OFX>\n";
	
	@Test
	public void testWrite() {
		Expenses expenses = new Expenses();
				
		Account householdAcc = new Account("Household");
		Account vacationAcc = new Account("Vacation");		
		
		Date date = new Date(1334008732591l);		
		
		Transaction groc = new Transaction(-22, "Groceries");
		groc.setTransactionUID("65e95e6e:13698c08325:-8000");
		groc.setTransactionTime(date);
		Transaction shelf = new Transaction(-200, "Shelf");
		shelf.setTransactionUID("65e95e6e:13698c08325:-7fff");
		shelf.setTransactionTime(date);
		
		householdAcc.addTransaction(groc);
		householdAcc.addTransaction(shelf);
		
		Transaction ice = new Transaction(-15, "Ice cream");
		ice.setTransactionUID("65e95e6e:13698c08325:-7ffe");	
		ice.setTransactionTime(date);
		Transaction hotel = new Transaction(-250, "Hotel");
		hotel.setTransactionUID("65e95e6e:13698c08325:-7ffd");
		hotel.setTransactionTime(date);
		
		vacationAcc.addTransaction(ice);		
		vacationAcc.addTransaction(hotel);
		
		expenses.addAccount(householdAcc);
		expenses.addAccount(vacationAcc);
		
		StringWriter strWriter = new StringWriter();
		try {
			OfxWriter.write(expenses, strWriter);			
		} catch (ParserConfigurationException e) {			
			e.printStackTrace();
		}
		
		assertEquals(OUTPUT, strWriter.toString());
	}

}

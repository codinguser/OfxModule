/*
 * Written By: Ngewi Fet <ngewif@gmail.com>
 * Copyright (c) 2012 Ngewi Fet
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, contact:
 *
 * Free Software Foundation           Voice:  +1-617-542-5942
 * 51 Franklin Street, Fifth Floor    Fax:    +1-617-542-2652
 * Boston, MA  02110-1301,  USA       gnu@gnu.org
 */

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

	public final String EXPECTED_OUTPUT = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<?OFX OFXHEADER=\"200\" VERSION=\"211\" SECURITY=\"NONE\" OLDFILEUID=\"NONE\" NEWFILEUID=\"NONE\"?>\n\n<OFX>\n  <STMTTRN>\n    <TRNTYPE>DEBIT</TRNTYPE>\n    <DTPOSTED>2012-04-09:23-58-52</DTPOSTED>\n    <DTUSER>2012-04-09:23-58-52</DTUSER>\n    <TRNAMT>-22.0</TRNAMT>\n    <FITID>65e95e6e:13698c08325:-8000</FITID>\n    <NAME>Groceries</NAME>\n    <MEMO/>\n  </STMTTRN>\n  <STMTTRN>\n    <TRNTYPE>DEBIT</TRNTYPE>\n    <DTPOSTED>2012-04-09:23-58-52</DTPOSTED>\n    <DTUSER>2012-04-09:23-58-52</DTUSER>\n    <TRNAMT>-200.0</TRNAMT>\n    <FITID>65e95e6e:13698c08325:-7fff</FITID>\n    <NAME>Shelf</NAME>\n    <MEMO/>\n  </STMTTRN>\n  <STMTTRN>\n    <TRNTYPE>DEBIT</TRNTYPE>\n    <DTPOSTED>2012-04-09:23-58-52</DTPOSTED>\n    <DTUSER>2012-04-09:23-58-52</DTUSER>\n    <TRNAMT>-15.0</TRNAMT>\n    <FITID>65e95e6e:13698c08325:-7ffe</FITID>\n    <NAME>Ice cream</NAME>\n    <MEMO/>\n  </STMTTRN>\n  <STMTTRN>\n    <TRNTYPE>DEBIT</TRNTYPE>\n    <DTPOSTED>2012-04-09:23-58-52</DTPOSTED>\n    <DTUSER>2012-04-09:23-58-52</DTUSER>\n    <TRNAMT>-250.0</TRNAMT>\n    <FITID>65e95e6e:13698c08325:-7ffd</FITID>\n    <NAME>Hotel</NAME>\n    <MEMO/>\n  </STMTTRN>\n</OFX>\n";
	
	@Test
	public void testWrite() {
		Expenses expenses = new Expenses();
				
		Account householdAcc = new Account("Household");
		Account vacationAcc = new Account("Vacation");		
		
		Date date = new Date(1334008732591l);		
		
		Transaction groc = new Transaction(-22, "Groceries");
		groc.setUID("65e95e6e:13698c08325:-8000");
		groc.setTime(date);
		Transaction shelf = new Transaction(-200, "Shelf");
		shelf.setUID("65e95e6e:13698c08325:-7fff");
		shelf.setTime(date);
		
		householdAcc.addTransaction(groc);
		householdAcc.addTransaction(shelf);
		
		Transaction ice = new Transaction(-15, "Ice cream");
		ice.setUID("65e95e6e:13698c08325:-7ffe");	
		ice.setTime(date);
		Transaction hotel = new Transaction(-250, "Hotel");
		hotel.setUID("65e95e6e:13698c08325:-7ffd");
		hotel.setTime(date);
		
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
		
		assertEquals(EXPECTED_OUTPUT, strWriter.toString());
	}

}

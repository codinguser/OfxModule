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

package org.gnucash.ofx;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.gnucash.ofx.data.Account;
import org.gnucash.ofx.data.Expenses;
import org.gnucash.ofx.data.Transaction;
import org.gnucash.ofx.io.OfxWriter;

/**
 * Main class, runs a scenario for the exporter
 * @author ngewif
 *
 */
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
			File file = new File("output.ofx");
			OfxWriter.write(expenses, new FileWriter(file));
		} catch (ParserConfigurationException e) {			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

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

package org.gnucash.ofx.data;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Represents total expenses
 */
public class Expenses {

	/**
	 * List of accounts in the expense report
	 */
	private ArrayList<Account> mAccountsList = new ArrayList<Account>();
	
	/**
	 * Constructor
	 */
	public Expenses() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Adds <code>account</code> to the expense report
	 * @param account {@link Account} to add to expense
	 */
	public void addAccount(Account account){
		mAccountsList.add(account);
	}
	
	/**
	 * Removes <code>account</code> from the expense report
	 * @param account
	 */
	public void removeAccount(Account account){
		mAccountsList.remove(account);
	}
	
	/**
	 * Returns list of accounts in expense report
	 * @return List of accounts in expense report
	 */
	public ArrayList<Account> getAccounts(){
		return mAccountsList;
	}
	
	/**
	 * Converts all expenses into OFX XML format and adds them to the XML document
	 * @param doc DOM document of the OFX expenses
	 * @param parent Parent node for all expenses in report
	 */
	public void toXml(Document doc, Element parent){
		for (Account account : mAccountsList) {
			account.toXml(doc, parent);			
		}
	}
}

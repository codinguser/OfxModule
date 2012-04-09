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
 * An account within which many Transactions occur
 * @author Ngewi Fet <ngewif@gmail.com>
 *
 */
public class Account {

	/**
	 * Name of this account
	 */
	private String mName;
	
	/**
	 * List of transactions in this account
	 */
	private ArrayList<Transaction> mTransactionsList = new ArrayList<Transaction>();
	
	/**
	 * Constructor
	 * @param name Name of the account
	 */
	public Account(String name) {
		this.mName = name;
	}

	/**
	 * Sets the name of the account
	 * @param name String name of the account
	 */
	public void setName(String name) {
		this.mName = name;
	}

	/**
	 * Returns the name of the account
	 * @return String containing name of the account
	 */
	public String getName() {
		return mName;
	}
	
	/**
	 * Adds a transaction to this account
	 * @param transaction {@link Transaction} to be added to the account
	 */
	public void addTransaction(Transaction transaction){
		mTransactionsList.add(transaction);
	}
	
	/**
	 * Removes <code>transaction</code> from this account
	 * @param transaction {@link Transaction} to be removed from account
	 */
	public void removeTransaction(Transaction transaction){
		mTransactionsList.remove(transaction);
	}
	
	/**
	 * Returns a list of transactions for this account
	 * @return Array list of transactions for the account
	 */
	public ArrayList<Transaction> getTransactions(){
		return mTransactionsList;
	}
	
	/**
	 * Converts this account's transactions into XML and adds them to the DOM document
	 * @param doc XML DOM document for the OFX data
	 * @param parent Node to which to add this account's transactions
	 */
	public void toXml(Document doc, Element parent){
		for (Transaction transaction : mTransactionsList) {
			parent.appendChild(transaction.toXml(doc));
		}
	}
}

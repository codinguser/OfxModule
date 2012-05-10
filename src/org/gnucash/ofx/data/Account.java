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
import java.util.UUID;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * An account within which many Transactions occur
 * @author Ngewi Fet <ngewif@gmail.com>
 *
 */
public class Account {

	/**
	 * The type of account
	 *
	 */
	public enum AccountType {CHECKING, SAVINGS, MONEYMRKT, CREDITLINE};
	
	/**
	 * Account ID
	 */
	private String mUID;
	
	/**
	 * Name of this account
	 */
	private String mName;
	
	private AccountType mAccountType = AccountType.CHECKING;
	
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
		this.mUID = UUID.randomUUID().toString();
		this.mUID.replaceAll("-", "");
		this.mUID.replaceAll(":", "");
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
	 * Returns the unique ID of this account
	 * @return String containing unique ID for the account
	 */
	public String getId(){
		return mUID;
	}
	
	/**
	 * Get the type of account
	 * @return {@link AccountType} type of account
	 */
	public AccountType getAccountType() {
		return mAccountType;
	}

	/**
	 * Sets the type of account
	 * @param mAccountType Type of account
	 * @see AccountType
	 */
	public void setAccountType(AccountType mAccountType) {
		this.mAccountType = mAccountType;
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
	 * Returns the aggregate of all transactions in this account.
	 * It takes into account debit and credit amounts
	 * @return Aggregate amount of all transactions in account.
	 */
	public double getBalance(){
		double balance = 0;
		for (Transaction transx : mTransactionsList) {
			balance += transx.getAmount();
		}
		return balance;
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

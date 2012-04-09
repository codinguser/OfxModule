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

import java.rmi.server.UID;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Data model representation of a transaction
 * @author ngewif
 *
 */
public class Transaction {
	/**
	 * Type of transaction, a credit or a debit
	 * 
	 */
	public enum TransactionType {DEBIT, CREDIT};
	
	private double mAmount;
	private String mTransactionUID;
	private String mName;
	private String mDescription = "";
	
	private Date mTimestamp;
	private TransactionType mType = TransactionType.DEBIT;
	
	/**
	 * Overloaded constructor. Creates a new transaction instance with the 
	 * provided data and initializes the rest to default values. 
	 * @param amount Amount for the transaction
	 * @param name Name of the transaction
	 */
	public Transaction(double amount, String name) {
		initDefaults();
		this.mAmount = amount;
		this.mName = name;
	}
	
	/**
	 * Overloaded constructor. Creates a new transaction instance with the 
	 * provided data and initializes the rest to default values. 
	 * @param amount Amount for the transaction
	 * @param name Name of the transaction
	 * @param type Type of transaction
	 */
	public Transaction(double amount, String name, TransactionType type){
		initDefaults();
		this.mAmount = amount;		
		this.mType = type;
		this.mName = name;
	}
	
	/**
	 * Initializes the different fields to their default values.
	 */
	private void initDefaults(){
		this.mTimestamp = new Date();
		this.mType = TransactionType.DEBIT;
		mTransactionUID = new UID().toString();
	}
	
	/**
	 * Set the amount of this transaction
	 * @param mAmount Amount of the transaction
	 */
	public void setAmount(double amount) {
		this.mAmount = amount;
	}

	/**
	 * Returns the amount involved in this transaction
	 * @return Amount in the transaction
	 */
	public double getAmount() {
		return mAmount;
	}
	
	/**
	 * Returns the name of the transaction
	 * @return Name of the transaction
	 */
	public String getName() {
		return mName;
	}

	/**
	 * Sets the name of the transaction
	 * @param name String containing name of transaction to set
	 */
	public void setName(String name) {
		this.mName = name;
	}

	/**
	 * Set short description of the transaction
	 * @param description String containing description of transaction
	 */
	public void setDescription(String description) {
		this.mDescription = description;
	}

	/**
	 * Returns the description of the transaction
	 * @return String containing description of transaction
	 */
	public String getDescription() {
		return mDescription;
	}

	/**
	 * Set the time of the transaction
	 * @param timestamp Time when transaction occurred as {@link Date}
	 */
	public void setTime(Date timestamp){
		this.mTimestamp = timestamp;
	}
	
	/**
	 * Returns time when transaction occured
	 * @return {@link Date} object for time when transaction occured
	 */
	public Date getTime(){
		return mTimestamp;
	}
	
	/**
	 * Sets the type of transaction
	 * @param type The transaction type 
	 * @see TransactionType 
	 */
	public void setTransactionType(TransactionType type){
		this.mType = type;
	}
		
	/**
	 * Returns the type of transaction
	 * @return Type of transaction
	 */
	public TransactionType getTransactionType(){
		return this.mType;
	}
	
	/**
	 * Set Unique Identifier for this transaction
	 * @param mTransactionUID Unique ID string
	 */
	public void setUID(String mTransactionUID) {
		this.mTransactionUID = mTransactionUID;
	}

	/**
	 * Returns unique ID string for transaction
	 * @return String with Unique ID of transaction
	 */
	public String getUID() {
		return mTransactionUID;
	}

	/**
	 * Converts transaction to XML DOM corresponding to OFX Statement transaction and 
	 * returns the element node for the transaction
	 * @param doc XML document to which transaction should be added
	 * @return Element in DOM corresponding to transaction
	 */
	public Element toXml(Document doc){
		Element transaction = doc.createElement("STMTTRN");
		Element type = doc.createElement("TRNTYPE");
		type.appendChild(doc.createTextNode(mType.toString()));
		transaction.appendChild(type);
		
		Element datePosted = doc.createElement("DTPOSTED");
		datePosted.appendChild(doc.createTextNode(
				new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").format(mTimestamp)));
		transaction.appendChild(datePosted);
		
		Element dateUser = doc.createElement("DTUSER");
		dateUser.appendChild(doc.createTextNode(
				new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").format(mTimestamp)));
		transaction.appendChild(dateUser);
		
		Element amount = doc.createElement("TRNAMT");
		amount.appendChild(doc.createTextNode(Double.toString(mAmount)));
		transaction.appendChild(amount);
		
		Element transID = doc.createElement("FITID");
		transID.appendChild(doc.createTextNode(mTransactionUID));
		transaction.appendChild(transID);
		
		Element name = doc.createElement("NAME");
		name.appendChild(doc.createTextNode(mName));
		transaction.appendChild(name);
		
		Element memo = doc.createElement("MEMO");
		memo.appendChild(doc.createTextNode(mDescription));
		transaction.appendChild(memo);
		
		return transaction;
	}
}

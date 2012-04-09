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

public class Transaction {
	public enum TransactionType {DEBIT, CREDIT};
	
	private double mAmount;
	private String mTransactionUID;
	private String mName;
	private String mDescription = "";
	
	private Date mTimestamp;
	private TransactionType mType = TransactionType.DEBIT;
	
	
	public Transaction(double amount, String name) {
		initDefaults();
		this.mAmount = amount;
		this.mName = name;
	}
	
	public Transaction(double amount, String name, TransactionType type){
		initDefaults();
		this.mAmount = amount;		
		this.mType = type;
		this.mName = name;
	}
	
	private void initDefaults(){
		this.mTimestamp = new Date();
		this.mType = TransactionType.DEBIT;
		mTransactionUID = new UID().toString();
	}
	
	public void setAmount(double mAmount) {
		this.mAmount = mAmount;
	}

	public double getAmount() {
		return mAmount;
	}
	
	/**
	 * @return the mName
	 */
	public String getName() {
		return mName;
	}

	/**
	 * @param mName the mName to set
	 */
	public void setName(String mName) {
		this.mName = mName;
	}

	public void setDescription(String mDescription) {
		this.mDescription = mDescription;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setTransactionTime(Date timestamp){
		this.mTimestamp = timestamp;
	}
	
	public Date getTime(){
		return mTimestamp;
	}
	
	public void setTransactionType(TransactionType type){
		this.mType = type;
	}
	
	public TransactionType getTransactionType(){
		return this.mType;
	}
	
	public void setUID(String mTransactionUID) {
		this.mTransactionUID = mTransactionUID;
	}

	public String getUID() {
		return mTransactionUID;
	}

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

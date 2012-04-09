package org.gnucash.ofx.data;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Account {

	private String mName;
	
	private ArrayList<Transaction> mTransactionsList = new ArrayList<Transaction>();
	
	public Account(String name) {
		this.mName = name;
	}

	public void setName(String mName) {
		this.mName = mName;
	}

	public String getName() {
		return mName;
	}
	
	public void addTransaction(Transaction transaction){
		mTransactionsList.add(transaction);
	}
	
	public void removeTransaction(Transaction transaction){
		mTransactionsList.remove(transaction);
	}
	
	public ArrayList<Transaction> getTransactions(){
		return mTransactionsList;
	}
	
	public void toXml(Document doc, Element parent){
		for (Transaction transaction : mTransactionsList) {
			parent.appendChild(transaction.toXml(doc));
		}
	}
}

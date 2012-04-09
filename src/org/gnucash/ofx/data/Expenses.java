package org.gnucash.ofx.data;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Expenses {

	private ArrayList<Account> mAccountsList = new ArrayList<Account>();
	
	public Expenses() {
		// TODO Auto-generated constructor stub
	}
	
	public void addAccount(Account transaction){
		mAccountsList.add(transaction);
	}
	
	public void removeAccount(Account transaction){
		mAccountsList.remove(transaction);
	}
	
	public ArrayList<Account> getTransactions(){
		return mAccountsList;
	}
	
	public void toXml(Document doc, Element parent){
		for (Account account : mAccountsList) {
			account.toXml(doc, parent);			
		}
	}
}

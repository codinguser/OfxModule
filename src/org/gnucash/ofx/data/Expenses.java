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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Represents total expenses
 */
public class Expenses {

	public final static SimpleDateFormat ofxDateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
	
	/**
	 * ID which will be used as the bank ID for OFX from this app
	 */
	public static String APP_ID = "org.gnucash.android";
	
	/**
	 * The Transaction ID is usually the client ID sent in a request.
	 * Since the data exported is not as a result of a request, we use 0
	 */
	public static final String UNSOLICITED_TRANSACTION_ID = "0";
	
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
	
	public static String getFormattedCurrentTime(){
		return getFormattedCurrentTime(System.currentTimeMillis());
	}
	
	public static String getFormattedCurrentTime(long milliseconds){
		Date date = new Date(milliseconds);
		String dateString = ofxDateFormatter.format(date);
		TimeZone tz = Calendar.getInstance().getTimeZone();
		int offset = tz.getRawOffset();
		int hours   = (int) (( offset / (1000*60*60)) % 24);
		String sign = offset > 0 ?  "+" : "";
		return dateString + "[" + sign + hours + ":" + tz.getDisplayName(false, TimeZone.SHORT, Locale.getDefault()) + "]";
	}
	
	/**
	 * Converts all expenses into OFX XML format and adds them to the XML document
	 * @param doc DOM document of the OFX expenses
	 * @param parent Parent node for all expenses in report
	 */
	public void toXml(Document doc, Element parent){
		
		Element transactionUid = doc.createElement("TRNUID");		
		//unsolicited because the data exported is not as a result of a request
		transactionUid.appendChild(doc.createTextNode(UNSOLICITED_TRANSACTION_ID));

		Element statementTransactionResponse = doc.createElement("STMTTRNRS");
		statementTransactionResponse.appendChild(transactionUid);
		
		Element bankmsgs = doc.createElement("BANKMSGSRSV1");
		bankmsgs.appendChild(statementTransactionResponse);
		
		parent.appendChild(bankmsgs);		
		
		for (Account account : mAccountsList) {	
			Element currency = doc.createElement("CURDEF");
			currency.appendChild(doc.createTextNode(Currency.getInstance(Locale.getDefault()).getCurrencyCode()));						
			
			//================= BEGIN BANK ACCOUNT INFO (BANKACCTFROM) =================================
			
			Element bankId = doc.createElement("BANKID");
			bankId.appendChild(doc.createTextNode(APP_ID));
			
			Element acctId = doc.createElement("ACCTID");
			acctId.appendChild(doc.createTextNode(account.getId()));
			
			Element accttype = doc.createElement("ACCTTPYE");
			accttype.appendChild(doc.createTextNode(account.getAccountType().toString()));
			
			Element bankFrom = doc.createElement("BANKACCTFROM");
			bankFrom.appendChild(bankId);
			bankFrom.appendChild(acctId);
			bankFrom.appendChild(accttype);
			
			//================= END BANC ACCOUNT INFO ============================================
			
			
			//================= BEGIN ACCOUNT BALANCE INFO =================================
			String balance = Double.toString(account.getBalance());
			String formattedCurrentTimeString = getFormattedCurrentTime();
			
			Element balanceAmount = doc.createElement("BALAMT");
			balanceAmount.appendChild(doc.createTextNode(balance));			
			Element dtasof = doc.createElement("DTASOF");
			dtasof.appendChild(doc.createTextNode(formattedCurrentTimeString));
			
			Element ledgerBalance = doc.createElement("LEDGERBAL");
			ledgerBalance.appendChild(balanceAmount);
			ledgerBalance.appendChild(dtasof);
			
			//================= END ACCOUNT BALANCE INFO =================================
			
			
			//================= BEGIN TIME PERIOD INFO =================================
			
			Element dtstart = doc.createElement("DTSTART");			
			dtstart.appendChild(doc.createTextNode(formattedCurrentTimeString));
			
			Element dtend = doc.createElement("DTEND");
			dtend.appendChild(doc.createTextNode(formattedCurrentTimeString));
			
			Element bankTransactionsList = doc.createElement("BANKTRANLIST");
			bankTransactionsList.appendChild(dtstart);
			bankTransactionsList.appendChild(dtend);
			
			//================= END TIME PERIOD INFO =================================
			
			
			
			Element statementTransactions = doc.createElement("STMTRS");
			statementTransactions.appendChild(currency);
			statementTransactions.appendChild(bankFrom);
			statementTransactions.appendChild(bankTransactionsList);
			statementTransactions.appendChild(ledgerBalance);
			
			statementTransactionResponse.appendChild(statementTransactions);
			
			//add account details (transactions) to the XML document			
			account.toXml(doc, bankTransactionsList);			
		}
	}
}

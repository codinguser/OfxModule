package org.gnucash.ofx.data;

import static org.junit.Assert.assertEquals;

import java.rmi.server.UID;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionTest {
	private static final String NAME = "Test expenditure";
	private static final String DESCRIPTION = "Lorem Ipsum description for testing";
	static Transaction mTransaction;
	
	@BeforeClass
	public static void prepare(){
		mTransaction = new Transaction(-25, NAME);
		mTransaction.setDescription(DESCRIPTION);
	}
	
	@Test
	public void testGetAmount() {
		assertEquals(-25, mTransaction.getAmount(), 0.000001);
	}
	
	@Test
	public void testSetAmount() {
		mTransaction.setAmount(20);
		assertEquals(20, mTransaction.getAmount(), 0.000001);
	}

	@Test
	public void testGetName() {
		assertEquals(NAME, mTransaction.getName());
	}

	@Test
	public void testSetName() {
		mTransaction.setName("Accessories");
		assertEquals(mTransaction.getName(), "Accessories");		
	}

	@Test
	public void testGetDescription() {
		assertEquals(DESCRIPTION, mTransaction.getDescription());
	}
	
	@Test
	public void testSetDescription() {
		mTransaction.setDescription("New gadgets and stuff");
		assertEquals("New gadgets and stuff", mTransaction.getDescription());
	}

	@Test
	public void testSetTime() {
		Date now = new Date();
		mTransaction.setTransactionTime(now);
		assertEquals(now, mTransaction.getTime());
	}

	@Test
	public void testGetTime() {
		Date now = new Date();
		mTransaction.setTransactionTime(now);
		assertEquals(now, mTransaction.getTime());
	}

	@Test
	public void testSetUID() {
		UID uid = new UID();
		mTransaction.setUID(uid.toString());
		assertEquals(uid.toString(), mTransaction.getUID());
	}

	@Test
	public void testUID() {
		UID uid = new UID();
		mTransaction.setUID(uid.toString());
		assertEquals(uid.toString(), mTransaction.getUID());
	}

}

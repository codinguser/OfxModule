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
		mTransaction.setTime(now);
		assertEquals(now, mTransaction.getTime());
	}

	@Test
	public void testGetTime() {
		Date now = new Date();
		mTransaction.setTime(now);
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

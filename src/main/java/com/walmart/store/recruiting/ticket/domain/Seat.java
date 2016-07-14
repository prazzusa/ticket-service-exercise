
package com.walmart.store.recruiting.ticket.domain;

import java.util.Calendar;
import java.util.Date;

public class Seat {

	/**
	 * ID
	 */
	private int id;

	/**
	 * Row containing seat
	 */
	private int row;

	/**
	 * Column containing seat
	 */
	private int column;

	/**
	 * is Available or not
	 */
	private boolean isAvailable;

	/**
	 *  Reserved or Not
	 */
	private boolean isReserved;

	/**
	 * Expire date of the seat
	 */
	private Calendar expired;

	/**
	 * 
	 */
	public Seat() {

	}

	/**
	 * Constructor with row, column
	 * 
	 * @param r
	 * @param c
	 */
	public Seat(int row, int column) {

	
		this.id = row * column;
		this.row = row;
		this.column = column;
		this.isAvailable = true;
		this.isReserved = false;
	}
	/**
	 * Constructor with id, row and col
	 * 
	 * @param id
	 * @param r
	 * @param c
	 */
	public Seat(int id, int row, int column) {
		
		this.id = id;
		this.row = row;
		this.column = column;
		this.isAvailable = true;
		this.isReserved = false;
	}

	/**
	 * Constructor with row, column, available
	 * 
	 * @param r
	 * @param c
	 * @param isAvailable
	 */
	public Seat(int row, int column, boolean isAvailable) {
		
		this.row = row;
		this.column = column;
		this.isAvailable = isAvailable;
		this.isReserved = false;
	}

	

	/**
	 * if the seat is not available and not reserved,but the hold has expired,
	 * make it available. Lazy update on available field.
	 * 
	 * @return
	 */
	public boolean isAvailable() {

		if (!isAvailable && !isReserved) {
			Calendar current = Calendar.getInstance();
			if (current.getTimeInMillis() >= this.expired.getTimeInMillis()) {
				this.isAvailable = true;

			}
		}
		return isAvailable;
	}

	/**
	 * 
	 * @param isAvailable
	 */
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	/**
	 * 
	 * @return
	 */
	public Calendar getExpired() {
		return expired;
	}

	public void setExpires(Calendar expired) {
		this.expired = expired;
	}

	/**
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @param isReserved
	 */
	public void setReserved(boolean isReserved) {
		this.isReserved = isReserved;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isReserved() {
		return isReserved;
	}

	

	/**
	 * For debugging purpose
	 */
	@Override
	public String toString() {
		return "Seat [id=" + id + ", r=" + row + ", c=" + column + ", isAvailable=" + isAvailable + ", isReserved="
				+ isReserved + ", expires=" + expired + "]";
	}

}

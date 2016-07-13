package com.walmart.store.recruiting.ticket.domain;

import java.util.List;

/**
 * This POJO contains the data relevant to a successful seat hold request, including the seat hold id which
 * may be used later to permanently reserve the seats.
 */
public class SeatHold {

    private String id;
    private int numSeats;
    private long holdTime;
   
    
    /**
     * Constructor.
     *
     * @param id the unique hold identifier
     * @param numSeats the number of seats that were held.
     * @param holdTime the time up to which the seat can be held
     */
    public SeatHold(String id, int numSeats, long holdTime) {
        this.id = id;
        this.numSeats = numSeats;
        this.holdTime=holdTime;
       
    }

    /**
     * @return the seat hold (reservation) id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the number of seats that are being held
     */
    public int getNumSeats() {
        return numSeats;
    }
    
    

	public long getHoldTime() {
		return holdTime;
	}

	public void setHoldTime(long holdTime) {
		this.holdTime = holdTime;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeatHold seatHold = (SeatHold) o;

        return id.equals(seatHold.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}

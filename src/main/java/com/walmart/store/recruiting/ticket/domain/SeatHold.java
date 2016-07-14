package com.walmart.store.recruiting.ticket.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This POJO contains the data relevant to a successful seat hold request, including the seat hold id which
 * may be used later to permanently reserve the seats.
 */
public class SeatHold {

    private String id;
    private int numSeats;
    private int [] seats;

    /**
     * Constructor.
     *
     * @param id the unique hold identifier
     * @param numSeats the number of seats that were held.
     */
    public SeatHold(String id, int numSeats) {
        this.id = id;
        this.numSeats = numSeats;
       
    }

    /**
     * 
     * @param id
     * @param numSeats
     * @param seats
     */
    public SeatHold(String id, int numSeats, int[] seats)
    {
    	this.id = id;
        this.numSeats = numSeats;
       this.seats=seats;
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
    
    

    public int[] getSeats() {
		return seats;
	}

	public void setSeats(int[] seats) {
		this.seats = seats;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
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

package com.walmart.store.recruiting.ticket.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a venue where customers can buy tickets to see a show.
 * Our Venue seating is rectangular to simplify things.
 */
public class Venue {

    private final int id;
    private final int rows;
    private final int seatsPerRow;
    
    private Map<Integer,Seat> seats;

    public Venue(int id, int rows, int seatsPerRow) {
        this.id = id;
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
        seats=new HashMap<>();
        
        for(int i=0;i<rows;i++)
        {
        	for(int j=1;j<=seatsPerRow;j++)
        	{
        		seats.put(i*seatsPerRow+j, new Seat(i*seatsPerRow+j,i,j));
        	}
        }
        
    }


    /**
     * @return the number of rows of seats in the venue
     */
    public int getRows() {
        return rows;
    }

    /**
     * @return the number of seats in each row
     */
    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    /**
     * @return the total number of seats in the venue
     */
    public int getMaxSeats() {
        return rows * seatsPerRow;
    }

    
    public Map<Integer,Seat> getSeatArrangements()
    {
    	return this.seats;
    }
    
    
    /**
	 * This method will give ids of consecutive ids, the consecutive ids can be
	 * in multiple rows, grouped by minimum people of two Getting consecutive
	 * seats
	 * 
	 * @param numSeats
	 * @return
	 */
    public int[] getConsecutiveSeatsIds(int numSeats)
    {
		boolean continued=false;
		boolean isPrevAvailable=true;
		int sum=0;


    	for(int i=0;i<rows;i++)
    	{
        	List<Integer> seatIds=new ArrayList<>();
        	if(!continued)
        	{
        		sum=0;
        	}
    		
    		for(int j=1;j<=seatsPerRow;j++)
    		{
    			Seat current=seats.get(i*seatsPerRow+j);
    			
    			if(current.isAvailable()&&isPrevAvailable&&!current.isReserved())
    			{
    				sum++;
    				seatIds.add(i*seatsPerRow+j);
    				isPrevAvailable=true;
    			}
    			else
    			{
    				isPrevAvailable=sum==0?true:false;
    				seatIds.clear();
    			}
    			
    			if(sum==numSeats)
        		{
        			return seatIds.stream().mapToInt(x->x).toArray();
        		}
    			
    		}
    		
    		if(isPrevAvailable)
			{
    			continued=true;
			}
    		
    		
    	}
    	
    	return new int[0];
    }
    
 
    
    /**
     * Setting all the hold seats to unavailable
     * @param ids
     */
    
    public void markSeatUnavailable(int [] ids)
    {
    	for(int i=0;i<ids.length;i++)
    	{
    		Seat s= seats.get(ids[i]);
    		s.setAvailable(false);
    		Calendar expires=Calendar.getInstance();
    		expires.add(Calendar.SECOND, 5);
    		s.setExpires(expires);
    		
    	}
    }
    
    /**
     * Setting all the seats back to available
     * @param ids
     */
    public void markSeatAvailable(int [] ids)
    {
    	for(int i=0;i<ids.length;i++)
    	{
    		seats.get(ids[i]).setAvailable(true);
    	}
    }
    
    /**
     * Mark the seat Reserved
     * @param ids
     */
    
    public void markSeatReserved(int [] ids)
    {
    	for(int i=0;i<ids.length;i++)
    	{
    		Seat s= seats.get(ids[i]);
    		s.setAvailable(false);
    		s.setReserved(true);
    		Calendar expires=Calendar.getInstance();
    		expires.add(Calendar.YEAR, 1);
    		s.setExpires(expires);
    		
    	}
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Venue venue = (Venue) o;

        return id == venue.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

}

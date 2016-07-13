package com.walmart.store.recruiting.ticket.service.impl;

import com.walmart.store.recruiting.ticket.domain.SeatHold;
import com.walmart.store.recruiting.ticket.domain.Venue;
import com.walmart.store.recruiting.ticket.service.TicketService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * A ticket service implementation.
 */
public class TicketServiceImpl implements TicketService {

    private int seatsAvailable;
    private int seatsReserved;
    private Map<String, SeatHold> seatHoldMap = new HashMap<>();

    public TicketServiceImpl(Venue venue) {
        seatsAvailable = venue.getMaxSeats();
        
        }

    @Override
    public int numSeatsAvailable() {
    	
        return seatsAvailable;
    }

   
    public int numSeatsReserved() {
        return this.seatsReserved;
    }

    @Override
    public Optional<SeatHold> findAndHoldSeats(int numSeats) {
        Optional<SeatHold> optionalSeatHold = Optional.empty();

        if (seatsAvailable >= numSeats) {
            String holdId = generateId();
            long holdableTime=getTimeStamp();
            
            SeatHold seatHold = new SeatHold(holdId, numSeats,holdableTime);
            optionalSeatHold = Optional.of(seatHold);
            seatHoldMap.put(holdId, seatHold);
            seatsAvailable -= numSeats;
        }

        return optionalSeatHold;
    }

    @Override
    public Optional<String> reserveSeats(String seatHoldId) {
        Optional<String> optionalReservation = Optional.empty();;
        SeatHold seatHold = seatHoldMap.get(seatHoldId);
        
        if (seatHold != null) {
        	long currentTime=System.currentTimeMillis();
        	//check whether hold seat is expired.If seatHold is expired then returns in the pool of available seats
        	if(seatHold.getHoldTime()>currentTime){	
        		
        		seatsAvailable+=seatHold.getNumSeats();
        		seatHoldMap.remove(seatHoldId);
        	}
        	
            seatsReserved += seatHold.getNumSeats();
            optionalReservation =  Optional.of(seatHold.getId());
            seatHoldMap.remove(seatHoldId);
        }

        return optionalReservation;
    }

    /**
     * sets the timestamp with expiration time 5 seconds from time of holding
     * @return holdtime in milliseconds
     * */
    private long getTimeStamp() {
		
		long heldStartTime=System.currentTimeMillis();
		long endHoldPeriod=heldStartTime+5000L;
		return endHoldPeriod;
		
	}
    
   
    

	private String generateId() {
        return UUID.randomUUID().toString();
    }
    

    
   

}

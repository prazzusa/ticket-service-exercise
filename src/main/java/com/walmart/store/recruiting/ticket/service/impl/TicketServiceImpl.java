package com.walmart.store.recruiting.ticket.service.impl;

import com.walmart.store.recruiting.ticket.domain.Seat;
import com.walmart.store.recruiting.ticket.domain.SeatHold;
import com.walmart.store.recruiting.ticket.domain.Venue;
import com.walmart.store.recruiting.ticket.service.TicketService;

import java.util.ArrayList;
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

	private Map<Integer, Seat> seats;

	private Venue venue;

	public TicketServiceImpl(Venue venue) {
		seatsAvailable = venue.getMaxSeats();
		seats = venue.getSeatArrangements();
		this.venue = venue;
	}

	@Override
	public int numSeatsAvailable() {
		return seatsAvailable;
	}

	public int numSeatsReserved() {
		return this.seatsReserved;
	}

	/**
	 * If there are not available ids, this means we did not find any seats
	 * together, so we drop the holding for now. Then we will create a seat hold
	 * with the ids, and mark the seats in the venue as not available. It is
	 * synchronised to make the thread safe
	 */
	@Override
	public synchronized Optional<SeatHold> findAndHoldSeats(int numSeats) {
		Optional<SeatHold> optionalSeatHold = Optional.empty();

		if (seatsAvailable >= numSeats) {
			String holdId = generateId();
			int[] seatids = venue.getConsecutiveSeatsIds(numSeats);
			if (seatids.length > 0) {

				SeatHold seatHold = new SeatHold(holdId, numSeats, seatids);
				optionalSeatHold = Optional.of(seatHold);
				seatHoldMap.put(holdId, seatHold);
				seatsAvailable -= numSeats;
				venue.markSeatUnavailable(seatids);
			} else {
				/**
				 * find other seats by groups recursively
				 */

				ArrayList<Integer> groupSeats = new ArrayList<>();
				int createdSeats = 0;
				int sizeOfSeatInGroup = numSeats - 1;
				while (createdSeats <= numSeats && sizeOfSeatInGroup > 0) {
					int[] tmpseats = venue.getConsecutiveSeatsIds(sizeOfSeatInGroup);
					if (tmpseats.length > 0) {
						createdSeats += tmpseats.length;
						for (int y : tmpseats) {
							groupSeats.add(y);
						}
					} else {
						sizeOfSeatInGroup--;
					}

				}

				if (groupSeats.size() == numSeats) {
					SeatHold seatHold = new SeatHold(holdId, numSeats, seatids);
					optionalSeatHold = Optional.of(seatHold);
					seatHoldMap.put(holdId, seatHold);
					seatsAvailable -= numSeats;
					int[] tmp = groupSeats.stream().mapToInt(it -> it).toArray();
					venue.markSeatUnavailable(tmp);
				}
			}

		}

		return optionalSeatHold;
	}

	@Override
	public synchronized Optional<String> reserveSeats(String seatHoldId) {
		Optional<String> optionalReservation = Optional.empty();
		;
		SeatHold seatHold = seatHoldMap.get(seatHoldId);
		if (seatHold != null) {
			int[] reservedSeatgroup = seatHold.getSeats();
			if (reservedSeatgroup.length > 0) {
				seatsReserved += seatHold.getNumSeats();
				optionalReservation = Optional.of(seatHold.getId());
				seatHoldMap.remove(seatHoldId);
				venue.markSeatReserved(reservedSeatgroup);
			}

		}

		return optionalReservation;
	}

	private String generateId() {
		return UUID.randomUUID().toString();
	}

}

package com.walmart.store.recruiting.ticket.service.impl;

import com.walmart.store.recruiting.ticket.service.TicketService;
import com.walmart.store.recruiting.ticket.domain.SeatHold;
import com.walmart.store.recruiting.ticket.domain.Venue;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * A few basic tests for the TicketService.
 */
public class TicketServiceImplTest {

	private static Venue TEN_SEAT_VENUE;
	private TicketService ticketService;

	@Before
	public void init() {
		TEN_SEAT_VENUE = new Venue(0, 2, 5);
		ticketService = new TicketServiceImpl(TEN_SEAT_VENUE);
	}

	

	@Test
	public void testSimpleSeatHold() {
		Optional<SeatHold> hold = ticketService.findAndHoldSeats(1);
		assertTrue(hold.isPresent());
		assertNotNull(hold.get().getId());
		assertEquals(1, hold.get().getNumSeats());
		assertEquals(9, ticketService.numSeatsAvailable());

		hold = ticketService.findAndHoldSeats(5);
		assertTrue(hold.isPresent());
		assertNotNull(hold.get().getId());
		assertEquals(5, hold.get().getNumSeats());
		assertEquals(4, ticketService.numSeatsAvailable());
	}

	@Test
	public void testReserveSeats() {
		Optional<SeatHold> hold = ticketService.findAndHoldSeats(5);
		assertTrue(hold.isPresent());
		assertNotNull(hold.get().getId());
		assertEquals(5, hold.get().getNumSeats());
		assertEquals(5, ticketService.numSeatsAvailable());

		Optional<String> reservationId = ticketService.reserveSeats(hold.get().getId());
		assertTrue(reservationId.isPresent());
		assertEquals(hold.get().getId(), reservationId.get());
	}

	@Test
	public void testReserveSeatsWithInvalidHold() {
		Optional<String> reservationId = ticketService.reserveSeats("AAAA");
		assertFalse(reservationId.isPresent());
	}

	@Test
	public void testMaxSeatHold() {
		Optional<SeatHold> hold = ticketService.findAndHoldSeats(10);
		assertTrue(hold.isPresent());
		assertNotNull(hold.get().getId());
		assertEquals(10, hold.get().getNumSeats());
	}

	@Test
	public void testEmptySeatHoldReturnedWhenRequestExceedsCapacity() {
		Optional<SeatHold> hold = ticketService.findAndHoldSeats(11);
		assertTrue(!hold.isPresent());
	}

	@Test
	public void testEmptySeatHoldReturnedWhenVenueIsFull() {
		testMaxSeatHold();
		Optional<SeatHold> hold = ticketService.findAndHoldSeats(1);
		assertTrue(!hold.isPresent());
	}

	@Test

	public void testGroupedSeating() {
		Optional<SeatHold> holdFirst = ticketService.findAndHoldSeats(2);
		Optional<SeatHold> holdSecond = ticketService.findAndHoldSeats(5);
		Optional<SeatHold> holdThird= ticketService.findAndHoldSeats(2);

		assertTrue(holdSecond.isPresent());
		assertNotNull(holdSecond.get().getId());
		assertEquals(5, holdSecond.get().getNumSeats());
		assertEquals(2, holdThird.get().getNumSeats());

	}

}

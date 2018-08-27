package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logic.CinemaComplex;
import logic.Theater;
import logic.Movie;

class TheaterTestCase {
	
	private Theater theater;
	private Movie movie;

	@BeforeEach
	void setUp() throws Exception {
		theater = new Theater(1, 2, 3);
		movie = new Movie("Mission Impossible: Fallout", 3);
	}

	@Test
	void testAddShowTime() {
		//not added movie yet
		theater.addShowTime(1, 3);
		assertEquals(0, theater.howManyShowtime());
		
		theater.setMovie(movie);
		//add invalid time
		theater.addShowTime(0, 3);
		assertEquals(0, theater.howManyShowtime());
		theater.addShowTime(10, 13);
		assertEquals(0, theater.howManyShowtime());
		theater.addShowTime(0, 2);
		assertEquals(0, theater.howManyShowtime());
		theater.addShowTime(11, 13);
		assertEquals(0, theater.howManyShowtime());
		theater.addShowTime(1, 4);
		assertEquals(0, theater.howManyShowtime());
		theater.addShowTime(1, 2);
		assertEquals(0, theater.howManyShowtime());
		//add valid time		
		theater.addShowTime(1, 3);
		assertEquals(1, theater.howManyShowtime());
		theater.addShowTime(3, 5);
		assertEquals(1, theater.howManyShowtime());
		theater.addShowTime(4, 6);
		assertEquals(2, theater.howManyShowtime());
		theater.addShowTime(5, 8);
		assertEquals(2, theater.howManyShowtime());
		theater.addShowTime(7, 9);
		assertEquals(3, theater.howManyShowtime());
		
		assertTrue(theater.isValidShowtime(1, 3));
		assertTrue(theater.isValidShowtime(4, 6));
		assertTrue(theater.isValidShowtime(7, 9));
		assertFalse(theater.isValidShowtime(3, 5));
		assertFalse(theater.isValidShowtime(5, 8));
		
		CinemaComplex.TIMESLOT = 15;
		theater = new Theater(2, 2, 3);
		theater.setMovie(movie);
		theater.addShowTime(11, 13);
		assertEquals(1, theater.howManyShowtime());
		assertTrue(theater.isValidShowtime(11, 13));
	}

	@Test
	void testDisplaySeats() {
		theater.setMovie(movie);
		theater.addShowTime(1, 3);
		assertFalse(theater.displaySeats(2));
		assertTrue(theater.displaySeats(1));
		
		//book some seats
		theater.bookSeat(1, 1, 1);
		theater.bookSeat(1, 1, 2);
		theater.bookSeat(1, 1, 3);
		assertTrue(theater.displaySeats(1));
		
		//make theater full
		theater.bookSeat(1, 2, 1);
		theater.bookSeat(1, 2, 2);
		theater.bookSeat(1, 2, 3);		
		assertFalse(theater.displaySeats(1));
	}

	@Test
	void testBookSeat() {
		theater.setMovie(movie);
		theater.addShowTime(1, 3);
		assertFalse(theater.bookSeat(2, 1, 1));
		assertFalse(theater.bookSeat(1, 0, 1));
		assertFalse(theater.bookSeat(1, 1, 4));
		
		//make theater full
		assertTrue(theater.bookSeat(1, 1, 1));
		assertTrue(theater.bookSeat(1, 1, 2));
		assertTrue(theater.bookSeat(1, 1, 3));
		assertTrue(theater.bookSeat(1, 2, 1));
		assertTrue(theater.bookSeat(1, 2, 2));
		assertTrue(theater.bookSeat(1, 2, 3));
		
		assertFalse(theater.bookSeat(1, 1, 1));
	}

}

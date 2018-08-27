package logic;

import java.util.ArrayList;
import java.util.Scanner;

/*
This class is literally a cinema complex!
It contains all the info you need about the cinema complex: movies, theaters, tickets.
You want to book a ticket? call a method in this class.
You want to know what movies they're showing? call a method in this class.
You want to view all the tickets you booked previously? call a method in this class.
You want to add new movie/theater/showtime? also call a method in this class.
*/

public class CinemaComplex {
	
	public static int TIMESLOT = 12;
	private ArrayList<Movie> movies;
	private ArrayList<Theater> theaters;
	private ArrayList<Ticket> tickets;
	
	public CinemaComplex() {
		movies = new ArrayList<Movie>();
		theaters = new ArrayList<Theater>();
		tickets = new ArrayList<Ticket>();
	}
	
	public void showAllMoviesInTheater() {
		System.out.println("Available Movies");
		for (int i = 0; i < movies.size(); i++) {
			Movie movie = movies.get(i);
			for (int ii = 0; ii < theaters.size(); ii++) {
				Theater theater = theaters.get(ii);
				if (theater.getMovie() == null) {
					continue;
				}
				if (movie.getName().equals(theater.getMovie().getName()) && theater.howManyShowtime() > 0) {
					System.out.println(movie.getName());
					break;
				}
			}
		}
	}
	
	public ArrayList<Theater> showAllShowtime(String movieName) {
		ArrayList<Theater> showingTheaters = new ArrayList<Theater>();
		
		//check if movie name is valid
		if (movieName.equals("")) {
			return showingTheaters;
		}
		movieName = movieName.toLowerCase();
		for (int i = 0; i < theaters.size(); i++) {
			if (theaters.get(i).getMovie() == null) {
				continue;
			}
			Theater theater = theaters.get(i);
			String showingMovieName = theater.getMovie().getName().toLowerCase();
			if (showingMovieName.contains(movieName)) {
				if (theater.howManyShowtimeLeft() > 0) {
					showingTheaters.add(theaters.get(i));
				}
			}
		}
		if (showingTheaters.size() > 0) {
			System.out.println("Available Showtime of " + showingTheaters.get(0).getMovie().getName());
			for (int i = 0 ; i < showingTheaters.size(); i++) {
				showingTheaters.get(i).showAllNotFullShowtime();
			}
		}
		return showingTheaters;
	}
	
	public void handleBookingProcess() {
		Scanner keyboard = Application.KEYBOARD;
		//accept blank input after nextInt
		String movieName = keyboard.nextLine();
		
		//check if there is any show time
		int emptyTheater = 0;
		for (int i = 0; i < theaters.size(); i++) {
			if (theaters.get(i).getMovie() == null || theaters.get(i).howManyShowtimeLeft() == 0) {
				emptyTheater++;
			}
		}
		if (emptyTheater == theaters.size()) {
			System.out.println("No tickets left for booking.");
			return;
		}	
		
		//select movie
		ArrayList<Theater> showingTheaters = new ArrayList<Theater>();
		while (showingTheaters.size() == 0) {
			System.out.print("Please select the movie: ");
			movieName = keyboard.nextLine();
			showingTheaters = showAllShowtime(movieName);
			if (showingTheaters.size() == 0) {
				System.out.println("!!! This movie is not available. Please try again. !!!");
			}
		}
		
		//select theater
		Theater theater = null;
		if (showingTheaters.size() == 1) {
			theater = showingTheaters.get(0);
		}
		else {
			int theaterNumber = 0;
			boolean validTheater = false;
			while(!validTheater) {
				System.out.print("Please select the theater: ");
				theaterNumber = keyboard.nextInt();
				for (int i = 0; i < showingTheaters.size(); i++) {
					if (theaterNumber == showingTheaters.get(i).getTheaterNumber()) {
						theater = showingTheaters.get(i);
						validTheater = true;
						break;
					}
					if (i == showingTheaters.size() - 1) {
						System.out.println("!!! This theater does not show this movie. Please try again !!!");
					}
				}
			}
		}
		
		//select show time
		System.out.print("Please select start time: ");
		int startTimeSlot = keyboard.nextInt();
		while (!theater.displaySeats(startTimeSlot)) {
			System.out.println("!!! This time is invalid. Please try again !!!");
			System.out.print("Please select start time: ");
			startTimeSlot = keyboard.nextInt();
		}
		
		//select seat
		System.out.println("Please select your seat:");
		System.out.print("Row: ");
		int row = keyboard.nextInt();
		System.out.print("Column: ");
		int column = keyboard.nextInt();
		while (/* a missing condition */) {
			System.out.println("!!! Row or column is invalid. Please try again !!!");
			System.out.print("Row: ");
			row = keyboard.nextInt();
			System.out.print("Column: ");
			column = keyboard.nextInt();
		}
		
		/// create a ticket object & add it to tickets arraylist here ///
		
		/////////////////////////////////////////////////////////////
		System.out.println("--- Booking Completed ---");
	}
	
	/// implement showAllTickets method here ///
	
	////////////////////////////////////////////
	
	public void createMovie(String movieInfo) {
		String[] info = movieInfo.split(",");
		Movie movie = new Movie(info[0], Integer.parseInt(info[1]));
		movies.add(movie);
	}
	
	public void createTheater(String theaterInfo) {
		String[] info = theaterInfo.split(",");
		int theaterNumber = Integer.parseInt(info[0]);
		int row = Integer.parseInt(info[1]);
		int column = Integer.parseInt(info[2]);
		Theater theater = new Theater(theaterNumber, row, column);
		theaters.add(theater);
	}
	
	public void assignMovieAndShowtimeToTheater(String programInfo) {
		String[] info = programInfo.split(",");
		
		//check if theater number is valid
		Theater theater = null;
		int theaterNumber = Integer.parseInt(info[0]);
		for (int i = 0; i < theaters.size(); i++) {
			if (theaterNumber == theaters.get(i).getTheaterNumber()) {
				theater = theaters.get(i);
				break;
			}
		}
		if (theater == null) {
			System.err.println("** Wrong theater number");
			return;
		}
		
		//check if movie name is valid
		Movie movie = null;
		String movieName = info[1];
		for (int i = 0; i < movies.size(); i++) {
			if (movieName.equals(movies.get(i).getName())) {
				movie = movies.get(i);
				break;
			}
		}
		if (movie == null) {
			System.err.println("** Movie not found");
			return;
		}
		
		//set movie & add show time
		theater.setMovie(movie);		
		for (int i = 2; i < info.length; i++) {
			String time = info[i];
			int dashIndex = time.indexOf("-");
			int startTimeSlot = Integer.parseInt(time.substring(0, dashIndex));
			int endTimeSlot = Integer.parseInt(time.substring(dashIndex + 1));
			/* a missing line */
		}
		if (theater.howManyShowtime() == 0) {
			theater.clearMovie();
		}
	}
}
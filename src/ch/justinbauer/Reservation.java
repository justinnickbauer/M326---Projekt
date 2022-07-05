package ch.justinbauer;

import ch.justinbauer.exceptions.ReservationException;

import java.util.UUID;


public class Reservation implements Showable{
	private UUID ident = UUID.randomUUID();
	private Date arrival = null;
	private Date departure = null;
	private int nights = 0;
	private int persons = 0;
	private Room room = null;
	
	public Reservation(Date arrival, Date departure, int persons, Room room)throws ReservationException  {
		this.arrival = arrival;
		this.departure = departure;
		this.persons = persons;
		
		this.nights = this.arrival.delta(this.departure);
		this.room = room;
		this.room.addReservation(this);
	}
	
	public Reservation(Date arrival, int nights, int persons, Room room) throws ReservationException {
		this(arrival, arrival.getFollowingDate(nights), persons, room);
	}
	public UUID getID() {
		return this.ident;
	}

	public Date getArrivalDate() {
		return this.arrival;
	}

	public Date getDepartureDate() {
		return this.departure;
	}
	
	public void cancel() throws ReservationException {
		this.room.cancelReservation(this);
	}

	public int getNights() {
		return this.nights;
	}

	public int getPersons() {
		return this.persons;
	}
	

	public void show() {
		System.out.println("Reservation: " + this.getID());
		System.out.println("Arrival: " + this.getArrivalDate());
		System.out.println("Departure: " + this.getDepartureDate());
		System.out.println("Night: " + this.getNights());
		System.out.println("Person: " + this.getPersons());
		System.out.println("Room Type:" + room.getClass().getName());
	
	}
	public String getInformation() {
		return "Reservation in " + this.room.getName() + " arrive in " + this.arrival + " departs in " + this.departure + ". Money is" + this.nights * room.getBasePrice()+"CHF in total";
	}
}

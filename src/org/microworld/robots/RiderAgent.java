/**
 * 
 */
package org.microworld.robots;

import org.microworld.models.Location;
import org.microworld.models.Participation;
import org.microworld.models.Person;
import org.microworld.models.Search;
import org.microworld.models.Trip;
import org.microworld.robots.intefaces.Rider;

/**
 * @author riccardo
 *
 */
public class RiderAgent extends Agent implements Rider {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Search searchTrip(Location origin, Location destination,
			Person Author) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void postParticipation(Trip trip) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Participation checkParticipationStatus(Trip trip) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cancelParticipation(Trip trip) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startParticipation(Trip trip) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finishParticipation(Trip trip) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makeDecision(int runLevel) {
		// TODO Auto-generated method stub
		
	}

}

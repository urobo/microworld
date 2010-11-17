/**
 * 
 */
package org.microworld.robots;

import java.util.List;

import org.microworld.models.Participation;
import org.microworld.models.Trip;
import org.microworld.robots.intefaces.Driver;

/**
 * @author riccardo
 *
 */
public class DriverAgent extends Agent implements Driver{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void makeDecision(int runLevel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Trip postTrip(Trip trip) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean activateTrip(Trip trip) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Participation> checkRideRequests(Trip trip) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void acceptRideRequest(Participation participation, Trip trip) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refuseRideRequest(Participation participation, Trip trip) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finishTrip(Trip trip) {
		// TODO Auto-generated method stub
		
	}

}

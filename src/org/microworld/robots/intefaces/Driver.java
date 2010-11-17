/**
 * 
 */
package org.microworld.robots.intefaces;

import java.util.List;

import org.microworld.models.Participation;
import org.microworld.models.Trip;

/**
 * @author riccardo
 *
 */
public interface Driver {
	public Trip postTrip(Trip trip);
	public boolean activateTrip(Trip trip);
	public List<Participation> checkRideRequests(Trip trip);
	public void acceptRideRequest(Participation participation, Trip trip);
	public void refuseRideRequest(Participation participation, Trip trip);
	public void finishTrip(Trip trip);
}

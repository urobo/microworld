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
	Trip postTrip(Trip trip);
	boolean activateTrip(Trip trip);
	boolean finishTrip(Trip trip);
	List<Participation> checkRideRequests(Trip trip);
	void acceptRideRequests(List<Participation> list);
	void refuseRideRequests(List<Participation> list);
}

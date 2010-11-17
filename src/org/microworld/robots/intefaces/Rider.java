/**
 * 
 */
package org.microworld.robots.intefaces;

import org.microworld.models.Location;
import org.microworld.models.Participation;
import org.microworld.models.Person;
import org.microworld.models.Search;
import org.microworld.models.Trip;

/**
 * @author riccardo
 *
 */
public interface Rider {
	public Search searchTrip(Location origin, Location destination, Person Author);
	public void postParticipation(Trip trip);
	public Participation checkParticipationStatus(Trip trip);
	public void cancelParticipation(Trip trip);
	public void startParticipation(Trip trip);
	public void finishParticipation(Trip trip);
}

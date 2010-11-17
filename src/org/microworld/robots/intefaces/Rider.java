/**
 * 
 */
package org.microworld.robots.intefaces;

import org.json.JSONException;
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
	Search searchTrip(Location origin, Location destination, Person Author);
	boolean finishParticipation();
	Participation postParticipation(Participation participation, Trip trip) throws JSONException;
	String checkParticipationStatus() throws JSONException;
	boolean cancelParticipation();
	boolean startParticipation();
}

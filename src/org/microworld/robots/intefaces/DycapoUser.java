/**
 * 
 */
package org.microworld.robots.intefaces;

import java.util.List;

import org.microworld.models.Location;
import org.microworld.models.Participation;
import org.microworld.models.Person;
import org.microworld.models.Trip;

/**
 * @author riccardo
 * 
 */
public interface DycapoUser {

	public Location updatePosition(Location position);

	public Location getPosition(Person person);

	public List<Participation> getParticipations(Trip trip);

	public void makeDecision(int runLevel);
}

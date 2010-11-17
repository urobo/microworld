/**
 * 
 */
package org.microworld.robots;

import java.util.List;

import org.microworld.models.Location;
import org.microworld.models.Participation;
import org.microworld.models.Person;
import org.microworld.models.Trip;

/**
 * @author riccardo
 *
 */
public abstract class Agent extends Role implements Runnable, DycapoUser {
	
	protected Robot path;
	protected Person user;
	protected Trip trip;
	/* (non-Javadoc)
	 * @see org.microworld.robots.DycapoUser#updatePosition(org.microworld.models.Location)
	 */
	@Override
	public void updatePosition(Location position) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.microworld.robots.DycapoUser#getPosition(org.microworld.models.Person)
	 */
	@Override
	public Location getPosition(Person person) {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see org.microworld.robots.DycapoUser#postParticipation(org.microworld.models.Participation)
	 */
	@Override
	public void postParticipation(Participation participation) {
		// TODO Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 * @see org.microworld.robots.DycapoUser#getParticipations(org.microworld.models.Trip)
	 */
	@Override
	public List<Participation> getParticipations(Trip trip) {
		// TODO Auto-generated method stub
		return null;
	}

}

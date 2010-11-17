/**
 * 
 */
package org.microworld.robots;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.microworld.client.DycapoHttpClient;
import org.microworld.models.Location;
import org.microworld.models.Participation;
import org.microworld.models.Person;
import org.microworld.models.Trip;
import org.microworld.robots.intefaces.DycapoUser;

import eu.fbk.dycapo.factories.json.DycapoObjectsFetcher;

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
		DycapoHttpClient.callDycapo(DycapoHttpClient.POST, user.getHref() + "location/", position.toJSONObject(), user.getUsername(), user.getPassword());
	}
	/* (non-Javadoc)
	 * @see org.microworld.robots.DycapoUser#getPosition(org.microworld.models.Person)
	 */
	@Override
	public Location getPosition(Person person) {
		String resp = DycapoHttpClient.callDycapo(DycapoHttpClient.GET, person.getHref() + "location/", null, user.getUsername(), user.getPassword());
		try {
			Location position = DycapoObjectsFetcher.buildLocation(new JSONObject(resp));
			return position;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.microworld.robots.DycapoUser#getParticipations(org.microworld.models.Trip)
	 */
	@Override
	public List<Participation> getParticipations(Trip trip) {
		String resp = DycapoHttpClient.callDycapo(DycapoHttpClient.GET, trip.getHref() + "participations/", null, user.getUsername(), user.getPassword());
		try {
			List<Participation> result = DycapoObjectsFetcher.extractTripParticipations(new JSONArray(resp));
			return result;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

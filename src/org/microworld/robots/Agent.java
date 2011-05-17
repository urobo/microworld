/**
 * 
 */
package org.microworld.robots;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.microworld.client.DycapoHttpClient;
import org.microworld.dycapo.DycapoGlobalVariables;
import org.microworld.logging.Log;
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
public abstract class Agent extends Thread implements DycapoUser{

	protected int runlevel;
	protected Robot path;
	protected Person user;
	protected Trip trip;
	protected double acceptanceRate;
	
	public Agent(double rate) {
		this.acceptanceRate = rate;
		this.setRunlevel(0);
	}

	/**
	 * @return the path
	 */
	public Robot getPath() {
		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(Robot path) {
		this.path = path;
	}

	/**
	 * @return the user
	 */
	public Person getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(Person user) {
		this.user = user;
	}

	/**
	 * @return the trip
	 */
	public Trip getTrip() {
		return trip;
	}

	/**
	 * @param trip
	 *            the trip to set
	 */
	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	/**
	 * @return the acceptanceRate
	 */
	public double getAcceptanceRate() {
		return acceptanceRate;
	}

	/**
	 * @param acceptanceRate
	 *            the acceptanceRate to set
	 */
	public void setAcceptanceRate(double acceptanceRate) {
		this.acceptanceRate = acceptanceRate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.microworld.robots.DycapoUser#updatePosition(org.microworld.models
	 * .Location)
	 */
	@Override
	public Location updatePosition(Location position) {
		Log.verbose(this.user.getUsername(),
				"update position" + position.toString());
		String result = DycapoHttpClient.callDycapo(DycapoHttpClient.POST,
				user.getHref() + "location/", position.toJSONObject(),
				user.getUsername(), user.getPassword());

		try {
			Location posi = DycapoObjectsFetcher.buildLocation(new JSONObject(
					result));
			return posi;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.microworld.robots.DycapoUser#getPosition(org.microworld.models.Person
	 * )
	 */
	@Override
	public Location getPosition(Person person) {
		Log.verbose(this.user.getUsername(), "getting position of user : "
				+ person.getUsername());
		String resp = DycapoHttpClient.callDycapo(DycapoHttpClient.GET,
				person.getHref() + "location/", null, user.getUsername(),
				user.getPassword());

		try {
			Location position = DycapoObjectsFetcher
					.buildLocation(new JSONObject(resp));
			Log.verbose(this.user.getUsername(), "position fetched : "
					+ position.toString());
			return position;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.microworld.robots.DycapoUser#getParticipations(org.microworld.models
	 * .Trip)
	 */
	@Override
	public List<Participation> getParticipations(Trip trip) {
		Log.verbose(this.user.getUsername(),
				"getting participations list of trip : " + trip.getHref());
		String resp = DycapoHttpClient.callDycapo(DycapoHttpClient.GET,
				trip.getHref() + "participations/", null, user.getUsername(),
				user.getPassword());
		try {
			List<Participation> result = DycapoObjectsFetcher
					.extractTripParticipations(new JSONArray(resp));
			Log.verbose(this.user.getUsername(),
					"number of participations retrieved : " + result.size());
			return result;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.microworld.robots.intefaces.DycapoUser#makeDecision(int)
	 */
	@Override
	public void makeDecision(int runLevel) {
		
	}

	public boolean register(Person person) {
		try {
			String response = DycapoHttpClient.callDycapo(
					DycapoHttpClient.POST, DycapoGlobalVariables.URL_BASIS
							+ DycapoGlobalVariables.PERSONS,
					person.toUserJSON(), null, null);
			Log.verbose(
					"Agent",
					"Registering a new User : \nusername: "
							+ person.getUsername() + "\t password: "
							+ person.getPassword());
			Log.verbose("Server Answer to registration", response);
			Person p = DycapoObjectsFetcher
					.buildPerson(new JSONObject(response));
			if (p instanceof Person && p.getUsername() != null
					&& p.getHref() != null) {
				this.user.setHref(p.getHref());
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @return the runlevel
	 */
	public int getRunlevel() {
		return runlevel;
	}

	/**
	 * @param runlevel
	 *            the runlevel to set
	 */
	public void setRunlevel(int runlevel) {
		this.runlevel = runlevel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (this.getRunlevel()!=-1) {
			try {
				makeDecision(this.runlevel);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

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
import org.microworld.robots.intefaces.RunLevelDecisions;

import eu.fbk.dycapo.factories.json.DycapoObjectsFetcher;

/**
 * @author riccardo
 * 
 */
public abstract class Agent implements Runnable, DycapoUser, RunLevelDecisions {

	protected int runlevel;
	protected Robot path;
	protected Person user;
	protected Trip trip;
	protected double acceptanceRate;

	public Agent(double rate) {
		this.acceptanceRate = rate;
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
	public void updatePosition(Location position) {
		DycapoHttpClient.callDycapo(DycapoHttpClient.POST, user.getHref()
				+ "location/", position.toJSONObject(), user.getUsername(),
				user.getPassword());
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
		String resp = DycapoHttpClient.callDycapo(DycapoHttpClient.GET,
				person.getHref() + "location/", null, user.getUsername(),
				user.getPassword());
		try {
			Location position = DycapoObjectsFetcher
					.buildLocation(new JSONObject(resp));
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
		String resp = DycapoHttpClient.callDycapo(DycapoHttpClient.GET,
				trip.getHref() + "participations/", null, user.getUsername(),
				user.getPassword());
		try {
			List<Participation> result = DycapoObjectsFetcher
					.extractTripParticipations(new JSONArray(resp));
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
	 * @see
	 * org.microworld.robots.intefaces.RunLevelDecisions#runLevelDecision0()
	 */
	@Override
	public void runLevelDecision0() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.microworld.robots.intefaces.RunLevelDecisions#runLevelDecision1()
	 */
	@Override
	public void runLevelDecision1() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.microworld.robots.intefaces.RunLevelDecisions#runLevelDecision2()
	 */
	@Override
	public void runLevelDecision2() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.microworld.robots.intefaces.DycapoUser#makeDecision(int)
	 */
	@Override
	public void makeDecision(int runLevel) {
		switch (runLevel) {
		case 0:
			runLevelDecision0();
			break;
		case 1:
			runLevelDecision1();
			break;
		case 2:
			runLevelDecision2();
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true) {
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

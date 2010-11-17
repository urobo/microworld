/**
 * 
 */
package org.microworld.robots;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.microworld.client.DycapoHttpClient;
import org.microworld.dycapo.DycapoGlobalVariables;
import org.microworld.models.Mode;
import org.microworld.models.Participation;
import org.microworld.models.Trip;
import org.microworld.robots.intefaces.Driver;

import eu.fbk.dycapo.factories.json.DycapoObjectsFetcher;

/**
 * @author riccardo
 * 
 */
public class DriverAgent extends Agent implements Driver {
	private Mode modality;

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
		String response = DycapoHttpClient.callDycapo(DycapoHttpClient.POST,
				DycapoGlobalVariables.URL_BASIS + "trips/",
				trip.toJSONObject(), user.getUsername(), user.getPassword());
		try {
			Trip result = DycapoObjectsFetcher.buildTrip(new JSONObject(
					response));
			return result;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean activateTrip(Trip trip) {
		trip.setActive(true);
		String response = DycapoHttpClient.callDycapo(DycapoHttpClient.PUT,
				trip.getHref(), trip.toJSONObject(), user.getUsername(),
				user.getPassword());
		try {
			Trip active = DycapoObjectsFetcher.buildTrip(new JSONObject(
					response));
			if (active != null && active.getActive())
				return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public List<Participation> checkRideRequests(Trip trip) {
		List<Participation> list = this.getParticipations(trip);
		List<Participation> rideRequests = new ArrayList<Participation>();
		for (int i = 0; i < list.size(); i++)
			if (list.get(i).getStatus().equals(Participation.REQUESTED))
				rideRequests.add(list.get(i));
		return rideRequests;
	}

	@Override
	public void acceptRideRequests(List<Participation> list) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setStatus(Participation.ACCEPTED);
			DycapoHttpClient.callDycapo(DycapoHttpClient.PUT, list.get(i)
					.getHref(), list.get(i).toJSONObject(), user.getUsername(),
					user.getPassword());
		}
	}

	@Override
	public boolean finishTrip(Trip trip) {
		trip.setActive(false);
		String response = DycapoHttpClient.callDycapo(DycapoHttpClient.PUT,
				trip.getHref(), trip.toJSONObject(), user.getUsername(),
				user.getPassword());
		try {
			Trip active = DycapoObjectsFetcher.buildTrip(new JSONObject(
					response));
			if (active != null && active.getActive())
				return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void refuseRideRequests(List<Participation> list) {
		while (!list.isEmpty()) {
			DycapoHttpClient.callDycapo(DycapoHttpClient.DELETE, list.get(0)
					.getHref(), null, user.getUsername(), user.getPassword());
			list.remove(0);
		}
	}

	/**
	 * @param modality
	 *            the modality to set
	 */
	public void setModality(Mode modality) {
		this.modality = modality;
	}

	/**
	 * @return the modality
	 */
	public Mode getModality() {
		return modality;
	}

}

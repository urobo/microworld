/**
 * 
 */
package org.microworld.robots;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.microworld.client.DycapoHttpClient;
import org.microworld.dycapo.DycapoGlobalVariables;
import org.microworld.logging.Log;
import org.microworld.models.Location;
import org.microworld.models.Mode;
import org.microworld.models.Participation;
import org.microworld.models.Trip;
import org.microworld.robots.intefaces.Driver;
import org.microworld.utils.Point;

import eu.fbk.dycapo.factories.json.DycapoObjectsFetcher;

/**
 * @author riccardo
 * 
 */
public class DriverAgent extends Agent implements Driver {
	public DriverAgent(double rate) {
		super(rate);
	}

	private Mode modality;

	@Override
	public Trip postTrip(Trip trip) {
		Log.verbose(this.user.getUsername(), "posting trip");

		String response = DycapoHttpClient.callDycapo(DycapoHttpClient.POST,
				DycapoGlobalVariables.URL_BASIS + "trips/",
				trip.toJSONObject(), user.getUsername(), user.getPassword());
		try {
			Trip result = DycapoObjectsFetcher.buildTrip(new JSONObject(
					response));
			Log.verbose(this.user.getUsername(), "trip posted successfully "
					+ result.getHref());
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
		Log.verbose(this.user.getUsername(),
				"activating trip  : " + trip.getHref());

		String response = DycapoHttpClient.callDycapo(DycapoHttpClient.PUT,
				trip.getHref(), trip.toJSONObject(), user.getUsername(),
				user.getPassword());
		try {
			JSONObject json = new JSONObject(response);
			if (json.has("message")) {
				Log.verbose("DriverAgent", json.getString("message"));
				this.trip.setActive(true);
			} else {
				Trip active = DycapoObjectsFetcher.buildTrip(new JSONObject(
						response));
				if (active != null && active.getActive()) {
					Log.verbose(this.user.getUsername(),
							"successfully activated trip :" + trip.getHref());
					return true;
				}
			}
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
		Log.verbose(this.user.getUsername(), "checking ride requests");
		for (int i = 0; i < list.size(); i++)
			if (list.get(i).getStatus().equals(Participation.REQUESTED)) {
				rideRequests.add(list.get(i));
				Log.verbose(this.user.getUsername(), "user "
						+ list.get(i).getAuthor().getUsername()
						+ " asked for a ride");
			}
		return rideRequests;
	}

	@Override
	public List<Participation> acceptRideRequests(List<Participation> list) {
		List<Participation> result = new ArrayList<Participation>();
		try {
			for (int i = 0; i < list.size(); i++) {
				list.get(i).setStatus(Participation.ACCEPTED);
				String response = DycapoHttpClient.callDycapo(
						DycapoHttpClient.PUT, list.get(i).getHref(), list
								.get(i).toJSONObject(), user.getUsername(),
						user.getPassword());
				Log.verbose(this.user.getUsername(), "user " + list.get(i)
						+ " has been added to the trip");

				result.add(DycapoObjectsFetcher
						.buildParticipation(new JSONObject(response)));
			}
			return result;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean finishTrip(Trip trip) {
		trip.setActive(false);
		DycapoHttpClient.callDycapo(DycapoHttpClient.PUT, trip.getHref(),
				trip.toJSONObject(), user.getUsername(), user.getPassword());
		return true;
	}

	@Override
	public void refuseRideRequests(List<Participation> list) {
		while (!list.isEmpty()) {
			DycapoHttpClient.callDycapo(DycapoHttpClient.DELETE, list.get(0)
					.getHref(), null, user.getUsername(), user.getPassword());
			Log.verbose(this.user.getUsername(),
					"has just refused to share a ride with " + list.get(0));
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

	public void runLevelDecision0() {
		this.trip.setActive(true);
		Trip trip = this.postTrip(this.trip);
		if (trip instanceof Trip) {
			this.trip = trip;
			this.setRunlevel(1);
		}
	}

	public void runLevelDecision1() {
		if (this.path.hasNext()) {
			List<Participation> alist = this.checkRideRequests(this.trip);
			this.fetchRideRequests(alist);
			if(this.path.hasNext()){
				Location position  = new Location();
				position.setLeaves(new Date(System.currentTimeMillis()));
				position.setGeorss_point(this.path
					.getNextPoint());
				position.setPoint(Location.POSI);
				this.updatePosition(position);
			}else{
				this.setRunlevel(-1);
				Log.verbose("Driver", this.user.getUsername() + " trip finished!");
			}
		} else {
			this.setRunlevel(2);
		}

	}
	
	public void runLevelDecision2() {
		this.finishTrip(this.trip);
		this.setRunlevel(-1);
	}

	@Override
	public void fetchRideRequests(List<Participation> list) {
		Log.verbose(this.user.getUsername(), "is fetching ride requests");
		int i = 0;
		List<Participation> refused = new ArrayList<Participation>();
		while (i < list.size()) {
			if (Math.random() <= this.acceptanceRate) {
				i++;
			} else {
				refused.add(list.get(i));
				list.remove(i);
			}
		}
		if (!list.isEmpty())
			this.acceptRideRequests(list);
		if (!refused.isEmpty())
			this.refuseRideRequests(refused);
	}


	/* (non-Javadoc)
	 * @see org.microworld.robots.Agent#makeDecision(int)
	 */
	@Override
	public void makeDecision(int runLevel) {
		switch(runlevel){
		case 0:
			this.runLevelDecision0();
			break;
		case 1:
			this.runLevelDecision1();
			break;
		case 2:
			this.runLevelDecision2();
			break;
		}
	}

}

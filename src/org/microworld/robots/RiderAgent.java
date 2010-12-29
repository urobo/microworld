/**
 * 
 */
package org.microworld.robots;

import org.json.JSONException;
import org.json.JSONObject;
import org.microworld.client.DycapoHttpClient;
import org.microworld.dycapo.DycapoGlobalVariables;
import org.microworld.logging.Log;
import org.microworld.models.Location;
import org.microworld.models.Participation;
import org.microworld.models.Person;
import org.microworld.models.Search;
import org.microworld.models.Trip;
import org.microworld.robots.intefaces.Rider;
import org.microworld.utils.Point;

import eu.fbk.dycapo.factories.json.DycapoObjectsFetcher;

/**
 * @author riccardo
 * 
 */
public class RiderAgent extends Agent implements Rider {

	public RiderAgent(double rate) {
		super(rate);
	}

	public RiderAgent(Person person, double rate) {
		super(rate);
		this.user = person;
	}

	Participation participation;

	@Override
	public Search searchTrip(Location origin, Location destination,
			Person Author) {
		Log.verbose(this.user.getUsername(), "is searching for a trip");
		Search search = new Search();
		search.setAuthor(user);
		search.setOrigin(origin);
		search.setDestination(destination);
		String response = DycapoHttpClient.callDycapo(DycapoHttpClient.POST,
				DycapoGlobalVariables.URL_BASIS + "searches/",
				search.toJSONObject(), user.getUsername(), user.getPassword());
		try {
			search = DycapoObjectsFetcher.buildSearch(new JSONObject(response));
			Log.verbose(
					this.user.getUsername(),
					"research posted and successfully retrieved "
							+ search.getHref());
			String searchresult = DycapoHttpClient.callDycapo(DycapoHttpClient.GET, search.getHref(), null, user.getUsername(), user.getPassword());
			search = DycapoObjectsFetcher.buildSearch(new JSONObject(searchresult));
			if (search.getTrips() != null)
				Log.verbose(this.user.getUsername(), "the search retrieved "
						+ search.getTrips().size() + " suitable trips");
			return search;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Participation postParticipation(Participation participation,
			Trip trip) {
		Log.verbose(this.user.getUsername(),
				"is posting a new participation to trip : " + trip.getHref());
		String response = DycapoHttpClient.callDycapo(DycapoHttpClient.POST,
				trip.getHref() + DycapoGlobalVariables.PARTICIPATIONS,
				participation.toJSONObject(), user.getUsername(),
				user.getPassword());
		try {
			Log.verbose(
					this.user.getUsername(),
					"participation posted successfully to trip : "
							+ trip.getHref());
			this.participation = DycapoObjectsFetcher
					.buildParticipation(new JSONObject(response));
			return this.participation;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return the participation
	 */
	public Participation getParticipation() {
		return participation;
	}

	/**
	 * @param participation
	 *            the participation to set
	 */
	public void setParticipation(Participation participation) {
		this.participation = participation;
	}

	@Override
	public String checkParticipationStatus() {
		Log.verbose(
				this.user.getUsername(),
				"is checking participation status "
						+ this.participation.getHref());
		String response = DycapoHttpClient.callDycapo(DycapoHttpClient.GET,
				this.participation.getHref(),
				this.participation.toJSONObject(), user.getUsername(),
				user.getPassword());
		Participation check;
		try {
			check = DycapoObjectsFetcher.buildParticipation(new JSONObject(
					response));
			if (!check.getStatus().equals(this.participation.getStatus()))
				this.participation = check;
			Log.verbose(this.user.getUsername(),
					"status : " + check.getStatus() + " of participation : "
							+ check.getHref());
			return check.getStatus();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean cancelParticipation() {
		DycapoHttpClient.callDycapo(DycapoHttpClient.DELETE,
				this.participation.getHref(), null, user.getUsername(),
				user.getPassword());
		Log.verbose(this.user.getUsername(),
				"cancelled his participation to the trip : "
						+ this.participation.getHref());
		return true;
	}

	@Override
	public boolean startParticipation() {
		this.participation.setStatus(Participation.STARTED);
		Log.verbose(this.user.getUsername(), "is starting participation : "
				+ this.participation.getHref());
		String response = DycapoHttpClient.callDycapo(DycapoHttpClient.PUT,
				this.participation.getHref(),
				this.participation.toJSONObject(), user.getUsername(),
				user.getPassword());
		try {
			Participation result = (DycapoObjectsFetcher
					.buildParticipation(new JSONObject(response)));
		
			return true;
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.participation.setStatus(Participation.REQUESTED);
		return false;
	}

	@Override
	public boolean finishParticipation() {
		this.participation.setStatus(Participation.FINISHED);
		Log.verbose(this.user.getUsername(),
				"is finishing is participation in the trip : "
						+ this.participation.getHref());
		String response = DycapoHttpClient.callDycapo(DycapoHttpClient.PUT,
				this.participation.getHref(),
				this.participation.toJSONObject(), user.getUsername(),
				user.getPassword());
		try {
			Participation result = DycapoObjectsFetcher
					.buildParticipation(new JSONObject(response));
			
			return true;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.participation.setStatus(Participation.STARTED);
		return false;
	}

	@Override
	public void runLevelDecision0() {
		Search search = this.searchTrip(this.trip.getOrigin(),
				this.trip.getDestination(), this.user);
		if (!search.getTrips().isEmpty()) {
			int i = 0;
			while (i < search.getTrips().size()) {
				if (Math.random() <= this.acceptanceRate)
					break;
				else
					i++;
			}
			Participation participation = new Participation();
			participation.setAuthor(this.user);
			participation.setStatus(Participation.REQUESTED);
			this.participation = this.postParticipation(participation, search
					.getTrips().get(i));
			this.runlevel++;
		}
	}

	@Override
	public void runLevelDecision1() {
		this.updatePosition(Point.getPositionFromPoint(this.path.getNextPoint()));
		String newStatus = this.checkParticipationStatus();
		if (newStatus != null && newStatus.equals(Participation.ACCEPTED)) {
			if (Math.random() <= this.acceptanceRate) {
				if (this.startParticipation())
					this.runlevel++;
			} else {
				this.cancelParticipation();
				this.runlevel = 0;
			}

		}
	}

	@Override
	public void runLevelDecision2() {
		if (this.checkTripStatus(this.trip)) {
			if (Math.random() <= 0.5) {
				this.finishParticipation();
				this.runlevel = 0;
			}
		}
	}

	@Override
	public boolean checkTripStatus(Trip trip) {
		Log.verbose(this.user.getUsername(), "is checking status of trip : "
				+ trip.getHref());
		String response = DycapoHttpClient.callDycapo(DycapoHttpClient.GET,
				this.trip.getHref(), null, this.user.getUsername(),
				this.user.getPassword());
		try {
			Trip result = DycapoObjectsFetcher.buildTrip(new JSONObject(
					response));
			if (result.getActive()) {
				Log.verbose(this.user.getUsername(),
						(result.getActive()) ? "trip is active : "
								: "trip is inactive : " + result.getHref());
				this.trip = result;
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}

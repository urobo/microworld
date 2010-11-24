/**
 * 
 */
package org.microworld.robots;

import org.json.JSONException;
import org.json.JSONObject;
import org.microworld.client.DycapoHttpClient;
import org.microworld.dycapo.DycapoGlobalVariables;
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

	Participation participation;

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public Search searchTrip(Location origin, Location destination,
			Person Author) {
		Search search = new Search();
		search.setAuthor(user);
		search.setOrigin(origin);
		search.setDestination(destination);
		String response = DycapoHttpClient.callDycapo(DycapoHttpClient.POST,
				DycapoGlobalVariables.URL_BASIS + "searches/",
				search.toJSONObject(), user.getUsername(), user.getPassword());
		try {
			search = DycapoObjectsFetcher.buildSearch(new JSONObject(response));
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
		String response = DycapoHttpClient.callDycapo(DycapoHttpClient.POST,
				trip.getHref() + DycapoGlobalVariables.PARTICIPATIONS,
				participation.toJSONObject(), user.getUsername(),
				user.getPassword());
		try {
			return DycapoObjectsFetcher.buildParticipation(new JSONObject(
					response));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String checkParticipationStatus() {
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
		return true;
	}

	@Override
	public boolean startParticipation() {
		this.participation.setStatus(Participation.STARTED);
		String response = DycapoHttpClient.callDycapo(DycapoHttpClient.PUT,
				this.participation.getHref(),
				this.participation.toJSONObject(), user.getUsername(),
				user.getPassword());
		try {
			Participation result = (DycapoObjectsFetcher
					.buildParticipation(new JSONObject(response)));
			if (result.getStatus().equals(Participation.STARTED)) {
				this.participation = result;
				return true;
			}
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
		String response = DycapoHttpClient.callDycapo(DycapoHttpClient.PUT,
				this.participation.getHref(),
				this.participation.toJSONObject(), user.getUsername(),
				user.getPassword());
		try {
			Participation result = DycapoObjectsFetcher
					.buildParticipation(new JSONObject(response));
			if (result.getStatus().equals(Participation.FINISHED)) {
				this.participation = result;
				return true;
			}

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
		search = this.fetchSearch(search);
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
	public Search fetchSearch(Search search) {
		String response = DycapoHttpClient.callDycapo(DycapoHttpClient.GET,
				search.getHref(), null, this.user.getUsername(),
				this.user.getPassword());
		try {
			Search result = DycapoObjectsFetcher.buildSearch(new JSONObject(
					response));
			return result;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean checkTripStatus(Trip trip) {
		String response = DycapoHttpClient.callDycapo(DycapoHttpClient.GET,
				this.trip.getHref(), null, this.user.getUsername(),
				this.user.getPassword());
		try {
			Trip result = DycapoObjectsFetcher.buildTrip(new JSONObject(
					response));
			if (result.getActive())
				return true;
			this.trip = result;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}

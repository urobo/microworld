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

import eu.fbk.dycapo.factories.json.DycapoObjectsFetcher;

/**
 * @author riccardo
 *
 */
public class RiderAgent extends Agent implements Rider {
	
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
		String response = DycapoHttpClient.callDycapo(DycapoHttpClient.POST,DycapoGlobalVariables.URL_BASIS + "searches/", search.toJSONObject(), user.getUsername(), user.getPassword());
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
	public Participation postParticipation(Participation participation , Trip trip) throws JSONException {
		String response = DycapoHttpClient.callDycapo(DycapoHttpClient.POST, trip.getHref() + DycapoGlobalVariables.PARTICIPATIONS, participation.toJSONObject(), user.getUsername(), user.getPassword());
		return DycapoObjectsFetcher.buildParticipation(new JSONObject(response));
	}

	@Override
	public String checkParticipationStatus() throws JSONException {
		String response = DycapoHttpClient.callDycapo(DycapoHttpClient.GET, this.participation.getHref(), this.participation.toJSONObject(), user.getUsername(), user.getPassword());
		Participation check = DycapoObjectsFetcher.buildParticipation(new JSONObject(response));
		if (!check.getStatus().equals(this.participation.getStatus()))
			this.participation = check;
			
		return check.getStatus();
	}

	@Override
	public boolean cancelParticipation() {
		DycapoHttpClient.callDycapo(DycapoHttpClient.DELETE, this.participation.getHref(), null, user.getUsername(), user.getPassword());
		return true;
	}

	@Override
	public boolean startParticipation() {
		this.participation.setStatus(Participation.STARTED);
		String response = DycapoHttpClient.callDycapo(DycapoHttpClient.PUT, this.participation.getHref(),this.participation.toJSONObject() , user.getUsername(), user.getPassword());
		try {
			Participation result = (DycapoObjectsFetcher.buildParticipation(new JSONObject(response)));
			if (result.getStatus().equals(Participation.STARTED)){
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
		String response = DycapoHttpClient.callDycapo(DycapoHttpClient.PUT, this.participation.getHref(), this.participation.toJSONObject(), user.getUsername(), user.getPassword());
		try {
			Participation result = DycapoObjectsFetcher.buildParticipation(new JSONObject(response));
			if (result.getStatus().equals(Participation.FINISHED)){
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
	public void makeDecision(int runLevel) {
		// TODO Auto-generated method stub
		
	}

}

/**
 * 
 */
package eu.fbk.dycapo.factories.json;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.microworld.models.Location;
import org.microworld.models.Trip;

/**
 * @author riccardo
 * 
 */
public abstract class TripFetcher {

	public static final Trip fetchTrip(JSONObject responseValue) {
		try {
			SimpleDateFormat parser = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");

			Trip result = new Trip();

			if (responseValue.has(Trip.ID))
				result.setId(responseValue.getInt(Trip.ID));

			if (responseValue.has(DycapoObjectsFetcher.HREF))
				result.setHref(responseValue
						.getString(DycapoObjectsFetcher.HREF));

			if (responseValue.has(Trip.ACTIVE))
				result.setActive(responseValue.getBoolean(Trip.ACTIVE));

			if (responseValue.has(Trip.EXPIRES))
				try {
					result.setExpires(parser.parse(responseValue
							.getString(Trip.EXPIRES)));
				} catch (ParseException e) {
					e.printStackTrace();
				}

			if (responseValue.has(Trip.PUBLISHED))
				try {
					result.setPublished(parser.parse(responseValue
							.getString(Trip.PUBLISHED)));
				} catch (ParseException e) {
					e.printStackTrace();
				}

			if (responseValue.has(Trip.UPDATED))
				try {
					result.setUpdated(parser.parse(responseValue
							.getString(Trip.UPDATED)));
				} catch (ParseException e) {
					e.printStackTrace();
				}

			if (responseValue.has(Trip.AUTHOR))
				result.setAuthor(PersonFetcher.fetchPerson(responseValue
						.getJSONObject(Trip.AUTHOR)));

			if (responseValue.has(Trip.MODE))

				result.setMode(ModeFetcher.fetchMode(responseValue
						.getJSONObject(Trip.MODE)));

			if (responseValue.has(Trip.PREFERENCES))

				result.setPreferences(PreferencesFetcher
						.fetchPreferences(responseValue
								.getJSONObject(Trip.PREFERENCES)));

			if (responseValue.has(Trip.LOCATIONS)) {
				JSONArray rawlocs = responseValue.getJSONArray(Trip.LOCATIONS);

				int length = rawlocs.length();

				ArrayList<Location> waypoints = new ArrayList<Location>();

				for (int i = 0; i < length; i++) {

					Location res = LocationFetcher.fetchLocation(rawlocs
							.getJSONObject(i));

					if (res.getPoint() == Location.ORIG) {
						result.setOrigin(res);

					} else if (res.getPoint() == Location.DEST) {
						result.setDestination(res);

					} else
						waypoints.add(res);
				}
				if (!waypoints.isEmpty())
					result.setWaypoints(waypoints);

			}

			return result;
		} catch (JSONException e) {
			e.printStackTrace();

		}
		return null;
	}

	public static final List<Trip> extractTrips(JSONArray value) {
		List<Trip> trips = new ArrayList<Trip>();
		try {
			for (int i = 0; i < value.length(); i++) {
				trips.add(fetchTrip(value.getJSONObject(i)));
			}
			return trips;
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return null;
	}
}

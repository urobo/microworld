/**
 * 
 */
package eu.fbk.dycapo.factories.json;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.microworld.models.Location;
import org.microworld.models.Mode;
import org.microworld.models.Participation;
import org.microworld.models.Person;
import org.microworld.models.Search;
import org.microworld.models.Trip;

/**
 * @author riccardo
 * 
 */

public abstract class DycapoObjectsFetcher {

	public static final String HREF = "href";


	/**
	 * @param jsonArray
	 * @return
	 */
	public static final List<Person> extractPersons(JSONArray jsonArray) {

		return PersonFetcher.fetchPersons(jsonArray);
	}

	/**
	 * @param responseValue
	 * @return
	 */
	public static final Trip buildTrip(JSONObject responseValue) {
		return TripFetcher.fetchTrip(responseValue);
	}

	/**
	 * @param responseValue
	 * @return
	 * @throws DycapoException
	 */
	public static final Location buildLocation(JSONObject responseValue) {

		return LocationFetcher.fetchLocation(responseValue);
	}

	/**
	 * @param responseValue
	 * @return
	 * @throws DycapoException
	 */
	public static final Person buildPerson(JSONObject responseValue) {

		return PersonFetcher.fetchPerson(responseValue);
	}

	/**
	 * @param responseValue
	 * @return
	 * @throws DycapoException
	 */
	public static final Mode buildMode(JSONObject responseValue) {

		return ModeFetcher.fetchMode(responseValue);
	}

	
	/**
	 * @param responseValue
	 * @return
	 */
	public static final Participation buildParticipation(
			JSONObject responseValue) {

		return ParticipationFetcher.fetchParticipation(responseValue);
	}

	public static final Search buildSearch(JSONObject responseValue) {

		return SearchFetcher.fetchSearch(responseValue);
	}

	public static final List<Participation> extractTripParticipations(
			JSONArray json) {

		return ParticipationFetcher.fetchTripParticipations(json);
	}
}

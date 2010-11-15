/**
 * 
 */
package eu.fbk.dycapo.factories.json;

import org.json.JSONException;
import org.json.JSONObject;
import org.microworld.models.Search;

/**
 * @author riccardo
 * 
 */
public abstract class SearchFetcher {

	public static final Search fetchSearch(JSONObject jsonobj) {
		Search search = new Search();
		try {
			if (jsonobj.has(Search.AUTHOR))
				search.setAuthor(PersonFetcher.fetchPerson(jsonobj
						.getJSONObject(Search.AUTHOR)));

			if (jsonobj.has(Search.DESTINATION))
				search.setDestination(LocationFetcher.fetchLocation(jsonobj
						.getJSONObject(Search.DESTINATION)));

			if (jsonobj.has(Search.ORIGIN))
				search.setOrigin(LocationFetcher.fetchLocation(jsonobj
						.getJSONObject(Search.ORIGIN)));

			if (jsonobj.has(Search.TRIPS)) {
				search.setTrips(TripFetcher.extractTrips(jsonobj
						.getJSONArray(Search.TRIPS)));
			}
			if (jsonobj.has(DycapoObjectsFetcher.HREF))
				search.setHref(jsonobj.getString(DycapoObjectsFetcher.HREF));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return search;
	}
}

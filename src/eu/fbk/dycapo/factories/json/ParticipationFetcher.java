/**
 * 
 */
package eu.fbk.dycapo.factories.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.microworld.models.Participation;

/**
 * @author riccardo
 * 
 */
public abstract class ParticipationFetcher {

	public static final Participation fetchParticipation(JSONObject jsonObject) {
		Participation participation = new Participation();
		if (jsonObject instanceof JSONObject)

			try {

				if (jsonObject.has(DycapoObjectsFetcher.HREF)) {
					participation.setHref(jsonObject
							.getString(DycapoObjectsFetcher.HREF));

				}

				if (jsonObject.has(Participation.STATUS)) {
					participation.setStatus(jsonObject
							.getString(Participation.STATUS));

				}

				if (jsonObject.has(Participation.AUTHOR)) {
					participation.setAuthor(PersonFetcher
							.fetchPerson(jsonObject
									.getJSONObject(Participation.AUTHOR)));

				}

				return participation;
			} catch (JSONException e) {
				e.printStackTrace();
			}

		return participation;
	}

	public static final List<Participation> fetchTripParticipations(
			JSONArray json) {
		List<Participation> tparticipations = new ArrayList<Participation>();
		try {
			int size = json.length();

			for (int i = 0; i < size; i++) {
				Participation p = fetchParticipation(json.getJSONObject(i));

				tparticipations.add(p);

			}

			return tparticipations;
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return tparticipations;

	}
}

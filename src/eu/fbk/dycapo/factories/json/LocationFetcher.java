/**
 * 
 */
package eu.fbk.dycapo.factories.json;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;
import org.microworld.models.Location;

/**
 * @author riccardo
 * 
 */
public abstract class LocationFetcher {
	public static final Location fetchLocation(JSONObject responseValue) {
		Location result = new Location();
		try {

			if (responseValue.has(DycapoObjectsFetcher.HREF)) {
				result.setHref(responseValue
						.getString(DycapoObjectsFetcher.HREF));

			}
			if (responseValue.has(Location.POINT)) {
				String point = responseValue.getString(Location.POINT);

				for (int i = 0; i < Location.POINT_TYPE.length; i++)
					if (point.equals(Location.POINT_TYPE[i]))
						result.setPoint(i);

			}

			SimpleDateFormat parser = new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");

			if (responseValue.has(Location.LEAVES)) {
				try {
					result.setLeaves(parser.parse(responseValue
							.getString(Location.LEAVES)));
					// Log.d(TAG, result.getLeaves().toGMTString());
				} catch (ParseException e) {

					e.printStackTrace();
				}
			}

			if (responseValue.has(Location.LABEL)) {
				result.setLabel(responseValue.getString(Location.LABEL));
				// Log.d(TAG, result.getLabel());
			}

			if (responseValue.has(Location.COUNTRY)) {
				result.setCountry(responseValue.getString(Location.COUNTRY));
				// Log.d(TAG, result.getCountry());
			}

			if (responseValue.has(Location.REGION)) {
				result.setRegion(responseValue.getString(Location.REGION));
				// Log.d(TAG, result.getRegion());
			}

			if (responseValue.has(Location.SUBREGION))
				result.setSubregion(responseValue.getString(Location.SUBREGION));

			if (responseValue.has(Location.RECURS))
				result.setRecurs(responseValue.getString(Location.RECURS));

			if (responseValue.has(Location.DAYS)) {
				result.setDays(responseValue.getString(Location.DAYS));
				// Log.d(TAG, result.getDays());
			}

			if (responseValue.has(Location.OFFSET)) {
				result.setOffset(responseValue.getInt(Location.OFFSET));
				// Log.d(TAG, result.getOffset().toString());
			}

			if (responseValue.has(Location.GEORSS_POINT)) {
				result.setGeorss_point(responseValue
						.getString(Location.GEORSS_POINT));
				// Log.d(TAG, result.getGeorss_point());
			}

			if (responseValue.has(Location.STREET)) {
				result.setStreet(responseValue.getString(Location.STREET));
				// Log.d(TAG, result.getStreet());
			}

			if (responseValue.has(Location.TOWN)) {
				result.setTown(responseValue.getString(Location.TOWN));
				// Log.d(TAG, result.getTown());
			}

			if (responseValue.has(Location.POSTCODE)) {
				result.setPostcode(responseValue.getInt(Location.POSTCODE));
				// Log.d(TAG, result.getPostcode().toString());
			}
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}

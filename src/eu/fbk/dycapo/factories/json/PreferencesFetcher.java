/**
 * 
 */
package eu.fbk.dycapo.factories.json;

import org.json.JSONException;
import org.json.JSONObject;
import org.microworld.models.Preferences;

/**
 * @author riccardo
 * 
 */
public abstract class PreferencesFetcher {
	public final static Preferences fetchPreferences(JSONObject jsonObject) {
		Preferences result = new Preferences();

		try {
			if (jsonObject.has(DycapoObjectsFetcher.HREF))
				result.setHref(jsonObject.getString(DycapoObjectsFetcher.HREF));

			if (jsonObject.has(Preferences.AGE))
				result.setAge(jsonObject.getString(Preferences.AGE));

			if (jsonObject.has(Preferences.DRIVE))
				result.setDrive(jsonObject.getBoolean(Preferences.DRIVE));

			if (jsonObject.has(Preferences.GENDER)) {
				int i = 0;
				while (i < Preferences.GENDER_PREFS.length) {
					if ((jsonObject.getString(Preferences.GENDER))
							.toLowerCase().equals(Preferences.GENDER_PREFS[i]))
						result.setGender(i);
					i++;
				}
			}

			if (jsonObject.has(Preferences.NONSMOKING))
				result.setNonsmoking(jsonObject
						.getBoolean(Preferences.NONSMOKING));

			if (jsonObject.has(Preferences.RIDE))
				result.setRide(jsonObject.getBoolean(Preferences.RIDE));
			return result;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}

/**
 * 
 */
package eu.fbk.dycapo.factories.json;

import org.json.JSONException;
import org.json.JSONObject;
import org.microworld.models.Mode;

/**
 * @author riccardo
 * 
 */
public abstract class ModeFetcher {
	public static final Mode fetchMode(JSONObject responseValue) {

		Mode result = new Mode();
		try {

			if (responseValue.has(DycapoObjectsFetcher.HREF)) {
				result.setHref(responseValue
						.getString(DycapoObjectsFetcher.HREF));

			}

			if (responseValue.has(Mode.CAPACITY))
				result.setCapacity(responseValue.getInt(Mode.CAPACITY));

			if (responseValue.has(Mode.COLOR))
				result.setColor(responseValue.getString(Mode.COLOR));

			if (responseValue.has(Mode.COST))
				result.setCost(responseValue.getDouble(Mode.COST));

			if (responseValue.has(Mode.KIND))
				result.setKind(responseValue.getString(Mode.KIND));

			if (responseValue.has(Mode.LIC))
				result.setLic(responseValue.getString(Mode.LIC));

			if (responseValue.has(Mode.MAKE))
				result.setMake(responseValue.getString(Mode.MAKE));

			if (responseValue.has(Mode.MODEL))
				result.setModel(responseValue.getString(Mode.MODEL));

			if (responseValue.has(Mode.VACANCY))
				result.setVacancy(responseValue.getInt(Mode.VACANCY));

			if (responseValue.has(Mode.YEAR))
				result.setYear(responseValue.getInt(Mode.YEAR));

			return result;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}

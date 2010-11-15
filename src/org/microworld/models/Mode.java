/**
 * 
 */
package org.microworld.models;

import org.json.JSONException;
import org.json.JSONObject;

import eu.fbk.dycapo.factories.json.DycapoJSONObjects;

/**
 * @author riccardo
 *
 */
/**
 * @author riccardo
 * 
 */
public class Mode implements DycapoJSONObjects {
	public static final String TAG = "Modality";

	public static final String KIND = "kind";
	public static final String CAPACITY = "capacity";
	public static final String VACANCY = "vacancy";
	public static final String MAKE = "make";
	public static final String MODEL = "model_name";
	public static final String YEAR = "year";
	public static final String COLOR = "color";
	public static final String LIC = "lic";
	public static final String COST = "cost";

	public static final String HREF = "href";

	private String kind; // must
	private Integer capacity; // must
	private Integer vacancy; // must
	private String make; // must
	private String model; // must
	private Integer year; // may
	private String color; // should
	private String lic; // should
	private Double cost; // should
	protected String href;

	/**
	 * @param kind
	 * @param capacity
	 * @param vacancy
	 * @param make
	 * @param model
	 */
	public Mode(String kind, Integer capacity, Integer vacancy, String make,
			String model) {
		this.kind = kind;
		this.capacity = capacity;
		this.vacancy = vacancy;
		this.make = make;
		this.model = model;

	}

	/**
	 * @param kind
	 * @param capacity
	 * @param vacancy
	 * @param make
	 * @param model
	 * @param year
	 */
	public Mode(String kind, Integer capacity, Integer vacancy, String make,
			String model, Integer year) {
		this.kind = kind;
		this.capacity = capacity;
		this.vacancy = vacancy;
		this.make = make;
		this.model = model;
		this.year = year;
	}

	/**
	 * 
	 */
	public Mode() {

	}

	/**
	 * @return the kind
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * @param kind
	 *            the kind to set
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * @return the capacity
	 */
	public Integer getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity
	 *            the capacity to set
	 */
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	/**
	 * @return the vacancy
	 */
	public Integer getVacancy() {
		return vacancy;
	}

	/**
	 * @param vacancy
	 *            the vacancy to set
	 */
	public void setVacancy(Integer vacancy) {
		this.vacancy = vacancy;
	}

	/**
	 * @return the make
	 */
	public String getMake() {
		return make;
	}

	/**
	 * @param make
	 *            the make to set
	 */
	public void setMake(String make) {
		this.make = make;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * @return the lic
	 */
	public String getLic() {
		return lic;
	}

	/**
	 * @param lic
	 *            the lic to set
	 */
	public void setLic(String lic) {
		this.lic = lic;
	}

	/**
	 * @return the cost
	 */
	public Double getCost() {
		return cost;
	}

	/**
	 * @param cost
	 *            the cost to set
	 */
	public void setCost(Double cost) {
		this.cost = cost;
	}

	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}

	/**
	 * @param href
	 *            the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject result = new JSONObject();
		try {
			if (this.capacity instanceof Integer)
				if (this.capacity >= 2)
					result.put(Mode.CAPACITY, this.capacity.intValue());

			if (this.color instanceof java.lang.String)
				result.put(Mode.COLOR, this.color);

			if (this.cost instanceof java.lang.Double)
				if (this.cost > 0)
					result.put(Mode.COST, this.cost.doubleValue());

			if (this.kind instanceof java.lang.String)
				result.put(Mode.KIND, this.kind);

			if (this.lic instanceof java.lang.String)
				result.put(Mode.LIC, this.lic);

			if (this.make instanceof java.lang.String)
				result.put(Mode.MAKE, this.make);

			if (this.model instanceof java.lang.String)
				result.put(Mode.MODEL, this.model);

			if (this.vacancy instanceof java.lang.Integer)
				if (this.vacancy >= 0 && this.vacancy < this.capacity)
					result.put(Mode.VACANCY, this.vacancy.intValue());

			if (this.year instanceof java.lang.Integer)
				if (this.year >= 0)
					result.put(Mode.YEAR, this.year.intValue());

			return result;
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		if (this.capacity instanceof Integer)
			out.append(CAPACITY + " : " + this.capacity.intValue());

		if (this.color instanceof java.lang.String)
			out.append(COLOR + " : " + this.color);

		if (this.cost instanceof java.lang.Double)
			out.append(COST + " : " + this.cost.doubleValue());

		if (this.kind instanceof java.lang.String)
			out.append(KIND + " : " + this.kind);

		if (this.lic instanceof java.lang.String)
			out.append(LIC + " : " + this.lic);

		if (this.make instanceof java.lang.String)
			out.append(MAKE + " : " + this.make);

		if (this.model instanceof java.lang.String)
			out.append(MODEL + " : " + this.model);

		if (this.vacancy instanceof java.lang.Integer)
			out.append(VACANCY + " : " + this.vacancy.intValue());

		if (this.year instanceof java.lang.Integer)
			out.append(YEAR + " : " + this.year.intValue());
		return out.toString();
	}

}

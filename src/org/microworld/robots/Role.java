/**
 * 
 */
package org.microworld.robots;

/**
 * @author riccardo
 * 
 */
public abstract class Role {
	public static final String ROLES[] = { "driver", "rider" };
	public static final int DRIVER = 0;
	public static final int RIDER = 1;

	protected String role;

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(int id) {
		if (id < ROLES.length && id >= 0) {
			this.role = Role.ROLES[id];
		}
	}

}

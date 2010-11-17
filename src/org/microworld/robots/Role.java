/**
 * 
 */
package org.microworld.robots;

/**
 * @author riccardo
 *
 */
public abstract class Role {
	protected int runlevel;
	protected String role;
	/**
	 * @return the runlevel
	 */
	public int getRunlevel() {
		return runlevel;
	}
	/**
	 * @param runlevel the runlevel to set
	 */
	public void setRunlevel(int runlevel) {
		this.runlevel = runlevel;
	}
	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
}

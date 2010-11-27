/**
 * 
 */
package org.microworld.logging;

/**
 * @author riccardo
 * 
 */
public abstract class Log {
	public static final void verbose(String tag, String message) {
		System.out.print(System.currentTimeMillis() + "\t" + tag + "\t"
				+ message + "\n");
	}
}

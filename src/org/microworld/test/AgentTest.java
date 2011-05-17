/**
 * 
 */
package org.microworld.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.microworld.dycapo.DycapoGlobalVariables;
import org.microworld.logging.Log;
import org.microworld.models.Location;
import org.microworld.models.Person;
import org.microworld.robots.Agent;
import org.microworld.robots.DriverAgent;

/**
 * @author riccardo
 * 
 */
public class AgentTest {
	static Person p = new Person();
	static Location loc = new Location();
	static Agent agent = new DriverAgent(1);

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		p.setUsername("django");
		p.setPassword("password");
		p.setEmail("superasd@gmail.com");
		p.setPhone("030318236");
		p.setGender(Person.MALE);

		loc.setPoint(Location.POSI);
		loc.setGeorss_point("46.491745 11.320360");
		loc.setLeaves(new Date(System.currentTimeMillis()));

		agent.setUser(p);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link org.microworld.robots.Agent#register(org.microworld.models.Person)}
	 * .
	 */
	@Test
	public void testRegister() {
		Log.verbose("AgentTest", AgentTest.p.toString());
		AgentTest.agent.setUser(AgentTest.p);
		assertTrue(AgentTest.agent.register(AgentTest.p));
	}

	/**
	 * Test method for
	 * {@link org.microworld.robots.Agent#updatePosition(org.microworld.models.Location)}
	 * .
	 */
	@Test
	public void testUpdatePosition() {
		Log.verbose("django update position", AgentTest.agent.getUser()
				.getUsername()
				+ " "
				+ AgentTest.agent.getUser().getPassword()
				+ " "
				+ AgentTest.agent.getUser().getHref());
		Location result = agent.updatePosition(loc);

		assertNotNull(result);
		assertTrue(result.getGeorss_point().equals(loc.getGeorss_point()));
		assertTrue(result.getPoint().equals(loc.getPoint()));
		assertTrue(result.getHref()
				.equals(AgentTest.agent.getUser().getHref()
						+ DycapoGlobalVariables.LOCATION));

	}

	/**
	 * Test method for
	 * {@link org.microworld.robots.Agent#getPosition(org.microworld.models.Person)}
	 * .
	 */
	@Test
	public void testGetPosition() {

		AgentTest.agent.updatePosition(AgentTest.loc);
		Location result = AgentTest.agent.getPosition(p);

		assertNotNull(result);
		assertTrue(result.getGeorss_point().equals(loc.getGeorss_point()));
		assertTrue(result.getPoint().equals(loc.getPoint()));
		assertTrue(result.getHref()
				.equals(AgentTest.agent.getUser().getHref()
						+ DycapoGlobalVariables.LOCATION));
	}

}

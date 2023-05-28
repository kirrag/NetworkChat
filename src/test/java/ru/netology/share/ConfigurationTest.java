package ru.netology;

import org.junit.jupiter.api.*;
import ru.netology.share.Configuration;
import java.util.List;

public class ConfigurationTest {

	@Test
	public void testGetServerPortConfig_success() {
		// given:
		final int expected = 8080;
	
		// when:
		final List<Configuration> configList = Configuration.getConfig();
		final int result = configList.get(0).getPort();

		// then:
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testGetClientPortConfig_success() {
		// given:
		final int expected = 8080;

	
		// when:
		final List<Configuration> configList = Configuration.getConfig();
		final int result = configList.get(1).getPort();

		// then:
		Assertions.assertEquals(expected, result);
	}

	@Test
	public void testGetClientIpAddrConfig_success() {
		// given:
		final String expected = "127.0.0.1";
	
		// when:
		final List<Configuration> configList = Configuration.getConfig();
		final String result = configList.get(1).getIpAddr();

		// then:
		Assertions.assertEquals(expected, result);
	}
}

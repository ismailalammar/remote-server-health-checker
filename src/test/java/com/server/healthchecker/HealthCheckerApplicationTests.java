package com.server.healthchecker;

import com.server.healthchecker.service.HostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("staging")
@RunWith(SpringJUnit4ClassRunner.class)
class HealthCheckerApplicationTests {

	private HostService hostService;

	@BeforeEach
	void init() {
		hostService = new HostService();
	}

	@Test
	void pingUrl_activeServer_true() {
		boolean result = hostService.pingURL("https://google.com" , 3000);
		Assertions.assertTrue(result);
	}

	@Test
	void pingUrl_downServer_false() {
		boolean result = hostService.pingURL("https://soma.soma" , 3000);
		Assertions.assertFalse(result);
	}
}

package com.flx.eng.iv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Test
	public void contextLoads() throws InterruptedException {
		// Ensure our stop function survives abuse
	    Application.stop();

	    // Start the app
		Application.main( new String[]{""} );

		// Wait a little bit
		Thread.sleep(1000);

		// Stop the app
        Application.stop();
	}

}

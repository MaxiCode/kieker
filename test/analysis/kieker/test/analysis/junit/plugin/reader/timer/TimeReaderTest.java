/***************************************************************************
 * Copyright 2013 Kieker Project (http://kieker-monitoring.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/

package kieker.test.analysis.junit.plugin.reader.timer;

import org.junit.Assert;
import org.junit.Test;

import kieker.analysis.AnalysisController;
import kieker.analysis.AnalysisController.STATE;
import kieker.analysis.AnalysisControllerThread;
import kieker.analysis.exception.AnalysisConfigurationException;
import kieker.analysis.plugin.filter.forward.CountingFilter;
import kieker.analysis.plugin.reader.timer.TimeReader;
import kieker.common.configuration.Configuration;

import kieker.test.common.junit.AbstractKiekerTest;

/**
 * A JUnit test for the {@link TimeReader}.<br>
 * </br>
 * 
 * Note: I know that some of the tests can result in race conditions (although this is not very likely). This is acceptable, as those are just tests (and not for
 * productive deployment). However, if the tests tend to fail, they have to be reworked.
 * 
 * @author Nils Christian Ehmke
 * 
 * @since 1.8
 */
public class TimeReaderTest extends AbstractKiekerTest {

	@SuppressWarnings("unused")
	@Test
	public void testNonBlockingMode() throws IllegalStateException, AnalysisConfigurationException, InterruptedException {
		final AnalysisController ac = new AnalysisController();
		final AnalysisControllerThread thread = new AnalysisControllerThread(ac);

		final Configuration configuration = new Configuration();
		configuration.setProperty(TimeReader.CONFIG_PROPERTY_NAME_BLOCKING_READ, "false");
		new TimeReader(configuration, ac);

		// We expect the reader to return immediately - in this case we expect the AC to return within five seconds
		thread.start();
		Thread.sleep(5000);

		Assert.assertEquals(STATE.TERMINATED, ac.getState());
	}

	@SuppressWarnings("unused")
	@Test
	public void testBlockingMode() throws InterruptedException {
		final AnalysisController ac = new AnalysisController();
		final AnalysisControllerThread thread = new AnalysisControllerThread(ac);

		final Configuration configuration = new Configuration();
		configuration.setProperty(TimeReader.CONFIG_PROPERTY_NAME_BLOCKING_READ, "true");

		new TimeReader(configuration, ac);

		// We expect that the reader doesn't return immediately - in this case we expect the AC to run at least five seconds
		thread.start();
		Thread.sleep(5000);

		Assert.assertEquals(STATE.RUNNING, ac.getState());
		ac.terminate();
	}

	@Test
	public void testConfigurationConservation() {
		final Configuration configuration = new Configuration();
		configuration.setProperty(TimeReader.CONFIG_PROPERTY_NAME_BLOCKING_READ, "false");
		configuration.setProperty(TimeReader.CONFIG_PROPERTY_NAME_DELAY_NS, "42");
		configuration.setProperty(TimeReader.CONFIG_PROPERTY_NAME_UPDATE_INTERVAL_NS, "21");
		final TimeReader tr = new TimeReader(configuration, new AnalysisController());

		Assert.assertEquals(42, tr.getCurrentConfiguration().getLongProperty(TimeReader.CONFIG_PROPERTY_NAME_DELAY_NS));
		Assert.assertEquals(21, tr.getCurrentConfiguration().getLongProperty(TimeReader.CONFIG_PROPERTY_NAME_UPDATE_INTERVAL_NS));
		Assert.assertEquals(false, tr.getCurrentConfiguration().getBooleanProperty(TimeReader.CONFIG_PROPERTY_NAME_BLOCKING_READ));
	}

	@Test
	public void testIntervalTimer() throws InterruptedException, IllegalStateException, AnalysisConfigurationException {
		// Running 5 seconds, firing one event per 100 ms, we expect to receive approx. 50 events.
		final AnalysisController ac = new AnalysisController();
		final AnalysisControllerThread thread = new AnalysisControllerThread(ac);

		final Configuration configuration = new Configuration();
		configuration.setProperty(TimeReader.CONFIG_PROPERTY_NAME_UPDATE_INTERVAL_NS, "100000000");
		final TimeReader tr = new TimeReader(configuration, ac);
		final CountingFilter cf = new CountingFilter(new Configuration(), ac);

		ac.connect(tr, TimeReader.OUTPUT_PORT_NAME_TIMESTAMP_RECORDS, cf, CountingFilter.INPUT_PORT_NAME_EVENTS);

		thread.start();
		Thread.sleep(5000);
		ac.terminate();

		Assert.assertTrue(cf.getMessageCount() > 40);
		Assert.assertTrue(cf.getMessageCount() < 60);
	}

}
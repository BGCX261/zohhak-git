package net.piotrturski.mintaka;

import static org.fest.assertions.api.Assertions.assertThat;

import net.piotrturski.mintaka.helper.SampleConfiguration;
import net.piotrturski.mintaka.helper.SampleType;
import net.piotrturski.mintaka.runners.MintakaRunner;

import org.junit.runner.RunWith;

@RunWith(MintakaRunner.class)
public class ConfigurationTest {

	@TestWith(value="a", configuration=SampleConfiguration.class)
	public void coercerFromMethodConfig(SampleType sampleType) {
		assertThat(sampleType.value).isEqualTo("a");
	}
	
}

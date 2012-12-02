package org.zohhak.programmatic;

import static org.fest.assertions.api.Assertions.assertThat;

import org.zohhak.api.Configure;
import org.zohhak.api.TestWith;
import org.zohhak.helper.SampleType;
import org.zohhak.helper.SecondCoercer;


@Configure(coercer=SecondCoercer.class)
public class ClassLevelConfiguration {

	@TestWith("a")
	public void samleType(SampleType sampleType) {
		assertThat(sampleType.value).isEqualTo("a");
	}
	
}

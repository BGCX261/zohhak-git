package org.zohhak.helper;

import org.zohhak.api.DefaultConfiguration;

public class SampleConfiguration2 extends DefaultConfiguration {

	@Override
	public Class<?>[] coercer() {
		return asArray(CoercerOfSampleType2.class);
	}
	
}

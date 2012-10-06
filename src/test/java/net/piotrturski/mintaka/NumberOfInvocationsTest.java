package net.piotrturski.mintaka;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import net.piotrturski.mintaka.runners.MintakaRunner;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

@RunWith(MintakaRunner.class)
public class NumberOfInvocationsTest {

	static List<Integer> passedParams = new ArrayList<Integer>(2);
	
	@TestWith({
		"13",
		"17"
	})
	public void simpleParams(int param) {
		passedParams.add(param);
	}
	
	@AfterClass public static void finalCheck() {
		assertThat(passedParams).containsOnly(17, 13).hasSize(2);
	}
}

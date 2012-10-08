package net.piotrturski.mintaka;

import static org.fest.assertions.api.Assertions.assertThat;
import net.piotrturski.mintaka.helper.SampleType;
import net.piotrturski.mintaka.programmatic.BadParameterProcessing;
import net.piotrturski.mintaka.programmatic.BasicAnnotationsUsage;
import net.piotrturski.mintaka.programmatic.MixedCoercers;
import net.piotrturski.mintaka.runners.MintakaRunner;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;


public class ProgrammaticRunTest {

	@Test
	public void runJunit() {
		Result runResult = JUnitCore.runClasses(BasicAnnotationsUsage.class);
		assertThat(runResult.getFailureCount()).isEqualTo(1);
		assertThat(runResult.getIgnoreCount()).isEqualTo(3);
		assertThat(runResult.getRunCount()).isEqualTo(4);
	}
	
	@Test
	public void coercionNotFound() {
		Failure failure = coercionFailureFromSingleMethodExecution(BadParameterProcessing.class, "typeWithoutCoercion");
		Throwable exception = failure.getException();
		
		assertThat(exception).hasMessageContaining(SampleType.class.getCanonicalName());
	}

	@Test
	public void wrongBoolFormat() {
		Failure failure = coercionFailureFromSingleMethodExecution(BadParameterProcessing.class, "wrongBooleanFormat");
		assertThat(failure.getException()).hasMessageContaining("boolean");
	}

	@Test
	public void wrongEnumFormat() {
		Failure failure = coercionFailureFromSingleMethodExecution(BadParameterProcessing.class, "wrongEnumFormat");
		assertThat(failure.getException()).hasMessageContaining("SampleEnum");
	}
	
	@Test
	public void coercerCache() {
		Result result = runClassWithForcedRunner(MixedCoercers.class, null);
		assertThat(result.getFailureCount()).isEqualTo(2);
		assertThat(result.getRunCount()).isEqualTo(3);
	}		
	
	static private Failure coercionFailureFromSingleMethodExecution(Class<?> classToTest, String methodToTest) {
		Result result = runClassWithForcedRunner(classToTest, methodToTest);
		
		assertThat(result.getRunCount()).isEqualTo(1);
		assertThat(result.getFailures()).hasSize(1);

		Failure failure = result.getFailures().get(0);
		assertThat(failure.getTestHeader())
								.doesNotContain("initializationError")
								.contains(methodToTest);
							
		assertThat(failure.getException())
							.hasMessageContaining("annot interpret string")
							.isInstanceOf(IllegalArgumentException.class);
		return failure;
	}
	
	
	static public Result runClassWithForcedRunner(Class<?> clazz, String methodName) {
		try {
			RunNotifier notifier = new RunNotifier();
			
			Result result= new Result();
			RunListener listener= result.createListener();
			notifier.addListener(listener);
			
			Request request = Request.runner(new MintakaRunner(clazz));
			if (StringUtils.isNotBlank(methodName)) { 
				Description methodDescription = Description.createTestDescription(clazz, methodName);
				request = request.filterWith(methodDescription);
			}
			Runner runner = request.getRunner();
			runner.run(notifier);
			return result;
			
		} catch (InitializationError e) {
			throw new RuntimeException(e);
		}
	}
	
}

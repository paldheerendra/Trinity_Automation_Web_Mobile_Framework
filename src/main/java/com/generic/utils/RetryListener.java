package com.generic.utils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

/**
 * TestNG RetryAnalyzer Annotation Transformer
 * @author Harshvardhan Yadav(Expleo)
 */
public class RetryListener implements IAnnotationTransformer {
    @Override
	@SuppressWarnings("rawtypes")
	public void transform(ITestAnnotation testannotation,  Class testClass,Constructor testConstructor, Method testMethod)	
	{
		
			testannotation.setRetryAnalyzer(Retry.class);
	
	}
}

/** Usage details
Add listener to testng.xml
	<listeners>
		<listener class-name="com.generic.utils.RetryListener"/>
	</listeners>
*/

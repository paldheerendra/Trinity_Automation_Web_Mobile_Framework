package com.generic.customAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD, 
	java.lang.annotation.ElementType.TYPE, 
	java.lang.annotation.ElementType.CONSTRUCTOR})

/**
 * Used to transfer test case id and file name to test data provider
 * @author Harshvardhan Yadav(Expleo)
 * */

public @interface TestDataSource {
	public String testCaseID() default "";
	public String dataSource() default "";
	public String dataSourceSheet() default "";
}
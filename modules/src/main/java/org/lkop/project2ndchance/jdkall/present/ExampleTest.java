package org.lkop.project2ndchance.jdkall.present.Test;

import org.junit.Test;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

@EapAdditionalTestsuite({"modules/testcases/jdkAll/master/Testsuite/src/main/java"})
public class ExampleTest {
    
    @Test
    public void hello() {
    
        System.out.println("Hello World");
    }
}

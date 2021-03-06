package org.lkop.project2ndchance.jdkall.past.Test;

import org.lkop.project2ndchance.StringCheck;
import org.lkop.project2ndchance.ProposalData;
import org.junit.Test;

import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

@EapAdditionalTestsuite({"modules/testcases/jdkAll/master/Testsuite/src/main/java#1.0.0*1.0.0"})
public class MainTest {

    @Test
    public void run(){

        //proposals section
        String pr_dir = "proposals";

        StringCheck sc = new StringCheck(System.getProperty("APP_DIR",".") + "/" + pr_dir + "/" + "proposal-12345.txt");

        ProposalData p_data = new ProposalData();

        //p_data.setEmail(sc.getEmail());
        p_data.setName(sc.getName());
        p_data.setLastname(sc.getLastname());


        System.out.println("p_data= " + p_data.getId());
        System.out.println("p_data= " + p_data.getName());
        System.out.println("p_data= " + p_data.getLastname());
        System.out.println("p_data= " + p_data.getEmail());
    }
}

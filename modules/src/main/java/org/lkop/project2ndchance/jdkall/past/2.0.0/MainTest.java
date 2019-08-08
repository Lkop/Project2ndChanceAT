package org.lkop.project2ndchance.jdkall.past.Test;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.lkop.project2ndchance.StringCheck;
import org.lkop.project2ndchance.FileCheck;
import org.lkop.project2ndchance.ProposalData;
import org.lkop.project2ndchance.AwardData;
import org.lkop.project2ndchance.SomethingWrong;
import org.junit.Test;

@EapAdditionalTestsuite({"modules/testcases/jdkAll/master/Testsuite/src/main/java#2.0.0*2.0.0"})
public class MainTest {
    
    @Test
    public void run() throws SomethingWrong, IOException{

        StringCheck sc = new StringCheck();
        
        //awards section
        String aw_dir = "awards";
        String my_file = System.getProperty("APP_DIR",".") + "/" + aw_dir + "/" + "awards.txt";
         
        FileCheck fc = new FileCheck(my_file);
        BufferedReader br = new BufferedReader(new FileReader(new File(my_file)));
        String[] lines = br.lines().toArray(String[]::new);
    
        List<AwardData> awlist_data = new ArrayList<>();
        
        AwardData aw_data = new AwardData();
        int i=1;     
        for(String line : lines){
            
            if(!line.equals("")){

                int id = sc.getId(line);
                if(id != 0){

                    aw_data.setId(id);
                }
                
                int prize = sc.getPrize(line);         
                if(prize != 0){
                   
                   aw_data.setPrize(prize);
                }
                
                if(i%2==0){
                    
                    awlist_data.add(aw_data);
                    aw_data = new AwardData();
                }
                i++;
            }
        }
            
        for(AwardData awprint_data : awlist_data){
        
            System.out.println(awprint_data.getId());
            System.out.println(awprint_data.getPrize());
        }

        //proposals section
        String pr_dir = "proposals";
        
        System.out.println("Files in this folder:");
        File currentFolder = new File(System.getProperty("APP_DIR",".") + "/" + pr_dir);
        String[] all_files = currentFolder.list();
        
        List<ProposalData> prlist_data = new ArrayList<>();
        
        for (String filename : all_files) {
            
            my_file  = System.getProperty("APP_DIR",".") + "/" + pr_dir + "/" + filename; 
            
            fc = new FileCheck(my_file);
            br = new BufferedReader(new FileReader(new File(my_file)));
            
            //each line to each row of an array
            lines = br.lines().toArray(String[]::new);

            ProposalData pr_data = new ProposalData();
            
            boolean student = true;
            
            for(String line : lines){
                
                if(!line.equals("")){

                    if(line.equals("<--Mentor-->")){
                        student = false;
                    }
                    
                    String name = sc.getName(line);
                    if(name != null){
                        if(student == true){
                            pr_data.setName(name);
                        }else{
                            pr_data.setMName(name);
                        }
                    }
                    
                    String lastname = sc.getLastname(line);
                    if(lastname != null){
                        if(student == true){
                            pr_data.setLastname(lastname);
                        }else{
                            pr_data.setMLastname(lastname);
                        }
                    }
                    
                    String email = sc.getEmail(line);
                    if(email != null){
                        if(student == true){
                            pr_data.setEmail(email);
                        }else{
                            pr_data.setMEmail(email);
                        }
                    }
                    
                    int semester = sc.getSemester(line);
                    if(semester != 0){
                        
                        pr_data.setSemester(semester);
                    }
                }
            }
            prlist_data.add(pr_data);
        }
        
        for(ProposalData prprint_data : prlist_data){
        
            System.out.println("ID: " + prprint_data.getId());
            System.out.println("Name: " + prprint_data.getName());
            System.out.println("Lastname: " + prprint_data.getLastname());
            System.out.println("Email: " + prprint_data.getEmail());
            System.out.println("Semester: " + prprint_data.getSemester());
            System.out.println("Mentor's Name: " + prprint_data.getMName());
            System.out.println("Mentor's Lastname: " + prprint_data.getMLastname());
            System.out.println("Mentor's Email: " + prprint_data.getMEmail()); 
        }
    }
}

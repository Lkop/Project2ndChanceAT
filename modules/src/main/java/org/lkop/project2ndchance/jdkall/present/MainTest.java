package org.lkop.project2ndchance.jdkall.present.Test;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.lkop.project2ndchance.StringCheck;
import org.lkop.project2ndchance.FileCheck;
import org.lkop.project2ndchance.ProposalData;
import org.lkop.project2ndchance.AwardData;
import org.lkop.project2ndchance.SomethingWrong;
import org.lkop.project2ndchance.MappingData;
import org.junit.Test;

@EapAdditionalTestsuite({"modules/testcases/jdkAll/master/Testsuite/src/main/java#2.1.1"})
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
            boolean info = false;
            
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
                    
                    if(line.equals("<--Information-->")){
                        info = true;
                    }
                    
                    if(info){
                        
                        boolean phase_p = sc.getPhasePass(line);
                        if(phase_p){
                            pr_data.setPhasePass(phase_p);
                        }
                        
                        boolean final_p = sc.getFinalPass(line);
                        if(final_p){
                            pr_data.setFinalPass(final_p);
                        }
                        
                        LocalDate start_date = sc.getStartDate(line);
                        if(start_date != null){
                            pr_data.setStartDate(start_date);
                        }
                        
                        LocalDate end_date = sc.getEndDate(line);
                        if(end_date != null){
                            pr_data.setEndDate(end_date);
                        }
                    }   
                }
            }
            
            if(pr_data.getStartDate() == null || pr_data.getEndDate() == null){
                throw new SomethingWrong("Invalid Date");
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
            System.out.println("Phase pass: " + prprint_data.getPhasePass());
            System.out.println("Final pass: " + prprint_data.getFinalPass());
            System.out.println("Start date: " + prprint_data.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            System.out.println("End date: " + prprint_data.getEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            System.out.println();
        }
        
        //MAPPING
        List<MappingData> mplist_data = new ArrayList<>();
        Random random = new Random();

        for(i=0; i<awlist_data.size()/2; i++){
            
            if(!prlist_data.isEmpty()){
                int rand = random.nextInt(prlist_data.size());
                
                if(prlist_data.get(rand).getPhasePass()){
                    MappingData mp_data = new MappingData(awlist_data.get(i), awlist_data.get(i+awlist_data.size()/2), prlist_data.get(rand));
                    mplist_data.add(mp_data);
                    prlist_data.remove(rand);
                }else{
                    prlist_data.remove(rand);
                }
            }   
        }
        
        for(MappingData mpprint_data : mplist_data){
        
            System.out.println("ID: " + mpprint_data.getProposal().getId());
            System.out.println("Name: " + mpprint_data.getProposal().getName());
            System.out.println("Lastname: " + mpprint_data.getProposal().getLastname());
            System.out.println("Email: " + mpprint_data.getProposal().getEmail());
            System.out.println("Semester: " + mpprint_data.getProposal().getSemester());
            System.out.println("Mentor's Name: " + mpprint_data.getProposal().getMName());
            System.out.println("Mentor's Lastname: " + mpprint_data.getProposal().getMLastname());
            System.out.println("Mentor's Email: " + mpprint_data.getProposal().getMEmail());
            System.out.println();
            
            System.out.println("Developer's Award");
            System.out.println("ID: " + mpprint_data.getDevAward().getId());
            System.out.println("Prize: " + mpprint_data.getDevAward().getPrize());            
            System.out.println();
            
            System.out.println("Mentor's Award");
            System.out.println("ID: " + mpprint_data.getMenAward().getId());
            System.out.println("Prize: " + mpprint_data.getMenAward().getPrize());
        }
    }
}

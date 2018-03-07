/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetalgo;

import java.util.ArrayList;
import java.util.List;
import static projetalgo.Algo1.test1;
import static projetalgo.Algo1.test2;
import static projetalgo.Algo1.test3;

/**
 *
 * @author p1506193
 */
public class ProjetAlgo {

    public static final long K = 1000L;
    public static final long M = 1000000L;
    public static final long G = 1000000000L;
    public static final long T = 1000000000000L;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        schedulingSolution1();
    }
    
    public static void schedulingSolution1() {
        test1();
        test2();
        test3();
    }
    
    public static double workOnJobs(List<Job> jobs, List<Machine> machines){
        double elapsedSeconds = 0d, totalElapsedSeconds = 0d;
        
        boolean isWorkDone = false;
        while(!isWorkDone){
            isWorkDone = true;
            
            for(Job job : jobs){
                for(Task task : job.getTasks()){
                    task.workOnTask(machines);
                    if(!task.isDone()){
                        isWorkDone = false;
                    }
                }
            }
            
            for(Machine machine : machines){
                elapsedSeconds = Math.max(elapsedSeconds, machine.getElapsedSeconds());
            }
            
            resetMachines(machines);
            totalElapsedSeconds += elapsedSeconds;
            elapsedSeconds = 0d;
        }
        
        return totalElapsedSeconds;
    }
    
    public static void resetMachines(List<Machine> machines){
        for(Machine machine : machines){
            machine.resetMachine();
        }
    }
    
}

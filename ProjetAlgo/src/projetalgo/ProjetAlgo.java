/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetalgo;

import java.util.ArrayList;
import java.util.List;

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
        //Test 1
        Machine m1 = new Machine(ServerEnum.CPU, 40);
        List<Machine> machines = new ArrayList<Machine>();
        machines.add(m1);
        
        Task t1 = new Task(ServerEnum.CPU, 10, 1);
        Task t2 = new Task(ServerEnum.CPU, 10, 2);
        t2.addRequiredTask(t1);
        List<Task> tasks = new ArrayList<Task>();
        tasks.add(t1);
        tasks.add(t2);
        
        Job j1 = new Job(tasks);
        List<Job> jobs = new ArrayList<Job>();
        jobs.add(j1);
        
        System.out.println(workOnJobs(jobs, machines));
        
        //Test 2
        Machine m2 = new Machine(ServerEnum.CPU, 1);
        machines.add(m2);
        Machine m3 = new Machine(ServerEnum.GPU, 1);
        machines.add(m3);
        
        t1 = new Task(ServerEnum.CPU, 1, 1);
        t2 = new Task(ServerEnum.CPU, 1, 2);
        t2.addRequiredTask(t1);
        Task t3 = new Task(ServerEnum.CPU, 1, 3);
        tasks.clear();
        tasks.add(t1);
        tasks.add(t2);
        tasks.add(t3);
        
        System.out.println(workOnJobs(jobs, machines));
        
        //Test 3
        t1 = new Task(ServerEnum.CPU, 1, 1);
        t2 = new Task(ServerEnum.CPU, 1, 2);
        t2.addRequiredTask(t1);
        t3 = new Task(ServerEnum.CPU, 3, 3);
        tasks.clear();
        tasks.add(t1);
        tasks.add(t2);
        tasks.add(t3);
        
        System.out.println(workOnJobs(jobs, machines));
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

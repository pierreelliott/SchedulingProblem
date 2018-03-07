/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetalgo;

import java.util.ArrayList;
import java.util.List;
import static projetalgo.ProjetAlgo.workOnJobs;

/**
 *
 * @author p1506193
 */
public class Algo1 {
    public static void test1(boolean optimize) {
        Machine m1 = new Machine(ServerEnum.CPU, 20);
        List<Machine> machines = new ArrayList<Machine>();
        machines.add(m1);
        
        Task t1 = new Task(ServerEnum.CPU, 5, 1);
        Task t2 = new Task(ServerEnum.CPU, 5, 2);
        t2.addRequiredTask(t1);
        List<Task> tasks = new ArrayList<Task>();
        tasks.add(t1);
        tasks.add(t2);
        
        Job j1 = new Job(tasks);
        List<Job> jobs = new ArrayList<Job>();
        jobs.add(j1);
        
        System.out.println(workOnJobs(jobs, machines, optimize));
    }
    
    public static void test2(boolean optimize) {
        List<Machine> machines = new ArrayList<Machine>();
        Machine m1 = new Machine(ServerEnum.CPU, 20);
        machines.add(m1);
        Machine m2 = new Machine(ServerEnum.CPU, 20);
        machines.add(m2);
        Machine m3 = new Machine(ServerEnum.GPU, 10000);
        machines.add(m3);
        
        Task t1 = new Task(ServerEnum.CPU, 20, 1);
        Task t2 = new Task(ServerEnum.CPU, 20, 2);
        t2.addRequiredTask(t1);
        Task t3 = new Task(ServerEnum.CPU, 20, 3);
        List<Task> tasks = new ArrayList<Task>();
        tasks.add(t1);
        tasks.add(t2);
        tasks.add(t3);
        
        Job j1 = new Job(tasks);
        List<Job> jobs = new ArrayList<Job>();
        jobs.add(j1);
        
        System.out.println(workOnJobs(jobs, machines, optimize));
    }
    
    public static void test3(boolean optimize) {
        List<Machine> machines = new ArrayList<Machine>();
        Machine m1 = new Machine(ServerEnum.CPU, 20);
        machines.add(m1);
        Machine m2 = new Machine(ServerEnum.CPU, 20);
        machines.add(m2);
        Machine m3 = new Machine(ServerEnum.GPU, 10000);
        machines.add(m3);
        
        Task t1 = new Task(ServerEnum.CPU, 20, 1);
        Task t2 = new Task(ServerEnum.CPU, 20, 2);
        t2.addRequiredTask(t1);
        Task t3 = new Task(ServerEnum.CPU, 50, 3);
        List<Task> tasks = new ArrayList<Task>();
        tasks.add(t1);
        tasks.add(t2);
        tasks.add(t3);
        
        Job j1 = new Job(tasks);
        List<Job> jobs = new ArrayList<Job>();
        jobs.add(j1);
        
        System.out.println(workOnJobs(jobs, machines, optimize));
    }
}

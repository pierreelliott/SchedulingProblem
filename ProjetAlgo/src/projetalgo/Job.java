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
public class Job {
    private List<Task> tasks;
    private int jobNumber;
    
    public Job(List<Task> tasks){
        this.tasks = tasks;
        this.jobNumber = ++jobTotalNumber;
    }
    
    public void addTask(Task task){
        tasks.add(task);
    }
    
    public List<Task> getTasks(){
        return tasks;
    }
    
    /* ============================ */

    @Override
    public String toString() {
        String desc = "Job " + jobNumber + " = [";
        for(Task t: tasks) {
            desc += t.getName();
            if(tasks.indexOf(t) != tasks.size()-1) {
                desc += ", ";
            }
        }
        desc += "]\n";
        
        for(Task t: tasks) {
            desc += "\t" + t.toString();
        }
        return desc;
    }
    
    /* ============================ */
    
    private static int jobTotalNumber = 0;
    public static void setJobStartNumber(int n) { jobTotalNumber = n; }
    public static int getJobStartNumber() { return jobTotalNumber; }
    
    public static Job generateRandomJob(int nbTasks) {
        Job job = null;
        List<Task> tasks = new ArrayList<>();
        
        for(int i = 1; i <= nbTasks; i++) {
            Task t = Task.generateRandomTask(i);
            if(i != 1) {
                Task.randomAncestors(t, tasks);
            }
            tasks.add(t);
        }
        job = new Job(tasks);
        
        return job;
    }
    
    public static Job readJob(String jobString) {
        Job job = null;
        List<Task> tasks = new ArrayList<>();
        
        String[] tasksString = jobString.replaceAll("\t", "").split("\n");
        for(int i = 1; i < tasksString.length; i++) {
            tasks.add(Task.readTask(jobString, tasks));
        }
        
        job = new Job(tasks);
        
        return job;
    }
}

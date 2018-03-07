/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetalgo;

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
        desc += "]";
        
        for(Task t: tasks) {
            desc += "\t" + t.getName() + " = " + t.toString();
        }
        return desc;
    }
    
    /* ============================ */
    
    private static int jobTotalNumber = 0;
    
    public static void generateRandomJob(int minTasks, int maxTasks) {
        
    }
}

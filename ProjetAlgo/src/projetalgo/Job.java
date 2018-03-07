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
    
    public Job(List<Task> tasks){
        this.tasks = tasks;
    }
    
    public void addTask(Task task){
        tasks.add(task);
    }
    
    public List<Task> getTasks(){
        return tasks;
    }
}

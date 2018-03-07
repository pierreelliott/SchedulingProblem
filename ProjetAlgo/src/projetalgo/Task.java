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
public class Task {
    private List<Task> requiredTasks;
    private String machineType;
    private long /*totalOperations,*/ remainingOperations;
    
    public Task(List<Task> requiredTasks, String machineType, long remainingOperations){
        this.requiredTasks = requiredTasks;
        this.machineType = machineType;
        this.remainingOperations = remainingOperations;
    }
    
    public Task(String machineType, long remainingOperations){
        this(new ArrayList<Task>(), machineType, remainingOperations);
    }
    
    public void addRequiredTask(Task task){
        requiredTasks.add(task);
    }
    
    public void workOnTask(List<Machine> machines){
        for(Task requiredTask : requiredTasks){
            if(!requiredTask.isDone()){
                requiredTask.workOnTask(machines);
            }
        }
        
        for(Machine machine : machines){
            if(machine.getType()!=machineType){
                continue;
            }

            long machineAvailableOperations = machine.getAvailableOperations();
            long machineTotalOperations = machine.getTotalOperations();
            
            if(machineAvailableOperations <= 0){
                continue;
            }
            else{
                long operationsToMake = Math.min(machineAvailableOperations, remainingOperations);
                machine.makeOperations(operationsToMake);

                remainingOperations -= operationsToMake;
            }

            if(this.isDone()){
                break;
            }
        }
    }
    
    public boolean isDone(){
        return remainingOperations <= 0;
    }
}

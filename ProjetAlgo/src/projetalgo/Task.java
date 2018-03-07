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
    private ServerEnum machineType;
    private long /*totalOperations,*/ remainingOperations;
    private String name;
    
    public Task(List<Task> requiredTasks, ServerEnum machineType, long remainingOperations, int nom){
        this.requiredTasks = requiredTasks;
        this.machineType = machineType;
        this.remainingOperations = remainingOperations;
        this.name = "T" + nom;
    }
    
    public Task(ServerEnum machineType, long remainingOperations, int nom){
        this(new ArrayList<Task>(), machineType, remainingOperations, nom);
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
    
    /* ========================== */
    
    public String getName() {
        return name;
    }
    
    public void setName(String n) {
        this.name = n;
    }

    @Override
    public String toString() {
        String desc = machineType + ", " + getCapacity() + ", [";
        for(Task t: requiredTasks) {
            desc += t.getName();
            if(requiredTasks.indexOf(t) != requiredTasks.size()-1) {
                desc += ",";
            }
        }
        desc += "]\n";
        return desc;
    }
    
    private String getCapacity() {
        if(this.remainingOperations >= 1000) {
            return this.remainingOperations + "T";
        } else {
            return this.remainingOperations + "G";
        }
    }
    
    /* ========================== */
    
    public static Task generateRandomTask() {
        // TODO Choix serveur aléatoire, choix capacité semi aléatoire (dépend du serveur)
        return new Task(ServerEnum.CPU, 2000, 0);
    }
}

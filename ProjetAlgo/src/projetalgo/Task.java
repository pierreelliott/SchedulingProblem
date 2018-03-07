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
                return;
            }
        }
        
        for(Machine machine : machines){
            if(machine.getType()!=machineType){
                continue;
            }

            long machineAvailableOperations = machine.getAvailableOperations();
            
            if(machineAvailableOperations <= 0){
                continue;
            }
            else{
                long operationsToMake = Math.min(machineAvailableOperations, remainingOperations);
                remainingOperations -= machine.makeOperations(operationsToMake);
            }

            if(this.isDone()){
                break;
            }
        }
    }
    
    //Cette fonction permet d'exécuter une tâche sur toutes les machines disponibles
    //même quand une seule suffirait pour le faire en 1 seconde max
    public void workOnTaskOptimized(List<Machine> machines){
        for(Task requiredTask : requiredTasks){
            if(!requiredTask.isDone()){
                return;
            }
        }
        
        //On parcourt les machines une première fois pour calculer leur capacité quand on les regroupe
        long machineTotalAvailableOperations = 0L;
        for(Machine machine : machines){
            if(machine.getType()!=machineType){
                continue;
            }

            machineTotalAvailableOperations += machine.getAvailableOperations();
        }
        if(machineTotalAvailableOperations <= 0d){
            return;
        }
        
        //On calcule combien de temps doit travailler chaque machine sur la tâche
        //(afin d'optimiser au maximum ce temps doit être identique sur chaque machine)
        double timeToWork = (double)remainingOperations/machineTotalAvailableOperations;
        
        //Le reste de la fonction demeure le même, à part le nombre d'opérations à faire que l'on calcule en foncion du temps de travail
        for(Machine machine : machines){
            if(machine.getType()!=machineType){
                continue;
            }

            long machineAvailableOperations = machine.getAvailableOperations();
            
            if(machineAvailableOperations <= 0){
                continue;
            }
            else{
                long operationsToMake = Math.min(machineAvailableOperations, (long)(machineAvailableOperations*timeToWork));
                remainingOperations -= machine.makeOperations(operationsToMake);
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

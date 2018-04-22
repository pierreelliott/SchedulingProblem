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
        this(requiredTasks, machineType, remainingOperations, "T"+nom);
    }
    
    public Task(List<Task> requiredTasks, ServerEnum machineType, long remainingOperations, String nom){
        this.requiredTasks = requiredTasks;
        this.machineType = machineType;
        this.remainingOperations = remainingOperations;
        this.name = nom;
    }
    
    public Task(ServerEnum machineType, long remainingOperations, int nom){
        this(new ArrayList<>(), machineType, remainingOperations, nom);
    }
    
    public Task(ServerEnum machineType, long remainingOperations, String nom){
        this(new ArrayList<>(), machineType, remainingOperations, nom);
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
    
    public String getName(boolean number) {
        if(number) {
            return name.substring(1);
        } else {
            return getName();
        }
    }
    
    public void setName(String n) {
        this.name = n;
    }
    
    public void setName(int n) {
        this.name = "T" + n;
    }

    @Override
    public String toString() {
        String desc = name + " = " + machineType.getString() + ", " + getCapacity() + ", [";
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
        return getCapacity(this.remainingOperations);
    }
    
    /* ========================== */
    
    // Ressources nécessaires pour une tâche en fonction du type de serveur
    // (jusqu'à 10 fois la puissance max du serveur)
    private static final int minCPU = 1; // = 1G
    private static final int maxCPU = 500; // = 500G
    private static final int minGPU = 1000; // = 1T
    private static final int maxGPU = 250000; // = 250T
    private static final int minIO = 1; // = 1G
    private static final int maxIO = 15; // = 15G
    
    private static String getCapacity(long cap) {
        return cap + "G";
    }
    
    private static long getCapacity(String cap) {
        if(cap.endsWith("T")) { // Si l'unité est ele Teraoctet
            return Long.parseLong(cap.substring(0, cap.length()))*1000;
        }
        // Sinon, on considère que c'est en Gigaoctet
        return Long.parseLong(cap.substring(0, cap.length()));
    }
    
    public static Task generateRandomTask(int name) {
        // Choisit un type de serveur aléatoirement
        int rand = (int)(Math.random()*1000*(ServerEnum.values().length))/1000;
        return generateRandomTask(ServerEnum.values()[rand], name);
    }
    
    public static Task generateRandomTask(ServerEnum enu, int name) {
        // Choisit le besoin en ressources semi-aléatoirement
        // (suivant le type de serveur)
        long cap = randomCapacity(enu);
        return new Task(enu, cap, name);
    }
    
    public static long randomCapacity(ServerEnum type) {
        switch(type) {
            case CPU:
                return (long)(Math.random()*(maxCPU - minCPU) + minCPU);
            case GPU:
                return (long)(Math.random()*(maxGPU - minGPU) + minGPU);
            case IO:
            default:
                return (long)(Math.random()*(maxIO - minIO) + minIO);
        }
    }
    
    public static void randomAncestors(Task tache, List<Task> tasks) {
        int rand;
        for(Task t : tasks) {
            rand = (int)(Math.random()*1000);
            if(rand > 700) { // 30% de chances que cette tâche soit une dépendance de la tache actuelle
                tache.addRequiredTask(t);
            }
        }
    }
    
    public static Task readTask(String taskString, List<Task> tasks) {
        Task task = null;
        
        String[] temp = taskString.split(" = ");
        String name = temp[0];
        
        temp = temp[1].split(", ");
        String type = temp[0];
        String capacity = temp[1];
        
        task = new Task(ServerEnum.getEnum(type), getCapacity(capacity), name);
        
        if(!temp[2].equalsIgnoreCase("[]")) {
            temp = temp[2].replaceAll("[", "").replaceAll("]", "").split(",");
            for(int i = 0; i < tasks.size(); i++) {
                Task t = tasks.get(i);
                if(t.getName().equalsIgnoreCase(temp[i])) {
                    task.addRequiredTask(t);
                }
            }
        }
        
        return task;
    }
}

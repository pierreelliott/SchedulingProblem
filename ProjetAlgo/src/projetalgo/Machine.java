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
public class Machine {
    
    private static final int minCPU = 5; //G
    private static final int maxCPU = 50; //G
    private static final int minGPU = 5000; //G
    private static final int maxGPU = 25000; //G
    private static final int minIO = 1; //G
    private static final int maxIO = 3; //G
    
    private long totalOperations, availableOperations;
    private ServerEnum type;
    private double currentTime = 0;
    private List<Task> tasksDone = new ArrayList<>();
    private int rank = 0;
    
    public Machine(ServerEnum type, long totalOperations){
        this.totalOperations = checkEnum(totalOperations, type);
        this.availableOperations = this.totalOperations;
        this.type = type;
    }
    
    private long checkEnum(long cap, ServerEnum enu) {
        switch(enu) {
            case CPU:
                cap = verifyCapacity(cap, minCPU, maxCPU);
                break;
            case GPU:
                cap = verifyCapacity(cap, minGPU, maxGPU);
                break;
            case IO:
                cap = verifyCapacity(cap, minIO, maxIO);
                break;
        }
        return cap;
    }
    
    private long verifyCapacity(long cap, long min, long max) {
        if(cap < min) return min;
        if(cap > max) return max;
        return cap;
    }
    
    public long getAvailableOperations(){
        return availableOperations;
    }
    
    public ServerEnum getType(){
        return type;
    }
    
    public long makeOperations(long operationsNumber){
        if(operationsNumber > availableOperations){
            return 0;
        }
        availableOperations -= operationsNumber;
        return operationsNumber;
    }
    
    public long getTotalOperations(){
        return totalOperations;
    }

    public List<Task> getTasksDone() { return tasksDone; }
    
    public void resetMachine(){
        availableOperations = totalOperations;
    }
    
    public double getElapsedSeconds(){
        return (double)(totalOperations-availableOperations)/totalOperations;
    }
    
    public double timeToDo(long operations) {
        return ((double)totalOperations)/((double)operations);
    }
    public double timeToDo(Task task) { return timeToDo(task.getTotalOperations()); }
    
    public void execute(Task task, double offset) {
        this.currentTime += timeToDo(task) + offset;
        this.tasksDone.add(task);
        task.setDoneAt(currentTime);
    }
    
    public double getCurrentTime() { return currentTime; }
    public double getMinimumTime() {
        double time = 0;
        for(Task t : tasksDone) {
            //if(t.getDoneAt() > time) {
            //    time = t.getDoneAt();
            //}
            time += timeToDo(t);
        }
        return time;
    }
    public int getRank() { return rank; }
    public void setRank(int n) { rank = n; }
    
    /* ================================= */

    @Override
    public String toString() {
        return totalOperations + "G";
    }
    
    /* ================================= */
    
    public static List<Machine> generateListServer(ServerEnum type, int nbServer) {
        List<Machine> list = new ArrayList<>();
        long randCap;
        
        for(int i = 0; i < nbServer; i++) {
            randCap = randomCapacity(type);
            list.add(new Machine(type, randCap));
        }
        
        return list;
    }
    
    public static long randomCapacity(ServerEnum type) {
        switch(type) {
            case CPU:
                return (long)(Math.random()*(maxCPU - minCPU) + minCPU);
            case GPU:
                return (long)(Math.random()*(maxGPU - minGPU) + minGPU);
            case IO:
                return (long)(Math.random()*(maxIO - minIO) + minIO);
        }
        return 3;
    }
    
    public static List<Machine> readFile(String serverString) {
        List<Machine> servers = new ArrayList<>();
        
        serverString = serverString.replace("Servers\n", "").replace("\t", "");
        String[] tab = serverString.split("\n");
        
        // ======= CPU ========
        String type = tab[0].split(" = ")[0];
        String[] serv = tab[0].split(" = ")[1].replace("[", "").replace("]", "").split(", ");
        for(int i = 0; i < serv.length; i++) {
            servers.add(new Machine(ServerEnum.getEnum(type), getCapacity(serv[i])));
        }
        
        // ======= GPU ========
        type = tab[1].split(" = ")[0];
        serv = tab[1].split(" = ")[1].replace("[", "").replace("]", "").split(", ");
        for(int i = 0; i < serv.length; i++) {
            servers.add(new Machine(ServerEnum.getEnum(type), getCapacity(serv[i])));
        }
        
        // ======= I/O ========
        type = tab[2].split(" = ")[0];
        serv = tab[2].split(" = ")[1].replace("[", "").replace("]", "").split(", ");
        for(int i = 0; i < serv.length; i++) {
            servers.add(new Machine(ServerEnum.getEnum(type), getCapacity(serv[i])));
        }
        
        return servers;
    }
    
    public static long getCapacity(String cap) {
        if(cap.endsWith("T")) { // Si l'unité est le Teraoctet
            return Long.parseLong(cap.substring(0, cap.length()-1))*1000;
        }
        // Sinon, on considère que c'est en Gigaoctet
        return Long.parseLong(cap.substring(0, cap.length()-1));
    }
    
    public static Machine best(ServerEnum type, List<Machine> servers) {
        Machine best = null;
        for(Machine s : servers) {
            if(s.getType() == type) {
                if(best == null) {
                    best = s;
                } else {
                    if(best.totalOperations < s.totalOperations) {
                        best = s;
                    }
                }
            }
        }
        return best;
    }
    
    public static List<Machine> getServerOfType(List<Machine> servers, ServerEnum type) {
        List<Machine> serv = new ArrayList<>();
        for(Machine s: servers) {
            if(s.getType() == type) {
                serv.add(s);
            }
        }
        return serv;
    }
}

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
    private static final int minGPU = 5000; //T
    private static final int maxGPU = 25000; //T
    private static final int minIO = 1; //G
    private static final int maxIO = 3; //G
    
    private long totalOperations, availableOperations;
    private ServerEnum type;
    
    public Machine(ServerEnum type, long totalOperations){
        this.totalOperations = checkEnum(totalOperations, type);
        availableOperations = this.totalOperations;
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
    
    public void makeOperations(long operationsNumber){
        if(operationsNumber > availableOperations){
            return;
        }
        availableOperations -= operationsNumber;
    }
    
    public long getTotalOperations(){
        return totalOperations;
    }
    
    public void resetMachine(){
        availableOperations = totalOperations;
    }
    
    public double getElapsedSeconds(){
        return (double)(totalOperations-availableOperations)/totalOperations;
    }
    
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
                return (long)(Math.random()*1000*(maxCPU - minCPU) + minCPU);
            case GPU:
                return (long)(Math.random()*1000*(maxGPU - minGPU) + minGPU);
            case IO:
                return (long)(Math.random()*1000*(maxIO - minIO) + minIO);
        }
        return 3;
    }
}

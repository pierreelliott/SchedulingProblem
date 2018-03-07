/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetalgo;

/**
 *
 * @author p1506193
 */
public class Machine {
    private long totalOperations, availableOperations;
    private String type;
    
    public Machine(long totalOperations, String type){
        this.totalOperations = totalOperations;
        availableOperations = totalOperations;
        this.type = type;
    }
    
    public long getAvailableOperations(){
        return availableOperations;
    }
    
    public String getType(){
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
}

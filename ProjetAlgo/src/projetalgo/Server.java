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
 * @author p1402690
 */
public class Server {
    
    private final int minCPU = 5; //G
    private final int maxCPU = 50; //G
    private final int minGPU = 5000; //T
    private final int maxGPU = 25000; //T
    private final int minIO = 1; //G
    private final int maxIO = 3; //G
    
    public static final long K = 1000L;
    public static final long M = 1000000L;
    public static final long G = 1000000000L;
    public static final long T = 1000000000000L;
    
    private int capacity;
    private ServerEnum type;
    
    public Server(int cap, ServerEnum enu) {
        this.capacity = checkEnum(cap, enu);
        this.type = enu;
    }
    
    private int checkEnum(int cap, ServerEnum enu) {
        switch(enu) {
            case CPU:
                cap = verifyCapacity(cap, minCPU, maxCPU);
                break;
            case GPU:
                cap = verifyCapacity(cap, minCPU, maxCPU);
                break;
            case IO:
                cap = verifyCapacity(cap, minCPU, maxCPU);
                break;
        }
        return cap;
    }
    
    private int verifyCapacity(int cap, int min, int max) {
        if(cap < min) return min;
        if(cap > max) return max;
        return cap;
    }
    
    public static List<Server> generateListServer(int nbMin, int nbMax) {
        List<Server> list = new ArrayList<>();
        
        // TODO Traitements (avec #nb CPU par rapportau nb total, ...)
        
        return list;
    }
}

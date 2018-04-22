/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetalgo;

/**
 *
 * @author p1402690
 */
public enum ServerEnum {
    
    CPU("CPU"),
    GPU("GPU"),
    IO("I/O");
    
    private final String value;
    private ServerEnum(String value) {
        this.value = value;
    }
    
    /**
     * @return String name
     */
    public String getString() {
        return this.value;
    }
    
    /**
     * Get Enum from name
     * @param s ID
     * @return Enum
     */
    public static ServerEnum getEnum(String s){
        if(s.equalsIgnoreCase("CPU"))
            return ServerEnum.CPU;
        if(s.equalsIgnoreCase("GPU"))
            return ServerEnum.GPU;
        if(s.equalsIgnoreCase("I/O"))
            return ServerEnum.IO;
        if(s.equalsIgnoreCase("IO"))
            return ServerEnum.IO;
        return null;
    }
}

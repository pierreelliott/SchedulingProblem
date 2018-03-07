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
public class FileGenerator {
    
    public static void generate() {
        int totalServers = 10;
        int totalJobs = 20;
        
        List<Machine> servers = generateServers(totalServers);
        List<Job> jobs = generateJobs(totalJobs);
        
        System.out.println(serversToString(servers));
    }
    
    public static String serversToString(List<Machine> servers) {
        String desc = "Servers\n";
        String serv = "";
        
        // Print the CPU part
        desc += "\t" + "CPU = [";
        for(Machine s: servers) {
            if(s.getType() == ServerEnum.CPU) {
                serv += s.toString() + ", ";
            }
        }
        if(serv.endsWith(", ")) {
            serv = serv.substring(0, serv.length()-2);
        }
        desc += serv + "]\n";
        
        /* =========================================== */
        // Print the GPU part
        desc += "\t" + "GPU = [";
        serv = "";
        for(Machine s: servers) {
            if(s.getType() == ServerEnum.GPU) {
                serv += s.toString() + ", ";
            }
        }
        if(serv.endsWith(", ")) {
            serv = serv.substring(0, serv.length()-3);
        }
        desc += serv + "]\n";
        
        /* =========================================== */
        // Print the IO part
        desc += "\t" + "I/o = [";
        serv = "";
        for(Machine s: servers) {
            if(s.getType() == ServerEnum.IO) {
                serv += s.toString() + ", ";
            }
        }
        if(serv.endsWith(", ")) {
            serv = serv.substring(0, serv.length()-3);
        }
        desc += serv + "]\n";
        
        return desc;
    }
    
    public static List<Machine> generateServers(int totalServers) {
        List<Machine> servers = new ArrayList<>();
        double n1 = Math.random()*0.25+0.25;
        int nb1 = (int)(n1*totalServers);
        
        servers.addAll(Machine.generateListServer(ServerEnum.CPU, nb1));
        
        n1 = Math.random()*0.25+0.25;
        int nb2 = (int)(n1*totalServers);
        servers.addAll(Machine.generateListServer(ServerEnum.GPU, nb2));
        
        servers.addAll(Machine.generateListServer(ServerEnum.IO, totalServers-(nb1+nb2)));
        
        return servers;
    }
    
    public static List<Job> generateJobs(int totalJobs) {
        List<Job> jobs = new ArrayList<>();
        // TODO Générer min/max tâches aléatoirement (avec bornes min et max)
        int minTasks = 3;
        int maxTasks = 6;
        int nbTasks = (int)(Math.random()*(maxTasks - minTasks)+minTasks);
        
        for(int i = 0; i<totalJobs; i++) {
            jobs.add(Job.generateRandomJob(nbTasks));
        }
        
        return jobs;
    }
}

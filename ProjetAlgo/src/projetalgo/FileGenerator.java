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
    
    public static String generate() {
        int totalServers = 10;
        int totalJobs = 20;
        
        List<Machine> servers = generateServers(totalServers);
        List<Job> jobs = generateJobs(totalJobs);
        
        String str = serversToString(servers) + jobsToString(jobs);
        //System.out.println(str);
        return str;
    }
    
    public static String serversToString(List<Machine> servers) {
        String desc = "Servers\n";
        String serv = "";
        
        // Print the CPU part
        desc += "\t" + "CPU = [";
        for(Machine s: getServerOfType(servers,"CPU")) {
            serv += s.toString() + ", ";
        }
        desc += serv.substring(0, serv.length()-2) + "]\n";
        
        /* =========================================== */
        // Print the GPU part
        desc += "\t" + "GPU = [";
        serv = "";
        for(Machine s: getServerOfType(servers,"GPU")) {
            serv += s.toString() + ", ";
        }
        desc += serv.substring(0, serv.length()-2) + "]\n";
        
        /* =========================================== */
        // Print the I/O part
        desc += "\t" + "I/O = [";
        serv = "";
        for(Machine s: getServerOfType(servers,"IO")) {
            serv += s.toString() + ", ";
        }
        desc += serv.substring(0, serv.length()-2) + "]\n";
        
        return desc;
    }
    
    public static List<Machine> getServerOfType(List<Machine> servers, String type) {
        List<Machine> serv = new ArrayList<>();
        for(Machine s: servers) {
            if(s.getType() == ServerEnum.valueOf(type)) {
                serv.add(s);
            }
        }
        return serv;
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
        int nbTasks;
        
        for(int i = 0; i<totalJobs; i++) {
            nbTasks = (int)(Math.random()*(maxTasks - minTasks)+minTasks);
            jobs.add(Job.generateRandomJob(nbTasks));
        }
        
        return jobs;
    }
    
    public static String jobsToString(List<Job> jobs) {
        String chaine = "";
        for(Job j : jobs) {
            chaine += j.toString();
        }
        return chaine;
    }
}

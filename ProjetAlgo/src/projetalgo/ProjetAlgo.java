/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetalgo;

import java.util.ArrayList;
import java.util.List;
import static projetalgo.Algo1.test1;
import static projetalgo.Algo1.test2;
import static projetalgo.Algo1.test3;

/**
 *
 * @author p1506193
 */
public class ProjetAlgo {

    public static final long K = 1000L;
    public static final long M = 1000000L;
    public static final long G = 1000000000L;
    public static final long T = 1000000000000L;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        FileGenerator.generate();
        //test_schedulingSolution1();
        //test_schedulingSolution2();
    }
    
    public static void test_schedulingSolution1() {
        test1(false);
        test2(false);
        test3(false);
    }
    
    public static void test_schedulingSolution2() {
        test1(true);
        test2(true);
        test3(true);
    }
    
    public static void schedulingSolution1(String file) {
        String[] f = file.split("\nJob ");
        List<Machine> servers = Machine.readFile(f[0]);
        List<Job> jobs = new ArrayList<>();
        for(int i = 1; i < f.length; i++) {
            jobs.add(Job.readJob(f[i]));
        }
        
        System.out.println(workOnJobs(jobs, servers, false));
    }
    
    public static void rankServers(List<Machine> servers) {
        /*  Est censé classer les serveurs pour pouvoir choisir celui le moins
        *   avancé et le plus efficace.
        */
    }
    
    public static void algo3(List<Job> jobs, List<Machine> servers) {
        Machine bestCPU = Machine.best(ServerEnum.CPU, servers);
        Machine bestGPU = Machine.best(ServerEnum.GPU, servers);
        Machine bestIO = Machine.best(ServerEnum.IO, servers);
        Machine[] bests = { bestCPU, bestGPU, bestIO };
        
        rankServers(servers);
        
        int tasksNumber = 0;
        for(Job j : jobs)
            tasksNumber += j.getTasks().size();
        
        Object[][] tab = new Object[tasksNumber][2];
        int count = 0;
        
        // Estime le temps des chemins critiques de chaque tâche
        for(Job j : jobs) {
            for(Task t : j.getTasks()) {
                tab[count][0] = t;
                tab[count][1] = new Double(t.estimateTime(bests));
                count++;
            }
        }
        boolean finished = false;
        while(!finished) {
            double worstTime = 0;
            Task taskToExecute = null;
            for(int i = 0; i < tasksNumber; i++) {
                if( !((Task)tab[i][0]).isDone() && (double)tab[i][1] > worstTime
                        && ((Task)tab[i][0]).parentsAreDone() ) {
                    taskToExecute = (Task)tab[i][0];
                    worstTime = (double)tab[i][1];
                }
            }
            if(taskToExecute == null) { // Si aucune tâche choisie, on a fini
                break;
            }
            // Sinon on choisit la machine sur laquelle on va l'exécuter
            
            double now = Double.MAX_VALUE;
            Machine serv = null;
            for(Machine server : Machine.getServerOfType(servers, taskToExecute.getType())) {
                if(server.getCurrentTime() < now) {
                    serv = server;
                    now = server.getCurrentTime();
                }
            }
            serv.execute(taskToExecute, taskToExecute.getLastParentDoneAt());
        }
        
        System.out.println("Emploi du temps effectué");
    }
    
    /**
     * 
     * @param jobs
     * @param machines
     * @param optimize <em>False</em> -> solution par algo 1 <br><em>True</em> -> solution par algo 2
     * @return 
     */
    public static double workOnJobs(List<Job> jobs, List<Machine> machines, boolean optimize){
        double elapsedSeconds = 0d, totalElapsedSeconds = 0d;
        
        boolean isWorkDone = false;
        while(!isWorkDone){
            isWorkDone = true;
            
            for(Job job : jobs){
                for(Task task : job.getTasks()){
                    if(optimize){
                        task.workOnTaskOptimized(machines);
                    }
                    else{
                        task.workOnTask(machines);
                    }
                    if(!task.isDone()){
                        isWorkDone = false;
                    }
                }
            }
            
            for(Machine machine : machines){
                elapsedSeconds = Math.max(elapsedSeconds, machine.getElapsedSeconds());
            }
            
            resetMachines(machines);
            totalElapsedSeconds += elapsedSeconds;
            elapsedSeconds = 0d;
        }
        
        return totalElapsedSeconds;
    }
    
    public static void resetMachines(List<Machine> machines){
        for(Machine machine : machines){
            machine.resetMachine();
        }
    }
    
}

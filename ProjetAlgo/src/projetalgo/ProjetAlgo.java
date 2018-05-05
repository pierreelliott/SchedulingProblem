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
        String file = FileGenerator.generate();
        System.out.println("--- Début du fichier généré ---");
        System.out.println(file);
        System.out.println("--- Fin du fichier généré ---");
        schedulingSolution(file, 1);
        schedulingSolution(file, 2);
        schedulingSolution(file, 3);
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
    
    public static void schedulingSolution(String file, int solutionNumber) {
        String[] f = file.split("\nJob ");
        List<Machine> servers = Machine.readFile(f[0]);
        List<Job> jobs = new ArrayList<>();
        for(int i = 1; i < f.length; i++) {
            jobs.add(Job.readJob(f[i]));
        }
        
        double time = -1;
        
        switch(solutionNumber){
            case 1:
                time = workOnJobs(jobs, servers, false);
                break;
            case 2:
                time = workOnJobs(jobs, servers, true);
                break;
            case 3:
                time = algo3(jobs, servers);
                break;
        }
        
        if(time >= 0){
            System.out.println("Temps d'exécution de l'algo " + solutionNumber + " : " + time);
        }
    }
    
    public static void rankServers(List<Machine> servers) {
        /*  Est censé classer les serveurs pour pouvoir choisir celui le moins
        *   avancé et le plus efficace.
        */
    }
    
    public static double algo3(List<Job> jobs, List<Machine> servers) {
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
        double now = Double.MAX_VALUE;
        int tasksDone = 0;
        while(!finished) {
            
            int remainingTasksToDo = tasksNumber;
            for(int i = 0; i < tasksNumber; i++) {
                if( ((Task)tab[i][0]).isDone(true) ) {
                    remainingTasksToDo--;
                }
            }
            //System.out.println("Tâches à faire : " + remainingTasksToDo);
            
            double worstTime = 0;
            Task taskToExecute = null;
            for(int i = 0; i < tasksNumber; i++) {
                if( !((Task)tab[i][0]).isDone(true) ) {
                    if( (double)tab[i][1] > worstTime ) {
                        if( ((Task)tab[i][0]).parentsAreDone() ) {
                            taskToExecute = (Task)tab[i][0];
                            worstTime = (double)tab[i][1];
                        }
                    }
                }
            }
            if(taskToExecute == null) { // Si aucune tâche choisie, on a fini
                break;
            }
            // Sinon on choisit la machine sur laquelle on va l'exécuter
            
            now = Double.MAX_VALUE;
            Machine serv = null;
            double tmpTime = Double.MAX_VALUE;
            for(Machine server : Machine.getServerOfType(servers, taskToExecute.getType())) {
                if(server.getMinimumTime() < now) {
                    // Le temps est un peu plus logique, donc il y a bien un souci
                    // sur le "setDoneAt" des tâches
                //if(server.getCurrentTime() < now) {
                    serv = server;
                    now = server.getCurrentTime();
                }
            }
            serv.execute(taskToExecute, taskToExecute.getLastParentDoneAt());
            tasksDone++;
        }
        
        System.out.println("Emploi du temps effectué");

        now = 0;
        for(Machine server : servers) {
            if(server.getCurrentTime() > now) {
                now = server.getCurrentTime();
            }
        }

        return now;
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
        int etape = 0;
        
        boolean isWorkDone = false;
        while(!isWorkDone){
            isWorkDone = true;
            etape++;
            //System.out.println("Etape " + etape);
            
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
                    //System.out.println(task.toString());
                }
            }
            
            for(Machine machine : machines){
                elapsedSeconds = Math.max(elapsedSeconds, machine.getElapsedSeconds());
            }
            
            resetMachines(machines);
            totalElapsedSeconds += elapsedSeconds;
            elapsedSeconds = 0d;
            //System.out.println("Current number of seconds : " + totalElapsedSeconds);
        }
        
        return totalElapsedSeconds;
    }
    
    public static void resetMachines(List<Machine> machines){
        for(Machine machine : machines){
            machine.resetMachine();
        }
    }
    
}

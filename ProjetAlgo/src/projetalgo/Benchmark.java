package projetalgo;

import java.util.List;

public class Benchmark {

    public static double pourcentageUtilProc(Machine server, double fin) {
        return tempsUtilProc(server)/fin;
    }

    public static double tempsUtilProc(Machine server) {
        double tps = 0;
        for(Task t : server.getTasksDone()) {
            tps += server.timeToDo(t);
        }
        return tps;
    }

    public static String tempsUtilProcMoyen(List<Machine> servers) {
        String text = "";
        double fin = 0;
        for(Machine server : servers) {
            text += tempsUtilProc(server);
        }
        return text;
    }

    public static String rapport(List<Machine> servers, List<Job> jobs) {
        // Pourcentage d'utilisation des proc

        // Rapport (ressources moyennes des tâches)/(ressources du proc)

        // Temps d'attente moyen des Jobs

        return "";
    }
}

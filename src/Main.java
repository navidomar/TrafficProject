import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        List<Job> listedTravelTimes = new ArrayList<>();

        //3 hours in seconds (6 AM to 9 AM)
        listedTravelTimes = simulation.run(10800.0);
        System.out.println("\n| E-Bike ride time | Bus ride Time | Train ride time | Metro ride time | Bus stop wait time | Train Station wait time | Metro wait time | Total |");
        for (Job job : listedTravelTimes) {
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println(job.TotalTravelTime());
        }
    }
}
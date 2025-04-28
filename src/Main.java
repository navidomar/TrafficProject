import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        List<Job> listedTravelTimes = new ArrayList<>();

        //3 hours in seconds (6 AM to 9 AM)
        listedTravelTimes = simulation.run(10800.0);

        for (Job job : listedTravelTimes) {
            System.out.println(job.TotalTravelTime());
        }
    }
}
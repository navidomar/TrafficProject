import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Simulation simulation = new Simulation();
        List<Job> listedTravelTimes = new ArrayList<>();

        //3 hours in seconds (6 AM to 9 AM)
        listedTravelTimes = simulation.run(10800.0);

        String file = "simData.csv";
        FileWriter writer = new FileWriter(file);

        for (Job job : listedTravelTimes) {
            System.out.println(job.TotalTravelTime());
            writer.append(job.TotalTravelTime());
            writer.append("\n");
        }


    }
}
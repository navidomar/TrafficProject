import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Simulation simulation = new Simulation();
        List<Commuter> listedTravelTimes = new ArrayList<>();

        //3 hours in seconds (6 AM to 9 AM)
        listedTravelTimes = simulation.run(10800.0);

        String file = "simData.csv";
        FileWriter writer = new FileWriter(file);

        for (Commuter commuter : listedTravelTimes) {
            System.out.println(commuter.TotalTravelTime());
            writer.append(commuter.TotalTravelTime());
            writer.append("\n");
        }


    }
}
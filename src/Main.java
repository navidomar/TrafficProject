public class Main {
    public static void main(String[] args) {
        int numLines = 3;
        double lambda = 1.0;
        double simulationTime = 100.0;

        Simulation simulation = new Simulation(numLines, lambda);

        simulation.run(simulationTime);

        System.out.println("...Simulation Completed.");
    }
}
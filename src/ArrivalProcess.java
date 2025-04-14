public class ArrivalProcess {
    private RandomDistribution arrivalDisribution;
    private double nextArrivalTime;
    public ArrivalProcess(double lambda) {
        this.arrivalDisribution = new ExponentialDistribution(lambda);
        this.nextArrivalTime = arrivalDisribution.run();
    }

    public double getNextArrivalTime() {
        return nextArrivalTime;
    }

    public void generateNextArrival() {
        nextArrivalTime += arrivalDisribution.run();
    }
}


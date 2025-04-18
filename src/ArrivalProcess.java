public class ArrivalProcess {
    private RandomDistribution arrivalDistribution;
    private double nextArrivalTime;
    public ArrivalProcess(double lambda) {
        this.arrivalDistribution = new ExponentialDistribution(lambda);
        this.nextArrivalTime = arrivalDistribution.run();
    }

    public double getNextArrivalTime() {
        return nextArrivalTime;
    }

    public void generateNextArrival() {
        nextArrivalTime += arrivalDistribution.run();
    }
}


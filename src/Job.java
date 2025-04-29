public class Job {
    private double startTime;  //Job start time
    private double eBikeRideTime;  //E-bike travel time to bus stop
    private double busRideTime;   //Bus ride time to train station
    private double trainRideTime; //Train ride time to D.C.
    private double metroRideTime;

    private double busStopWaitTime = 0.0;
    private double trainStationWaitTime = 0.0;
    private double metroWaitTime = 0.0;

    private double metroArrivalTime;

    public Job(double arrivalTime, double eBikeRideTime, double busRideTime, double trainRideTime, double metroRideTime) {
        this.startTime = arrivalTime;
        this.eBikeRideTime = eBikeRideTime;
        this.busRideTime = busRideTime;
        this.trainRideTime = trainRideTime;
        this.metroRideTime = metroRideTime;
    }

    public void setBusStopWaitTime(double currentSimTime) {
        busStopWaitTime = currentSimTime - startTime;
    }

    public void setTrainStationWaitTime(double currentSimTime) {
        trainStationWaitTime = currentSimTime - (busStopWaitTime + startTime);
    }

    public void setMetroArrivalTime(double time) {
        this.metroArrivalTime = time;
    }

    public void setMetroWaitTime(double currentSimTime) {
        metroWaitTime = currentSimTime - metroArrivalTime;
    }

    public String TotalTravelTime() {
        double total = eBikeRideTime + busRideTime + trainRideTime + metroRideTime + busStopWaitTime + trainStationWaitTime + metroWaitTime;
        return eBikeRideTime + " | " + busRideTime + " | " + trainRideTime + " | " + metroRideTime + " | "
                + busStopWaitTime + " | " + trainStationWaitTime + " | " + metroWaitTime + " | " + total + " | ";
    }

}
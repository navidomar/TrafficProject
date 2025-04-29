public class Commuter {
    private double startTime;  //Commuter start time
    private double eBikeRideTime;  //E-bike travel time to bus stop
    private double busRideTime;   //Bus ride time to train station
    private double trainRideTime; //Train ride time to D.C.
    private double metroRideTime; //Metro ride time to work

    private double busStopWaitTime = 0.0;
    private double trainStationWaitTime = 0.0;
    private double metroWaitTime = 0.0;

    public Commuter(double startTime, double eBikeRideTime, double busRideTime, double trainRideTime, double metroRideTime) {
        this.startTime = startTime;
        this.eBikeRideTime = eBikeRideTime;
        this.busRideTime = busRideTime;
        this.trainRideTime = trainRideTime;
        this.metroRideTime = metroRideTime;
    }

    public void setBusStopWaitTime(double currentSimTime) {
        busStopWaitTime = currentSimTime - startTime;
    }

    public void setTrainStationWaitTime(double currentSimTime) {
        trainStationWaitTime = currentSimTime - busStopWaitTime - startTime;
    }

    public void setMetroWaitTime(double currentSimTime) {
        metroWaitTime = currentSimTime - trainStationWaitTime - busStopWaitTime - startTime;
    }

    public String TotalTravelTime() {
        return eBikeRideTime + ", " + busRideTime + ", " + trainRideTime + ", " + metroRideTime + ", " + busStopWaitTime + ", " + trainStationWaitTime + ", " + metroWaitTime + ", " + (eBikeRideTime + busRideTime + trainRideTime + metroRideTime + busStopWaitTime + trainStationWaitTime + metroWaitTime);
    }

}
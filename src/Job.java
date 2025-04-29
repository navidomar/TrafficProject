public class Job {
    private double startTime;  //Job start time
    private double eBikeRideTime;  //E-bike travel time to bus stop
    private double busRideTime;   //Bus ride time to train station
    private double trainRideTime; //Train ride time to D.C.

    private double busStopWaitTime = 0.0;
    private double trainStationWaitTime = 0.0;

    public Job(double startTime, double eBikeRideTime, double busRideTime, double trainRideTime) {
        this.startTime = startTime;
        this.eBikeRideTime = eBikeRideTime;
        this.busRideTime = busRideTime;
        this.trainRideTime = trainRideTime;
    }

    public double getBusRideTime() {
        return busRideTime;
    }

    public void setBusStopWaitTime(double currentSimTime) {
        busStopWaitTime = currentSimTime - startTime;
    }

    public void setTrainStationWaitTime(double currentSimTime) {
        trainStationWaitTime = currentSimTime - (busStopWaitTime + startTime);
    }

    public String TotalTravelTime() {
        return eBikeRideTime + ", " + busRideTime + ", " + trainRideTime + ", " + (busStopWaitTime*46.0) + ", " + trainStationWaitTime + ", " + (eBikeRideTime + busRideTime + trainRideTime + (busStopWaitTime) + (trainStationWaitTime));
    }

}
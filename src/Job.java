public class Job {
    private double startTime;
    private double arrivalTime;
    Stop currentStop;
    Stop destination;
    double distanceFromStop;
    boolean usesEBike;
    double readyAtStopTime;
    String[] journeyStages;
    int stageIndex;

    public Job(double startTime, Stop origin, Stop destination) {

        this.startTime = startTime;
        this.currentStop = origin;
        this.destination = destination;
        this.journeyStages = new String[10];
        this.stageIndex = 0;

        //Some basic test values for now will change later
        this.distanceFromStop = Math.random() * 5;
        this.usesEBike = distanceFromStop > 1.5;
        double travelTime = usesEBike ? (distanceFromStop / 40.0) * 60.0 : 0;
        this.readyAtStopTime = startTime + travelTime;

        if (usesEBike) {
            addStage("Used E-bike for " + String.format("%2f", distanceFromStop)
                    + " miles. Reached bus stop at " + String.format("%2f", readyAtStopTime));
        } else {
            addStage("Walked to bus stop. Arrived immediately at " + startTime);
        }
    }
    public void addStage(String description) {
        if(stageIndex < journeyStages.length) {
            journeyStages[stageIndex++] = description;
        }
    }
    public boolean isReady(double currentTime) {
        return currentTime >= readyAtStopTime;
    }

    public void printJourney() {
        for(int i = 0; i < stageIndex; i++) {
            System.out.println(journeyStages[i]);
        }
    }

    public void setTime(double time1) {
        startTime = time1;
    }
}

public class SingleServerQueue {
    private Queue<Job> jobQueue = new Queue<Job>();
    private Job jobInService = null;
    private double nextEndServiceTime = Double.MAX_VALUE;
    private double lambda = 1;
    private RandomDistribution serviceTimeDistribution = new ExponentialDistribution(lambda);

    public void add(Job job, Double currentTime) {
        if (jobInService == null) {
            jobInService = job;
            nextEndServiceTime = serviceTimeDistribution.run() + currentTime;
        }
        else {
            jobQueue.enqueue(job);
            job.setTime(currentTime);
        }
    }

    public double getEndServiceTime() {
        if (jobInService == null) {
            nextEndServiceTime = Double.MAX_VALUE;
        }
        return nextEndServiceTime;
    }

    public void complete(double currentTime) {
        jobQueue.dequeue().setTime(currentTime);
        if (jobQueue != null) {
            nextEndServiceTime = serviceTimeDistribution.run() + currentTime;
        }
    }

    /*public static void doUnitTests() {
        int testCount = 0;
        int failCount = 0;
        SingleServerQueue testQueue = new SingleServerQueue();
        Job testJob = new Job(0);
        Job testJob2 = new Job(0);
        double initTime = testQueue.getEndServiceTime();
        testQueue.add(testJob, 1.2);
        double finalTime = testQueue.getEndServiceTime();

        if (initTime > finalTime) System.out.println("Add and getEndServiceTime work");   //Compares what should be max value to service time after adding a job
        else {
            System.out.println("Add and getEndServiceTime failed");
            failCount++;
        }
        testCount++;

        testQueue.add(testJob2, 274.7);
        double initTime2 = testQueue.getEndServiceTime();
        testQueue.complete(376.2);
        double finalTime2 = testQueue.getEndServiceTime();

        if (finalTime2 > initTime2) System.out.println("Complete works");   //Compares times of testJob2 added to its EndServiceTime from the complete function
        else {
            System.out.println("Complete failed");
            failCount++;
        }
        testCount++;
        System.out.println("\nSingleServerQueue class tests run: " + testCount + ", failed: " + failCount + "\n");
    }*/

}

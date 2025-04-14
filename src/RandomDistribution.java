abstract class RandomDistribution {
    abstract double run();

    public static void doUnitTests() {
        int testCount = 0;
        int failCount = 0;
        RandomDistribution a = new ExponentialDistribution(2.35);
        RandomDistribution b = new NormalDistribution(5.01, 1.5);

        if (a.run() > 0) System.out.println("Exponential Distribution Works");
        else {
            System.out.println("Exponential Distribution failed");
            failCount++;
        }
        testCount++;
        System.out.println("Exponential Sample: " + a.run());

        if (b.run() > 0) System.out.println("Normal Distribution Works");
        else {
            System.out.println("Normal Distribution failed");
            failCount++;
        }
        testCount++;
        System.out.println("Normal Sample: " + b.run());
        System.out.println("\nRandomDistribution class tests run: " + testCount + ", failed: " + failCount + "\n");
    }

}

class ExponentialDistribution extends RandomDistribution {
    private double lambda;

    public ExponentialDistribution(double lambda) {
        this.lambda=lambda;
    }

    @Override
    double run() {
        double y = Math.random();
        double x = -(1/lambda)*Math.log(y);   //Exponential Distribution formula
        return x;
    }
}

class NormalDistribution extends RandomDistribution {
    private double mu;
    private double sigma;

    public NormalDistribution(double mu, double sigma) {
        this.mu=mu;
        this.sigma=sigma;
    }

    @Override
    double run() {
        double sumOfRandom = 0.0;
        int n = 30;
        for (int i = 0; i < n; i++) {
            double random = Math.random();
            sumOfRandom = sumOfRandom+random;
        }
        double z = (sumOfRandom-(n/2))/(Math.sqrt(n/12));   // z is an approximation of the normal distribution formula
        return mu + sigma * z;
    }
}


import java.util.LinkedList;
import java.util.Queue;
public class Stop {
    String name;
    Queue<Job> queue;

    public Stop(String name) {
        this.name = name;
        this.queue = new LinkedList<>();
    }

    public void addJob(Job job){
        queue.add(job);
    }
    public void serve(double currentTime) {
        int capacity = 30;
        int boarded = 0;

        while (!queue.isEmpty() && boarded < capacity) {
            Job job = queue.poll();
            job.addStage("Boarded robovan at " + currentTime + " at " + name);
            boarded++;
        }
    }

}

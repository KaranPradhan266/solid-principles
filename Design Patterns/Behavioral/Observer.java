import java.util.ArrayList;
import java.util.List;

interface FitnessDataObserver { 
    void update(int steps, int cal);
}

interface FitnessDataSubjects {
    void subscribe(FitnessDataObserver fitnessDataObserver);
    void unsubscribe(FitnessDataObserver fitnessDataObserver);
    void notifySub();
}

class FitnessData implements FitnessDataSubjects {
    private List<FitnessDataObserver> subscribers = new ArrayList<>();
    private int currentSteps;
    private int currentCal;

    public FitnessData() {
    }

    public void setMeasurements(int steps, int cal) {
        this.currentSteps = steps;
        this.currentCal = cal;
        notifySub();
    }

    @Override
    public void subscribe(FitnessDataObserver fitnessDataObserver){
        subscribers.add(fitnessDataObserver);
    }

    @Override
    public void unsubscribe(FitnessDataObserver fitnessDataObserver){
        subscribers.remove(fitnessDataObserver);
    }

    @Override
    public void notifySub() {
        for (FitnessDataObserver subscriber : subscribers) {
            subscriber.update(currentSteps, currentCal);
        }
    }
}

class LiveActivityDisplay implements FitnessDataObserver {
    private int steps = 0;
    private int cal = 0;

    @Override
    public void update(int steps, int cal) {
        this.steps = steps;
        this.cal = cal;
        System.out.println("LiveActivityDisplay: steps: " + this.steps + " calories burned: " + this.cal);
    }
}

// New Concrete Observer 1: DatabaseUpdater
class DatabaseUpdater implements FitnessDataObserver {
    @Override
    public void update(int steps, int cal) {
        System.out.println("DatabaseUpdater: Updating database with Steps: " + steps + ", Calories: " + cal);
        // Simulate database update logic here
    }
}

// New Concrete Observer 2: GoalTracker
class GoalTracker implements FitnessDataObserver {
    private int stepGoal;

    public GoalTracker(int stepGoal) {
        this.stepGoal = stepGoal;
    }

    @Override
    public void update(int steps, int cal) {
        System.out.println("GoalTracker: Current Steps: " + steps + ", Goal: " + stepGoal);
        if (steps >= stepGoal) {
            System.out.println("GoalTracker: Congratulations! Step goal of " + stepGoal + " reached!");
        } else {
            System.out.println("GoalTracker: Keep going! " + (stepGoal - steps) + " steps remaining.");
        }
    }
}

public class Observer {
    public static void main(String[] args) {
        FitnessData fitnessDataSubjects = new FitnessData();

        FitnessDataObserver liveActivityDisplay = new LiveActivityDisplay();
        fitnessDataSubjects.subscribe(liveActivityDisplay);

        // New observers
        FitnessDataObserver databaseUpdater = new DatabaseUpdater();
        fitnessDataSubjects.subscribe(databaseUpdater);

        FitnessDataObserver goalTracker = new GoalTracker(5000); // Set a goal
        fitnessDataSubjects.subscribe(goalTracker);

        System.out.println("--- First Update ---");
        fitnessDataSubjects.setMeasurements(1000, 250);

        System.out.println("\n--- Second Update ---");
        fitnessDataSubjects.setMeasurements(2000, 500);

        fitnessDataSubjects.unsubscribe(goalTracker);
        
        System.out.println("\n--- Third Update (Goal Reached) ---");
        fitnessDataSubjects.setMeasurements(5500, 1200);

    }
}
import java.util.ArrayList;

/**
 * @author Nathan Appleby
 * @author Zackary Wake
 * @author Ella Cronk
 */

public class Project {
    // The state the project is in
    private State status;

    private String name;

    // The pattern being created
    private String pattern;

    // The yarn being used
    private ArrayList<Yarn> yarnNeeded;

    public Project(State status, String pattern, ArrayList<Yarn> yarnNeeded, String name){
        this.status = status;
        this.pattern = pattern;
        this.yarnNeeded = yarnNeeded;
        this.name = name;
    }

    /**
     * Changes the status of the project.
     * All error checking for state is done before method is called
     * @param p the project to be moved
     * @param status the desired state
     */
    public void changeProjectStatus(Project p, State status){
        if(status == State.inQueue){
            p.status = State.inQueue;
        }
        else if(status == State.WIP){
            p.status = State.WIP;
        }
        else if(status == State.complete){
            p.status = State.complete;
        }
    }

    public State getStatus() {
        return status;
    }

    public String getPattern() {
        return pattern;
    }

    public ArrayList<Yarn> getYarn() {
        return yarnNeeded;
    }

    public String getName(){ return name;}

    public String toString(){ return name + ", pattern link: " + pattern + ", yarn needed: " + yarnNeeded.toString() + ", status: " + status + "\n"; }
}

import java.util.ArrayList;

/**
 * @author Nathan Appleby
 * @author Zackary Wake
 * @author Ella Cronk
 */

public class Project {
    // The state the project is in
    private State status;

    // The pattern being created
    private Pattern pattern;

    // The yarn being used
    private Yarn yarn;

    public Project(State status, Pattern pattern, Yarn yarn){
        this.status = status;
        this.pattern = pattern;
        this.yarn = yarn;
    }

    public ArrayList<Project> projectList = new ArrayList<>();

    /**
     * Adds project to arraylist
     * @param p project to be added
     */
    public void addProject(Project p){
        projectList.add(p);
    }

    /**
     * Removes project from arraylist (not moving queue, removes from all queues totally)
     * @param p project to be deleted
     */
    public void removeProject(Project p){
        projectList.remove(p);
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

    public Pattern getPattern() {
        return pattern;
    }

    public Yarn getYarn() {
        return yarn;
    }
}

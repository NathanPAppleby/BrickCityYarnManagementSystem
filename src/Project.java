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

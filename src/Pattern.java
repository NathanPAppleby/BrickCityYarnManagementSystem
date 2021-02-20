/**
 * @author Nathan Appleby
 * @author Zackary Wake
 * @author Ella Cronk
 */

public class Pattern {
    // The name of the pattern
    private String patternName;

    // The link to the pattern
    private String link;

    public Pattern(String patternName, String link){
        this.patternName = patternName;
        this.link = link;
    }

    /**
     * Get the name of the pattern
     * @return the pattern's name
     */
    public String getPatternName() {
        return patternName;
    }


    /**
     * Get the link of the pattern
     * @return the pattern's link
     */
    public String getLink() {
        return link;
    }
}

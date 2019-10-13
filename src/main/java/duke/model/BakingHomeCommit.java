package duke.model;

/**
 * Represents a commit, including a BakingHome state and a commit message.
 */
public class BakingHomeCommit {
    /**
     * The state of BakingHome to commit.
     */
    public final ReadOnlyBakingHome bakingHome;

    /**
     * A message describing the details of the commit.
     */
    public final String commitMessage;

    /**
     * Creates a BakingHome commit.
     */
    public BakingHomeCommit(ReadOnlyBakingHome bakingHome, String commitMessage) {
        this.bakingHome = bakingHome;
        this.commitMessage = commitMessage;
    }

}

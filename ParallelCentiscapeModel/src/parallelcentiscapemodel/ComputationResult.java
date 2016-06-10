
package parallelcentiscapemodel;

import java.util.Vector;

/**
 * A simple result aggregation class: SP vector and root ID.
 * @author Ivan
 */
public class ComputationResult {
    private final int root;
    private final Vector result;

    public ComputationResult(int root, Vector result) {
        this.root = root;
        this.result = result;
    }

    public int getRoot() {
        return root;
    }

    public Vector getResult() {
        return result;
    }    
}

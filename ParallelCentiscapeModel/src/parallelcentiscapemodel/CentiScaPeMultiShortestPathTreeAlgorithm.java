package parallelcentiscapemodel;

import java.util.Vector;

/**
 *
 * @author Ivan
 */
public class CentiScaPeMultiShortestPathTreeAlgorithm {

    private final int root;

    public CentiScaPeMultiShortestPathTreeAlgorithm(int root /*and some other args*/) {
        this.root = root;
        // System.out.println("Created " + root);
    }

    public ComputationResult execute() {
        Vector v = new Vector();

        System.out.println("Started " + root);
        // ShortestPathTree calculation simulation
        for (int i = 0; i < 10; i++) {
            v.add(i + root);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }

        System.out.println("Done " + root);

        return new ComputationResult(root, v);
    }
}

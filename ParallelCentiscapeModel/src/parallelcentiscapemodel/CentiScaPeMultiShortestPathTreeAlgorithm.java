package parallelcentiscapemodel;

import java.util.Vector;

/**
 *
 * @author Ivan
 */
public class CentiScaPeMultiShortestPathTreeAlgorithm {

    private final int root;

    public CentiScaPeMultiShortestPathTreeAlgorithm(int root) {
        this.root = root;
        // System.out.println("Created " + root);
    }

    public ComputationResult execute() throws InterruptedException {
        // ShortestPathTree calculation simulation
        
        System.out.println("Started " + root);
        
        Vector v = new Vector();
        for (int i = 0; i < 10; i++) {
            v.add(i + root);
        }
        Thread.sleep((int) (Math.random() * 5000));

        return new ComputationResult(root, v);
    }
}

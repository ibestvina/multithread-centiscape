package parallelcentiscapemodel;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 *
 * @author Ivan
 */
public class Program {

    static final int THREADFACTOR = 1;
    static int numOfNodes = 10;
    

    public static void main(String[] args) {
        
        

        // mock-up network
        List<Integer> nodes = new LinkedList<>();
        for (int i = 0; i < numOfNodes; i++) {
            nodes.add(i);
        }

        ExecuteCentiScaPeAlgorithm(nodes);
    }

    private static void ExecuteCentiScaPeAlgorithm(List<Integer> nodes) {
        long timeStarted = System.currentTimeMillis();

        // get the number of procesors available
        int numProc = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of processors: " + numProc);

        // number of threads used
        int numThreads = numProc * THREADFACTOR;
        System.out.println("Number of threads: " + numThreads + "\n");


        // construct threadpool
        // note: newCachedThreadPool may also be used
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // list of CompletableFutures - one per each root node
        List<CompletableFuture<ComputationResult>> futureResults = new LinkedList<>();

        // initiate and execute SP algorithms from each node
        for (Integer root : nodes) {
            CompletableFuture<ComputationResult> futureResult = CompletableFuture.supplyAsync(() -> {
                // new instance of algorithm for this root
                CentiScaPeMultiShortestPathTreeAlgorithm SPalg = new CentiScaPeMultiShortestPathTreeAlgorithm(root);
                // execute
                return SPalg.execute();
            }, executor);
            futureResults.add(futureResult);
        }

        System.out.println("Initialization time " + (System.currentTimeMillis() - timeStarted) + " ms\n");

        /**
         * here, other work can be called also, while SPs are calculated
         */
        
        
        // result retrieval 
        timeStarted = System.currentTimeMillis();
        
        List<ComputationResult> results = new LinkedList<>();

        try {
            for (CompletableFuture<ComputationResult> futureResult : futureResults) {
                ComputationResult result = futureResult.get();
                System.out.println("Finished " + result.getRoot());
                results.add(result);
                // here, GUI progress bar notifications should be made
            }
        } catch (InterruptedException | ExecutionException ex) {
            // exceptions from the future should be handled here
        }
        
        executor.shutdown();

        System.out.println("\n\nTotal time: " + (System.currentTimeMillis() - timeStarted) + " milliseconds");
        System.out.println("Number of nodes: " + numOfNodes + "\n" + "Num of results: " + results.size());
    }

}

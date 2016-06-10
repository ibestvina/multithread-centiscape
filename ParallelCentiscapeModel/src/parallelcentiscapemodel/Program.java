package parallelcentiscapemodel;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ivan
 */
public class Program {

    static final int THREADFACTOR = 1;
    static int numOfNodes = 6;
    
    private static ExecutorService executor;
    
    private static boolean stop;
    

    public static void main(String[] args) {
        // mock-up network
        List<Integer> nodes = new LinkedList<>();
        for (int i = 0; i < numOfNodes; i++) {
            nodes.add(i);
        }

        ExecuteCentiScaPeAlgorithm(nodes);
    }

    private static void ExecuteCentiScaPeAlgorithm(List<Integer> nodes) {
        stop = false;
        
        long timeStarted = System.currentTimeMillis();

        // get the number of procesors available
        int numProc = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of processors: " + numProc);

        // number of threads used
        int numThreads = numProc * THREADFACTOR;
        System.out.println("Number of threads: " + numThreads + "\n");


        // construct threadpool
        // note: newCachedThreadPool may also be used
        executor = Executors.newFixedThreadPool(numThreads);

        // list of CompletableFutures - one per each root node
        List<CompletableFuture<ComputationResult>> futureResults = new LinkedList<>();

        // initiate and execute SP algorithms from each node
        for (Integer root : nodes) {
            CompletableFuture<ComputationResult> futureResult = CompletableFuture.supplyAsync(() -> {
                try {
                    // new instance of algorithm for this root
                    CentiScaPeMultiShortestPathTreeAlgorithm SPalg = new CentiScaPeMultiShortestPathTreeAlgorithm(root);
                    // execute
                    return SPalg.execute();
                } catch (InterruptedException e) {
                    System.out.println("Execution of SPTreeAlg interrupted");
                    return null;
                }
            }, executor);
            futureResults.add(futureResult);
        }

        System.out.println("Initialization time " + (System.currentTimeMillis() - timeStarted) + " ms\n");

        
        /**
         * So far, tasks (one per each node) have been supplied to the executor 
         * which runs them using the threads from the thread pool.
         * Next part of the code finds the tasks that have finished and processes 
         * their results. Once all of the tasks are done, the executor is shut down.
         */
        
        
        timeStarted = System.currentTimeMillis();
        
        List<ComputationResult> results = new LinkedList<>();

        
        // cycle through the list of futureResults, looking for the finished ones
        
        while(!stop && !futureResults.isEmpty()){
            CompletableFuture<ComputationResult> futureResult = futureResults.remove(0);
            
            // if any root task has finished unexpectedly, cancel the whole algorithm.
            if(futureResult.isCompletedExceptionally()){
                cancel();
                return;
            }
            // isDone checks if it has completed in any fashion, but if previous statement is false, then regular outcome is the only option left
            if(futureResult.isDone()){
                ComputationResult result;
                try { 
                    result = futureResult.get(); // get the result of the computation
                } catch (InterruptedException | ExecutionException ex) {
                    System.out.println(ex);
                    cancel();
                    return;
                }
                
                /**
                 * Result processing goes here!
                 */
                System.out.println("Finished " + result.getRoot());
                results.add(result);
                
            } else{
                futureResults.add(futureResult);  // if not yet done, return futureResult to the end of the list
            }
        }
        
        // shutdown all the threads from the pool
        executor.shutdown();

        System.out.println("\n\nTotal time: " + (System.currentTimeMillis() - timeStarted) + " milliseconds");
        System.out.println("Number of nodes: " + numOfNodes + "\n" + "Num of results: " + results.size());
        
        /**
         * Centrality calculation goes here.
         */
    }
    

    private static void cancel(){
        System.out.println("Canceling");
        stop = true;
        if(executor != null) executor.shutdownNow();
    }

}

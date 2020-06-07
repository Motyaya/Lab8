import java.util.*;


public class URLPool {
    private int maxDepth;


    private int waitCount = 0;

    private LinkedList<URLDepthPair> pendingURLs;

    private LinkedList<URLDepthPair> processedURLs;

    private HashSet<String> seenURLs; // Only visit new URLs


    public URLPool(int max) {
        pendingURLs = new LinkedList<URLDepthPair>();
        processedURLs = new LinkedList<URLDepthPair>();
        seenURLs = new HashSet<String>();

        maxDepth = max;
    }

    public synchronized int getWaitCount() {
        return waitCount;
    }

    public synchronized void add(URLDepthPair nextPair) {
        String newURL = nextPair.getURL().toString();

        String trimURL = (newURL.endsWith("/")) ? newURL.substring(0, newURL.length() -1) : newURL;
        if (seenURLs.contains(trimURL)){
            return;
        }
        seenURLs.add(trimURL);

        if (nextPair.getDepth() < maxDepth) {
            pendingURLs.add(nextPair);
            notify();
        }
        processedURLs.add(nextPair);
    }

    public synchronized URLDepthPair get() {
        while (pendingURLs.size() == 0) {
            waitCount++;
            try {
                wait();
            }
            catch (InterruptedException e) {
                System.out.println("Ignoring unexpected InterruptedException - " +
                        e.getMessage());
            }
            waitCount--;
        }

        return pendingURLs.removeFirst();
    }


    public synchronized void printURLs() {
        System.out.println("\nUnique URLs Found: " + processedURLs.size());
        while (!processedURLs.isEmpty()) {
            System.out.println(processedURLs.removeFirst());
        }
    }
}
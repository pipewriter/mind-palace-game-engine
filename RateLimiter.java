public class RateLimiter {
    private int availablePermits = 5;
    private final long resetPeriod = 60000; // 60 seconds
    private long nextResetTime = System.currentTimeMillis() + resetPeriod;

    public synchronized void sendRequest(Runnable request) throws InterruptedException {
        // Block while there are no permits available or the current time is past the reset time
        while (availablePermits <= 0 || System.currentTimeMillis() < nextResetTime) {
            // Wait for a notify call or until the next reset time
            long waitTime = nextResetTime - System.currentTimeMillis();
            if (waitTime > 0) {
                wait(waitTime);
            } else {
                resetPermits();
            }
        }

        // Decrement the permit count and run the request
        availablePermits--;
        request.run();
    }

    private synchronized void resetPermits() {
        // Reset the available permits and update the next reset time
        availablePermits = 5;
        nextResetTime = System.currentTimeMillis() + resetPeriod;
        // Notify all waiting threads that permits have been reset
        notifyAll();
    }

    public static void main(String[] args) {
        RateLimiter rateLimiter = new RateLimiter();

        // Example usage
        for (int i = 0; i < 10; i++) {
            final int requestId = i;
            new Thread(() -> {
                try {
                    rateLimiter.sendRequest(() -> {
                        System.out.println("Executing request " + requestId + " at " + System.currentTimeMillis());
                        // Simulate request execution
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}
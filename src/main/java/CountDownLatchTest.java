import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchTest {

    static class Worker implements Runnable {
        private final CountDownLatch doneSignal;
        private final int i;

        public Worker(CountDownLatch doneSignal, int i) {
            this.doneSignal = doneSignal;
            this.i = i;
        }

        @Override
        public void run() {
            System.out.println("now is " + i);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            doneSignal.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int N = 10;
        CountDownLatch countDownLatch = new CountDownLatch(100);
        ExecutorService executor = Executors.newFixedThreadPool(N);
        for (int i = 0; i < 100; i++)
            executor.execute(new Worker(countDownLatch, i));

        while (countDownLatch.getCount() != 0) {
            System.out.println(countDownLatch.getCount());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        System.out.println("over");
    }
}
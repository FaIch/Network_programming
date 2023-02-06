import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Worker extends Thread{

    List<Runnable> tasks;
    Lock tasksLock = new ReentrantLock();
    Condition tasksCondition = tasksLock.newCondition();
    boolean bool = false;
    int numberOfThreads;
    List<Thread> workerThreads;

    public Worker(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        workerThreads = new ArrayList<>();
        tasks = new ArrayList<>();
        if (numberOfThreads == 1){
            System.out.println("Starting event loop: \n");
        }
        else{
            System.out.println("Starting worker threads: \n");
        }
    }

    void postTasks() {
        tasksLock.lock();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            post(() -> System.out.println("Task " + finalI + " runs in thread " + currentThread().getId()));
        }
        bool = true;
        tasksCondition.signal();
        tasksLock.unlock();
    }


    void post(Runnable task){
        tasksLock.lock();
        tasks.add(task);
        bool = true;
        tasksCondition.signal();
        tasksLock.unlock();
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfThreads; i++) {
            workerThreads.add(new Thread(() -> {
                while (bool) {
                    Runnable task = null;
                    {
                        tasksLock.lock();
                        long startTime = System.currentTimeMillis();

                        if (!tasks.isEmpty()) {
                            task = tasks.remove(0);
                        }
                        while (tasks.isEmpty()){
                            try {
                                tasksCondition.await(1, TimeUnit.SECONDS);
                            } catch (InterruptedException e) {
                                // Interrupted, break out of loop
                                break;
                            }
                            if (System.currentTimeMillis() - startTime > 5_000) break;
                        }
                        tasksLock.unlock();
                    }
                    if (task != null) {
                        post_timeout(task);
                    }
                    else if (tasks.isEmpty() && bool){
                        stopRunning();
                    }
                }
            }));
        }
        bool = true;
        for (Thread workerThread : workerThreads) {
            workerThread.start();
        }
        for (Thread workerThread : workerThreads) {
            try {
                workerThread.join();
            } catch (InterruptedException e) {
                // Interrupted, break out of loop
                break;
            }
        }
    }

    void stopRunning(){
        tasksLock.lock();
        bool = false;
        tasksCondition.signalAll();
        if (numberOfThreads == 1){
            System.out.println("Shutting down event loop \n");
        }
        else{
            System.out.println("Shutting down worker threads \n");
        }
        tasksLock.unlock();
    }

    void post_timeout(Runnable task) {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            task.run();
        }).start();
    }

    public static void main(String[] args) {
        Worker workers = new Worker(4);
        workers.start();
        workers.postTasks();

        try{
            workers.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Worker event_loop = new Worker(1);
        event_loop.start();
        event_loop.postTasks();


        try{
            event_loop.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

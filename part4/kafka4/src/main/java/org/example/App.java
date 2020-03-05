package org.example;

import java.util.Arrays;
import java.util.List;

public class App
{
    public static void main( String[] args ) throws InterruptedException {
        List<Thread> threads = Arrays.asList(
                new ProducerThread(),
                new ConsumerThread()
        );
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
    }

}

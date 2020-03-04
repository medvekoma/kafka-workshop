package org.example;

import java.io.IOException;
import java.lang.management.ThreadInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

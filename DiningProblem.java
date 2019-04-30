package Dine4_v1;

import java.util.concurrent.Semaphore;


public class DiningProblem {

    private static final int n = 5;

    public static void main(String[] args) {
        Semaphore[] chopsticks = new Semaphore[n];

        for (int i = 0; i < n; i++) {
            chopsticks[i] = new Semaphore(1);
        }

        Philosopher[] philosophers = new Philosopher[n];

        for (int i = 0; i < n; i++) {
            philosophers[i] = new Philosopher(i, chopsticks[i], chopsticks[(i + 1) % n]);
            new Thread(philosophers[i]).start();
        }

    }

}


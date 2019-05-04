# Dinning Philosopher Problem with Semaphore and Threads.
Semaphores are integer variables that are used to solve the critical section
problem by using two atomic operations, wait and signal that are used for
process synchronization.
The definitions of wait and signal are as follows:
1. Wait- The wait operation decrements the value of its argument s, if it is
positive. If s is negative or zero, then no operation is performed.
``` 
sem_wait(Semaphore s){
while(s<=0);
s--;
} 
```
2. Signal- The signal operation increments the value of its argument S.
```
sem_signal(Semaphore s){
s++;
}
```

A critical section is surrounded by both operations to implement
process synchronization as below.
```
Semaphore s=new Semaphore(1) // initialize the semaphore variable
to 1
sem_wait(s);
//Critical Section here
sem_post(s);
```
When one process running the critical section another process cannot access
the critical section until the sem_post runs and make the variable s equal to 1.

### Dinning Philosopher Problem-
The Dining Philosopher Problem states that K philosophers seated around a
circular table with one chopstick between each pair of philosophers. There is
one chopstick between each philosopher. A philosopher may eat if he can
pickup the two chopsticks adjacent to him. One chopstick may be picked upby any one of its adjacent followers but not both.
Dinning Philosopher Problem Solution
#### Algorithm:
```
repeat
wait(chopstick[i]);
wait(chopstick[(i+1) mod 5]);
. . .
eat
. . .
signal(chopstick[i]);
signal(chopstick[(i+1) mod 5]);
. . .
think
. . .
until false;
```
#### Source Code:
Make a Thread class named Philosopher
```
class Philosopher implements Runnable {
private int id;
private int amount=0;
// The chopsticks this philosopher may use
private Semaphore leftChopstick;
private Semaphore rightChopstick;
public Philosopher(int id, Semaphore leftChopstick, Semaphore
rightChopstick) {
this.id = id;
this.leftChopstick = leftChopstick;
this.rightChopstick = rightChopstick;
}
public void run() {//...
}
//other codes here
}
```
Make variables for Philosopher class. Make a constructor and initialize the
variables.
Create a method for thinking.
```
private void think() throws InterruptedException {
System.out.println("Philosopher " + id + " is thinking.\n");
System.out.flush();
Thread.sleep(new Random().nextInt(10));
}
```
sleep the think method for a random amount of time.
Then create methods for Picking up the left chopstick and picking up the right
chopstick.
Initially all the semaphore variable values equal to 1. When semaphore variable
is 1 philosopher can take the chopstick. Then lock the chopstick by acquire
method in semaphore class. That lock can be removed by calling the method
release in Semaphore class
If semaphore variable is 0 philosopher can not take the chopstick so that the
process will wait for a stick.
```
private void pickUpLeftChopstick() throws InterruptedException{
if(leftChopstick.availablePermits() ==0){
System.out.println("Philosopher " +id +" is waiting for left
chopstick");
}
leftChopstick.acquire();
System.out.println("Philosopher " + id + " is holding left
chopstick.\n");
System.out.flush();
}
private void pickUpRightChopstick() throws InterruptedException{
if(rightChopstick.availablePermits() ==0){
System.out.println("Philosopher " +id +" is waiting for rightchopstick");
}
rightChopstick.acquire();
System.out.println("Philosopher " + id + " is holding
chopstick.\n");
System.out.flush();
}
```
right
Release the locks for chopsticks in the putDownChopstick method
```
private void putDownChopsticks() {
leftChopstick.release();
rightChopstick.release();
System.out.println("Philosopher " + id +" ate "+amount+"% and"+"
released left and right sticks \n");
}
```
Create a method for eating
```
private void eat() throws InterruptedException {
System.out.println("Philosopher " + id + " is eating.\n");
System.out.flush();
amount+=20;
Thread.sleep(new Random().nextInt(10));
}
```
I Assumed that each of the philosopher ate 20% of their meal in each thread.
Therefore when eats amount increment by 20.
Now implement the class with the main method to run the program
```
public class DiningProblem {
private static final int n = 5;
public static void main(String[] args) {
Semaphore[] chopsticks = new Semaphore[n];
for (int i = 0; i < n; i++) {
chopsticks[i] = new Semaphore(1);
}
Philosopher[] philosophers = new Philosopher[n];
for (int i = 0; i < n; i++) {
philosophers[i] = new Philosopher(i, chopsticks[i],
chopsticks[(i + 1) % n]);
new Thread(philosophers[i]).start();}
}
}
```
n is the number of philosophers. In the main method make an array of
semaphore variables for each thread and initialize all of them to 1. Then make an
array of philosophers and initialize with the id, chopstick at the left, chopstick at
the right . Start running the each philosopher thread class.
Here is a part of the output of the above code:
```
Philosopher 0 is thinking.
Philosopher 1 is thinking.
Philosopher 2 is thinking.
Philosopher 2 is holding left chopstick.
Philosopher 3 is thinking.
Philosopher 2 is holding right chopstick.
Philosopher 2 is eating.
Philosopher 4 is thinking.
Philosopher 3 is waiting for left chopstick
Philosopher 1 is holding left chopstick.
Philosopher 0 is holding left chopstick.
Philosopher 1 is waiting for right chopstick
Philosopher 0 is waiting for right chopstick
Philosopher 2 ate 20% and released left and right sticks ...
```



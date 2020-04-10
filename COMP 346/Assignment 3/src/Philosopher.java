import java.util.Random;

import common.BaseThread;

/**
 * Class Philosopher. Outlines main subrutines of our virtual philosopher.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Philosopher extends BaseThread {
	/**
	 * Max time an action can take (in milliseconds)
	 */
	public static final long TIME_TO_WASTE = 1000;

	/**
	 * The act of eating. 
	 * - Print the fact that a given phil (their TID) has started eating. 
	 * - yield 
	 * - Then sleep() for a random interval. 
	 * - yield - The print that they are done eating.
	 */
	public void eat() {
		try {
			System.out.printf("Philosopher %d: is to eat\n", getTID());
			yield();
			sleep((long) (Math.random() * TIME_TO_WASTE));
			yield();
			System.out.printf("Philosopher %d: is done eating\n", getTID());
		} catch (InterruptedException e) {
			System.err.println("Philosopher.eat():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	/**
	 * The act of thinking. - Print the fact that a given phil (their TID) has
	 * started thinking. - yield - Then sleep() for a random interval. - yield - The
	 * print that they are done thinking.
	 */
	public void think() {
		try {
			System.out.printf("Philosopher %d: is thinking\n", getTID());
			yield();
			sleep((long) (Math.random() * TIME_TO_WASTE));
			yield();
			System.out.printf("Philosopher %d: is done think\n", getTID());
		} catch (InterruptedException e) {
			System.err.println("Philosopher.think():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	/**
	 * The act of talking. - Print the fact that a given phil (their TID) has
	 * started talking. - yield - Say something brilliant at random - yield - The
	 * print that they are done talking.
	 */
	public void talk() {

		System.out.printf("Philosopher %d: wants talking\n", getTID());
		yield();
		saySomething();
		yield();
		System.out.printf("Philosopher %d: has been silenced\n", getTID());

	}

	/**
	 * No, this is not the act of running, just the overridden Thread.run()
	 */
	public void run() {
		for (int i = 0; i < DiningPhilosophers.DINING_STEPS; i++) {
		//for (int i = 0; i < 1; i++) { //Running just once for testing
			DiningPhilosophers.soMonitor.pickUp(getTID());
			eat();
			DiningPhilosophers.soMonitor.putDown(getTID());

			think();
			
			Random rand = new Random();
			if(rand.nextInt(10) < 5){
			//if (true){
					DiningPhilosophers.soMonitor.iWantToTalk(getTID());
					DiningPhilosophers.soMonitor.requestTalk();
					talk();
					DiningPhilosophers.soMonitor.endTalk();
					DiningPhilosophers.soMonitor.iDontWantToTalk(getTID());
			}

			yield();
			System.out.println();
		}
	} // run()

	/**
	 * Prints out a phrase from the array of phrases at random. Feel free to add
	 * your own phrases.
	 */
	public void saySomething() {
		String[] astrPhrases = { "Eh, it's not easy to be a philosopher: eat, think, talk, eat...",
				"You know, true is false and false is true if you think of it",
				"2 + 2 = 5 for extremely large values of 2...", "If thee cannot speak, thee must be silent",
				"My number is " + getTID() + "" };

		System.out.println(
				"Philosopher " + getTID() + " says: " + astrPhrases[(int) (Math.random() * astrPhrases.length)]);
	}
}

// EOF

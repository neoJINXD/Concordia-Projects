/**
 * Class Monitor To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor {
	/*
	 * ------------ Data members ------------
	 */
	enum STATE {
		THINK, EATING, TALKING, WANTTALK
	}

	STATE[] states;
	int nChop; // number of chopstick aka number of philosophers

	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers) {
		nChop = piNumberOfPhilosophers;
		states = new STATE[piNumberOfPhilosophers];
		for (int i = 0; i < piNumberOfPhilosophers; i++) {
			states[i] = STATE.THINK;
		}

	}

	/*
	 * ------------------------------- User-defined monitor procedures
	 * -------------------------------
	 */

	public synchronized void iWantToTalk(int i) {
		states[i - 1] = STATE.WANTTALK;
	}

	public synchronized void iDontWantToTalk(int i) {
		states[i - 1] = STATE.THINK;
	}

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID) {

		System.out.printf("Philosopher %d: wants to take the chopsticks\n", piTID);

		// Waits if either of the philosopher's neighbours are eating, meaning the
		// chopsticks are not available
		while (states[piTID % states.length] == STATE.EATING
				|| states[(piTID + states.length - 2) % states.length] == STATE.EATING) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println(e);
				System.exit(404);
			}
		}
		// Sets current state to eating, meaning the philosopher is now going to pick up
		// the chopsticks
		states[piTID - 1] = STATE.EATING;

		System.out.printf("Philosopher %d: has taken the chopsticks\n", piTID);
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down and
	 * let others know they are available.
	 */
	public synchronized void putDown(final int piTID) {
		System.out.printf("Philosopher %d: is putting away the chopsticks\n", piTID);
		// Resets the philosopher's state to thinking and lets everyone waiting know
		// that chopsticks are available
		int i = piTID - 1;
		states[i] = STATE.THINK;
		notifyAll();
	}

	/**
	 * Only one philopher at a time is allowed to philosophy (while she is not
	 * eating).
	 */
	public synchronized void requestTalk() {
		// Checks with every other philosopher, and only allows talking if no one else
		// is currently talking
		for (int i = 0; i < nChop; i++) {
			if (states[i] == STATE.TALKING) {
				try {
					wait();
				} catch (InterruptedException e) {
					System.err.println(e);
				}
			}
			if (states[i] == STATE.WANTTALK)
				states[i] = STATE.TALKING;
		}

	}

	/**
	 * When one philosopher is done talking stuff, others can feel free to start
	 * talking.
	 */
	public synchronized void endTalk() {
		// Let's the others know that someonn has stopped talking
		notifyAll();
	}
}

// EOF

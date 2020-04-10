/**
 * Class DiningPhilosophers The main starter.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class DiningPhilosophers {
	/*
	 * ------------ Data members ------------
	 */

	/**
	 * This default may be overridden from the command line
	 */
	public static final int DEFAULT_NUMBER_OF_PHILOSOPHERS = 4;

	/**
	 * Dining "iterations" per philosopher thread while they are socializing there
	 */
	public static final int DINING_STEPS = 10;

	/**
	 * Our shared monitor for the philosphers to consult
	 */
	public static Monitor soMonitor = null;

	/*
	 * ------- Methods -------
	 */

	/**
	 * Main system starts up right here
	 */
	public static void main(String[] argv) {
		try {

			//Default nb of philosophers if not command line argument is passed
			int iPhilosophers = DEFAULT_NUMBER_OF_PHILOSOPHERS;

			//if one is passed
			if (argv.length != 0) {
				try {
					if (argv.length > 1){
						//crashes if more than 1 value is passed
						throw new IllegalArgumentException("More than 1 value provided");
					}

					iPhilosophers = Integer.parseInt(argv[0]);

					if (iPhilosophers < 0 || iPhilosophers > 5) {
						//crashes if invalid number of philosophers
						throw new IllegalArgumentException("Invalid number of philosophers present");
					}
				} catch (NumberFormatException e) {
					reportException(e);
					System.exit(404);
				} catch (IllegalArgumentException e) {
					reportException(e);
					System.exit(404);
				}
			}

			// Make the monitor aware of how many philosophers there are
			soMonitor = new Monitor(iPhilosophers);

			// Space for all the philosophers
			Philosopher aoPhilosophers[] = new Philosopher[iPhilosophers];

			System.out.println(iPhilosophers + " philosopher(s) came in for a dinner.");

			// Let 'em sit down
			for (int j = 0; j < iPhilosophers; j++) {
				aoPhilosophers[j] = new Philosopher();
				aoPhilosophers[j].start();
			}

			// Main waits for all its children to die...
			// I mean, philosophers to finish their dinner.
			for (int j = 0; j < iPhilosophers; j++)
				aoPhilosophers[j].join();

			System.out.println("All philosophers have left. System terminates normally.");
		} catch (InterruptedException e) {
			System.err.println("main():");
			reportException(e);
			System.exit(1);
		}
	} // main()

	/**
	 * Outputs exception information to STDERR
	 * 
	 * @param poException Exception object to dump to STDERR
	 */
	public static void reportException(Exception poException) {
		System.err.println("Caught exception : " + poException.getClass().getName());
		System.err.println("Message          : " + poException.getMessage());
		System.err.println("Stack Trace      : ");
		poException.printStackTrace(System.err);
	}
}

// EOF

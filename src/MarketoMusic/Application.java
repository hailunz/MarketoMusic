package MarketoMusic;

/*
 * Author: Hailun Zhu
 * Email: hailunz@andrew.cmu.edu
 */
import java.io.FileNotFoundException;

/*
 * Main function.
 * Run the application.
 */

public class Application {

	public static void main(String[] args) throws FileNotFoundException {
		// input file as the first argument
		MainMenu mainMenu = new MainMenu(args[0]);
		mainMenu.application();
	}

}

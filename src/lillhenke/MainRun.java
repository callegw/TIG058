package lillhenke;

/**
 * @author Calle Gosch-Wåhlander
 * 840829-0339
 * TIG058
 * Göteborgs universitet
 * VT-14
 * 
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainRun {

	public static void main(String[] args) throws IOException {

		Members[] enArraj = new Members[Metoder.countRows(new File(
				"medlemmar.txt"))];

		Menyn.startMenu();

	}

}

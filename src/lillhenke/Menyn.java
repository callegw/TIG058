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
import java.io.IOException;
import java.util.Scanner;

public class Menyn {

	public static boolean startMenu() throws IOException {
		Members[] enArraj = new Members[Metoder.countRows(new File(
				"medlemmar.txt"))];

		String fileName = "medlemmar.txt";
		File memberFile = new File(fileName);

		enArraj = Metoder.readFileToArray(memberFile);

		boolean run = true;
		menyUI();

		String val = Metoder.readInput("Ange val: ", 1);

		while (run == true) {

			if (val.equals("1")) {
				meny1(enArraj, fileName);
			} else if (val.equals("2")) {
				meny2(enArraj, fileName);
			} else if (val.equals("3")) {
				meny3(enArraj, fileName);
			} else if (val.equals("4")) {
				meny4(enArraj, fileName);
			} else if (val.equals("5")) {
				System.out.println("Programmet avslutas...");
				System.exit(0);
				run = false;
			}
		}

		return true;

	}

	public static void meny1(Members[] enArraj, String fileName)
			throws IOException {

		String val;
		File filen = new File("medlemmar.txt");

		System.out.println("Meny ett");

		System.out
				.println("1 = 1.1 Sortera efter efternamn\n2 = 1.2 Sortera efter födelsedatum\n3 "
						+ "= 1.3 Sortera efter datum man blev medlem\n4 "
						+ "= 1.4 Sortera efter medlem blev aktiv\n5 "
						+ "= 1.5 Skriv ut föräldrarna\n6 "
						+ "= 1.6 Återgå till startmenyn\n");
		val = Metoder.readInput("Ange val: ", 1);

		if (val.equals("1")) {
			System.out.println("1.1 Sortera efter efternamn");
			Metoder.sortNprint(enArraj, fileName, 0);

		} else if (val.equals("2")) {
			System.out.println("1.2 Sortera efter födelsedatum");
			Metoder.sortNprint(enArraj, fileName, 1);

		} else if (val.equals("3")) {
			System.out.println("1.3 Sortera efter datum man blev medlem");
			Metoder.sortNprint(enArraj, fileName, 2);

		} else if (val.equals("4")) {
			System.out
					.println("1.4 Sortera avaktiverade medlemmar efter man blivit medlem");
			Metoder.sortNprint(enArraj, fileName, 3);

		} else if (val.equals("5")) {
			System.out.println("1.5 Skriv ut föräldrarna");
			Metoder.printParents(filen);
		} else if (val.equals("6")) {
			startMenu();
		}

	}

	public static void meny2(Members[] enArraj, String fileName)
			throws IOException {

		String val;
		System.out.println("Meny två");
		System.out
				.println("1 = 2.1 Ta bort medlem\n2 = 2.2 Återgå till startmenyn\n");
		val = Metoder.readInput("Ange val: ", 1);
		File memberFile = new File(fileName);

		if (val.equals("1")) {
			System.out.println("2.1 Ta bort medlem");
			int inId;
			inId = Integer.parseInt(Metoder.readInput("Ange ID: ", 1));
			Metoder.removeMember(inId, memberFile);

		} else if (val.equals("2")) {
			startMenu();

		}

	}

	public static void meny3(Members[] enArraj, String fileName)
			throws IOException {
		String val;
		System.out.println("Meny tre");
		System.out
				.println("1 = 3.1 Lägg till medlem\n2 = 3.2 Återgå till startmenyn\n");
		val = Metoder.readInput("Ange val: ", 1);

		if (val.equals("1")) {
			System.out.println("3.1 Lägg till medlem");
			Metoder.membersInput();
		} else if (val.equals("2")) {
			startMenu();
		}
	}

	public static void meny4(Members[] enArraj, String fileName)
			throws IOException {
		String val;
		String sid;
		int id;
		File filen = new File("medlemmar.txt");
		System.out.println("Meny fyra: ");
		System.out
				.println("1 = 4.1 Sätt medlem som aktiv\n2 = "
						+ "4.2 sätt medlem som inaktiv\n3 = 4.3 Återgå till startmenyn\n");
		val = Metoder.readInput("Ange val: ", 1);

		if (val.equals("1")) {
			System.out.println("4.1 Sätt medlem som aktiv");
			sid = Metoder.readInput("Ange IDnr: ", 1);
			id = Integer.parseInt(sid);
			Metoder.setMemberStatus(id, filen, true);

			Metoder.writeToFile(Metoder.writeArrayToString(enArraj),
					"medlemmar.txt");

		} else if (val.equals("2")) {
			System.out.println("4.2 Sätt medlem som inaktiv");
			sid = Metoder.readInput("Ange IDnr: ", 1);
			id = Integer.parseInt(sid);
			Metoder.setMemberStatus(id, filen, false);

			Metoder.writeToFile(Metoder.writeArrayToString(enArraj),
					"medlemmar.txt");

		} else if (val.equals("3")) {
			startMenu();
		}

		// Metoder.writeToFile()

	}

	private static void menyUI() {
		String[] uiMeny = {
				"======================================\n==Meny=================="
						+ "==============\n======================================",
				"1. Skriv ut medlemmar.", "2. Ta bort medlem.",
				"3. Lägg till medlem.", "4. Aktivera / Avaktivera medlem.",
				"5. Avsluta programmet." };

		for (int i = 0; i < uiMeny.length; i++) {

			System.out.println(uiMeny[i]);

		}
	}

}

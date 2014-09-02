package lillhenke;

/**
 * @author Calle Gosch-W�hlander
 * 840829-0339
 * TIG058
 * G�teborgs universitet
 * VT-14
 * 
 * 
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Metoder {

	public static boolean setMemberStatus(int id, File memberFile,
			boolean isActive) throws IOException {

		int i = 0;

		Members[] Members = null;
		Members[] memberArray = Members;
		memberArray = readFileToArray(memberFile);

		for (i = 0; i < memberArray.length; i++) {

			if (memberArray[i].getId() == id) {
				memberArray[i].setActive(isActive);
			}

		}

		/*
		 * F�r debugging. Skriver ut angivet och n�rliggande idn och visar dess
		 * status
		 * 
		 * for (i = (id-5); i < (id+5); i++) { System.out.println("ID:\t"+
		 * memberArray[i].getId()+"\tIsActive:\t"+memberArray[i].getActive()); }
		 */

		return true;

	}

	public static boolean removeMember(int id, File memberFile)
			throws IOException {

		int i = 0;
		String output = "";

		Members[] Members = null;
		Members[] memberArray = Members;
		memberArray = readFileToArray(memberFile);

		PrintWriter fileOut = new PrintWriter(new BufferedWriter(
				new FileWriter(memberFile)));

		for (i = 0; i < memberArray.length; i++) {

			if (memberArray[i].getId() != id) {
				output = memberArray[i].getRow();

				fileOut.println(output);
			} else {
				addRemovedIdToFile(memberArray[i].getId());
			}
		}

		fileOut.close();

		return true;

	}

	public static boolean findMemberId(int id, Members[] memberArray) {
		boolean result = false;

		for (int i = 0; i < memberArray.length; i++) {
			if (id == memberArray[i].getId()) {
				result = true;
			}
		}

		return result;

	}

	//L�gger IDt fr�n den borttagna medlemmen i en text-fil (oldId.txt)
	//Anv�nds sen f�r att se vilka IDnr som �r lediga.
	public static boolean addRemovedIdToFile(int id) throws IOException {

		PrintWriter pw = new PrintWriter(new FileOutputStream(new File(
				"oldId.txt"), true));
		pw.append(Integer.toString(id) + "\n");
		pw.flush();
		pw.close();

		return true;
	}

	public static int countRows(File file) throws FileNotFoundException {

		int numRows = 0;
		Scanner fileReader = new Scanner(file);

		while (fileReader.hasNextLine()) {

			numRows++;
			fileReader.nextLine();
		}

		fileReader.close();
		return numRows;
	}

	/*Metod f�r att r�kna antalet medlemmar per lag. Metoden �r inte klar �n.
	public static int countTeamMembersByTeam(Members inArray[])
			throws Exception {

		int teamMembers = 0;
		int i = 0;
		int n = 0;

		String teamName;

		for (i = 0; i < countRows(new File("medlemmar.txt")); i++) {

			teamName = inArray[i].getTeam();

			while (n < 200) {

				if (teamName.equals(inArray[n].getTeam())) {
					teamMembers++;

				}

				n++;

			}
			System.out.println("Lag: " + teamName + " Antal spelare: "
					+ teamMembers);
		}

		return teamMembers;
	}
	*/

	public static boolean addMember(String inId, String inGivenName,
			String inFamilyName, String inBirth, String inMemberSince,
			String inRole, String inTeam, String inGender) {

		Members newMember = new Members(inId, inGivenName, inFamilyName,
				inBirth, inMemberSince, inRole, inTeam, inGender);

		PrintWriter pw;

		try {
			pw = new PrintWriter(new FileOutputStream(
					new File("medlemmar.txt"), true));
			pw.append("\n" + newMember.getRow());
			pw.flush();
			pw.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		return true;
	}

	//Skriver ut f�r�ldrar.
	public static boolean printParents(File memberFile) throws IOException {

		Members[] Members = null;
		Members[] memberArray = Members;
		memberArray = readFileToArray(memberFile);
		System.out.println("\nID\t\tF�rnamn\t\tEfternamn\n");
		for (int i = 0; i < memberArray.length; i++) {
			if (memberArray[i].getRole() == 2) {

				System.out.printf("%-15s %-15s %-15s %n",
						memberArray[i].getId(), memberArray[i].getGivenName(),
						memberArray[i].getFamilyName());

			}

		}

		return true;
	}

	//Letar reda p� det sista IDt som finns i textfilen.
	public static String findLastId(File memberFile) throws IOException {
		String lastId = null;
		int id;

		Members[] Members = null;
		Members[] memberArray = Members;
		memberArray = readFileToArray(memberFile);

		id = memberArray[memberArray.length].getId() + 1;

		lastId = String.valueOf(id);

		return lastId;
	}

	//L�ser in textfilen till en array. Anv�nder delimitern ; samt space d�r det beh�vs.
	
	public static Members[] readFileToArray(File file) throws IOException {

		int numRows = countRows(file);
		int i = 0;

		Members[] sourceArray = new Members[countRows(file)];

		Scanner fileReader = new Scanner(file);
		fileReader.useDelimiter("[;\\n]");

		String id;
		String givenName;
		String familyName;
		String birth;
		String memberSince;
		String role;
		String team;
		String gender;

		while (i < numRows) {

			//Byter ut alla blanksteg som finns i textfilen mot "icke-blanksteg"
			id = fileReader.next().replaceAll("\\s+", "");
			givenName = fileReader.next().replaceAll("\\s+", "");
			familyName = fileReader.next().replaceAll("\\s+", "");
			birth = fileReader.next().replaceAll("\\s+", "");
			memberSince = fileReader.next().replaceAll("\\s+", "");
			role = fileReader.next().replaceAll("\\s+", "");
			team = fileReader.next().replaceAll("\\s+", "");
			gender = fileReader.next().replaceAll("\\s+", "");

			sourceArray[i] = new Members(id, givenName, familyName, birth,
					memberSince, role, team, gender);

			i++;

		}

		fileReader.close();

		return sourceArray;
	}

	//Anv�nds f�r att skapa en fil.
	public static File createFile(String fileName) throws IOException {

		File file = new File(fileName);
		file.createNewFile();

		return file;
	}

	//Anv�nds f�r att skriva inneh�llet fr�n arrayn till den textfil.
	public static void writeToFile(String output, String fileName)
			throws IOException {

		File outputFile = createFile(fileName);

		PrintWriter fileOut = new PrintWriter(new BufferedWriter(
				new FileWriter(outputFile)));

		fileOut.println(output);

		fileOut.close();
	}

	/*
	 * H�mtar information utifr�n anv�ndarens val som sorteras och skrivs ut p�
	 * sk�rmen 0: Sortera efter efternamn 1: Sortera efter f�delsedatum 2:
	 * Sortera efter blevMedlem 3: Sortera efter blevMedlem f�r avaktiverade
	 * medlemmar
	 */

	public static boolean sortNprint(Members[] inArray, String fileName,
			int option) throws IOException {

		File membersFile = new File(fileName);

		File swapFile = createFile("swapFile");
		String[] sortedArray = new String[countRows(membersFile)];
		String output = "";

		Scanner scReadFile = new Scanner(swapFile);

		scReadFile.useDelimiter("[;\\n]");

		if (option == 0) {

			for (int u = 0; u < inArray.length; u++) {
				output = output
						+ (inArray[u].getFamilyName() + " "
								+ inArray[u].getGivenName() + "\n");

			}

			System.out.println(output);

			writeToFile(output, "swapFile");

			sortedArray = new String[countRows(swapFile)];

			int x = 0;

			while (scReadFile.hasNextLine()) {
				sortedArray[x] = scReadFile.nextLine();
				x++;
			}

			scReadFile.close();

			Arrays.sort(sortedArray);

			for (int y = 0; y < sortedArray.length; y++) {

				System.out.println(sortedArray[y]);

			}

		}

		else if (option == 1) {
			for (int u = 0; u < inArray.length; u++) {
				output = output
						+ (inArray[u].getBirth() + " "
								+ inArray[u].getFamilyName() + " "
								+ inArray[u].getGivenName() + "\n");

			}

			writeToFile(output, "swapFile");

			sortedArray = new String[countRows(swapFile)];

			int x = 0;

			while (scReadFile.hasNextLine()) {
				sortedArray[x] = scReadFile.nextLine();
				x++;
			}

			scReadFile.close();

			Arrays.sort(sortedArray);

			for (int y = 0; y < sortedArray.length; y++) {

				System.out.println(sortedArray[y]);

			}

		}

		else if (option == 2) {
			for (int u = 0; u < inArray.length; u++) {
				output = output
						+ (inArray[u].getMemberSince() + " "
								+ inArray[u].getFamilyName() + " "
								+ inArray[u].getGivenName() + "\n");

			}

			writeToFile(output, "swapFile");

			sortedArray = new String[countRows(swapFile)];

			int x = 0;

			while (scReadFile.hasNextLine()) {
				sortedArray[x] = scReadFile.nextLine();
				x++;
			}

			scReadFile.close();

			Arrays.sort(sortedArray);

			for (int y = 0; y < sortedArray.length; y++) {

				System.out.println(sortedArray[y]);

			}
		}

		else if (option == 3) {

			// Debugtest:
			// inArray[200].setActive(false);

			for (int u = 0; u < inArray.length; u++) {
				if (inArray[u].getActive() == false) {
					output = output
							+ (inArray[u].getMemberSince() + " "
									+ inArray[u].getFamilyName() + " "
									+ inArray[u].getGivenName() + "\n");
				}
			}

			writeToFile(output, "swapFile");

			sortedArray = new String[countRows(swapFile)];

			int x = 0;

			while (scReadFile.hasNextLine()) {
				sortedArray[x] = scReadFile.nextLine();
				x++;
			}

			scReadFile.close();

			Arrays.sort(sortedArray);

			for (int y = 0; y < sortedArray.length; y++) {

				System.out.println(sortedArray[y]);

			}
		}

		else {
			scReadFile.close();
			return false;
		}
		swapFile.delete();
		Arrays.sort(sortedArray);

		return true;
	}

	//Skriver hela arrayraden till en string.
	public static String writeArrayToString(Members[] inArray) {

		String output = "";

		for (int u = 0; u < inArray.length; u++) {
			output = output + (inArray[u].getRow());
		}

		return output;

	}

	//Metoden anv�nds f�r att kolla s� input fr�n System.in �r en int. 
	//Om det �r en string / s� g�rs metoden om tills det �r en int.
	public static boolean checkInt(String inString) throws IOException {
		try {
			int isInt = Integer.parseInt(inString);

		} catch (NumberFormatException ne) {
			System.out.println("Inga bokst�ver, bara siffor...");
			Menyn.startMenu();
			return false;
		}
		return true;
	}

	// Option 0 = kolla ej efter int.
	// Option 1 = kolla efter int.
	public static String readInput(String message, int option) {

		Scanner scinput = new Scanner(System.in);
		System.out.println(message);
		String input = null;
		input = scinput.next();

		if (option == 1) {
			try {
				if (checkInt(input) == true) {

					return input;
				}

				scinput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		scinput.close();

		return input;
	}

	//Kan se fulkodat ut men det fungerar.
	public static void membersInput() throws IOException {

		File memberFile = new File("medlemmar.txt");
		Scanner scin = new Scanner(System.in);

		System.out.println("Ange f�rnamn: ");
		String fNamn = scin.next();
		System.out.println("Ange efternamn: ");
		String eNamn = scin.next();
		System.out.println("Ange f�delsedatum: (yyyy-MM-dd): ");
		String fDatum = scin.next();
		System.out
				.println("Ange roll: 0 = spelare, 1 = tr�nare, 2 = f�r�lder: ");
		String role = scin.next();
		System.out.println("Ange lag: (Ex: F01 (Flickor f�dda 2001))");
		String team = scin.next();
		System.out.println("Ange k�n: 1 = kvinna, 2 = man");
		String gender = scin.next();

		String id = findLastId(memberFile);
		String msDatum = getTodaysDate();

		addMember(id, fNamn, eNamn, fDatum, msDatum, role, team, gender);

	}

	//Anv�nds f�r att f� dagens datum. Anv�nds tillsammans med metoden f�r att skapa en ny medlem.
	public static String getTodaysDate() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar c = new GregorianCalendar();

		String todayDate = sf.format(c.getTime());

		return todayDate;
	}

}

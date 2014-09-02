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
		 * För debugging. Skriver ut angivet och närliggande idn och visar dess
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

	//Lägger IDt från den borttagna medlemmen i en text-fil (oldId.txt)
	//Används sen för att se vilka IDnr som är lediga.
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

	/*Metod för att räkna antalet medlemmar per lag. Metoden är inte klar än.
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

	//Skriver ut föräldrar.
	public static boolean printParents(File memberFile) throws IOException {

		Members[] Members = null;
		Members[] memberArray = Members;
		memberArray = readFileToArray(memberFile);
		System.out.println("\nID\t\tFörnamn\t\tEfternamn\n");
		for (int i = 0; i < memberArray.length; i++) {
			if (memberArray[i].getRole() == 2) {

				System.out.printf("%-15s %-15s %-15s %n",
						memberArray[i].getId(), memberArray[i].getGivenName(),
						memberArray[i].getFamilyName());

			}

		}

		return true;
	}

	//Letar reda på det sista IDt som finns i textfilen.
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

	//Läser in textfilen till en array. Använder delimitern ; samt space där det behövs.
	
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

	//Används för att skapa en fil.
	public static File createFile(String fileName) throws IOException {

		File file = new File(fileName);
		file.createNewFile();

		return file;
	}

	//Används för att skriva innehållet från arrayn till den textfil.
	public static void writeToFile(String output, String fileName)
			throws IOException {

		File outputFile = createFile(fileName);

		PrintWriter fileOut = new PrintWriter(new BufferedWriter(
				new FileWriter(outputFile)));

		fileOut.println(output);

		fileOut.close();
	}

	/*
	 * Hämtar information utifrån användarens val som sorteras och skrivs ut på
	 * skärmen 0: Sortera efter efternamn 1: Sortera efter födelsedatum 2:
	 * Sortera efter blevMedlem 3: Sortera efter blevMedlem för avaktiverade
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

	//Metoden används för att kolla så input från System.in är en int. 
	//Om det är en string / så görs metoden om tills det är en int.
	public static boolean checkInt(String inString) throws IOException {
		try {
			int isInt = Integer.parseInt(inString);

		} catch (NumberFormatException ne) {
			System.out.println("Inga bokstäver, bara siffor...");
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

		System.out.println("Ange förnamn: ");
		String fNamn = scin.next();
		System.out.println("Ange efternamn: ");
		String eNamn = scin.next();
		System.out.println("Ange födelsedatum: (yyyy-MM-dd): ");
		String fDatum = scin.next();
		System.out
				.println("Ange roll: 0 = spelare, 1 = tränare, 2 = förälder: ");
		String role = scin.next();
		System.out.println("Ange lag: (Ex: F01 (Flickor födda 2001))");
		String team = scin.next();
		System.out.println("Ange kön: 1 = kvinna, 2 = man");
		String gender = scin.next();

		String id = findLastId(memberFile);
		String msDatum = getTodaysDate();

		addMember(id, fNamn, eNamn, fDatum, msDatum, role, team, gender);

	}

	//Används för att få dagens datum. Används tillsammans med metoden för att skapa en ny medlem.
	public static String getTodaysDate() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar c = new GregorianCalendar();

		String todayDate = sf.format(c.getTime());

		return todayDate;
	}

}

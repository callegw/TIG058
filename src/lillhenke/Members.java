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
public class Members {

	private int id;
	private String givenName;
	private String familyName;
	private String birth;
	private String memberSince;
	private int role;
	private String team;
	private int gender;
	private boolean isActive;

	public Members() {

	}

	public Members(String inId, String inGivenName, String inFamilyName,
			String inBirth, String inMemberSince, String inRole, String inTeam,
			String inGender) {

		id = Integer.parseInt(inId);
		givenName = inGivenName;
		familyName = inFamilyName;
		birth = inBirth;
		memberSince = inMemberSince;
		role = Integer.parseInt(inRole);
		team = inTeam;
		gender = Integer.parseInt(inGender);
		isActive = true;

	}

	public int getId() {
		return id;
	}

	public String getGivenName() {
		return givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public String getBirth() {
		return birth;
	}

	public String getMemberSince() {
		return memberSince;
	}

	public int getRole() {
		return role;
	}

	public String getTeam() {
		return team;
	}

	public int getGender() {
		return gender;
	}

	public boolean getActive() {
		return isActive;
	}

	public String getRow() {
		String row;

		row = Integer.toString(id) + ";" + givenName + ";" + familyName + ";"
				+ birth + ";" + memberSince + ";" + Integer.toString(role)
				+ ";" + team + ";" + Integer.toString(gender)+"\n";

		return row;
	}

	public void setId(int inId) {
		id = inId;
	}

	public void setGivenName(String inGivenName) {
		givenName = inGivenName;
	}

	public void setFamilyName(String inFamilyName) {
		familyName = inFamilyName;
	}

	public void setBirth(String inBirth) {
		birth = inBirth;
	}

	public void setMemberSince(String inMemberSince) {
		memberSince = inMemberSince;
	}

	public void setRole(int inRole) {
		role = inRole;
	}

	public void setTeam(String inTeam) {
		team = inTeam;
	}

	public void setGender(int inGender) {
		gender = inGender;
	}

	public void setActive(boolean inIsActive) {
		isActive = inIsActive;
	}

}
// End of file

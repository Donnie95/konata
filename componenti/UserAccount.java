package componenti;
/**
 * 
 * @author gandalf
 *
 */
public class UserAccount {

	private String userName;
	private String password;
	/**
	 * ambiente collegato all'utente
	 */
	public int ambientID;
	private int privilegi;
	
	public UserAccount(String Name) {
		this.userName = Name;
	}
	
	public int getAmbientID() {
		return ambientID;
	}

	public void setAmbientID(int ambientID) {
		this.ambientID = ambientID;
	}

	public UserAccount() {
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getPrivilegi() {
		return privilegi;
	}
	public void setPrivilegi(int privilegi) {
		this.privilegi = privilegi;
	}
	
	public String getPrivilegiStr() {
		
		String privStr = null;
		
		if(this.getPrivilegi() == 0) {
			privStr = "Amministratore";
		}
		if(this.getPrivilegi() == 1) {
			privStr = "Standard";
		}
		if(this.getPrivilegi() == 2) {
			privStr = "SuperUser";
		}
		
		return privStr;
	}
}

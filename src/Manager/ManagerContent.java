package Manager;

public class ManagerContent extends Manager {

	public ManagerContent(int workerID, String Fname, String Lname, String Email, int position, int permission) {
		super(workerID, Fname, Lname, Email, position, permission);
	}
	
	public boolean addContent() {
		return true;
	}

	public boolean editContent() {
		return true;
	}
	
	public boolean deleteContent() {
		return true;
	}
	
	public boolean confirmVersion() {
		return true;
	}
	
	public boolean askPermissionRate() {
		return true;
	}
	
	public boolean setRate() {
		return true;
	}
}

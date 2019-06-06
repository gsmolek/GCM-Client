package Manager;

public class Manager {
	
	private int _permission;
	private String _Fname;
	private String _Lname;
	private int _workerID;
	private String _Email;
	private int _position;
	
	public Manager(int workerID, String Fname, String Lname, String Email, int position, int permission) {
		this._workerID = workerID;
		this._Fname = Fname;
		this._Lname = Lname;
		this._Email = Email;
		this._position = position;
		this._permission = permission;		
	}	
}

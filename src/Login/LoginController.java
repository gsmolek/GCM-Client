package Login;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.SQLException;

/*
 * fistName - varchar
 * lastName - varchar
 * user name - varchar
 * password - varchar
 * email - varchar
 * permission - int
 * phone - int
 * creditCard - int
 */

public class LoginController {
	private String sql;
	private ArrayList<Object> sendSQL;
	public LoginController() {
		sql = null;
		sendSQL = new ArrayList<Object>();
	}
	public boolean login() {
		
		//need to check if the user name or the password is empty//
		String table = "users";
		String username = "sharon";
		String password = "123";
		sql = "SELECT userName, password, permission FROM " + table + " WHERE userName = '" + username
				+ "' AND password = '" + password + "';";
		
		try {
			// send SQL string to the server
			// get ResltSet back from the server
			Statement stmt;
			stmt = dbConnector.con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			//if resultSet is empty the user doesn't exist & return false
			if (!rs.first()) {
				System.out.println("userName or password is incorect");
				return false;
			}

			/*
			 * 1 - company manager 
			 * 2 - content manager 
			 * 3 - client
			 */
			switch (rs.getInt(3)) {
			case 1:
				System.out.println("company manager");
				System.out.println(rs.getString(1) + "  " + rs.getString(2));
				break;
			case 2:
				System.out.println("content manager");
				System.out.println(rs.getString(1) + "  " + rs.getString(2));
				break;
			default:
				System.out.println("user");
				System.out.println(rs.getString(1) + "  " + rs.getString(2));
				break;
			}

			rs.close();
			//if the connection successes return true
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean register() {
		
		String table = "users";
		String username = "def";
		String password = "123";
		String repassword="123";
		String email = "def@as.com";
		String reEmail = "def@as.com";
		String firstName = "amnon";
		String lastName = "vetamar";
		int phone = 0564623 ;
		int creditCard = 123456789;
		
		//password fields does not match
		if(!password.equals(repassword)) {
			System.out.println("password does not match");
			return false;
		}
		
		//email fields does not match
		if(!email.equals(reEmail)) {
			System.out.println("email does not match");
			return false;
		}
		
		
		
		try {
			//check if the user name exist in the DB
			sendSQL.clear();
			sendSQL.add("select");
			sendSQL.add(table);
			sendSQL.add(username);
			sql = "SELECT userName FROM " + sendSQL.get(1) + " WHERE userName = '" + sendSQL.get(2)+ "';";
			sendSQL.add(sql);
			
			
			// will be in server
			Statement stmt;
			ResultSet rs;
			PreparedStatement st = null;
			stmt = dbConnector.con.createStatement();
			rs = stmt.executeQuery(sql);
			// end server
			
			
			if (rs.first()) {
				System.out.println("userName already taken");
				return false;
			}
			
			
			//check if the email exist in the DB
			sendSQL.clear();
			sendSQL.add("select");
			sendSQL.add(table);
			sendSQL.add(email);
			sql = "SELECT email FROM " + sendSQL.get(1) + " WHERE email = '" + sendSQL.get(2)+ "';";
			sendSQL.add(sql);
			
			//will be in server //
			stmt = dbConnector.con.createStatement();
			rs = stmt.executeQuery(sql);
			//end server//
			
			
			if (rs.first()) {
				System.out.println("email already taken");
				return false;
			}
			
			//insert into the DB the new user
			sendSQL.clear();
			sendSQL.add("insert");
			sendSQL.add(table);
			sendSQL.add(username);
			sendSQL.add(password);
			sendSQL.add(email);
			sendSQL.add(phone);
			sendSQL.add(creditCard);
			sendSQL.add(firstName);
			sendSQL.add(lastName);
			
			sql = "insert into "+sendSQL.get(1)+" (userName, password, email, phone,creditCard, firstName, lastName) "
					+ "values ('"+sendSQL.get(2)+"','"+sendSQL.get(3)+"','"+sendSQL.get(4)+"','"+sendSQL.get(5)+"','"+sendSQL.get(6)+"','"+sendSQL.get(7)+"','"+sendSQL.get(8)+"');";
			
			sendSQL.add(sql);
			
			//will be in the server
			st = dbConnector.con.prepareStatement(sql);
			st.executeUpdate();
			//end server
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	
}

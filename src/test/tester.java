package test;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

import Login.*;

import connector.*;

public class tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		dbConnector.connect();
		
		LoginController a = new LoginController();
		a.register();
		
		/*String table="flights";
		

		Statement stmt;
		try 
		{
			stmt = dbConnector.con.createStatement();
			
			PreparedStatement st = dbConnector.con.prepareStatement("insert into "+table+" (name,last_name) values(?,?);");
			
			st.setString(1, "boker");
			st.setString(2, "tov");
			
			st.executeUpdate();
			
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery("SELECT * FROM "+table+" ;");
			
	 		while(rs.next())
	 		{
				 // Print out the values
				 System.out.println(rs.getString(1)+"  " +rs.getString(2)+"  " +rs.getString(3));
			} 
			rs.close();
			
			//stmt.executeUpdate("UPDATE course SET semestr=\"W08\" WHERE num=61309");
		} catch (SQLException e) {e.printStackTrace();}*/
		
		
	}

}

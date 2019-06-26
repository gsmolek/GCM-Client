

package ServerConnection;

import ocsf.client.*;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import javafx.scene.image.Image;


public class ChatClient extends AbstractClient
{
	
  private ArrayList<Object> str=new ArrayList<Object>();
  private ResultSet rs;
  private ArrayList<ArrayList<String>> array;
  private byte[][] result;
  private Image image;

  public byte[][] returnByteArray() {
	  return result;
  }
  public ArrayList<ArrayList<String>> getArray() {
	return array;
}

public void setRs(ResultSet rs) {
	this.rs = rs;
}

public ChatClient(String ip , int port) 
    throws IOException 
  {
    super(ip, port); //Call the superclass constructor
    openConnection();
  }

  public void handleMessageFromServer(Object msg) 
  {
	  System.out.println("hmfc");
	  ArrayList<Object> dataFromServer = (ArrayList<Object>)msg;
	  String command= (String)dataFromServer.get(0);
	  switch(command)
	  {
	  case "1":
	  
		  String sql=(String)dataFromServer.get(dataFromServer.size()-1);
		  System.out.println("1");
	  break;
	  case "2":
	  {
		  System.out.println("2");
		  this.array = (ArrayList<ArrayList<String>>) dataFromServer.get(1);
	  }
	  break;
	  case "3": break;
	  case "5":
	  {
		  String SaveFileAtPath="C:\\Users\\MOLEK\\Desktop\\afula.jpg";
		  byte[] image =(byte[]) dataFromServer.get(1);
		  int fileSize=image.length;

		  try {
			FileUtils.writeByteArrayToFile(new File(SaveFileAtPath), image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  System.out.println(", Size: "+image.length+ " Received from server");

		  //try 
		  //{
			  //FileUtils.writeByteArrayToFile(new File("pathname"), myByteArray)
			  /*
			  System.out.println("1");
			  FileOutputStream fos = new FileOutputStream(SaveFileAtPath);  
			  System.out.println("2");
			  fos.write(image);
			  System.out.println("3");
			  
			  fos.flush();
			  fos.close();
			  */
		  //}
		// catch (IOException e) {
		//	 System.out.println("couldn't get image file from server");
		//}
		  break;
	  }
	  }
  }
public ArrayList<Object> getStr()
{
	return str;
}
public void clearStr()
{
	this.str.clear();
}

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClient(Object message)  
  {
	  try
	    {
	    	ArrayList<Object> sendSQL=(ArrayList<Object>)message;
			InetAddress inetAddress=null;
			try {
				inetAddress = InetAddress.getLocalHost();
			} catch (UnknownHostException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			sendSQL.add(inetAddress);
	    	sendToServer(message);
	    }
	    catch(IOException e)
	    {
	        System.out.println("Could not send message to server.  Terminating client.");
	        e.printStackTrace();
	      quit();
	    }
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  public Image getImage()
  {
	  return this.image;
  }
}


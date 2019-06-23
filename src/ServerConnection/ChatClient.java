

package ServerConnection;

import ocsf.client.*;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
  public void clearArray() {
	this.array.clear();
}

public void setRs(ResultSet rs) {
	this.rs = rs;
}

public ChatClient() 
  {
    super("localhost", 5550); //Call the superclass constructor
    try {
		openConnection();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
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
		  
		  array = (ArrayList<ArrayList<String>>) dataFromServer.get(1);

	  break;
	  case "3": break;
	  case "5":
	  {
		  String SaveFileAtPath="C:/Users/PP/Desktop";
		  ImageStream image =(ImageStream) dataFromServer.get(1);
		  int fileSize=image.getSize();
		  System.out.println("Image: "+image.getFileName() +", Size: "+image.getSize()+ " Received from server");
		  
		  byte[] imageByteArray=new byte[fileSize];
		  try 
		  {
			  FileOutputStream fos = new FileOutputStream(SaveFileAtPath);  
			  imageByteArray = image.getImageStreamByteArray();
			  fos.write(imageByteArray);
			  fos.flush();
			  fos.close();
		  }
		 catch (IOException e) {
			 System.out.println("couldn't get image file from server");
		}
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
<<<<<<< HEAD
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
      quit();
    }
=======
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
	      quit();
	    }
>>>>>>> branch 'gilad' of https://github.com/gsmolek/GCM-Client.git
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


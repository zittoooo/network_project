import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
	public static String get(String str, String str2)
	{
		String result = "";
		try
		{

	    	File file = new File(str);
	    	FileReader fileReader = new FileReader(file);
	    	BufferedReader buffReader = new BufferedReader(fileReader);
	    	String line = "";
	    	while((line = buffReader.readLine()) != null)
	    	{
	    		if(line.contains(str2))
	    		{
	    			result = line.substring(str2.length());
	    			break;
	    		}
	    		else;
	    	}
	    	buffReader.close();

		}catch (FileNotFoundException e)
		{
			System.out.println(e);
		}catch(IOException e)
		{
			System.out.println(e);
		}
		return result;
	}
	
	public static String getHost()
	{
		return Reader.get("configure.txt", "serverIP:");
	}
	
	public static int getPort()
	{
		return Integer.parseInt(Reader.get("configure.txt", "port:"));
	}
	public static int getPort(int SSLPort)
	{
		return Integer.parseInt(Reader.get("configure.txt", "SSLServerPort:"));
	}

	 public static int getIDAttribute(int key, int n_th)
	    {

	       int[] num = new int[4];
	       boolean keyFound = false;
	      try
	       {
	          File file = new File("IDList.txt");
	          FileReader fileReader = new FileReader(file);
	          BufferedReader buffReader = new BufferedReader(fileReader);
	          String line = "";
	          while((line = buffReader.readLine()) != null)
	          {

	             if(line.contains(key+""))
	             {
			         keyFound = true;
	            	 break;
	             }
	             else;
	          }  // while

	          if(keyFound)
	          {
	              String str[] = line.split(",");
		          
			       num[0] = Integer.parseInt(str[0]);
			       num[1] = Integer.parseInt(str[1]);
			       num[2] = Integer.parseInt(str[2]);
			       num[3] = Integer.parseInt(str[3]);
			       
		          buffReader.close();
	          }
	          
	       }catch (FileNotFoundException e)
	       {
	          System.out.println(e);
	       }catch(IOException e)
	       {
	          System.out.println(e);
	       }
  
	      if(n_th>3) n_th=0;
	      return num[n_th];
	    }
	 public static String getIDAttribute(String key, int n_th)
	 {
		 return ""+Reader.getIDAttribute(Integer.parseInt(key), n_th);
	 }
	 public static void main(String args[])
	 {
	 }
}


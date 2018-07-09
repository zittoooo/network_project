import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
	
	static String eServer = "";
	static int ePort = 0000;
	static Socket chatSocket = null;

	public static void main(String[] args) {


		
		ePort = Reader.getPort();
		eServer = Reader.getHost();
		
		try {
			chatSocket = new Socket(eServer, ePort);
			// clientID = chatSocket.getLocalPort();
		} catch (BindException b) {
			System.out.println("Can't bind on: "+ePort);
			System.exit(1);
		} catch (IOException i) {
			System.out.println(i);
			System.exit(1);
		}
		new Thread(new ClientReceiver(chatSocket)).start();
		new Thread(new ClientSender(chatSocket)).start();
	}

}

class ClientSender implements Runnable {
	private Socket chatSocket = null;

	ClientSender(Socket socket)
	{
		this.chatSocket = socket;
	}
	public void run()
	{
		Scanner KeyIn = null;
		PrintWriter out = null;
		try
		{
			KeyIn = new Scanner(System.in);
			out = new PrintWriter(chatSocket.getOutputStream(),
					true);		
			String userInput = "";			
			//check if user has right pw
			System.out.println("input pw1 pw2 \nex1235,4567)");
			userInput = KeyIn.nextLine();
			if(!userInput.contains(","))
			{
				System.out.println("usage : pw1,pw2");
				System.exit(0);
			}
			out.println(userInput);
			out.flush();
			
			System.out.println("Your are "+chatSocket.getLocalPort()
			+ ", Type Message (\"Bye.\" to leave)\nThe first question is »ç°ú");
		
			while ((userInput=KeyIn.nextLine()) != null)
			{
				out.println(userInput);;
				out.flush();
				if(userInput.equalsIgnoreCase("Bye."))
					break;
			}
			KeyIn.close();
			out.close();
			chatSocket.close();
		}catch(IOException i)
		{
			try
			{
				if(out != null) out.close();
				if(KeyIn != null) KeyIn.close();
				if(chatSocket != null) chatSocket.close();
			}catch (IOException e)
			{
			}
			System.exit(1);		
		}
	}
}

class ClientReceiver implements Runnable
{
	private Socket chatSocket = null;
	
	ClientReceiver(Socket socket)
	{
		this.chatSocket = socket;
	}
	
	public void run()
	{
		while(chatSocket.isConnected()) 
		{
			BufferedReader in = null;
			try
			{
				in = new BufferedReader(new InputStreamReader
				(chatSocket.getInputStream()));
				String readSome = null;
				while((readSome = in.readLine()) != null)
				{
					System.out.println(readSome);
				}
				in.close();
				chatSocket.close();
			
			} catch (IOException i)
			{
				try
				{
					if(in != null) in.close();
					if(chatSocket != null) chatSocket.close();
					
				} catch (IOException e)
				{
				}
				//SocketTimeout, "Bye.", Max connection and more
				System.out.println("Leave.");
				System.exit(1);
				
			}
		}
	}
}
import java.net.*; 
import java.io.*; 

public class ChatServer implements Runnable {
	private int finalStage = 10; 
	private int currStage = 0; 
	private int winnerIDs[] = new int[finalStage+1]; 
	private String[] questionKor = {"사과", "바나나", "고양이"
			," 용", "달걀", "날다", "가다", "지옥", "정수", "농담", "한국"};
	private String[] question = {"apple", "banana", "cat", "dragon", 
			"egg", "fly", "go", "hell", "integer", "joke", "korea"};
	private String coAns = question[currStage]; 
	
	private ChatServerRunnable clients[] = new ChatServerRunnable[3];
	public int clientCount = 0;

	private int ePort = -1;

	
	public ChatServer(int port) {
		this.ePort = port;
	}

	public void run() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(ePort);
			System.out.println ("Server started: socket created on " + ePort);
			
			while (true) {
				addClient(serverSocket);
			}
		} catch (BindException b) {
			System.out.println("Can't bind on: "+ePort);
		} catch (IOException i) {
			System.out.println(i);
		} finally {
			try {
				if (serverSocket != null) serverSocket.close();
			} catch (IOException i) {
				System.out.println(i);
			}
		}
	}
	
	public int whoClient(int clientID) {
		for (int i = 0; i < clientCount; i++)
			if (clients[i].getClientID() == clientID)
				return i;
		return -1;
	}
	public void putClient(int clientID, String inputLine) { //

		int idLength = (""+clientID).length();
		String ans = inputLine.substring(idLength+1); //player's answer
		boolean isThereWinner=false;

		for (int i = 0; i < clientCount; i++)
		{
			System.out.println(inputLine+"from "+clientID);
			if(ans.equalsIgnoreCase(coAns))	
			{
				
				isThereWinner = true;
				if(clients[i].getClientID()!=clientID)
					clients[i].out.println(inputLine);
			}
			else
				clients[i].out.println(inputLine + " X");
			
		}
		if(isThereWinner) 
		{//when any player wins the current stage, we go to the next stage
			if(currStage==finalStage)
				gameOver();
			System.out.println("winner:clientID");
			upStage(clientID);
			isThereWinner = false;
		}
	}

	public void gameOver()
	{
		for(int i=0; i<clientCount; i++)
		{
			clients[i].out.println("game over");
			for(int j=0; j<finalStage;j++)
				clients[i].out.println("stage["+(j+1)+"]'s winner :"+winnerIDs[j]);
		}
		System.out.println("game over");
		System.exit(1);
	}
	public void upStage(int winnerID) //
	{
		winnerIDs[currStage] = winnerID; //This is the list of each stage's winner
		currStage++;
		for (int i = 0; i < clientCount; i++)
			clients[i].out.println("stage["+(currStage-1)+"]winner:"+ winnerID
					+ "\nWe are going to stage["+currStage+"]" 
					+ "next question is" + questionKor[currStage]);
		coAns = question[currStage];
		
	}
	
	
	
	public void addClient(ServerSocket serverSocket) {
		Socket clientSocket = null;
		
		if (clientCount < clients.length) { 
			try {
				clientSocket = serverSocket.accept();
			//	clientSocket.setSoTimeout(40000); // 1000/sec
			} catch (IOException i) {
				System.out.println ("Accept() fail: "+i);
			}
			clients[clientCount] = new ChatServerRunnable(this, clientSocket);
			new Thread(clients[clientCount]).start();
			clientCount++;
			System.out.println ("Client connected: " + clientSocket.getPort()
					+", CurrentClient: " + clientCount);
		} else {
			try {
				Socket dummySocket = serverSocket.accept();
				ChatServerRunnable dummyRunnable = new ChatServerRunnable(this, dummySocket);
				new Thread(dummyRunnable);
				dummyRunnable.out.println(dummySocket.getPort()
						+ " < Sorry maximum user connected now");
				System.out.println("Client refused: maximum connection "
						+ clients.length + " reached.");
				dummyRunnable.close();
			} catch (IOException i) {
				System.out.println(i);
			}	
		}
	}
	
	public synchronized void delClient(int clientID) {
		int pos = whoClient(clientID);
		ChatServerRunnable endClient = null;
	      if (pos >= 0) {
	    	   endClient = clients[pos];
	    	  if (pos < clientCount-1)
	    		  for (int i = pos+1; i < clientCount; i++)
	    			  clients[i-1] = clients[i];
	    	  clientCount--;
	    	  System.out.println("Client removed: " + clientID
	    			  + " at clients[" + pos +"], CurrentClient: " + clientCount);
	    	  endClient.close();
	      }
	}
	
	public static void main(String[] args) throws IOException {

		int ePort = Reader.getPort();		
		new Thread(new ChatServer(ePort)).start();
	}
}

class ChatServerRunnable implements Runnable {
	protected ChatServer chatServer = null;
	protected Socket clientSocket = null;
	protected PrintWriter out = null;
	protected BufferedReader in = null;
	public int clientID = -1;
	protected boolean isVerified = false; 
	
	public ChatServerRunnable ( ChatServer server, Socket socket)
	{
		this.chatServer = server;
		this.clientSocket = socket;
		clientID = clientSocket.getPort();
		try
		{
			out = new
			PrintWriter(clientSocket.getOutputStream(),true);
			in = new BufferedReader(
			new InputStreamReader( clientSocket.getInputStream()));
		}catch (IOException i)
		{
		}
		
	}
	public void run()
	{		
		try 
		{		
			String key = in.readLine();
	        String num[] = key.split(",");
	        int id = RSA.result(num[0], Reader.getIDAttribute(num[1],1), num[1]);
			if(id == Integer.parseInt(Reader.getIDAttribute(num[1], 2)))
					isVerified=true;
	        System.out.println("Verifying......");
	        if(!isVerified) System.out.println("failed"+id);
	        else System.out.println("success");
			
			String inputLine;
			
			while((inputLine = in.readLine()) != null)
			{
				chatServer.putClient(getClientID(),  getClientID() + ":"
				+inputLine);
				if(inputLine.equalsIgnoreCase("Bye."))
					break;
			}
			chatServer.delClient(getClientID());
		} catch (SocketTimeoutException ste)
		{
			System.out.println("Socket timeout Occured, force close(); :"
					+ +getClientID());
			chatServer.delClient(getClientID());;
		} catch (IOException e)
		{
			chatServer.delClient(getClientID());
		}
		
	}
	
	public int getClientID()
	{
		return clientID;
	}
	
	public void close()
	{
		try
		{
			if ( in != null) in.close();
			if ( out != null) out.close();
			if (clientSocket != null) clientSocket.close();
		}catch (IOException i)
		{
		}
		
	}
}

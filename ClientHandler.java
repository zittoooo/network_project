import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientHandler {
	private static String tmp = "";
	 
	public static void main(String args[]) {

		 String str = "java -Djava -Djavax.net.ssl.trustStore=ServerKey -Djava.security."
				 + "policy=policy HelloClient";
		 
		 ClientHandler.commend(str);
	
         String num[] = tmp.split(",");

		 String enc = ""+RSA.result(
				 Integer.parseInt(num[0]), Integer.parseInt(num[1]), Integer.parseInt(num[2]));
		 System.out.println("client usage : PW1["+enc+"] PW2["+num[2]+"]");
		 

	}
	public static void commend(String str)
	  {
		  String s="";
		  try {

			  Process oProcess = new ProcessBuilder("cmd", "/c", str ).start();
			  // 자바 1.5 이상

			  // 외부 프로그램 출력 읽기
			  BufferedReader buffReader   = 
					  new BufferedReader(new InputStreamReader(oProcess.getInputStream()));

			  if((s = buffReader.readLine()) != null)  tmp = s;


		  } catch (IOException e) { // 에러 처리
			  System.err.println("error: clientHandler" + e.getMessage());
			  System.exit(-1);
		  }
	}

	public static int firstBlank(String str)
	{
		  for(int i=0;i<str.length(); i++)
			  if(str.charAt(i)==' ') return i;
		  return -1;
	}

}
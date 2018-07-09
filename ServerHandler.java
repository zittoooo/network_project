import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerHandler {
	  public static void main(String args[]) {

		 String str1 = "javac *.java";
		 String str2 = "rmic HelloImpl";
		 String str3 = "java -Djava.security.policy=policy HelloImpl";

		 ServerHandler.commend(str1);
		 ServerHandler.commend(str2);
		 ServerHandler.commend(str3);

	      System.exit(-1);
		 
	  }
	  public static void commend(String str)
	  {
		  try {
	
			  Process oProcess = new ProcessBuilder("cmd", "/c", str ).start();
		    // 자바 1.5 이상
			 
			  String s;
			
		    // 외부 프로그램 출력 읽기
		    BufferedReader buffReader = 
		    		new BufferedReader(new InputStreamReader(oProcess.getInputStream()));
		    BufferedReader buffError = 
		    		new BufferedReader(new InputStreamReader(oProcess.getErrorStream()));
	
		    // "표준 출력"과 "표준 에러 출력"을 출력
		    while ((s = buffReader.readLine()) != null) System.out.println(s);
		    while ((s = buffError.readLine()) != null) System.err.println(s);
	
		  } catch (IOException e) { // 에러 처리
		      System.err.println("serverHandler error\n" + e.getMessage());
		      System.exit(-1);
		    }
		  
	  }
}
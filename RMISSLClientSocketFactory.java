import java.io.*;
import java.net.*;
import java.rmi.server.*;
import javax.net.ssl.*;
import java.io.IOException;


public class RMISSLClientSocketFactory
	implements RMIClientSocketFactory, Serializable {

    private static final long serialVersionUID = 1L;
	
	private String hostIP = "1.1.1.1";
    public Socket createSocket(String host, int port) throws IOException {
    	hostIP = Reader.getHost();
    	SSLSocketFactory factory =
		(SSLSocketFactory)SSLSocketFactory.getDefault();
	    SSLSocket socket = (SSLSocket)factory.createSocket(hostIP, port);
	    return socket;
    }
    public int hashCode() {
	return getClass().hashCode();
    }

    public String getIP()
    {
    	return hostIP;
    }
  
    public boolean equals(Object obj) {
	if (obj == this) {
	    return true;
	} else if (obj == null || getClass() != obj.getClass()) {
	    return false;
	}
	return true;
    }
  
    
}

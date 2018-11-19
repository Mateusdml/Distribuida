import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class InputClient extends Thread{
	
	private Socket client;
	private static DataInputStream input;
	
	
	public InputClient(Socket socket) {
		try {
			input  = new DataInputStream(socket.getInputStream());
			this.client = socket;
		} catch (IOException e) {
			System.out.println("Conexão encerrado");
		}
	}
	
	public void run() {
		try {
			while(!client.isClosed()) {
				System.out.println(input.readUTF());
			}
				
		} catch (IOException e) {
			System.out.println("Conexão encerrado");
		}
		
	}
}
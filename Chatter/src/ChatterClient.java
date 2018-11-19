import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatterClient {

	static Scanner sc = new Scanner(System.in);
	private static Socket socket;
	private static DataOutputStream output;
	
	
	public static void main(String[] args) throws IOException{
		ChatterClient client = new ChatterClient();
		socket = new Socket("localhost", 6500);
		output = new DataOutputStream(socket.getOutputStream());
		System.out.println("Digite o seu UserName: ");		
		client.input();
	}
	

	public void input() throws IOException{
		Thread t = new InputClient(socket);
		t.start();
		String message;
		while(true) {
			message = sc.nextLine();
			if(!"bye".equalsIgnoreCase(message) && message != null)
				output.writeUTF(message);
			else {
				output.writeUTF(message);
			}
		}
	}

}

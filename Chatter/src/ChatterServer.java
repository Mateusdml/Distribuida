import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatterServer extends Thread {
	// Socket do servidor
	private static ServerSocket serverSocket = null;
	// Socket do cliente
	private static Socket clientSocket = null;
	// Lista de clientes
	public static ArrayList<ChatterServer> clients = new ArrayList<ChatterServer>();
	//Entrada e Sa�da
	private DataInputStream input = null;
	private DataOutputStream output = null;
	private String username = "usrname";

	public ChatterServer(Socket socket) {
		clientSocket = socket;
		try {
			input = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		// Inicia os processos da thread

		try {
			// Inicia Output do cliente ligado a thread

			output = new DataOutputStream(clientSocket.getOutputStream());
			clients.add(this);
			
			do {
				String message = input.readUTF();
				if (!UsrNameExists(message) && !message.equals("usrname")) {
					this.username = message;
					output.writeUTF("\n Username cadastrado com sucesso.\n");
					String cabecalho = 	("===================================\n"
										+"\n       Cliente        "+message+
										"\n===================================\n");
					output.writeUTF(cabecalho);
					break;
				} else {
					output.writeUTF("\n Username ja existente, favor informar outro\n");
				}
			} while (this.username.equals("usrname"));

			// Tutorial para o cliente
			String tutorial = "Tutorial:\nDigite: send -all <mensagem>, para enviar para todos usuarios. \n"
					+ "Digite: send -user <username_destino> <mensagem>, para enviar mensagem privada \n"
					+ "Digite: list, para ver usuarios conectados. \n" + "Digite: help, para ver tutorial novamente \n"
					+ "Digite: rename, para renomear \n" + "Digite: bye, para sair do chat \n";

			// Envia tutorial e lista de clientes conectados
			output.writeUTF(tutorial);
			list();
			System.out.println("lendo mensagem");
			// L� primeira mensagem do chat que o cliente envia
			String message = input.readUTF();

			// Se a mensagem n�o for bye, avisa a todos que um novo usu�rio se conectou
			do {// tratamento das mensagens
				if (message.toLowerCase().startsWith("send -all")) {
					// Envia para todos
					SendAll(message);
				} else if (message.toLowerCase().startsWith("send -user")) {
					// Envia para um us�rio, caso usu�rio n�o seja encontrado, informa ao cliente.
					// M�todo SendUser retorna true caso o usu�rio seja encontrado, falso se n�o.
					if (!SendUser(message)) {
						output.writeUTF("\nSystem = Usu�rio n�o encontrado at " + getDateTime());
					}else{output.writeUTF("Mensagem enviada\n");}
				} else if (message.toLowerCase().equals("list")) {
					// Lista usu�rios
					list();
				} else if (message.toLowerCase().startsWith("rename")) {
					// Tenta renomear usu�rio
					String[] splited = message.split(" ");
					if (!UsrNameExists(splited[1])) {
						this.username = splited[1];
                                                output.writeUTF("\n System = Renomeado com sucesso" + getDateTime());
					} else {
						output.writeUTF("\n System = Username j� existente, favor informar outro " + getDateTime());
					}
				} else if (message.toLowerCase().trim().equals("help")) {
					// Exibe tutorial
					output.writeUTF(tutorial);
				} else {
					// Comando desconhecido
					this.output.writeUTF("System = Comando desconhecido, digite help para tutorial. " + getDateTime());
				}
				message = input.readUTF();
			}while(!"bye".equalsIgnoreCase(message));
			
		}catch(IOException e){System.out.println("Usuario saiu");}

		SendAllSystem("Usu�rio " + username + " saiu do chat");

		for (int i = 0; i < clients.size(); i++)  {
			if (clients.get(i) == this) {
				clients.remove(i);
			}
		}

		try {
			this.output.writeUTF("Voc� foi desconectado");
			output.close();
			input.close();
			clientSocket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getUsername() {
		return username;
	}

	public DataOutputStream getOutput() {
		return output;
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void SendAll(String message) {
		
		//Varre array e manda mensagem de cliente para todos clientes conectados
		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i) != null && clients.get(i) != this) {
				try {
					String address = ChatterServer.clientSocket.getInetAddress().toString();
					String port = String.valueOf(ChatterServer.clientSocket.getPort());
					String msg = (address + ":" + port + " / " + this.username + " : " + message + " At: "
							+ getDateTime());
					output = new DataOutputStream(clients.get(i).getOutput());
					output.writeUTF(msg);
					output.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void SendAllSystem(String message) {
		//Varre array e manda mensagem de sistema para todos clientes conectados
		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i) != null && clients.get(i) != this) {
				try {
					String msg = ("System =" + message + " At: " + getDateTime());
					clients.get(i).output.writeUTF(msg);
					output = new DataOutputStream(clients.get(i).getOutput());
					output.writeUTF(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean SendUser(String message) {
		//Busca usu�rio para enviar a mensagem

//Divide a string para separar comandos da mensagem, nome de usu�rio ficar� na 3� posi��o do array
		String[] splited = message.split(" ");

//Varre array em busca do usu�rio
		for (int i = 0; i < clients.size(); i++) {
			
			if (clients.get(i).getUsername().equals(splited[2]) && clients.get(i) != this) {
				try {
					// Se encontra usu�rio, manda mensagem para ele
					
					String address = ChatterServer.clientSocket.getInetAddress().toString();
					String port = String.valueOf(ChatterServer.clientSocket.getPort());
					String msg = (address + ":" + port + " / " + this.username + " (Privado) : " + message+ " At: "
							+ getDateTime());
					clients.get(i).output.writeUTF(msg);
					//this.output.writeUTF("Mensagem enviada para: " + splited[2]);
					return true;
				} catch (IOException e) {
					System.out.println("Erro no senduser");
				}
			}
		}
		return false;
	}

	public void list() {
		for (int i = 0; i < clients.size(); i++) {
			try {
				this.output.writeUTF(i + " - " + clients.get(i).getUsername());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Boolean UsrNameExists(String message) {
		String[] splited = message.split("\\s+");

		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i).getUsername().equals(splited[0])) {
				return true;
			}
		}
		return false;
	}

	public static void main(String args[]) {

		// Porta
		int port = 6500;
		System.out.println("========================================");
		System.out.println("================Servidor================");
		System.out.println("========================================");
		System.out.println("Iniciando conex�o na porta: " + port + "...");

		// Abre conex�o na porta default
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Conex�o Aberta na porta: " + port + "...");
			clients = new ArrayList<ChatterServer>();
			while (true) {
				try {
					System.out.println("Aguardando conex�o...");
					Socket socket = serverSocket.accept();
					System.out.println("Cliente conectado...");
					Thread t = new ChatterServer(socket);
					t.start();
				} catch (IOException e) {
					System.out.println(e);
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		}

	}
}
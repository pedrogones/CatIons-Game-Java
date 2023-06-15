package interfaceGrafica;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.HashMap;

public class Servidor implements Runnable {

	//Lista para armazenar os clientes que forem se conectando
	private static List<Socket> clientes = new ArrayList<>();
	private Socket s;
	private Semaphore lockin = new Semaphore(1);

	public Servidor(Socket ns) {
		s = ns;
	}

	public void run() {
		try {

			// Recupera o buffer de entrada e de saída do servidor
			DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());

			// Dicionario para poder tratar as mensagens enviadas pelos clientes
			Map<String, Integer> dicionario = new HashMap<>();

			dicionario.put("red Atualização: acertou", 11);
			dicionario.put("red Atualização: errou", 12);
			dicionario.put("blue Atualização: acertou", 21);
			dicionario.put("blue Atualização: errou", 22);

			// Adiciona o socket do cliente à lista
			clientes.add(s);

			// Atribui a cor ao cliente com base na ordem de chegada
			String corCliente = "";
			if (clientes.size() == 1) {
				corCliente = "red";
				out.writeUTF("Cor:" + corCliente);   
			} else if (clientes.size() == 2) {
				corCliente = "blue";
				out.writeUTF("Cor:" + corCliente);

				// Assim que a lista de clientes alcançar o tamanho 2, envia uma mensagem
				// para liberar o tabuleiro
				for (Socket cliente : clientes) {
					DataOutputStream clienteOut = new DataOutputStream(cliente.getOutputStream());
					clienteOut.write(100);
				}
			} 

			while (true) {
				String fromClient = in.readUTF();
				lockin.acquire();
				//Se estiver no dicionario, converte para um inteiro e manda de volta
				//se não estiver, retorna string
				if(dicionario.containsKey(fromClient)) {
					int conversor = dicionario.get(fromClient);
					System.out.println("cliente: "+ s.getInetAddress().getHostAddress() + " " + fromClient);
					System.out.println("Transformei em: " + conversor);

					for (Socket cliente : clientes) {
						DataOutputStream clienteOut = new DataOutputStream(cliente.getOutputStream());
						clienteOut.write(conversor);
					}
					lockin.release();

				}
			}
		}catch(IOException | InterruptedException e) {
			e.getStackTrace();
		}


		try {
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		try {

			try (ServerSocket s = new ServerSocket(4444)) {
				System.out.println("Servidor iniciado");
				while (true) {
					Socket ns = s.accept();
					System.out.println("Cliente " + ns.getInetAddress().getHostAddress() + " conectado");
					new Thread(new Servidor(ns)).start();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
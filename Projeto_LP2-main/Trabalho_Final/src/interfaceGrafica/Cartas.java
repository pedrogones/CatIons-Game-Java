package interfaceGrafica;

import java.util.ArrayList;
import java.util.Random;

public class Cartas {
	public static int numCarta;
	public static String cartaConteudo;
	public ArrayList<Integer> randonsAleatorios = new ArrayList<>();

	public Cartas() {
		

	}

	// gerar um indice
	public static int numCard() {
		Random random = new Random();
		int numeroAleatorio = random.nextInt(70);
				return numeroAleatorio;
	}

	// pegar a pergunta gerada, e suas respectivas respostas
	// vai sempre gerar uma pergunta, quando jogo for iniciado e enquanto cada
	// resposta for respondida
	public void getMyQuestion() throws InterruptedException {
		Deck n = new Deck(randonsAleatorios);
		n.getQuestion(n);
	}

	

}
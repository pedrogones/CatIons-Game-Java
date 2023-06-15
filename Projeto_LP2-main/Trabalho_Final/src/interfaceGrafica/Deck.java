package interfaceGrafica;

import java.io.IOException;
import java.util.ArrayList;


public class Deck extends DataBaseCards {
	public static int numCarta;
	public static String cartaConteudo;
	public static boolean hit_miss;
	ArrayList<Integer> randonsExistentes = new ArrayList<>();

	public Deck(ArrayList<Integer> randonsExistentes) {
		Deck.hit_miss = false;
		this.randonsExistentes = randonsExistentes;
	}

	public void generateRandomQuestion(int questionIndex) {
		System.out.println(idNum.get(questionIndex) + ":" + pergunta.get(questionIndex) + "\n a) Sim b) Não");
	}

	public String verificarAcerto(String resposta, int questionIndex) {
        if (respostasCorretas.get(questionIndex).equalsIgnoreCase(resposta)) {
            return "Acertou";
        } else {
            // System.out.println("Você errou!");
            return "Errou!";
        }
    }

	public Boolean verifyBool(String x) throws IOException, InterruptedException {
        if (x.equalsIgnoreCase("Acertou")) {
            return true;
        }
        else {
            return false;
        }
    }

	public void getQuestion(Deck n) {
		int randomIndex;
		do {
			randomIndex = Cartas.numCard();
		} while (randonsExistentes.contains(randomIndex));
		randonsExistentes.add(randomIndex);
		n.generateRandomQuestion(randomIndex);
		Cartas.numCarta = randomIndex;
		Cartas.cartaConteudo = pergunta.get(randomIndex);
	}

	public static void main(String[] args) {
	}
}
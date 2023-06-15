package interfaceGrafica;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.io.IOException;


public class Tabuleiro {

	static JFrame frame;
	static JPanel panel;
	static JLabel tabuleiro, playerRedIcon, playerBlueIcon;
	static EndGUI fimDeJogo;
	static private Timer animaçãoVermelho;
	private static Timer animaçãoAzul;
	private static boolean cartaVisivel = false;
	public static boolean boolEndGame = false;


	static String meuPlayer;
	static int contadorAnimadoVermelho, contadorAnimadoAzul;

	static Jogador playerRed, playerBlue;
	static CartaPergunta carta;


	Tabuleiro(String meuNome, String nomeAdversario, String minhaCor){

		playerRed = new Jogador(meuNome, "red");
		playerBlue = new Jogador(nomeAdversario, "blue");

		meuPlayer = definePlayer(minhaCor); 

		//playerRed.setCasaAtual(22);

		frame = new JFrame("Projeto LP2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(175, 0, 1020, 720);

		// Criação de um painel.
		panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		// Criação de um rótulo com uma imagem.
		tabuleiro = new JLabel("");
		tabuleiro.setIcon(new ImageIcon(Tabuleiro.class.getResource("./assets/map_scaled.png")));
		tabuleiro.setBounds(0, 0, 1020, 720);
		panel.add(tabuleiro);

		// Mostra o gato vermelho na tela.
		playerRedIcon = new JLabel(playerRed.getNome());
		playerRedIcon.setIcon(new ImageIcon(Tabuleiro.class.getResource(playerRed.getAtualSprite())));
		playerRedIcon.setBounds(playerRed.getPosiçãoInicialX(), playerRed.getPosiçãoInicialY(), 100, 52);
		panel.add(playerRedIcon);

		// Mostra o gato azul na tela.
		playerBlueIcon = new JLabel(playerBlue.getNome());
		playerBlueIcon.setIcon(new ImageIcon(Tabuleiro.class.getResource(playerBlue.getAtualSprite())));
		playerBlueIcon.setBounds(playerBlue.getPosiçãoInicialX(), playerBlue.getPosiçãoInicialY(), 100, 52);
		panel.add(playerBlueIcon);

		panel.setComponentZOrder(tabuleiro, 1);
		panel.setComponentZOrder(playerRedIcon, 0);
		panel.setComponentZOrder(playerBlueIcon, 0);

		frame.setVisible(true);

	}

	// Movimenta o Gatinho Vermelho.
	public static void movimentarVermelho() {

		int casaVermelho = playerRed.getCasaAtual();
		int casaAzul = playerBlue.getCasaAtual();

		String caminho = endGame(casaVermelho, casaAzul);
		System.out.println(caminho);

		animaçãoVermelho = new Timer(10, e ->{

			
				if (caminho.equalsIgnoreCase("./assets/draw.png") && !boolEndGame) {
					boolEndGame = true;
					fimDeJogo = new EndGUI("./assets/draw.png");
					animaçãoVermelho.stop();
					frame.dispose();
				}
				else if (caminho.equalsIgnoreCase("./assets/win_red.png") && !boolEndGame ) {
					System.out.println("Chegou");
					boolEndGame = true;
					fimDeJogo = new EndGUI("./assets/win_red.png");
					animaçãoVermelho.stop();
					frame.dispose();
				}
				else if (caminho.equalsIgnoreCase("./assets/win_blue.png") && !boolEndGame) {
					boolEndGame = true;
					fimDeJogo = new EndGUI("./assets/win_blue.png");
					frame.dispose();
				} else if (caminho.equalsIgnoreCase("./assets/lose_red.png") && !boolEndGame) {
					boolEndGame = true;
					fimDeJogo = new EndGUI("./assets/lose_red.png");
					animaçãoVermelho.stop();
					frame.dispose();
				}
				else if (caminho.equalsIgnoreCase("./assets/lose_blue.png")  && !boolEndGame) {
					boolEndGame = true;
					fimDeJogo = new EndGUI("./assets/lose_blue.png");
					animaçãoVermelho.stop();
					frame.dispose();
				}
			

			playerRed.mover();

			playerRedIcon.setIcon(new ImageIcon(Tabuleiro.class.getResource(playerRed.getAtualSprite())));
			playerRedIcon.setBounds(playerRed.getPosiçãoAtualX(), playerRed.getPosiçãoAtualY(), 100, 52);

			if (contadorAnimadoVermelho == playerRed.getMovimento()) {
				playerRed.setAtualSprite(playerRed.getFrontSprite());
				playerRedIcon.setIcon(new ImageIcon(Tabuleiro.class.getResource(playerRed.getAtualSprite())));
				playerRed.setCasaAtual(playerRed.getCasaAtual() + 1);
				contadorAnimadoVermelho = 0;

				if (meuPlayer.equals("red") && !boolEndGame)
					adicionarCarta();

				animaçãoVermelho.stop();
			}

			contadorAnimadoVermelho++;
		});

		animaçãoVermelho.start();

	}

	// Movimenta o Gatinho Azul.
	public static void movimentarAzul() {

		int casaVermelho = playerRed.getCasaAtual();
		int casaAzul = playerBlue.getCasaAtual();

		String caminho = endGame(casaVermelho, casaAzul);


		animaçãoAzul = new Timer(10, e -> {
			if (caminho.equalsIgnoreCase("./assets/draw.png")) {
					boolEndGame = true;
					fimDeJogo = new EndGUI("./assets/draw.png");
					animaçãoAzul.stop();
					frame.dispose();
				}
				else if (caminho.equalsIgnoreCase("./assets/win_red.png") ) {
					System.out.println("Chegou");
					boolEndGame = true;
					fimDeJogo = new EndGUI("./assets/win_red.png");
					animaçãoAzul.stop();
					frame.dispose();
				}
				else if (caminho.equalsIgnoreCase("./assets/win_blue.png") ) {
					boolEndGame = true;
					fimDeJogo = new EndGUI("./assets/win_blue.png");
					animaçãoAzul.stop();
					frame.dispose();
				} else if (caminho.equalsIgnoreCase("./assets/lose_red.png")) {
					boolEndGame = true;
					fimDeJogo = new EndGUI("./assets/lose_red.png");
					animaçãoAzul.stop();
					frame.dispose();
				}
				else if (caminho.equalsIgnoreCase("./assets/lose_blue.png")) {
					boolEndGame = true;
					fimDeJogo = new EndGUI("./assets/lose_blue.png");
					animaçãoAzul.stop();
					frame.dispose();
				}



			playerBlue.mover();

			playerBlueIcon.setIcon(new ImageIcon(Tabuleiro.class.getResource(playerBlue.getAtualSprite())));
			playerBlueIcon.setBounds(playerBlue.getPosiçãoAtualX(), playerBlue.getPosiçãoAtualY(), 100, 52);

			if (contadorAnimadoAzul == playerBlue.getMovimento()) {
				playerBlue.setAtualSprite(playerBlue.getFrontSprite());
				playerBlueIcon.setIcon(new ImageIcon(Tabuleiro.class.getResource(playerBlue.getAtualSprite())));
				playerBlue.setCasaAtual(playerBlue.getCasaAtual() + 1);
				System.out.println(playerBlue.getCasaAtual());
				contadorAnimadoAzul = 0;


				if (meuPlayer.equals("blue") && !boolEndGame)
					adicionarCarta();

				animaçãoAzul.stop();
			}

			contadorAnimadoAzul++;
		});

		animaçãoAzul.start();
	}


	//add carta:
	public static synchronized void adicionarCarta() {
		if (!cartaVisivel) {
			if (meuPlayer.equals("red")) {
				try {
					carta = new CartaPergunta();
					carta.getFrame().setVisible(true);
					cartaVisivel = true;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else if (meuPlayer.equals("blue")) {
				try {
					carta = new CartaPergunta();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				carta.getFrame().setVisible(true);
				cartaVisivel = true;
			}
		}
	}


	public static Tabuleiro criarTabuleiro(String meuNome, String nomeAdversario, String minhaCor) {
		return new Tabuleiro(meuNome, nomeAdversario, minhaCor);
	}

	public static void teste() {

		try {
			switch(Cliente.getIn().read()) {

			case 11:
				movimentarVermelho();
				break;
			case 12:
				System.out.println("vermelho errou");
				break;
			case 21:
				movimentarAzul();
				break;
			case 22:
				System.out.println("Azul errou");
				break;
			default:
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Define a cor do Player do Tabuleiro.
	public static String definePlayer(String mensagemDeCor) {	
		if (mensagemDeCor.equals("red"))
			return "red";
		else if (mensagemDeCor.equals("blue"))
			return "blue";
		else
			return "erro";		
	}


	public static String endGame(int casaVermelho, int casaAzul) {
		if(casaVermelho == 25 && casaAzul == 25) {
			return "./assets/draw.png";
		}else if(casaVermelho == 25 && meuPlayer.equals("red")) {
			return "./assets/win_red.png";
		}else if(casaVermelho == 25 && meuPlayer.equals("blue")) {
			return "./assets/lose_blue.png";
		}else if(casaAzul == 25 && meuPlayer.equals("blue")) {
			return "./assets/win_blue.png";	
		}else if(casaAzul == 25 && meuPlayer.equals("red")) {
			return "./assets/lose_red.png";	
		}else {
			return "";
		}
	}	

	
}

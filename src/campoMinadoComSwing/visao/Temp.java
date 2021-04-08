package campoMinadoComSwing.visao;

import campoMinadoComSwing.modelo.Tabuleiro;

public class Temp {
public static void main(String[] args) {
	Tabuleiro tabuleiro = new Tabuleiro(3,3,9);
	
	tabuleiro.registrarObservador(e -> {
		if(e.isGanhou()) {
			System.out.println("ganhou");
		}else {
			System.out.println("Perdeu");
		}
	});
	tabuleiro.abrir(2, 2);
	tabuleiro.alterarMarcacao(0, 0);
	tabuleiro.alterarMarcacao(0, 1);
	tabuleiro.alterarMarcacao(0, 2);
	tabuleiro.alterarMarcacao(1, 1);
	tabuleiro.alterarMarcacao(1, 2);
	
}
}

package campoMinadoComSwing.visao;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import campoMinadoComSwing.modelo.Tabuleiro;

public class PainelTabuleiro extends JPanel {
	@SuppressWarnings("serial")
	public PainelTabuleiro(Tabuleiro tabuleiro) {
		setLayout(new GridLayout(tabuleiro.getLinhas(), tabuleiro.getColunas()));
		tabuleiro.paraCada(c -> add(new BotaoCampo(c)));
		tabuleiro.registrarObservador(e -> {
			// todo
			//tabuleiro.reiniciar();
			SwingUtilities.invokeLater(() -> {
				if(e.isGanhou()) {
					JOptionPane.showMessageDialog(this, "Ganhou");
				}else {
					JOptionPane.showMessageDialog(this, "Perdeu");
				}
				tabuleiro.reiniciar();
			});
		});
	}
}

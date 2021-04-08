package campoMinadoComSwing.modelo;


import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;



public class Campo {
   
   private final int linha;
   private final int coluna;

   private boolean aberto = false;
   private boolean  minado = false; 
   private boolean marcado = false;
   
   private List<Campo> vizinhos = new ArrayList<>(); //relação 1 pra 1 com campo mesmo
   private List<CampoObservador> observadores = new ArrayList<>();
 // private List<BiConsumer<Campo, CampoEvento>> obser
  
   
   public Campo(int linha, int coluna){ // constructor
	   this.linha = linha;
	   this.coluna= coluna;
   }
   public void registrarObservador(CampoObservador observador) {
	   observadores.add(observador);
   }
   private void notificarObservadores(CampoEvento evento) {
	   observadores.stream().forEach(o -> o.eventoOcorreu(this, evento));
   }
   
   public boolean adicionarVizinho(Campo vizinho) { // ditra regra se um vizinho pode ser vizinho do outro
	   boolean linhaDiferente = linha != vizinho.linha;
	   boolean colunaDiferente = coluna != vizinho.coluna;
	   boolean diagonal = linhaDiferente && colunaDiferente;
	   
	   int deltaLinha = Math.abs(linha - vizinho.linha);
	   int deltaColuna= Math.abs(coluna - vizinho.coluna);
	   int deltaGeral = deltaColuna + deltaLinha;
	   if(deltaGeral == 1 && !diagonal ) {
		   vizinhos.add(vizinho);
		   return true;
	   }else if(deltaGeral == 2 && diagonal) {
		   vizinhos.add(vizinho);
		   return true;
	   }else {
		   return false;
	   }   
   }
   public void alternarMarcacao() {
	   if(!aberto) {
		   marcado = !marcado;
		   if(marcado) {
			   notificarObservadores(CampoEvento.MARCAR);
		   }else {
			   notificarObservadores(CampoEvento.DESMARCAR);
		   }
	   }
   }
   public boolean abrir() {
	   if(!aberto && !marcado) {
		   aberto = true;		   
		   if(minado) {
			  // todo implwmwntar nova versão
			   notificarObservadores(CampoEvento.EXPLODIR);
			   return true;
			   // fixme Erro
		   }
		   setAberto(true);
		   aberto = true;
		   notificarObservadores(CampoEvento.ABRIR);
		   if(viznhancaSegura()) {
			   vizinhos.forEach(v -> v.abrir()); // se for segura abri os vizinhos
		   }
		   return true;
	   }else {
	   return false;
	   }
   }
 public  boolean viznhancaSegura() {
	   return vizinhos.stream().noneMatch(v -> v.minado); //match em cima do predicado, vizinhanca segura
   }
  public void minar() {
	     minado = true;
   }
  public boolean isMinado() {
	  return minado;
  }
   public boolean isMarcado() {
	   return marcado;
   }
   public boolean isAberto() {
	   return aberto;
   }
   public void setAberto(boolean aberto) {
	this.aberto = aberto;
	if(aberto) {
		notificarObservadores(CampoEvento.ABRIR);
	}
}


public boolean isFechado() {
	   return !isAberto();
   }


public int getLinha() {
	return linha;
}


public int getColuna() {
	return coluna;
}
boolean objetivoAlcancado() {
	boolean desvendado = !minado && aberto;
	boolean protegido = minado && marcado;
	return desvendado || protegido;
}
public int minasVizinhanca() {
	return (int) vizinhos.stream().filter(v -> v.minado).count(); // filtrando vizinhos q tem mina, count contar
}
void reiniciar() {
	aberto = false;
	minado = false;
	marcado = false;
	notificarObservadores(CampoEvento.REINICAR);
}
}

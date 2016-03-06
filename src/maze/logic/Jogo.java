package maze.logic;

import java.awt.Point;
import java.util.Random;
import maze.cli.CommandLineInterface;
import maze.logic.Dragao.EstadoDragao;
import maze.logic.Heroi.EstadoHeroi;
import java.util.ArrayList;

public class Jogo {

	private Tabuleiro tab;
	//private Dragao dragao;
	private Heroi heroi;
	private Espada espada;
	private boolean fimDeJogo=false;
	private CommandLineInterface cli;
	private int modoJogo;
	public ArrayList<Dragao> dragoes= new ArrayList<Dragao>();

	public enum Movimento{
		DIREITA,ESQUERDA,CIMA,BAIXO
	}

	public Jogo(){
		tab=new Tabuleiro();
		//dragao=new Dragao(3,1,'D');
		heroi=new Heroi(1,1,'H');
		espada=new Espada(4,1,'E');
		cli=new CommandLineInterface();
		//tab.inserirChar(dragao.getP(), dragao.getSimbolo());
		tab.inserirChar(heroi.getP(), heroi.getSimbolo());
		tab.inserirChar(espada.getP(), espada.getSimbolo());
	}

	public void colocaDragoes(int numeroDragoes){

		for(int i=0;i<numeroDragoes;i++){
			Point ponto=cli.retornaCoordenadaDragao();
			if (podeColocarDragao(ponto)){
				Dragao d = new Dragao (ponto.x,ponto.y,'D');
				dragoes.add(d);
				tab.inserirChar(ponto,'D');
			}
			else {
				cli.imprimir("Coordenada inv�lida. Repita !");
				i--;
			}
		}

	}

	public boolean podeColocarDragao(Point ponto){

		if (tab.retornaChar(ponto)==' ')
			return true;

		return false;

	}

	public boolean podeMoverHeroi(Movimento direcao ){
		boolean valido=false;

		//	int linha=heroi.getP().x,coluna=heroi.getP().y;
		Point p= (Point)heroi.getP().clone();

		switch (direcao){

		case DIREITA:
			p.y+=1;
			break;
		case ESQUERDA:
			p.y-=1;
			break;
		case BAIXO:
			p.x+=1;
			break;
		case CIMA:
			p.x-=1;
			break;
		default:
			break;
		}

		if(dragoesVivos()){
			if(tab.retornaChar(p)!='X' && tab.retornaChar(p)!='S' && tab.retornaChar(p)!= 'd'){
				valido=true;
			}
		}
		else if(tab.retornaChar(p)!='X')
			valido=true;

		return valido;

	}

	public boolean dragoesVivos(){

		for (int i=0;i<dragoes.size();i++){
			if(dragoes.get(i).getEstado()!=EstadoDragao.MORTO)
				return true;
		}
		return false;
	}

	public void moverHeroi(){

		Movimento direcao=cli.lerDirecao();
		tab.inserirChar(heroi.getP(),' ');
		moveHeroiSegundoDirecao(direcao);
	
	}
	
	public void moveHeroiSegundoDirecao(Movimento direcao){
		switch (direcao){
		case ESQUERDA: 
			if(podeMoverHeroi(Movimento.ESQUERDA))
				heroi.moverEsquerda();
			break;
		case DIREITA: 
			if(podeMoverHeroi(Movimento.DIREITA)){
				heroi.moverDireita();
			}
			break;
		case BAIXO:
			if(podeMoverHeroi(Movimento.BAIXO))
				heroi.moverBaixo();
			break;
		case CIMA:
			if(podeMoverHeroi(Movimento.CIMA))
				heroi.moverCima();
			break;
		default:
			break;
		}

		verificaSaida();
		tab.inserirChar(heroi.getP(), heroi.getSimbolo());
		
	}

	public void verificaSaida(){

		if(tab.retornaChar(heroi.getP())=='S')
			fimDeJogo=true;
	}

	public void verificaEspada(){

		if(heroi.getP().x==espada.getP().x && heroi.getP().y==espada.getP().y){
			heroi.setSimbolo('A');
			heroi.setEstado(EstadoHeroi.ARMADO);
			tab.inserirChar(espada.getP(),heroi.getSimbolo());
		}
	}

	public void verificaDragao(){

		for (int i=0;i<dragoes.size();i++){

			if(dragoes.get(i).getEstado()!= EstadoDragao.MORTO){
				boolean mesmaPos=false;

				if(dragoes.get(i).getEstado()==EstadoDragao.ACORDADO || heroi.getSimbolo()=='A'){

					if(heroi.getP().x-1==dragoes.get(i).getP().x && heroi.getP().y==dragoes.get(i).getP().y)
						mesmaPos=true;
					else if(heroi.getP().x+1==dragoes.get(i).getP().x && heroi.getP().y==dragoes.get(i).getP().y)
						mesmaPos=true;
					else if(heroi.getP().x==dragoes.get(i).getP().x && heroi.getP().y-1==dragoes.get(i).getP().y)
						mesmaPos=true;
					else if(heroi.getP().x==dragoes.get(i).getP().x && heroi.getP().y+1==dragoes.get(i).getP().y)
						mesmaPos=true;

					if (mesmaPos){
						if (heroi.getEstado()==EstadoHeroi.ARMADO){
							dragoes.get(i).setEstado(EstadoDragao.MORTO);
							tab.inserirChar(dragoes.get(i).getP(), ' ');
						}
						else if (dragoes.get(i).getEstado()==EstadoDragao.ACORDADO) {
							fimDeJogo=true;
						}
					}
				}
			}
		}
	}

	public void dragaoEEspada(Point p, int indice){

		if( dragoes.get(indice).isPorCimaEspada()){	
			dragoes.get(indice).setPorCimaEspada(false);
			tab.inserirChar(dragoes.get(indice).getP(),'E');
		}
		else tab.inserirChar(dragoes.get(indice).getP(),' ');

		dragoes.get(indice).setP(p);

		if(tab.retornaChar(p)== 'E'){
			dragoes.get(indice).setPorCimaEspada(true);
			tab.inserirChar(p, 'F');
		}
		else {
			tab.inserirChar(p, dragoes.get(indice).getSimbolo());
		}
	}

	public void dragaoADormir(int indice){
		Random rn=new Random();
		int direcao;

		direcao=rn.nextInt(2);

		switch (direcao){
		case 0: 
			break;
		case 1:
			dragoes.get(indice).setEstado(EstadoDragao.ACORDADO);
			dragoes.get(indice).setSimbolo('D'); 
			tab.inserirChar(dragoes.get(indice).getP(), 'D');
			break;
		}
	}

	public void dragaoAcordado(int indice){

		Random rn=new Random();

		int direcao;
		boolean valido=false;
		Point p;

		direcao=rn.nextInt(6);
		p=(Point)dragoes.get(indice).getP().clone();

		switch (direcao){
		case 0: 
			p.y-=1;
			break;
		case 1: 
			p.y+=1;
			break;
		case 2:
			p.x+=1;
			break;
		case 3:
			p.x-=1;
			break;
		case 4:
			break;
		case 5:
			if (modoJogo==3){
				dragoes.get(indice).setEstado(EstadoDragao.DORMIR);
				dragoes.get(indice).setSimbolo('d');
				tab.inserirChar(p, 'd');
			}
			break;

		}
		if(DragaoPodeMover(p)){
			valido=true;
		}

		if(valido){
			dragaoEEspada(p,indice);
		}
	}
	
	public boolean DragaoPodeMover(Point p){
		if(tab.retornaChar(p)!='X' && tab.retornaChar(p)!= 'S' && tab.retornaChar(p)!= 'D' && tab.retornaChar(p)!= 'd')
			return true;
		return false;
	}

	public void moverDragao(){

		for (int i=0;i<dragoes.size();i++){
			if (dragoes.get(i).getEstado()==EstadoDragao.ACORDADO){
				dragaoAcordado(i);
			}
			else if (dragoes.get(i).getEstado()==EstadoDragao.DORMIR ){
				dragaoADormir(i);
			}
		}

	}


	public boolean jogada( ){
		moverHeroi();
		verificaEspada();
		verificaDragao();
		if (modoJogo!=1){
			moverDragao();
		}
		verificaDragao();

		return fimDeJogo;
	}

	public Tabuleiro getTab() {
		return tab;
	}

	public int getModoJogo() {
		return modoJogo;
	}

	public void setModoJogo(int modoJogo) {
		this.modoJogo = modoJogo;
	}

	public Heroi getHeroi() {
		return heroi;
	}

	public void setHeroi(Heroi heroi) {
		this.heroi = heroi;
	}

	public boolean isFimDeJogo() {
		return fimDeJogo;
	}

	public void setFimDeJogo(boolean fimDeJogo) {
		this.fimDeJogo = fimDeJogo;
	}

	public Espada getEspada() {
		return espada;
	}

	public void setEspada(Espada espada) {
		this.espada = espada;
	}

}
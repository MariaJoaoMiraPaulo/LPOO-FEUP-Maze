package maze.test;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import maze.logic.Dragao;
import maze.logic.Jogo;
import maze.logic.Jogo.Movimento;
import maze.logic.Dragao.EstadoDragao;

public class TestMazeRandomMove {

	@Test
	public void DragaoDormirNaoMata() {
		Jogo j= new Jogo();
		Dragao d=new Dragao(3,1,'D');
		assertEquals(false,j.isFimDeJogo());
		j.dragoes.add(d);
		j.dragoes.get(0).moverCima();
		j.dragoes.get(0).setEstado(EstadoDragao.DORMIR);
		j.verificaDragao();
		assertEquals(false,j.isFimDeJogo());
	}
	
	@Test
	public void MoverDragaoCimaEspada(){
		Jogo j= new Jogo();
		Dragao d=new Dragao(3,1,'D');
		j.dragoes.add(d);
		Point p=new Point(3,1);
		p=j.getEspada().p;
		assertEquals(false,j.podeColocarDragao(p));
	}
	
	@Test
	public void MoverDragaoCelulaLivre(){
		Jogo j= new Jogo();
		Dragao d=new Dragao(3,1,'D');
		j.dragoes.add(d);
		Point p=new Point(6,1);
		assertEquals(true,j.podeColocarDragao(p));
	}
	
	@Test
	public void MoverDragaoCimaDragao(){
		Jogo j= new Jogo();
		Point p=new Point(6,1);
		Dragao d=new Dragao(3,1,'D');
		Dragao d1=new Dragao(6,1,'D');
		j.dragoes.add(d);
		j.getTab().inserirChar(d.p, 'D');
		assertEquals(true,j.dragaoPodeMover(p));
		j.dragoes.add(d1);
		j.getTab().inserirChar(d1.p, 'D');
		assertEquals(false,j.dragaoPodeMover(p));
	}
	
	@Test
	public void MoverDragaoCimaDragaoDormir(){
		Jogo j= new Jogo();
		Point p=new Point(6,1);
		Dragao d=new Dragao(3,1,'D');
		Dragao d1=new Dragao(6,1,'D');
		d1.setEstado(EstadoDragao.DORMIR);
		j.dragoes.add(d);
		j.getTab().inserirChar(d.p, 'D');
		assertEquals(true,j.dragaoPodeMover(p));
		j.dragoes.add(d1);
		j.getTab().inserirChar(d1.p, 'd');
		assertEquals(false,j.dragaoPodeMover(p));
	}
	
	@Test
	public void MoverDragaoParaSaida(){
		Jogo j= new Jogo();
		Dragao d=new Dragao(1,8,'D');
		j.dragoes.add(d);
		for (int i=0;i<4;i++){
			d.moverBaixo();
		}
		d.moverDireita();
		assertEquals(false,j.dragaoPodeMover(d.p));
	}
	
	@Test
	public void MoverDragaoParaParede(){
		Jogo j= new Jogo();
		Dragao d=new Dragao(3,1,'D');
		j.dragoes.add(d);
		d.moverDireita();
		assertEquals(false,j.dragaoPodeMover(d.p));
	}
	
	@Test
	public void ExistemDragoesVivos(){
		Jogo j= new Jogo();
		Dragao d=new Dragao(3,1,'D');
		Dragao d1=new Dragao(6,1,'D');
		Dragao d2=new Dragao(1,6,'D');
		Dragao d3=new Dragao(1,4,'D');
		j.dragoes.add(d);
		j.dragoes.add(d1);
		j.dragoes.add(d2);
		j.dragoes.add(d3);
		j.dragoes.get(0).setEstado(EstadoDragao.MORTO);
		j.dragoes.get(1).setEstado(EstadoDragao.MORTO);
		j.dragoes.get(2).setEstado(EstadoDragao.MORTO);
		assertEquals(true,j.dragoesVivos());
		j.dragoes.get(3).setEstado(EstadoDragao.MORTO);
		assertEquals(false,j.dragoesVivos());
	}
	
	@Test
	public void DragaoCimaEspada(){
		Jogo j= new Jogo();
		Dragao d=new Dragao(5,1,'D');
		j.dragoes.add(d);
		//j.dragoes.get(0).moverCima();
		Point p=new Point (4,1);
		j.dragaoEEspada(p, 0);
		assertEquals('F',j.getTab().retornaChar(j.dragoes.get(0).p));
	}
	
	@Test
	public void DragaoNaoFicaCimaEspada(){
		Jogo j= new Jogo();
		Dragao d=new Dragao(5,1,'D');
		j.dragoes.add(d);
		//j.dragoes.get(0).moverCima();
		Point p=new Point (6,1);
		j.dragaoEEspada(p, 0);
		assertEquals('D',j.getTab().retornaChar(j.dragoes.get(0).p));
	}
	
	@Test
	public void NaSaidaComDragoesVivos(){
		Jogo j= new Jogo();
		Dragao d=new Dragao(3,1,'D');
		Dragao d1=new Dragao(1,4,'D');
		Dragao d2=new Dragao(1,6,'D');
		Dragao d3=new Dragao(6,1,'D');
		j.dragoes.add(d);
		j.dragoes.add(d1);
		j.dragoes.add(d2);
		j.dragoes.add(d3);
		j.dragoes.get(0).setEstado(EstadoDragao.MORTO);
		j.dragoes.get(1).setEstado(EstadoDragao.MORTO);
		j.dragoes.get(2).setEstado(EstadoDragao.MORTO);
		for(int i=0;i<7;i++)
			j.moveHeroiSegundoDirecao(Movimento.DIREITA);
		for(int i=0;i<4;i++)
			j.moveHeroiSegundoDirecao(Movimento.BAIXO);
		j.podeMoverHeroi(Movimento.DIREITA);
		assertEquals(false,j.isFimDeJogo());
	}
	
	@Test
	public void NaSaidaDragoesMortosGanha(){
		Jogo j= new Jogo();
		Dragao d=new Dragao(3,1,'D');
		Dragao d1=new Dragao(1,4,'D');
		Dragao d2=new Dragao(1,6,'D');
		Dragao d3=new Dragao(6,1,'D');
		j.dragoes.add(d);
		j.dragoes.add(d1);
		j.dragoes.add(d2);
		j.dragoes.add(d3);
		j.dragoes.get(0).setEstado(EstadoDragao.MORTO);
		j.dragoes.get(1).setEstado(EstadoDragao.MORTO);
		j.dragoes.get(2).setEstado(EstadoDragao.MORTO);
		j.dragoes.get(3).setEstado(EstadoDragao.MORTO);
		for(int i=0;i<7;i++)
			j.moveHeroiSegundoDirecao(Movimento.DIREITA);
		for(int i=0;i<4;i++)
			j.moveHeroiSegundoDirecao(Movimento.BAIXO);
		//j.getHeroi().moverBaixo();
		j.moveHeroiSegundoDirecao(Movimento.DIREITA);
		assertEquals(true,j.isFimDeJogo());
	}
	
	@Test
	public void DragaoSaiCimaEspada(){
		Jogo j= new Jogo();
		Dragao d=new Dragao(3,1,'D');
		j.dragoes.add(d);
		Point p=new Point (4,1);
		j.dragaoEEspada(p, 0);
		assertEquals('F',j.getTab().retornaChar(j.dragoes.get(0).p));
		assertEquals(true,j.dragoes.get(0).isPorCimaEspada());
		Point p2=new Point (5,1);
		j.dragaoEEspada(p2, 0);
		assertEquals('E',j.getTab().retornaChar(j.getEspada().p));
		assertEquals('D',j.getTab().retornaChar(j.dragoes.get(0).p));
	}
	
	@Test
	public void DragaoDormirNaoSeMove(){
		Jogo j= new Jogo();
		Dragao d=new Dragao(3,1,'D');
		Point p=new Point(3,1);
		j.dragoes.add(d);
		j.dragoes.get(0).setEstado(EstadoDragao.DORMIR);
		j.moverDragao();
		assertEquals(p,j.dragoes.get(0).p);
	}
	
	@Test
	public void HeroiMataUmDragaoApenas(){
		Jogo j= new Jogo();
		Dragao d=new Dragao(1,3,'D');
		Dragao d1=new Dragao(1,6,'D');
		Point p= new Point(1,3);
		j.dragoes.add(d);
		j.dragoes.add(d1);
		assertEquals('H', j.getHeroi().getSimbolo());
		for(int i=0;i<3;i++)
			j.getHeroi().moverBaixo();
		j.verificaEspada();
		assertEquals('A', j.getHeroi().getSimbolo());
		for(int i=0;i<3;i++)
			j.moveHeroiSegundoDirecao(Movimento.CIMA);
		for(int i=0;i<1;i++)
			j.moveHeroiSegundoDirecao(Movimento.DIREITA);
		j.verificaDragao();
		assertEquals(' ',j.getTab().retornaChar(p));
		assertEquals(true,j.dragoesVivos());
	}
	
	@Test
	public void porDragaoDormir(){
		Jogo j= new Jogo();
		Dragao d=new Dragao(1,3,'D');
		Point p=new Point(1,3);
		j.dragoes.add(d);
		assertEquals('D',j.dragoes.get(0).getSimbolo());
		assertEquals(EstadoDragao.ACORDADO,j.dragoes.get(0).getEstado());
		j.porDragaoDormir(0, p);
		assertEquals('d',j.dragoes.get(0).getSimbolo());
		assertEquals(EstadoDragao.DORMIR,j.dragoes.get(0).getEstado());
	}
	
	@Test
	public void acordarDragao(){
		Jogo j= new Jogo();
		Dragao d=new Dragao(1,3,'D');
		Point p=new Point(1,3);
		j.dragoes.add(d);
		assertEquals('D',j.dragoes.get(0).getSimbolo());
		assertEquals(EstadoDragao.ACORDADO,j.dragoes.get(0).getEstado());
		j.porDragaoDormir(0, p);
		assertEquals('d',j.dragoes.get(0).getSimbolo());
		assertEquals(EstadoDragao.DORMIR,j.dragoes.get(0).getEstado());
		j.acordarDragao(0);
		assertEquals('D',j.dragoes.get(0).getSimbolo());
		assertEquals(EstadoDragao.ACORDADO,j.dragoes.get(0).getEstado());
	}
	
	
	
	
	
	

}

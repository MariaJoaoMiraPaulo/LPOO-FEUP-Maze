package maze.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import maze.logic.Jogo;
import maze.logic.Jogo.Movimento;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JScrollPane;

public class JanelaJogo {

	private JFrame frmJogoDoLabirinto;
	private JTextField dimensaoLabirinto;
	private JTextField numeroDragoes;
	private JButton btnDireita;
	private JButton btnBaixo;
	private JButton btnEsquerda;
	private JButton btnCima;
	private Jogo jogo;
	private JScrollPane scrollPane;
	private JTextArea mostradorLabirinto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaJogo window = new JanelaJogo();
					window.frmJogoDoLabirinto.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JanelaJogo() {
		initialize();
	}


	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {
		frmJogoDoLabirinto = new JFrame();
		frmJogoDoLabirinto.setResizable(false);
		frmJogoDoLabirinto.setTitle("Jogo do Labirinto");
		frmJogoDoLabirinto.setBounds(100, 100, 623, 466);
		frmJogoDoLabirinto.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJogoDoLabirinto.getContentPane().setLayout(null);

		JLabel lblDimensaoDoLabirinto = new JLabel("Dimensao do labirinto");
		lblDimensaoDoLabirinto.setBounds(54, 41, 174, 14);
		frmJogoDoLabirinto.getContentPane().add(lblDimensaoDoLabirinto);

		JLabel lblNumeroDeDragoes = new JLabel("Numero de dragoes\r\n");
		lblNumeroDeDragoes.setBounds(54, 70, 141, 14);
		frmJogoDoLabirinto.getContentPane().add(lblNumeroDeDragoes);

		JLabel lblTipoDeDragoes = new JLabel("Tipo de dragoes\r\n");
		lblTipoDeDragoes.setBounds(54, 102, 114, 14);
		frmJogoDoLabirinto.getContentPane().add(lblTipoDeDragoes);

		dimensaoLabirinto = new JTextField();
		dimensaoLabirinto.setBounds(207, 38, 108, 20);
		frmJogoDoLabirinto.getContentPane().add(dimensaoLabirinto);
		dimensaoLabirinto.setColumns(10);

		numeroDragoes = new JTextField();
		numeroDragoes.setColumns(10);
		numeroDragoes.setBounds(207, 67, 108, 20);
		frmJogoDoLabirinto.getContentPane().add(numeroDragoes);

		JComboBox modosJogo = new JComboBox();
		modosJogo.setModel(new DefaultComboBoxModel(new String[] {"Estaticos", "Moveis", "A dormir"}));
		modosJogo.setSelectedIndex(1);
		modosJogo.setBounds(204, 100, 111, 20);
		frmJogoDoLabirinto.getContentPane().add(modosJogo);

		btnCima = new JButton("Cima");
		btnCima.setEnabled(false);
		btnCima.setBounds(438, 194, 117, 29);
		frmJogoDoLabirinto.getContentPane().add(btnCima);
		
		btnEsquerda = new JButton("Esquerda");
		btnEsquerda.setEnabled(false);
		btnEsquerda.setBounds(382, 235, 117, 29);
		frmJogoDoLabirinto.getContentPane().add(btnEsquerda);
		
		btnDireita = new JButton("Direita");
		btnDireita.setEnabled(false);
		btnDireita.setBounds(500, 235, 117, 29);
		frmJogoDoLabirinto.getContentPane().add(btnDireita);
		
		btnBaixo = new JButton("Baixo");
		btnBaixo.setEnabled(false);
		btnBaixo.setBounds(438, 276, 117, 29);
		frmJogoDoLabirinto.getContentPane().add(btnBaixo);

		
		btnCima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processarDirecao(Movimento.CIMA);
			}
		});
		
		btnEsquerda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processarDirecao(Movimento.ESQUERDA);
			}
		});
		
		btnDireita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processarDirecao(Movimento.DIREITA);
			}

		});
		
		
		btnBaixo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				processarDirecao(Movimento.BAIXO);
			}

		});
		
		JButton btnGerarLabirinto = new JButton("Gerar Labirinto");
		btnGerarLabirinto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nDragoes=0;
				try{
					if(numeroDragoes.getText().equals("")){
						JOptionPane.showMessageDialog(frmJogoDoLabirinto, "Preencha a caixa com o numero de dragoes");
						return;
					}
					else {
						nDragoes=Integer.parseInt(numeroDragoes.getText());
						if(nDragoes>5){
							JOptionPane.showMessageDialog(frmJogoDoLabirinto, "Dragoes a mais!");
							return;
						}
					}
				}
				catch (NumberFormatException ex){
					JOptionPane.showMessageDialog(frmJogoDoLabirinto, "Formato n�o v�lido");
					return;
				}

				int dimensao=0;
				try{
					if(dimensaoLabirinto.getText().equals("")){
						dimensao=11;
					}
					else {
						dimensao=Integer.parseInt(dimensaoLabirinto.getText());
						if(dimensao%2==0){
							JOptionPane.showMessageDialog(frmJogoDoLabirinto, "A dimensao do labirinto tem de ser impar!");
							return;
						}
					}
				}
				catch (NumberFormatException ex){
					JOptionPane.showMessageDialog(frmJogoDoLabirinto, "Formato n�o v�lido");
					return;
				}

				jogo=new Jogo(nDragoes,dimensao);
				if(modosJogo.getSelectedItem().equals("Estaticos"))
					jogo.setModoJogo(1);
				else if(modosJogo.getSelectedItem().equals("Moveis"))
					jogo.setModoJogo(2);
				else jogo.setModoJogo(3);
				
				mostradorLabirinto.setText(jogo.getTab().paraString());
				setEnableEmVariosBotoes(true);

			}
		});
		btnGerarLabirinto.setBounds(406, 38, 174, 23);
		frmJogoDoLabirinto.getContentPane().add(btnGerarLabirinto);

		JButton btnTerminarPrograma = new JButton("Terminar Programa");
		btnTerminarPrograma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnTerminarPrograma.setBounds(406, 88, 174, 23);
		frmJogoDoLabirinto.getContentPane().add(btnTerminarPrograma);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(55, 176, 301, 250);
		frmJogoDoLabirinto.getContentPane().add(scrollPane);
		
		mostradorLabirinto = new JTextArea();
		mostradorLabirinto.setFont(new Font("Courier New", Font.PLAIN, 13));
		mostradorLabirinto.setEditable(false);
		scrollPane.setViewportView(mostradorLabirinto);



	}
	
	public void setEnableEmVariosBotoes(boolean flag){
		btnBaixo.setEnabled(flag);
		btnCima.setEnabled(flag);
		btnEsquerda.setEnabled(flag);
		btnDireita.setEnabled(flag);
	}
	
	public void processarDirecao(Movimento direcao){
		if(jogo.jogada(direcao))
			acabouJogo();
		mostradorLabirinto.setText(jogo.getTab().paraString());
	}
	
	public void acabouJogo(){
		setEnableEmVariosBotoes(false);
		if(!jogo.dragoesVivos())
			JOptionPane.showMessageDialog(frmJogoDoLabirinto, "Ganhou o jogo!!");
		else JOptionPane.showMessageDialog(frmJogoDoLabirinto, "Perdeu o jogo!!");
	}
}

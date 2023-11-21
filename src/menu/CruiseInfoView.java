package menu;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/** Janela onde aparece a informação de um cruzeiro 
 */
class CruiseInfoView {
	
	/** Indica qual o índice da experiência escolhida, -1 quando não tem experiências escolhida */
	private int experienciaIndex = -1;
	/** Indica qual o número de camarotes escolhido */
	private int numCamarotes;
	/** ìndice da experiência que é adicionada à janela */
	private int proxExpe = 0;
	
	/** formatador usado na apresentação das datas */
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	// Elementos gráficos
	private JDialog janela;
	private JTable escalasTb;
	private JLabel nomeLbl = new JLabel( );
	private JLabel dataLbl = new JLabel( );
	private DefaultTableModel modeloEscalas;
	private JPanel experienciaPanel;
	private JComboBox<Integer> camarotesBox;

	/** cria a janela de informação do cruzeiro
	 * @param owner janela mãe desta
	 */
	public CruiseInfoView(Frame owner) {		
		janela = new JDialog( owner, true );
		janela.setSize( 500, 600);
		janela.setContentPane( setupInterface() );
		janela.setLocationRelativeTo( owner );
	}

	/** Método chamado quando o utilizador escolhe um cruzeiro e
	 * se pretende verificar se o pode reservar
	 * @return true se puder ser reservado
	 */
	private boolean podeReservar() {
		// TODO ver se pode reservar
		// TODO ver se o cruzeiro ainda não partiu
		if( Math.abs( 2 ) == -2 ) {
			JOptionPane.showMessageDialog( janela, "O cruzeiro já partiu");
			return false;
		}
		// TODO ver se tem camarotes livres
		if( Math.abs( 2 ) == -2 ) {
			JOptionPane.showMessageDialog( janela, "Não há camarotes livres suficientes!");
			return false;
		}
		return true;
	}
	
	/** Apresenta o total ao utilizador e pede para confirmar a reserva
	 * @param preco custo por pessoa da reserva 
	 * @param numCamarotes número de camarotes escolhido
	 * @return true se confirmou a reserva
	 */
	private boolean confirmarPagamento(long preco, int numCamarotes ) {
		long total =  numCamarotes * preco;
		return JOptionPane.showConfirmDialog( janela, "Reservar tem um custo de " + total + "€. Continuar?",
				                              "Confrmação de reserva", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}

	/** Apresenta a janela e espera que o utilizador escolha
	 * uma experiência das disponíveis
	 * 
	 * @return  o indice da experiencia que o utilizador escolheu, -1 se não reservou
	 */
	public int mostrarCruzeiro( ) {
		janela.setVisible( true );
		return experienciaIndex;
	}
	
	/** indica o índice da experiência escolhida
	 * @return o índice da experiência escolhida, -1 se não escolheu nenhuma
	 */
	public int getExperienciaIndex() {
		return experienciaIndex;
	}
	
	/** Indica o número de camarotes pretendidos. Este valor só é válido
	 * se o utilizador tiver escolhido uma experiência
	 * @return o número de camarotes pretendidos
	 */
	public int getNumeroCamarotes( ) {
		return numCamarotes;
	}
	
	/** Define qual a informação sobre o cruzeiro que deve ser apresentada
	 * @param nome nome do cruzeiro
	 * @param numDias duração do cruzeiro
	 * @param preco preço base
	 * @param partida data de partida
	 */
	public void setCruiseInfo( String nome, int numDias, long preco, LocalDate partida ) {
		janela.setTitle( nome );
		nomeLbl.setText( nome );
		String data = partida.format(formatter);
		dataLbl.setText( data + "  + " + numDias + " dias");
	}
	
	/** Adiciona uma escala à tabela de Escalas
	 * @param dia dia da escala
	 * @param porto porto da escala
	 * @param chegada hora de chegada
	 * @param saida hora de saída
	 */
	public void addEscala( int dia, String porto, String chegada, String saida ) {
		Object data[] = {dia, porto, chegada, saida };
		modeloEscalas.addRow( data );	
		escalasTb.setPreferredScrollableViewportSize( escalasTb.getPreferredSize() );
	}
	
	/** adiciona uma experiência à lista de experiências suportadas
	 * @param descricao nome da experiência
	 * @param preco preco base por camarote
	 */
	public void addExperiencia( String descricao, long preco ) {
		JPanel p = new JPanel( new FlowLayout( FlowLayout.LEFT ) );
		JLabel l = new JLabel( descricao + "   " + preco + "€ por camarote" );
		JButton bt = new JButton( "Reservar" );
		int eid = proxExpe++;
		bt.addActionListener( e ->{ numCamarotes = (Integer)camarotesBox.getSelectedItem();
									if( !podeReservar( ) ) return;							
									if( !confirmarPagamento( preco, numCamarotes ) ) return;
									experienciaIndex = eid;
		                            janela.setVisible(false);} );
		p.add( l );
		p.add( bt );
		experienciaPanel.add( p );
	}

	/** configura a janela
	 * @return o painel da janela 
	 */
	private JPanel setupInterface() {
		SpringLayout layout = new SpringLayout();
		JPanel painel = new JPanel( layout );
		
		painel.add( nomeLbl );
		nomeLbl.setFont( JanelaEscolhaCruzeiros.nomeFont );
		
		painel.add( dataLbl );
		dataLbl.setFont( JanelaEscolhaCruzeiros.mediaFontBold );
		
		String nomesColunas[] = {"Dia", "Porto", "Chegada", "Saída" };
		modeloEscalas = new DefaultTableModel( nomesColunas, 0 );
		
		escalasTb = new JTable( modeloEscalas );
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		escalasTb.getColumnModel().getColumn(0).setMaxWidth(30);
		escalasTb.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		escalasTb.getColumnModel().getColumn(2).setMaxWidth(70);
		escalasTb.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
		escalasTb.getColumnModel().getColumn(3).setMaxWidth(70);
		escalasTb.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
			
		escalasTb.setEnabled( false );
		
		JScrollPane scroll = new JScrollPane( escalasTb ); 
		scroll.setBorder( BorderFactory.createTitledBorder("Itinerário") );
		painel.add( scroll );
		
		Integer numCamarotes[] = {1, 2, 3};		
		camarotesBox = new JComboBox<>( numCamarotes );
		JLabel camarotesLbl = new JLabel( "Número de camarotes: ");
		painel.add( camarotesLbl );
		painel.add( camarotesBox );
		
		experienciaPanel = new JPanel( new GridLayout(0,1) );
		painel.add( experienciaPanel );
		
		layout.putConstraint( SpringLayout.NORTH, nomeLbl, 5, SpringLayout.NORTH, painel);
		layout.putConstraint( SpringLayout.HORIZONTAL_CENTER, nomeLbl, 0, SpringLayout.HORIZONTAL_CENTER, painel);
		
		layout.putConstraint( SpringLayout.NORTH, dataLbl, 5, SpringLayout.SOUTH, nomeLbl );
		layout.putConstraint( SpringLayout.HORIZONTAL_CENTER, dataLbl, 0, SpringLayout.HORIZONTAL_CENTER, painel);
		
		layout.putConstraint( SpringLayout.NORTH, scroll, 5, SpringLayout.SOUTH, dataLbl );
		layout.putConstraint( SpringLayout.HORIZONTAL_CENTER, scroll, 0, SpringLayout.HORIZONTAL_CENTER, painel);
		layout.putConstraint( SpringLayout.EAST, scroll, -5, SpringLayout.EAST, painel);
		layout.putConstraint( SpringLayout.WEST, scroll, 5, SpringLayout.WEST, painel);

		layout.putConstraint( SpringLayout.NORTH, camarotesLbl, 5, SpringLayout.SOUTH, scroll );
		layout.putConstraint( SpringLayout.WEST, camarotesLbl, 5, SpringLayout.WEST, painel);
		layout.putConstraint( SpringLayout.WEST, camarotesBox, 5, SpringLayout.EAST, camarotesLbl );
		layout.putConstraint( SpringLayout.BASELINE, camarotesBox, 0, SpringLayout.BASELINE, camarotesLbl );
		
		layout.putConstraint( SpringLayout.NORTH, experienciaPanel, 5, SpringLayout.SOUTH, camarotesBox );
		layout.putConstraint( SpringLayout.HORIZONTAL_CENTER, experienciaPanel, 0, SpringLayout.HORIZONTAL_CENTER, painel);
		layout.putConstraint( SpringLayout.EAST, experienciaPanel, -5, SpringLayout.EAST, painel);
		layout.putConstraint( SpringLayout.WEST, experienciaPanel, 5, SpringLayout.WEST, painel);
		
		return painel;
	}	
}
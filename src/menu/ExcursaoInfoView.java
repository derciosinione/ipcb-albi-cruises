package menu;

import java.awt.Frame;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/** Janela que apresenta a informação sobre excursões 
 */
@SuppressWarnings("serial")
class ExcursaoInfoView extends JDialog {
	
	/** ìndice da excursão selecioanda, -1 se não houver excursão selecionada */
    private int excursaoIndex = -1;
    /** Número de lugares pretendidos */
    private int numLugares;
    /** Número máximo de pessoas na reserva */
    private int numPessoasMax;

    /** formatador para a apresentaçao das datas */
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	// elementos gráficos
	private JComboBox<Integer> camarotesBox;
	private JButton reservarbt;	
	private JTable excursoesTb;
	private JLabel nomeLbl = new JLabel( );
	private JLabel dataLbl = new JLabel( );
	private DefaultTableModel modeloEscalas;

	/** cria a janela de informação de excursões
	 * @param owner janela mãe
	 * @param porto porto da excursão
	 * @param numPessoasMax número pessoas máximas a considerar
	 */
	public ExcursaoInfoView(Frame owner, String porto, int numPessoasMax) {
		super(owner, true);
		setSize( 500, 300 );
		this.numPessoasMax = numPessoasMax;
		setupInterface();
	}

	/** Método chamado quando o utilizador escolhe uma excursão
	 * para saber se esta pode ser escolhida
	 * @param excIndex índice da excursão a testar
	 * @return true se a excursão pode ser reservada, false caso contrário
	 */
	private boolean podeReservar( int excIndex ) {
		// ver a informação sobre os dados escolhidos pelo utilizador
		numLugares = (Integer)camarotesBox.getSelectedItem();
		int nVagas = (Integer)excursoesTb.getValueAt(excIndex, 2);

		// TODO ver se pode contratar 
		if( Math.abs(2) == -2 ) {
			JOptionPane.showMessageDialog(this, "A sua experiência ainda não permite contratar excursões!");
			return false;
		}
		
		// TODO ver se número de lugares é válido
		if( Math.abs( 2 ) == -2 ){
			JOptionPane.showMessageDialog(this, "Não há vagas para esses lugares!");
			return false;
		}
		return true;
	}

	/** Método chamado para confirmar a reserva da excursão
	 * @param precoBase preço base da excursão
	 * @return true se foi confirmada
	 */
	private boolean confirmarExcursao( long precoBase ) {
		long custo = precoBase * numLugares;
		return JOptionPane.showConfirmDialog( this, "A reserva terá um custo de " + custo + "€. Continuar?",
				                           "Confirmar Excursão", JOptionPane.YES_OPTION) == JOptionPane.YES_OPTION;
	}
	
	/** retorna o índice da excursão selecionada
	 * @return o índice da excursão selecionada, -1 se nenhuma excursão foi selecionada
	 */
	public int getExcursaoIndex() {
		return excursaoIndex;
	}
	
	/** Retorna o número de lugares pretendido
	 * @return o número de lugares pretendido
	 */
	public int getNumeroLugares( ) {
		return numLugares;
	}
	
	/** Define a informação do cruzeiro que será apresentada
	 * @param nome nome do cruzeiro
	 * @param numDias número de dias do cruzeiro
	 * @param preco preco total pago
	 * @param partida data de partida
	 */
	public void setCruiseInfo( String nome, int numDias, long preco, LocalDate partida ) {
		setTitle( nome );
		nomeLbl.setText( nome );
		String data = partida.format(formatter);
		dataLbl.setText( data + "  + " + numDias + " dias");
	}
	
	/** adiciona uma excursão à lista a apresentar
	 * @param nome nome da excursão
	 * @param preco preço por pessoa
	 * @param lugaresVagos lugares vagos disponíveis 
	 */
	public void addExcursao( String nome, long preco, int lugaresVagos ) {
		Object data[] = {nome, preco, lugaresVagos };
		modeloEscalas.addRow( data );	
		excursoesTb.setPreferredScrollableViewportSize( excursoesTb.getPreferredSize() );
	}

	/** configura a janela da aplicação
	 */
	private void setupInterface() {
		SpringLayout layout = new SpringLayout();
		JPanel painel = new JPanel( layout );
		
		painel.add( nomeLbl );
		nomeLbl.setFont( JanelaEscolhaCruzeiros.nomeFont );
		
		painel.add( dataLbl );
		dataLbl.setFont( JanelaEscolhaCruzeiros.mediaFontBold );
		
		String nomesColunas[] = {"Nome", "Preço", "Vagas" };
		modeloEscalas = new DefaultTableModel( nomesColunas, 0 ){
		      @Override
		    public boolean isCellEditable(int row, int column) {
	    		return false;
		    }
		};
		
		excursoesTb = new JTable( modeloEscalas );
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		excursoesTb.getColumnModel().getColumn(1).setMaxWidth(50);
		excursoesTb.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
		excursoesTb.getColumnModel().getColumn(2).setMaxWidth(50);
		excursoesTb.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
		excursoesTb.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		
		JScrollPane scroll = new JScrollPane( excursoesTb ); 
		scroll.setBorder( BorderFactory.createTitledBorder("Itinerário") );
		painel.add( scroll );
		
		Vector<Integer> numCamarotes = new Vector<Integer>();
		for( int i=1; i <= numPessoasMax; i++ )
			numCamarotes.add( i );
		camarotesBox = new JComboBox<>( numCamarotes );
		JLabel camarotesLbl = new JLabel( "Número de lugares: ");
		painel.add( camarotesLbl );
		painel.add( camarotesBox );
		
		reservarbt = new JButton("Reservar");
		reservarbt.addActionListener( e -> {
			int excIndex = excursoesTb.getSelectedRow(); 
			if( excIndex == -1 ) {
				JOptionPane.showMessageDialog(this, "Tem de escolher uma excursão");
				return;
			}
			if( !podeReservar( excIndex ) )
				return;
			if( !confirmarExcursao( (Long)excursoesTb.getValueAt(excIndex, 1) ) )
				return;
			excursaoIndex = excIndex;
			setVisible( false );
		});
		painel.add( reservarbt );
		
		setContentPane(painel);
		
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
		
		layout.putConstraint( SpringLayout.NORTH, reservarbt, 5, SpringLayout.SOUTH, camarotesBox );
		layout.putConstraint( SpringLayout.HORIZONTAL_CENTER, reservarbt, 0, SpringLayout.HORIZONTAL_CENTER, painel);
	}
}
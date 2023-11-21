package menu;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Janela onde o utilizador pode gerir as reservas
 */
@SuppressWarnings("serial")
public class JanelaGestaoReservas extends JFrame {

	/** Formatador para apresentar as datas */ 
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	// elementos gráficos
	private JPanel painelExcursoes;
	private JPanel reservaInfoPanel;
	private JTextField referenciaTf;
	private JLabel reservaLbl;
	private JLabel nomeLbl = new JLabel();
	private JLabel dataLbl = new JLabel();
	private DefaultTableModel modeloEscalas;
	private JTable escalasTb;

	/** Cria a janela de gestão de reservas
	 */
	public JanelaGestaoReservas( ) {
		setTitle( "AlbiCruises - Gestor de reservas" );
		setupJanela( );
	}
	
	/** método chamado quando o utilizador pressiona o botão de pesquisar reserva.
	 * Este método deve preparar a informação da reserva para ser apresentada
	 * @param reservaRef o identificador de reserva que o utilizador escreveu
	 */
	private void apresentarReserva( String reservaRef ) {
		clearReservaInfo();  // limpar a reserva visualizada anteriormente
		
		// TODO ver se há identificador associado
		if( Math.abs(2) == -2 ) {
			JOptionPane.showMessageDialog(this, "Não há reservas com a referência " + reservaRef, "Reserva Desconhecida", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// TODO para a reserva apresentar a informação seguinte
		// TODO a informação do cruzeiro
		String nome = "Cruzeiro Exemplo";
		int numDias = 7;
		LocalDate partida = LocalDate.of(2023, 11, 28);
		setCruiseInfo( nome, numDias, partida );
		
		// TODO informação da reserva
		int numCamarotes = 2;
		String nomeExperiencia ="Experiência Bella";
		long precoPago = 1100;
		setReservaInfo( numCamarotes, nomeExperiencia, precoPago );
		
		// TODO para cada escala colocar
		int dia = 1;
		String nomePorto = "Castelo Branco";
		// atenção, se não tiver hora de chegada/partida deve ser -
		String horaChegada = "10:00", horaPartida = "20:00";
		addEscala( dia, nomePorto, horaChegada, horaPartida );
		
		// TODO para cada excursão já contratada
		int idReservaExcursao = 1, numLugares = 3;
		String nomePortoExcursao = "Castelo Branco";
		String nomeExcursao = "Visita ao Castelo";
		addReservaExcursao( idReservaExcursao, nomePortoExcursao, nomeExcursao, numLugares );		
	}
	
	/** Método chamado quando o utilizador pressiona o botão de ver reservas.
	 * Este método deve colocar numa janela todas as reservas existentes no sistema
	 */
	private void mostrarTodasReservas() {
		Vector<String> listaIds = new Vector<>(); // cria um vetor onde armazenar a info
				
		// TODO para cada reserva adicionar o seu identificador
		listaIds.add( "123ASD") ;
		
		// criar a lista visual
		JList<String> list = new JList<String>( listaIds );
		
		// sempre que se seleciona um elemento copiar este para o textField de pesquisa
		list.addListSelectionListener( e -> { if( list.getSelectedIndex() != -1)
			                                     referenciaTf.setText( list.getSelectedValue() ); } );
		
		// apresentar a lista de reservas
		JOptionPane.showMessageDialog( this, list );
	}

	/** método chamado quando o utilizador escolhe uma escala na tabela de itinerário
	 * Este método deve apresentar todas as excursões naquela escala
	 * Vai apresentar uma janela onde o utilizador pode escolher a excursão a reservar
	 * 
	 * @param dia o dia em que se faz a escala
	 */
	private void mostrarExcursoes(int dia) {
		// TODO criar uma janela de visualização da excursão com a informação indicada
		String nomePorto = "Castelo Branco";
		int maxLugares = 4;
		ExcursaoInfoView ev = new ExcursaoInfoView( this, nomePorto, maxLugares );
		
		// TODO para cada excursão na escala selecionada
		String nomeExcursao = "Visita Castelo Branco";
		long precoPorPessoa = 40;
		int lugaresVagos = 30;
		ev.addExcursao( nomeExcursao, precoPorPessoa, lugaresVagos );
		
		// apresentar a janela de escolha
		ev.setLocationRelativeTo( this );
		ev.setVisible( true );
		
		// Ver se alguma excursão foi escolhida
		// se sim retorna o índice da excursão escolhida
		int excursaoIndex = ev.getExcursaoIndex();  // indice da excursão escolhida (-1 se nenhum foi)
		int numLugares = ev.getNumeroLugares();     // número de lugares pretendido
		if( excursaoIndex == -1 )
			return;

		// TODO reservar a excursão escolhida

		
		// TODO atualizar a janela chamando com a mesma referência
		String reservaRef = "MESMA RESERVA";
		apresentarReserva( reservaRef );			
	}

	/** Método chamado quando o utilizador pressiona o botão
	 * de cancelar para uma excursão
	 * 
	 * @param id identificador da excursão a cancelar
	 */
	public void cancelarExcursao(int id) {		
		// TODO ver qual a excursaõ a cancelar
		//      em principio há sempre, mas pode haver um erro
		if( Math.abs( 2 ) == -2 )
			return;
			
		// TODO ver se pode cancelar a excursão
		if( Math.abs(2) == -2 ) {
			JOptionPane.showMessageDialog( this, "A sua experiência não permite cancelar a Reserva");
			return;
		}
		
		// TODO calvular o custo de cancelamento
		long custo = 50;
		if( JOptionPane.showConfirmDialog( this, "O cancelamento terá um custo de " + custo + "€. Confirmar?",
				                                 "Confirmar cancelamento", JOptionPane.YES_NO_OPTION ) != JOptionPane.YES_OPTION )
			return;
		
		// TODO cancelar a excursão
		
		// TODO atualizar a janela indicando o mesmo número de reserva
		String reservaRef = "MESMA RESERVA";
		apresentarReserva( reservaRef );
	}

	/** Método que define quais as informações do cruzeiro que serão apresentadas 
	 * 
	 * @param nome nome do cruzeiro
	 * @param numDias número de dias de duração
	 * @param partida data da partida
	 */
	public void setCruiseInfo( String nome, int numDias, LocalDate partida ) {
		setTitle( nome );
		nomeLbl.setText( nome );
		String data = partida.format(formatter);
		dataLbl.setText( data + "  + " + numDias + " dias");
	}
	
	/** Método que define quais as informações da reserva que serão mostradas
	 * @param numCamarotes número de camarotes reservados
	 * @param experienciaName nome da experiencia escolhida
	 * @param preco preco total pago
	 */
	public void setReservaInfo( int numCamarotes, String experienciaName, long preco ) {
		reservaLbl.setText( numCamarotes + " camarotes    " + experienciaName + "    " + preco + "€");
	}
	
	/** adiciona uma escala à tabela de Escalas
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
	
	/** adiciona uma excursão à lista de excursões contratadas
	 * @param id identificador da excursão
	 * @param porto porto de escala
	 * @param nome noe da excursão
	 * @param numLugares núm lugares vagos na excursão
	 */
	public void addReservaExcursao( int id, String porto, String nome, int numLugares ) {
		PainelReserva pr = new PainelReserva(id, porto, nome, numLugares);
		painelExcursoes.add( pr );
	}

	/** configura a janela 
	 */
	private void setupJanela( ) {
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		JPanel escolhas = setupPainelReserva( );
		reservaInfoPanel = setupPainelCruzeiro();
		getContentPane().add( escolhas, BorderLayout.NORTH );
		getContentPane().add( reservaInfoPanel, BorderLayout.CENTER );
		setSize( 600, 650 );
	}

	/** cofigura o painel de apresentação de reservas
	 * @return o painel configurado
	 */
	private JPanel setupPainelReserva( ) {
		JPanel painel = new JPanel( );
		painel.setBorder( BorderFactory.createTitledBorder("Escolher Reserva"));
		
		painel.add( new JLabel("Referência reserva: ") );
		
		referenciaTf = new JTextField( 10 );
		painel.add( referenciaTf );
		
		JButton procuraBt = new JButton( "Pesquisar" );
		painel.add( procuraBt );
		
		procuraBt.addActionListener( e -> {
			String idr = referenciaTf.getText().trim();
			if( idr.isBlank() || idr.length() != 6 )
				JOptionPane.showMessageDialog(this, "Referência tem de ter 6 caracteres");
			else {				
				apresentarReserva( idr );
				reservaInfoPanel.setVisible( true );
			}
		} );

		JButton verTodasBt = new JButton("Ver");
		verTodasBt.addActionListener( e -> mostrarTodasReservas() );
		painel.add( verTodasBt );
		
		return painel;
	}

	/** Apaga a informação da reserva anterior
	 */
	private void clearReservaInfo() {
		modeloEscalas.setRowCount(0);	
		painelExcursoes.removeAll();
	}

	/** Configura o painel de informação do cruzeiro
	 * @return o painel configurado
	 */
	private JPanel setupPainelCruzeiro() {		
		SpringLayout layout = new SpringLayout();
		JPanel painel = new JPanel( layout );
		
		painel.add( nomeLbl );
		nomeLbl.setFont( JanelaEscolhaCruzeiros.nomeFont );
		
		painel.add( dataLbl );
		dataLbl.setFont( JanelaEscolhaCruzeiros.mediaFontBold );

		reservaLbl = new JLabel( ); 
		painel.add( reservaLbl );
		reservaLbl.setFont( JanelaEscolhaCruzeiros.nomeFont );
		
		String nomesColunas[] = {"Dia", "Porto", "Chegada", "Saída" };
		modeloEscalas = new DefaultTableModel( nomesColunas, 0 ){
		      @Override
		    public boolean isCellEditable(int row, int column) {
	    		return false;
		    }
		};
		
		escalasTb = new JTable( modeloEscalas );
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		escalasTb.getColumnModel().getColumn(0).setMaxWidth(30);
		escalasTb.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		escalasTb.getColumnModel().getColumn(2).setMaxWidth(70);
		escalasTb.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
		escalasTb.getColumnModel().getColumn(3).setMaxWidth(70);
		escalasTb.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
		
		escalasTb.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		escalasTb.getSelectionModel().addListSelectionListener(
				e -> { int selIndex = ((ListSelectionModel)e.getSource()).getMinSelectionIndex();
					   if( !e.getValueIsAdjusting() && selIndex >= 0 )
						   mostrarExcursoes( selIndex ); } 
		);
		
		
		JScrollPane scroll = new JScrollPane( escalasTb ); 
		scroll.setBorder( BorderFactory.createTitledBorder("Itinerário") );
		painel.add( scroll );
		
		JPanel englobaExcursoes = new JPanel( new GridBagLayout() );
		englobaExcursoes.setBorder( BorderFactory.createTitledBorder("Excursões contratadas"));
		
		painelExcursoes = new PainelListador();	
		painel.add( painelExcursoes );
				
		layout.putConstraint( SpringLayout.NORTH, nomeLbl, 5, SpringLayout.NORTH, painel);
		layout.putConstraint( SpringLayout.HORIZONTAL_CENTER, nomeLbl, 0, SpringLayout.HORIZONTAL_CENTER, painel);
		
		layout.putConstraint( SpringLayout.NORTH, dataLbl, 5, SpringLayout.SOUTH, nomeLbl );
		layout.putConstraint( SpringLayout.HORIZONTAL_CENTER, dataLbl, 0, SpringLayout.HORIZONTAL_CENTER, painel);

		layout.putConstraint( SpringLayout.NORTH, reservaLbl, 5, SpringLayout.SOUTH, dataLbl );
		layout.putConstraint( SpringLayout.HORIZONTAL_CENTER, reservaLbl, 0, SpringLayout.HORIZONTAL_CENTER, painel);

		layout.putConstraint( SpringLayout.NORTH, scroll, 5, SpringLayout.SOUTH, reservaLbl  );
		layout.putConstraint( SpringLayout.EAST, scroll, -5, SpringLayout.EAST, painel);
		layout.putConstraint( SpringLayout.WEST, scroll, 5, SpringLayout.WEST, painel);

		layout.putConstraint( SpringLayout.NORTH, painelExcursoes, 5, SpringLayout.SOUTH, scroll );
		layout.putConstraint( SpringLayout.EAST, painelExcursoes, -5, SpringLayout.EAST, painel);
		layout.putConstraint( SpringLayout.WEST, painelExcursoes, 5, SpringLayout.WEST, painel);
		layout.putConstraint( SpringLayout.SOUTH, painelExcursoes, -5, SpringLayout.SOUTH, painel );
		return painel;
	}	

	/** Painel onde aparece a informação de uma reserva
	 */
	private class PainelReserva extends JPanel {
		private JLabel portoLbl, nomeLbl, nLugaresLbl;
		private JButton cancelarBt;
		
		public PainelReserva( int id, String porto, String nome, int nlugares ) {
			SpringLayout layout = new SpringLayout();
			setLayout( layout );
			portoLbl = new JLabel( porto );
			nomeLbl = new JLabel( nome );
			nLugaresLbl = new JLabel( "" + nlugares );
			cancelarBt = new JButton("X");
			cancelarBt.addActionListener( e -> cancelarExcursao( id ) );
			
			add( portoLbl );
			add( nomeLbl );
			add( nLugaresLbl );
			add( cancelarBt );
			
			layout.putConstraint( SpringLayout.NORTH, portoLbl, 5, SpringLayout.NORTH, this );
			layout.putConstraint( SpringLayout.WEST, portoLbl, 5, SpringLayout.WEST, this );
			layout.putConstraint( SpringLayout.EAST, portoLbl, 100, SpringLayout.WEST, portoLbl );
			
			layout.putConstraint( SpringLayout.BASELINE, nomeLbl, 0, SpringLayout.BASELINE, portoLbl );
			layout.putConstraint( SpringLayout.WEST, nomeLbl, 5, SpringLayout.EAST, portoLbl );
			layout.putConstraint( SpringLayout.EAST, nomeLbl, -5, SpringLayout.WEST, nLugaresLbl );
			
			layout.putConstraint( SpringLayout.BASELINE, nLugaresLbl, 0, SpringLayout.BASELINE, portoLbl );
			layout.putConstraint( SpringLayout.EAST, nLugaresLbl, -5, SpringLayout.WEST, cancelarBt );
			layout.putConstraint( SpringLayout.WEST, nLugaresLbl, -20, SpringLayout.EAST, nLugaresLbl );
			
			layout.putConstraint( SpringLayout.BASELINE, cancelarBt, 0, SpringLayout.BASELINE, portoLbl );
			layout.putConstraint( SpringLayout.EAST, cancelarBt, -5, SpringLayout.EAST, this );
			
			layout.putConstraint( SpringLayout.SOUTH, this, 5, SpringLayout.SOUTH, cancelarBt );
		}
	}
}

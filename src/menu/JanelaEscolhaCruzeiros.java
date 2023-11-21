package menu;

import Storage.Storage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

/** Janela onde o utilizador irá fazer pesquisas por cruzeiros e reservar cruzeiros
 */
@SuppressWarnings("serial")
public class JanelaEscolhaCruzeiros extends JFrame {

	/** enumeração para indicar qual o tipo de porto a pesquisar
	 */
	private enum TipoPorto {
		QUALQUER_PORTO, PORTO_PARTIDA, PORTO_CHEGADA
	}

	/** formatador para apresentar as datas */
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	// elementos gráficos 
	private DefaultListModel<CruiseInfo> modelCruiseInfo;
	private PainelListador painelLista;
	// fontes a usar nas listagens
	static Font nomeFont = new Font("ROMAN", Font.BOLD, 16 );
	static Font mediaFontBold = new Font("ROMAN", Font.BOLD, 13 );
	private static Font mediaFont = new Font("ROMAN", Font.PLAIN, 13 );

	/** cria a janela de escolha de cruzeiros
	 */
	public JanelaEscolhaCruzeiros( ) {
		setTitle( "AlbiCruises - Escolha o seu Cruzeiro" );
		
		// TODO Criar uma lista com todos os nomes dos portos existentes no sistema -> Done
		//      Isto deverá ser feito automaticamente e não como está aqui exemplificado -> Done

		List<String> nomesPortos = new ArrayList<>();

		for (var porto: Storage.Portos)
			nomesPortos.add(porto.getNome());

		setupJanela( nomesPortos );
	}
	
	/** método que pesquisa os cruzeiros para encontrar um cruzeiro que obedeça aos critérios indicados
	 * @param nomePorto nome do porto a procurar, null se servir qualquer porto
	 * @param tp se nomePorto foi especificado, indica se este é o porto de partida,
	 *           de chegada ou um porto numa qualquer escala
	 * @param mes mês em que se pretende o cruzeiro, 0 se servir qualquer mês
	 * @param durMin número de dias mínimo para o cruzeiro
	 * @param durMax número de dias máximo para o cruzeiro
	 */
	private void pesquisarCruzeiros( String nomePorto, TipoPorto tp, int mes, int durMin, int durMax) {
		// TODO fazer a pesquisa de acordo com os critérios estabelecidos
		
		// TODO para cada cruzeiro encontrado chamar o método listarCruzeiro, com
		//      a informação adequada 
		String nome = "As maravilhas do Ocreza", portos = "Castelo Branco, Sernache";
		int id = 1, duracao = 5;
		LocalDate partida = LocalDate.of(2023, 11, 19);
		listarCruzeiro( id, nome, partida, duracao, portos, 200 );		
	}


	/** Método chamado quadno o utilizador pressiona o botão de reservar o cruzeiro
	 * @param id id do cruzeiro que foi selecionado
	 */
	private void reservarCruzeiro(int id) {
		// criar a janela de apresentação do cruzeiro
		CruiseInfoView view = new CruiseInfoView(this);
			
		// TODO para cada escala apresentar a informação seguinte, chamando o método indicado
		String nome = "Exemplo de um Cruzeiro";
		int numDias = 5;
		long preco = 450;
		LocalDate partida = LocalDate.of(2023, 11, 28);
		view.setCruiseInfo( nome, numDias, preco, partida); // método que apresenta a informação
		
		// TODO para cada escala, chamar o método com a informação indicada
		int dia = 1;
		String nomePorto = "Castelo Branco", nomeEscala;
		// atenção, se não tiver hora de chegada/partida deve ser -
		String horaChegada = "10:00", horaPartida = "20:00"; 
		view.addEscala( dia, nomePorto, horaChegada, horaPartida ); // método que apresenta a informação 

		// TODO para cada experiência, chamr o método com a informaçao indicada
		String nomeExperiencia = "Experiência Bella";
		long precoPorPessoa = 300;
		view.addExperiencia( nomeExperiencia, precoPorPessoa ); // método que apresenta a informação

		// apresentar a informação do cruzeiro e esperar pelo resultado
		// que será retornar o índice da experiência selecionada
		// ou -1 se o cliente não quis reservar
		int exp = view.mostrarCruzeiro();		
		if( exp == -1 ) 
			return;		

		// ver quantos camarotes foram selecionados
		int numCamarotes = view.getNumeroCamarotes();
		
		// TODO processar a reserva
		
		
		JOptionPane.showMessageDialog( this, "A sua reserva tem o ID " + "123ASD" );
	}
	
	/** Método chamado quando o utilizador escolhe a opção de ver reservas
	 * @param id id do cruzeiro selecionado
	 */
	public void verReservasCruzeiro(int id) {
		// Criar um vetor de Strings com a informação indicada 
		Vector<String> reservas = new Vector<String>();		
		
		// TODO preencher o vetor com a informação indicada para cada reserva
		String identificador = "123ASD";
		int numCamarotes = 2;
		reservas.add( identificador + " " + numCamarotes + " camarotes"); // mostra a info
		
		// apresentar a informação
		if( reservas.isEmpty() )
			JOptionPane.showMessageDialog( this, "Sem reservas!" ); 
		else
			JOptionPane.showMessageDialog( this, new JList<String>( reservas ));
	}

	/** Método auxiliar que coloca a informação do cruzeiro numa lista a apresentar na janela
	 * @param id identificador do cruzeiro
	 * @param nome nome do cruzeiro
	 * @param saida data de partida do cruzeiro
	 * @param duracao número de dias  
	 * @param portos string com uma lisgaem de todosa os portos por onde o cruzeiro passa
	 * @param preco o preço base do cruzeiro
	 */
	private void listarCruzeiro(int id, String nome, LocalDate saida, int duracao, String portos, long preco) {		
		String data = saida.format(formatter);
		CruiseInfo ci = new CruiseInfo(id, nome, data, duracao, portos, preco);
		painelLista.add( ci.renderer.getComponent() );
	}
	
	/** Configura esta janela 
	 * @param nomesPortos nome dos portos suportados pelo sistema
	 */
	private void setupJanela(List<String> nomesPortos) {
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		JPanel escolhas = setupPainelEscolha( nomesPortos );
		JPanel tabelas = setupPainelCruzeiros( );
		getContentPane().add( escolhas, BorderLayout.NORTH );
		getContentPane().add( tabelas, BorderLayout.CENTER );
		setSize( 600, 650 );
	}

	/** Configura o painel de escolha dos cruzeiros, onde o utilizador
	 * pode definir os critérios de pesquisa
	 * @param nomesPortos lista com os nomes dos portos suportados
	 * @return o painel configurado
	 */
	private JPanel setupPainelEscolha(List<String> nomesPortos) {
		SpringLayout layout = new SpringLayout();
		JPanel painel = new JPanel( layout );
		painel.setBorder( BorderFactory.createTitledBorder("Critérios de seleção"));
		Vector<String> nomes = new Vector<String>();
		nomes.add( "Qualquer Porto");
		nomes.addAll( nomesPortos );
		JComboBox<String> portosBox = new JComboBox<String>( nomes );
		painel.add( portosBox );

		ButtonGroup portosGrp = new ButtonGroup();
		JRadioButton partidaBt = new JRadioButton("partida", true);
		painel.add( partidaBt );
		portosGrp.add( partidaBt );

		JRadioButton chegadaBt = new JRadioButton("chegada");
		painel.add( chegadaBt );
		portosGrp.add( chegadaBt );

		JRadioButton qualquerBt = new JRadioButton("qualquer");
		painel.add( qualquerBt );
		portosGrp.add( qualquerBt );
		
		String meses[] = { "Mês","Janeiro", "Fevereiro","Março", "Abril", "Maio","Junho",
				                          "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro" }; 
		JComboBox<String> mesBox = new JComboBox<String>( meses ); 
		painel.add( mesBox );
		
		String duracoes[] = { "Duração","1-5 dias", "6-10 dias","11-15 dias", "16-25 dias", "+25 dias" };
		int duracoesDias[] = { 1, Integer.MAX_VALUE, 1, 5, 6, 10, 11, 15, 16, 25, 26, Integer.MAX_VALUE };
		JComboBox<String> duracaoBox = new JComboBox<String>( duracoes ); 
		painel.add( duracaoBox );
		
		JButton pesquisaBt = new JButton("Pesquisar");
		painel.add( pesquisaBt );
		
		pesquisaBt.addActionListener( e -> {
			int indexDuracaoes = duracaoBox.getSelectedIndex()*2;
			modelCruiseInfo.clear();
			painelLista.removeAll();
			pesquisarCruzeiros( portosBox.getSelectedIndex() == 0? null: (String)portosBox.getSelectedItem(),
				                partidaBt.isSelected()? TipoPorto.PORTO_PARTIDA: (chegadaBt.isSelected()? TipoPorto.PORTO_CHEGADA: TipoPorto.QUALQUER_PORTO),
				                mesBox.getSelectedIndex(),
				                duracoesDias[indexDuracaoes], duracoesDias[indexDuracaoes+1]);
		});
		
		layout.putConstraint( SpringLayout.NORTH, portosBox, 5, SpringLayout.NORTH, painel);
		layout.putConstraint( SpringLayout.WEST, portosBox, 5, SpringLayout.WEST, painel);
		layout.putConstraint( SpringLayout.EAST, portosBox, 200, SpringLayout.WEST, portosBox);
		
		layout.putConstraint( SpringLayout.BASELINE, partidaBt, 0, SpringLayout.BASELINE, portosBox);
		layout.putConstraint( SpringLayout.WEST, partidaBt, 5, SpringLayout.EAST, portosBox);

		layout.putConstraint( SpringLayout.BASELINE, chegadaBt, 0, SpringLayout.BASELINE, portosBox);
		layout.putConstraint( SpringLayout.WEST, chegadaBt, 5, SpringLayout.EAST, partidaBt);

		layout.putConstraint( SpringLayout.BASELINE, qualquerBt, 0, SpringLayout.BASELINE, portosBox);
		layout.putConstraint( SpringLayout.WEST, qualquerBt, 5, SpringLayout.EAST, chegadaBt);

		layout.putConstraint( SpringLayout.NORTH, mesBox, 5, SpringLayout.SOUTH, portosBox);
		layout.putConstraint( SpringLayout.WEST, mesBox, 0, SpringLayout.WEST, portosBox);
		
		layout.putConstraint( SpringLayout.BASELINE, duracaoBox, 0, SpringLayout.BASELINE, mesBox );
		layout.putConstraint( SpringLayout.WEST, duracaoBox, 5, SpringLayout.EAST, mesBox );

		layout.putConstraint( SpringLayout.EAST, painel, 5, SpringLayout.EAST, qualquerBt );
		layout.putConstraint( SpringLayout.SOUTH, painel, 5, SpringLayout.SOUTH, mesBox );

		layout.putConstraint( SpringLayout.EAST, pesquisaBt, -5, SpringLayout.EAST, painel );
		layout.putConstraint( SpringLayout.BASELINE, pesquisaBt, 0, SpringLayout.BASELINE, mesBox );

		return painel;
	}
	
	/** Configura o painel onde serão apresentados os resultados da pesauisa de cruzeiros
	 * @return o painel de apresentação dos resultados
	 */
	private JPanel setupPainelCruzeiros() {
		JPanel painel = new JPanel( new BorderLayout() );
		modelCruiseInfo = new DefaultListModel<>();

		painelLista = new PainelListador();
		JScrollPane pu = new JScrollPane( painelLista );
		
		pu.setBorder( BorderFactory.createTitledBorder("Cruzeiros"));
		painel.add( pu, BorderLayout.CENTER );
		return painel;		
	}

	/**
	 * Classe auxiliar que guarda a informação a apresentar para cada cruzeiro
	 * Isto seria melhor declarado como um record, mas como não falamos disso nas aulas...
	 * ATENÇÃO: não utilizar esta classe em mais nenhum lado
	 */
	private class CruiseInfo {
		String nome, dataSaida, escalas;
		int id, numDias;
		long preco;
		CruiseComponent renderer;
		
		public CruiseInfo(int id, String nome, String dataSaida, int numDias, String escalas, long preco) {
			super();
			this.id = id;
			this.nome = nome;
			this.dataSaida = dataSaida;
			this.numDias = numDias;
			this.escalas = escalas;
			this.preco = preco;
			renderer = new CruiseComponent( this );
		}
	}

	
	/** classe que agrupa os vários componentes de apresentação na lista
	 * dos cruzeiros
	 */
	private class CruiseComponent {
		JPanel painelCruise;
		CruiseInfo currentInfo;

		public CruiseComponent( CruiseInfo ci ) {
			currentInfo = ci;
			SpringLayout layout = new SpringLayout();
			painelCruise = new JPanel( layout ) {
				protected void paintComponent(java.awt.Graphics g) {
					super.paintComponent(g);
					g.setFont( nomeFont );
					g.drawString( currentInfo.nome + "      " + currentInfo.preco + "€", 10,20);
					g.setFont(mediaFontBold);
					g.drawString("Partida a " + currentInfo.dataSaida + "  " + currentInfo.numDias + " dias", 10, 40);
					g.setFont(mediaFont);
					g.drawString("Portos: " + currentInfo.escalas, 10, 60);
				};
			};
			
			JButton reservarBt = new JButton("Ver");
			reservarBt.addActionListener( e -> reservarCruzeiro( ci.id ) );
			JButton verBt = new JButton("Ver reservas");
			verBt.addActionListener( e -> verReservasCruzeiro( ci.id ) );
			painelCruise.add( reservarBt );
			painelCruise.add( verBt );
			painelCruise.setPreferredSize( new Dimension( 400, 70 ) );
			
			layout.putConstraint( SpringLayout.NORTH, reservarBt, 10, SpringLayout.NORTH,  painelCruise);
			layout.putConstraint( SpringLayout.WEST, reservarBt, 350, SpringLayout.WEST,  painelCruise);
			
			layout.putConstraint( SpringLayout.WEST, verBt, 5, SpringLayout.EAST, reservarBt );
			layout.putConstraint( SpringLayout.BASELINE, verBt, 0, SpringLayout.BASELINE, reservarBt );
		}
		
		public Component getComponent() {
			return painelCruise;
		};
	}
}
package menu;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import Models.*;
import Storage.Storage;

public class Main {

	/** Arranca com a aplicação 
	 */

	public static ArrayList<Experiencia> experiencias = new ArrayList<>();
	public static ArrayList<Excursao> excursoes = new ArrayList<>();
	public static ArrayList<Escala> escalas = new ArrayList<>();
	private static int cruzeiroIndex = 0;
	private static int escalaIndex = 0;


	public static void main(String[] args) {
		// ler os ficheiros
		readPortos( "portos.txt" );
		readCruzeiros( "cruzeiros.txt" );

		var ds = Storage.Cruzeiros;
		
		// criar as janelas 
		JanelaEscolhaCruzeiros je = new JanelaEscolhaCruzeiros( );
		JanelaGestaoReservas jr = new JanelaGestaoReservas( );
		
		// posicioná-las
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); // ver a dimensão do écan
		Rectangle r1 = je.getBounds();
		Rectangle r2 = jr.getBounds();
		int posx1 = (d.width - r1.width - r2.width - 10)/2;
		int posx2 = posx1 + r1.width + 10;
		int posy = (d.height - Math.max(r1.height,r2.height))/2;

		// apresentá-las
		je.setLocation( posx1, posy );
		je.setVisible( true );
		jr.setLocation( posx2, posy );
		jr.setVisible( true );
	}

	/** lê o ficheiros com a informação dos portos
	 * @param portosFile come do ficheiro com a informação dos portos
	 */
	private static void readPortos( String portosFile ) {
		try( BufferedReader fin = new BufferedReader( new FileReader( portosFile ) ) ){
			// TODO processar portos -> Done
			String linha;

			while ((linha = fin.readLine()) != null) {
				String[] info = linha.split("\t");

				Porto porto = new Porto(info[0], info[1]);

				Storage.Portos.add(porto);
			}
				
		} catch (FileNotFoundException e) {
			System.out.println("Não tenho o ficheiro " + portosFile );
			System.exit( 0 );
		}
		catch (Exception e) {
			System.out.println("Erro na leitura do ficheiro " + portosFile );
			e.printStackTrace();
			System.exit( 0 );
		}
	}

	/** Lê os ficheiros com a infrmação dos cruzeiros
	 * @param file nome do ficheiro com a informação dos cruzeiros
	 */
	private static void readCruzeiros( String file ){
		// fromatos em que estão as datas e horas
		final DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("d/M/yyyy");
		final DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("H:m:s");

		try ( BufferedReader fin = new BufferedReader( new FileReader( "cruzeiros.txt" ) )){
			// TODO processar cruzeiros -> Done
			String linha;
			int contador=0;
			int totalCruzeiro = 0;
			int identificador = 0;
			boolean estaEmEscala = false;
			Cruzeiro cruzeiro = new Cruzeiro();

			while ((linha = fin.readLine()) != null) {

				if (contador==0){
					totalCruzeiro = Integer.parseInt(linha);
					contador++;
					continue;
				}

				if (linha.contains("----")){
					cruzeiro = new Cruzeiro();
					identificador=0;
					estaEmEscala = false;

					if (Storage.Cruzeiros.size()>0){
						Storage.Cruzeiros.get(cruzeiroIndex).setEscalas(escalas);
						Storage.Cruzeiros.get(cruzeiroIndex).setExperiencias(experiencias);
						experiencias = new ArrayList<>();
						escalas = new ArrayList<>();
						cruzeiroIndex++;
					}
					continue;
				}

				if (identificador==0){
					identificador++;
					adicionarCruzeiro(linha, formatterData);
				} else if (identificador==1){
					identificador++;
					adicionarExperiencia(linha);
				}
				else {
					estaEmEscala = true;
				}
				
				if (estaEmEscala){
                    String[] info = linha.split("\t");
					var contemNumero = verificarNumeroRegex(info[0]);

                    if (contemNumero) adicionarEscala(info,formatterHora);
					else adicionarExcursao(info);
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("Não tenho o ficheiro " + file );
			System.exit( 0 );
		}
		catch (Exception e) {
			System.out.println("Erro na leitura do ficheiro " + file );
			e.printStackTrace();
			System.exit( 0 );
		}
	}

	private static void adicionarCruzeiro(String linha, DateTimeFormatter formatterData) {
		String[] info = linha.split("\t");

		int id = Integer.parseInt(info[0]);
		String nome = info[1];
		LocalDate dataPartida = LocalDate.parse(info[2], formatterData);
		int numeroCamarotes=Integer.parseInt(info[3]);
		long preco=Long.parseLong(info[4]);
		int numeroEscalas=Integer.parseInt(info[4]);

		Cruzeiro cruzeiro = new Cruzeiro(id,nome,dataPartida,numeroCamarotes,preco,numeroEscalas);

		Storage.Cruzeiros.add(cruzeiro);
	}

	public static boolean verificarNumero(String texto) {
		for (char c : texto.toCharArray()) {
			if (Character.isDigit(c)) {
				return true;
			}
		}
		return false;
	}

	private static void adicionarEscala(String[] info, DateTimeFormatter formatterHora) {
		int dia= Integer.parseInt(info[0]);
		String codigoPorto= info[1];
		LocalTime horaChegada=(info[2].equals("-")) ? null : LocalTime.parse(info[2], formatterHora);
		LocalTime horaPartida=(info[3].equals("-")) ?  null : LocalTime.parse(info[3], formatterHora);
		int numExcursoes=Integer.parseInt(info[4]);

		Escala escala = new Escala(dia,codigoPorto,numExcursoes);

		escala.setHoraPartida(horaPartida);
		escala.setHoraChegada(horaChegada);

		escalas.add(escala);
		escalaIndex++;
	}

	private static void adicionarExcursao(String[] info) {
		String nome = info[0];
		long preco=Long.parseLong(info[1]);
		int numLugares=Integer.parseInt(info[2]);
		Excursao excursao = new Excursao(nome,preco,numLugares);
	}

	private static void adicionarExperiencia(String linha) {
		String[] info = linha.split("\t");
		for (String item: info) {
			Experiencia experiencia = new Experiencia(item);
			experiencias.add(experiencia);
		}
	}


	public static boolean verificarNumeroRegex(String texto) {
		return texto.matches(".*\\d.*");
	}

}

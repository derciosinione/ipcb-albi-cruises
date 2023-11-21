package albicruises;

public class AlbiCruises {

	/** Gera um código alfanumérico de 6 caracteres aleatórios
	 * @return um código alfanumérico de 6 caracteres aleatórios
	 */
	public static String gerarReservaId() {
		return GeradorCodigos.gerarCodigo( 6 );
	}


}

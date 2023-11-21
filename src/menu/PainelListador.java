package menu;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

/** Representa um painel que apresenta o seu conteúdo
 * como se fosse uma lista, sendo cada componente
 * um component único e real, ao contrário da JList em que cada componente
 * é apenas um desenho. Ocupa mais memória, mas permite facilmente ter botões
 * e outros componentes interativos numa lista
 */
@SuppressWarnings("serial")
public class PainelListador extends JPanel {
	private JPanel conteudo;

	/** Cria o painel listador
	 */
	public PainelListador() {
		super();
		setLayout( new GridBagLayout() );
		conteudo = new JPanel( new GridLayout(0,1));
		JPanel espaco = new JPanel();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridx = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add( conteudo, gbc );
		gbc.weightx = 1;
		gbc.weighty = 1;
		add( espaco, gbc );
	}
	
	@Override
	public Component add(Component comp) {
		conteudo.add(comp);
		validate();
		repaint();
		return comp;
	}
	
	@Override
	public void removeAll() {
		conteudo.removeAll();
		validate();
		repaint();
	}
}

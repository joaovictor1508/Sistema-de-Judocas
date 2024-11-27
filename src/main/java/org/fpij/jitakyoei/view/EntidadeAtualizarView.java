package org.fpij.jitakyoei.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.fpij.jitakyoei.facade.AppFacade;
import org.fpij.jitakyoei.model.beans.Aluno;
import org.fpij.jitakyoei.model.beans.Entidade;
import org.fpij.jitakyoei.model.dao.DAO;
import org.fpij.jitakyoei.model.dao.DAOImpl;
import org.fpij.jitakyoei.view.forms.EntidadeForm;
import org.fpij.jitakyoei.view.gui.EntidadeAtualizarPanel;

public class EntidadeAtualizarView implements ViewComponent {

	private EntidadeAtualizarPanel gui;
	private AppFacade facade;
	private EntidadeForm entidadeForm;
	private MainAppView parent;
	
	
	public EntidadeAtualizarView(MainAppView parent, Entidade entidade) {
		this.parent = parent;
		gui = new EntidadeAtualizarPanel();
		gui.getCancelar().addActionListener(new CancelarActionHandler());
		gui.getAtualizarEntidade().addActionListener(new AtualizarActionHandler());
		gui.getApagar().addActionListener(new ApagarActionHandler());
		entidadeForm = new EntidadeForm(gui.getEntidadePanel(), entidade);
		gui.setVisible(true);
	}

	@Override
	public JPanel getGui() {
		return gui;
	}

	@Override
	public void registerFacade(AppFacade fac) {
		this.facade = fac;
	}
	
	/**
	 * Classe interna responsável por responder aos cliques no botão "Cadastrar".
	 * 
	 * @author Aécio
	 */
	public class AtualizarActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				if (entidadeForm.getEntidade().getNome() == null || entidadeForm.getEntidade().getNome().isEmpty()) {
					JOptionPane.showMessageDialog(gui, "Erro: O nome da entidade não pode ser vazio!", 
												  "Erro de Validação", JOptionPane.ERROR_MESSAGE);
					return;  
				}
	
				if (entidadeForm.getEntidade().getCnpj() == null || entidadeForm.getEntidade().getCnpj().isEmpty()) {
					JOptionPane.showMessageDialog(gui, "Erro: O CNPJ da entidade não pode ser vazio!", 
												  "Erro de Validação", JOptionPane.ERROR_MESSAGE);
					return;  
				}
	
				if (entidadeForm.getEntidade().getTelefone1() == null || entidadeForm.getEntidade().getTelefone1().isEmpty()) {
					JOptionPane.showMessageDialog(gui, "Erro: O telefone da entidade não pode ser vazio!", 
												  "Erro de Validação", JOptionPane.ERROR_MESSAGE);
					return;  
				}
				facade.updateEntidade(entidadeForm.getEntidade()); 
				JOptionPane.showMessageDialog(gui, "Entidade alterada com sucesso!");
				parent.removeTabPanel(gui);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Classe interna responsável por responder aos cliques no botão "Cancelar".
	 * 
	 * @author Aécio
	 */
	public class CancelarActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			parent.removeTabPanel(gui);
		}
	}
	public class ApagarActionHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Entidade entidade = entidadeForm.getEntidade();
			DAO<Entidade> dao = new DAOImpl<Entidade>(Entidade.class);
			int confirmacao = JOptionPane.showConfirmDialog(
					parent.getFrame(), 
					"Tem certeza que deseja apagar esta entidade?",
					"Confirmar Exclusão",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE 
			);

			if (confirmacao == JOptionPane.YES_OPTION) {
				dao.delete(entidade);
				parent.removeTabPanel(gui);
			}
		}
	}
}
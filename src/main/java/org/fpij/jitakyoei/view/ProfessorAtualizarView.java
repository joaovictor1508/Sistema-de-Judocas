package org.fpij.jitakyoei.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.fpij.jitakyoei.facade.AppFacade;
import org.fpij.jitakyoei.model.beans.Aluno;
import org.fpij.jitakyoei.model.beans.Professor;
import org.fpij.jitakyoei.model.dao.DAO;
import org.fpij.jitakyoei.model.dao.DAOImpl;
import org.fpij.jitakyoei.view.forms.ProfessorForm;
import org.fpij.jitakyoei.view.gui.ProfessorAtualizarPanel;

public class ProfessorAtualizarView implements ViewComponent{
	private ProfessorAtualizarPanel gui;
	private ProfessorForm professorForm;
	private AppFacade facade;
	private MainAppView parent;

	public ProfessorAtualizarView(MainAppView parent, Professor professor){
		this.parent = parent;
		gui = new ProfessorAtualizarPanel();
		professorForm = new ProfessorForm(gui.getProfessorPanel(), professor);
		gui.getCancelar().addActionListener(new CancelarActionHandler());
		gui.getAtualizar().addActionListener(new AtualizarActionHandler());
		gui.getApagar().addActionListener(new ApagarActionHandler());
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
	 * Classe interna responsável por responder aos cliques no botão "Atualizar".
	 * 
	 * @author Aécio
	 */
	public class AtualizarActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Professor professor = professorForm.getProfessor();
			try {
				if (professor.getFiliado().getNome() == null || professor.getFiliado().getNome().isEmpty()) {
					JOptionPane.showMessageDialog(gui, "Erro: O nome do aluno não pode ser vazio!", 
												  "Erro de Validação", JOptionPane.ERROR_MESSAGE);
					return; 
				}
	
				if (professor.getFiliado().getCpf() == null || professor.getFiliado().getCpf().isEmpty()) {
					JOptionPane.showMessageDialog(gui, "Erro: O CPF do aluno não pode ser vazio!", 
												  "Erro de Validação", JOptionPane.ERROR_MESSAGE);
					return; 
				}
	
				if (professor.getFiliado().getEmail() == null || professor.getFiliado().getEmail().isEmpty()) {
					JOptionPane.showMessageDialog(gui, "Erro: O e-mail do aluno não pode ser vazio!", 
												  "Erro de Validação", JOptionPane.ERROR_MESSAGE);
					return;
				}
	
				if (professor.getFiliado().getDataNascimento() == null) {
					JOptionPane.showMessageDialog(gui, "Erro: A data de nascimento do aluno não pode ser vazia!", 
												  "Erro de Validação", JOptionPane.ERROR_MESSAGE);
					return; 
				}
				facade.updateProfessor(professor);
				JOptionPane.showMessageDialog(gui, "Professor atualizado com sucesso!");
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
			Professor professor = professorForm.getProfessor();
			DAO<Professor> dao = new DAOImpl<Professor>(Professor.class);
			int confirmacao = JOptionPane.showConfirmDialog(
					parent.getFrame(), 
					"Tem certeza que deseja apagar este professor?",
					"Confirmar Exclusão",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE 
			);

			if (confirmacao == JOptionPane.YES_OPTION) {
				dao.delete(professor);
				parent.removeTabPanel(gui);
			}
		}
	}
}

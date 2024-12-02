package org.fpij.jitakyoei.facade;

import java.util.Arrays;
import java.util.List;

import org.fpij.jitakyoei.business.AlunoBO;
import org.fpij.jitakyoei.business.EntidadeBO;
import org.fpij.jitakyoei.business.ProfessorBO;
import org.fpij.jitakyoei.business.ProfessorEntidadeBO;
import org.fpij.jitakyoei.model.beans.Aluno;
import org.fpij.jitakyoei.model.beans.Entidade;
import org.fpij.jitakyoei.model.beans.Professor;
import org.fpij.jitakyoei.model.beans.ProfessorEntidade;
import org.fpij.jitakyoei.view.AppView;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AppFacadeImplTest {

    private AppFacadeImpl appFacade;
    private AppView mockView;
    private AlunoBO mockAlunoBO;
    private ProfessorBO mockProfessorBO;
    private EntidadeBO mockEntidadeBO;
    private ProfessorEntidadeBO mockProfessorEntidadeBO;

    @Before
    public void setUp() {
        mockView = mock(AppView.class);
        mockAlunoBO = mock(AlunoBO.class);
        mockProfessorBO = mock(ProfessorBO.class);
        mockEntidadeBO = mock(EntidadeBO.class);
        mockProfessorEntidadeBO = mock(ProfessorEntidadeBO.class);
        appFacade = new AppFacadeImpl(mockView);
    }

    @Test
    public void testCreateAluno() throws Exception {
        Aluno aluno = new Aluno();
        doNothing().when(mockAlunoBO).createAluno(aluno);

        appFacade.createAluno(aluno);

        verify(mockAlunoBO).createAluno(aluno);
    }

    @Test
    public void testUpdateAluno() throws Exception {
        Aluno aluno = new Aluno();
        doNothing().when(mockAlunoBO).updateAluno(aluno);

        appFacade.updateAluno(aluno);

        verify(mockAlunoBO).updateAluno(aluno);
    }

    @Test
    public void testSearchAluno() throws Exception {
        Aluno aluno = new Aluno();
        List<Aluno> alunos = Arrays.asList(new Aluno(), new Aluno());
        when(mockAlunoBO.searchAluno(aluno)).thenReturn(alunos);

        List<Aluno> result = appFacade.searchAluno(aluno);

        verify(mockAlunoBO).searchAluno(aluno);
        assertEquals(2, result.size());
    }

    @Test
    public void testCreateProfessor() throws Exception {
        Professor professor = new Professor();
        doNothing().when(mockProfessorBO).createProfessor(professor);

        appFacade.createProfessor(professor);

        verify(mockProfessorBO).createProfessor(professor);
    }

    @Test
    public void testListProfessores() throws Exception {
        List<Professor> professores = Arrays.asList(new Professor(), new Professor());
        when(mockProfessorBO.listAll()).thenReturn(professores);

        List<Professor> result = appFacade.listProfessores();

        verify(mockProfessorBO).listAll();
        assertEquals(2, result.size());
    }

    @Test
    public void testCreateEntidade() throws Exception {
        Entidade entidade = new Entidade();
        doNothing().when(mockEntidadeBO).createEntidade(entidade);

        appFacade.createEntidade(entidade);

        verify(mockEntidadeBO).createEntidade(entidade);
    }

    @Test
    public void testCreateProfessorEntidade() throws Exception {
        Professor professor = new Professor();
        Entidade entidade = new Entidade();
        ProfessorEntidade professorEntidade = new ProfessorEntidade(professor, entidade);
        List<ProfessorEntidade> relacionamentos = Arrays.asList(professorEntidade);
        doNothing().when(mockProfessorEntidadeBO).createProfessorEntidade(relacionamentos);

        appFacade.createProfessorEntidade(relacionamentos);

        verify(mockProfessorEntidadeBO).createProfessorEntidade(relacionamentos);
    }
}

package org.fpij.jitakyoei.business;

import java.util.Arrays;
import java.util.List;

import org.fpij.jitakyoei.model.beans.Entidade;
import org.fpij.jitakyoei.model.beans.Professor;
import org.fpij.jitakyoei.model.beans.ProfessorEntidade;
import org.fpij.jitakyoei.model.dao.DAO;
import org.fpij.jitakyoei.view.AppView;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ProfessorEntidadeBOImplTest {

    private ProfessorEntidadeBOImpl professorEntidadeBO;
    private DAO<ProfessorEntidade> mockDao;
    private AppView mockView;

    @Before
    public void setUp() {
        mockDao = mock(DAO.class);
        mockView = mock(AppView.class);
        professorEntidadeBO = new ProfessorEntidadeBOImpl(mockView);
        professorEntidadeBO.dao = mockDao;
    }

    @Test
    public void testCreateProfessorEntidade_Success() throws Exception {
        Professor professor1 = new Professor();
        Professor professor2 = new Professor();
        Entidade entidade1 = new Entidade();
        Entidade entidade2 = new Entidade();

        ProfessorEntidade professorEntidade1 = new ProfessorEntidade(professor1, entidade1);
        ProfessorEntidade professorEntidade2 = new ProfessorEntidade(professor2, entidade2);

        List<ProfessorEntidade> relacionamentos = Arrays.asList(professorEntidade1, professorEntidade2);

        professorEntidadeBO.createProfessorEntidade(relacionamentos);

        for (ProfessorEntidade professorEntidade : relacionamentos) {
            verify(mockDao).save(professorEntidade);
        }

        verify(mockView).handleModelChange(relacionamentos);
    }

    @Test
    public void testCreateProfessorEntidade_Exception() throws Exception {
        Professor professor = new Professor();
        Entidade entidade = new Entidade();

        ProfessorEntidade professorEntidade = new ProfessorEntidade(professor, entidade);

        List<ProfessorEntidade> relacionamentos = Arrays.asList(professorEntidade);

        doThrow(new RuntimeException("Erro ao salvar")).when(mockDao).save(professorEntidade);

        try {
            professorEntidadeBO.createProfessorEntidade(relacionamentos);
            fail("Deveria lançar uma Exception");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Desculpe, ocorreu um erro desconhecido ao salvar os relacionamentos."));
        }
    }

    @Test
    public void testCreateProfessorEntidade_IllegalArgumentException() throws Exception {
        Professor professor = new Professor();
        Entidade entidade = new Entidade();

        ProfessorEntidade professorEntidade = new ProfessorEntidade(professor, entidade);

        List<ProfessorEntidade> relacionamentos = Arrays.asList(professorEntidade);

        doThrow(new IllegalArgumentException("Erro ao associar")).when(mockDao).save(professorEntidade);

        try {
            professorEntidadeBO.createProfessorEntidade(relacionamentos);
            fail("Deveria lançar uma IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("Ocorreu um erro ao associar o professor às suas entidades!"));
        }
    }
}

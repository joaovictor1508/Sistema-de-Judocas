package org.fpij.jitakyoei.business;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

import org.fpij.jitakyoei.model.beans.Professor;
import org.fpij.jitakyoei.model.beans.Entidade;
import org.fpij.jitakyoei.model.beans.Filiado;
import org.fpij.jitakyoei.model.beans.ProfessorEntidade;
import org.fpij.jitakyoei.model.dao.DAO;
import org.fpij.jitakyoei.view.AppView;
import org.junit.Before;
import org.junit.Test;

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
        Filiado filiado1 = new Filiado();
        filiado1.setId(1L);
        Filiado filiado2 = new Filiado();
        filiado2.setId(2L);

        Professor professor1 = new Professor();
        professor1.setFiliado(filiado1);
        Entidade entidade1 = new Entidade();
        entidade1.setNome("Entidade A");

        Professor professor2 = new Professor();
        professor2.setFiliado(filiado2);
        Entidade entidade2 = new Entidade();
        entidade2.setNome("Entidade B");

        ProfessorEntidade professorEntidade1 = new ProfessorEntidade(professor1, entidade1);
        ProfessorEntidade professorEntidade2 = new ProfessorEntidade(professor2, entidade2);

        List<ProfessorEntidade> relacionamentos = Arrays.asList(professorEntidade1, professorEntidade2);

        professorEntidadeBO.createProfessorEntidade(relacionamentos);

        verify(mockDao).save(professorEntidade1);
        verify(mockDao).save(professorEntidade2);
        verify(mockView).handleModelChange(relacionamentos);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProfessorEntidade_InvalidData() throws Exception {
        professorEntidadeBO.createProfessorEntidade(null);
    }


    @Test
    public void testCreateProfessorEntidade_ExceptionHandling() {
        Filiado filiado = new Filiado();
        filiado.setId(1L);
        Professor professor = new Professor();
        professor.setFiliado(filiado);
        Entidade entidade = new Entidade();
        entidade.setNome("Entidade A");

        ProfessorEntidade professorEntidade = new ProfessorEntidade(professor, entidade);
        List<ProfessorEntidade> relacionamentos = Arrays.asList(professorEntidade);

        try {
            doThrow(new RuntimeException("Erro ao salvar")).when(mockDao).save(professorEntidade);
            professorEntidadeBO.createProfessorEntidade(relacionamentos);
            fail("Deveria lançar uma Exception");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Desculpe, ocorreu um erro desconhecido ao salvar os relacionamentos."));
        }
    }
}



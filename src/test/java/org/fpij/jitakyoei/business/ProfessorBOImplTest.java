package org.fpij.jitakyoei.business;

import java.util.Arrays;
import java.util.List;

import org.fpij.jitakyoei.model.beans.Professor;
import org.fpij.jitakyoei.model.dao.DAO;
import org.fpij.jitakyoei.view.AppView;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProfessorBOImplTest {

    private ProfessorBOImpl professorBO;
    private DAO<Professor> mockDao;
    private AppView mockView;

    @Before
    public void setUp() {
        mockDao = mock(DAO.class);
        mockView = mock(AppView.class);
        professorBO = new ProfessorBOImpl(mockView);
    }

    @Test
    public void testCreateProfessor_Success() throws Exception {
        // Criar um objeto Filiado com um nome fictício e associá-lo ao Professor
        Professor professor = new Professor();

        professorBO.createProfessor(professor);

        verify(mockDao).save(professor);
        verify(mockView).handleModelChange(professor);
    }

    @Test
    public void testCreateProfessor_Exception() throws Exception {
        Professor professor = new Professor();

        doThrow(new RuntimeException("Erro ao salvar")).when(mockDao).save(professor);

        try {
            professorBO.createProfessor(professor);
            fail("Deveria lançar uma Exception");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Desculpe, ocorreu um erro desconhecido ao salvar o professor."));
        }
    }

    @Test
    public void testUpdateProfessor_Success() throws Exception {
        Professor professor = new Professor();

        professorBO.updateProfessor(professor);

        verify(mockDao).save(professor);
        verify(mockView).handleModelChange(professor);
    }

    @Test
    public void testUpdateProfessor_Exception() throws Exception {
        Professor professor = new Professor();

        doThrow(new RuntimeException("Erro ao atualizar")).when(mockDao).save(professor);

        try {
            professorBO.updateProfessor(professor);
            fail("Deveria lançar uma Exception");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Desculpe, ocorreu um erro desconhecido ao atualizar o professor."));
        }
    }

    @Test
    public void testListAll_Success() throws Exception {
        Professor professor1 = new Professor();
        Professor professor2 = new Professor();

        List<Professor> professores = Arrays.asList(professor1, professor2);

        when(mockDao.list()).thenReturn(professores);

        List<Professor> result = professorBO.listAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(mockDao).list();
    }

    @Test
    public void testListAll_Exception() throws Exception {
        doThrow(new RuntimeException("Erro ao listar")).when(mockDao).list();

        try {
            professorBO.listAll();
            fail("Deveria lançar uma Exception");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Desculpe, ocorreu um erro desconhecido ao listar professores."));
        }
    }

    @Test
    public void testSearchProfessor_Success() throws Exception {
        Professor professor = new Professor();

        List<Professor> professores = Arrays.asList(professor);

        when(mockDao.search(professor)).thenReturn(professores);

        List<Professor> result = professorBO.searchProfessor(professor);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(mockDao).search(professor);
    }

    @Test
    public void testSearchProfessor_Exception() throws Exception {
        Professor professor = new Professor();

        doThrow(new RuntimeException("Erro ao buscar")).when(mockDao).search(professor);

        try {
            professorBO.searchProfessor(professor);
            fail("Deveria lançar uma Exception");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Desculpe, ocorreu um erro desconhecido ao buscar os professores."));
        }
    }
}

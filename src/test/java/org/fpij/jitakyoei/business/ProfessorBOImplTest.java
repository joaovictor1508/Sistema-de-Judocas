package org.fpij.jitakyoei.business;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.nio.channels.Pipe.SourceChannel;
import java.util.ArrayList;
import java.util.List;

import org.fpij.jitakyoei.model.beans.Filiado;
import org.fpij.jitakyoei.model.beans.Professor;
import org.fpij.jitakyoei.model.dao.DAO;
import org.fpij.jitakyoei.view.AppView;
import org.junit.Before;
import org.junit.Test;

public class ProfessorBOImplTest {
    private ProfessorBOImpl professorBO;
    private AppView mockView;
    private DAO<Professor> mockDao;

    @Before
    public void setUp() {
        mockView = mock(AppView.class);
        mockDao = mock(DAO.class);
        ProfessorBOImpl.dao = mockDao; 
        professorBO = new ProfessorBOImpl(mockView);
    }

    
    @Test
    public void testCreateProfessor_Success() throws Exception {

        Filiado filiado = new Filiado();
        filiado.setId(1L); 
        filiado.setNome("João Silva");
        filiado.setCpf("12345678900");
        filiado.setEmail("joao.silva@email.com");
    

        Professor professor = new Professor();
        professor.setFiliado(filiado);
    

        when(mockDao.save(professor)).thenReturn(true);

        professorBO.createProfessor(professor);

        verify(mockDao).save(professor);
        verify(mockView).handleModelChange(professor);
    }        


    @Test
    public void testCreateProfessor_InvalidData() {
        Professor professor = null;

        try {
            professorBO.createProfessor(professor);
            fail("Deveria lançar uma Exception");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertTrue(e.getMessage().contains("Ocorreu um erro ao cadastrar o professor!"
				+ " Verifique se todos os dados foram preenchidos corretamente."));
        }
    }

    @Test
    public void testUpdateProfessor_Success() throws Exception {
        Professor professor = new Professor();

        professorBO.updateProfessor(professor);

        verify(mockDao).save(professor);
        verify(mockView).handleModelChange(professor);
    }

    @Test(expected = Exception.class)
    public void testUpdateProfessor_Exception() throws Exception {
        Professor professor = new Professor();

        doThrow(new Exception("DAO error")).when(mockDao).save(any(Professor.class));

        professorBO.updateProfessor(professor);
    }

    @Test
    public void testListAll_Success() throws Exception {
        List<Professor> professors = new ArrayList<>();
        when(mockDao.list()).thenReturn(professors);

        List<Professor> result = professorBO.listAll();

        assertSame(professors, result);
        verify(mockDao).list();
    }

    @Test(expected = Exception.class)
    public void testListAll_Exception() throws Exception {
        when(mockDao.list()).thenThrow(new Exception("DAO error"));

        professorBO.listAll();
    }

    @Test
    public void testSearchProfessor_Success() throws Exception {
        Professor professor = new Professor();
        List<Professor> professors = new ArrayList<>();
        when(mockDao.search(professor)).thenReturn(professors);

        List<Professor> result = professorBO.searchProfessor(professor);

        assertSame(professors, result);
        verify(mockDao).search(professor);
    }

    @Test(expected = Exception.class)
    public void testSearchProfessor_Exception() throws Exception {
        Professor professor = new Professor();

        when(mockDao.search(professor)).thenThrow(new Exception("DAO error"));

        professorBO.searchProfessor(professor);
    }
}

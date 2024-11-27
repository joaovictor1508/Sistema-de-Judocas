package org.fpij.jitakyoei.business;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.fpij.jitakyoei.business.AlunoBOImpl;
import org.fpij.jitakyoei.model.beans.Aluno;
import org.fpij.jitakyoei.model.beans.Filiado;
import org.fpij.jitakyoei.model.dao.DAO;
import org.fpij.jitakyoei.view.AppView;
import org.junit.Before;
import org.junit.Test;

public class AlunoBOImplTest {
    private DAO<Aluno> mockDAO;
    private AppView mockView;
    private AlunoBOImpl alunoBO;

    @Before
    public void setUp() {
        mockDAO = mock(DAO.class);
        mockView = mock(AppView.class);
        
        alunoBO = new AlunoBOImpl(mockView);
        AlunoBOImpl.dao = mockDAO;
    }

    @Test
    public void testCreateAlunoSuccess() throws Exception {
        Aluno aluno = new Aluno();
        Filiado filiado = new Filiado();
        aluno.setFiliado(filiado);

        alunoBO.createAluno(aluno);

        verify(mockDAO, times(1)).save(aluno);

        verify(mockView, times(1)).handleModelChange(aluno);
    }


    @Test
    public void testUpdateAlunoSuccess() throws Exception {
        Aluno existingAluno = new Aluno();
        Filiado existingFiliado = new Filiado();
        existingFiliado.setId(1L);
        existingAluno.setFiliado(existingFiliado);

        Aluno aluno = new Aluno();
        Filiado newFiliado = new Filiado();
        newFiliado.setId(1L);
        newFiliado.setNome("Novo Nome");
        aluno.setFiliado(newFiliado);

        when(mockDAO.get(aluno)).thenReturn(existingAluno);

        alunoBO.updateAluno(aluno);

        assertEquals("Novo Nome", existingAluno.getFiliado().getNome());

        verify(mockDAO, times(1)).get(aluno);

        verify(mockView, times(1)).handleModelChange(existingAluno);
    }


    @Test
    public void testSearchAlunoSuccess() throws Exception {
        Aluno aluno = new Aluno();
        List<Aluno> mockResult = new ArrayList<>();
        mockResult.add(new Aluno());

        when(mockDAO.search(aluno)).thenReturn(mockResult);

        List<Aluno> result = alunoBO.searchAluno(aluno);

        verify(mockDAO, times(1)).search(aluno);

        assertEquals(1, result.size());
    }

    @Test
    public void testListAllSuccess() throws Exception {
        List<Aluno> mockResult = new ArrayList<>();
        mockResult.add(new Aluno());
        mockResult.add(new Aluno());

        when(mockDAO.list()).thenReturn(mockResult);

        List<Aluno> result = alunoBO.listAll();

        verify(mockDAO, times(1)).list();

        assertEquals(2, result.size());
    }

    @Test(expected = Exception.class)
    public void testCreateAlunoUnknownError() throws Exception {
        Aluno aluno = new Aluno();
        Filiado filiado = new Filiado();
        aluno.setFiliado(filiado);

        doThrow(new Exception()).when(mockDAO).save(aluno);

        alunoBO.createAluno(aluno);
    }
}
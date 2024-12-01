package org.fpij.jitakyoei.business;

import java.util.Arrays;
import java.util.List;

import org.fpij.jitakyoei.model.beans.Aluno;
import org.fpij.jitakyoei.model.beans.Filiado;
import org.fpij.jitakyoei.model.dao.DAO;
import org.fpij.jitakyoei.view.AppView;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AlunoBOImplTest {

    private AlunoBOImpl alunoBO;
    private DAO<Aluno> mockDao;
    private AppView mockView;
    private Aluno aluno;
    private Filiado filiado;

    @Before
    public void setUp() {
        mockDao = mock(DAO.class);
        mockView = mock(AppView.class);
        alunoBO = new AlunoBOImpl(mockView);
        filiado = new Filiado();
        filiado.setId(1L);
        aluno = new Aluno();
        aluno.setFiliado(filiado);
    }

    @Test
    public void testCreateAlunoSucesso() throws Exception {
        doNothing().when(mockDao).save(aluno);
        alunoBO.createAluno(aluno);
        verify(mockDao).save(aluno);
        verify(mockView).handleModelChange(aluno);
    }

    @Test
    public void testCreateAlunoExcecao() {
        doThrow(new RuntimeException("Erro de banco")).when(mockDao).save(aluno);
        try {
            alunoBO.createAluno(aluno);
            fail("Esperava exceção ao salvar aluno");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Desculpe, ocorreu um erro desconhecido ao salvar o aluno"));
        }
    }

    @Test
    public void testUpdateAlunoSucesso() throws Exception {
        Aluno alunoAtualizado = new Aluno();
        Filiado filiadoAtualizado = new Filiado();
        filiadoAtualizado.setId(2L);
        alunoAtualizado.setFiliado(filiadoAtualizado);
        when(mockDao.get(aluno)).thenReturn(aluno);
        alunoBO.updateAluno(alunoAtualizado);
        verify(mockDao).get(aluno);
        verify(mockView).handleModelChange(aluno);
    }

    @Test
    public void testUpdateAlunoExcecao() {
        when(mockDao.get(aluno)).thenThrow(new RuntimeException("Erro ao buscar aluno"));
        try {
            alunoBO.updateAluno(aluno);
            fail("Esperava exceção ao atualizar aluno");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Desculpe, ocorreu um erro desconhecido ao salvar o aluno"));
        }
    }

    @Test
    public void testSearchAlunoSucesso() throws Exception {
        List<Aluno> alunos = Arrays.asList(aluno);
        when(mockDao.search(aluno)).thenReturn(alunos);
        List<Aluno> resultado = alunoBO.searchAluno(aluno);
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(aluno, resultado.get(0));
    }

    @Test
    public void testSearchAlunoExcecao() {
        when(mockDao.search(aluno)).thenThrow(new RuntimeException("Erro na busca"));
        try {
            alunoBO.searchAluno(aluno);
            fail("Esperava exceção ao buscar aluno");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Desculpe, ocorreu um erro desconhecido ao buscar os aluno"));
        }
    }

    @Test
    public void testListAllSucesso() throws Exception {
        List<Aluno> alunos = Arrays.asList(aluno);
        when(mockDao.list()).thenReturn(alunos);
        List<Aluno> resultado = alunoBO.listAll();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(aluno, resultado.get(0));
    }

    @Test
    public void testListAllExcecao() {
        when(mockDao.list()).thenThrow(new RuntimeException("Erro na listagem"));
        try {
            alunoBO.listAll();
            fail("Esperava exceção ao listar alunos");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Desculpe, ocorreu um erro desconhecido o obter a lista de alunos"));
        }
    }
}

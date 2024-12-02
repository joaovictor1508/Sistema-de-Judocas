package org.fpij.jitakyoei.business;

import java.util.Arrays;
import java.util.List;

import org.fpij.jitakyoei.model.beans.Entidade;
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

public class EntidadeBOImplTest {

    private EntidadeBOImpl entidadeBO;
    private DAO<Entidade> mockDao;
    private AppView mockView;

    @Before
    public void setUp() {
        mockDao = mock(DAO.class);
        mockView = mock(AppView.class);
        entidadeBO = new EntidadeBOImpl(mockView);
    }

    @Test
    public void testCreateEntidade_Success() throws Exception {
        Entidade entidade = new Entidade();
        entidade.setNome("Entidade A");

        entidadeBO.createEntidade(entidade);

        verify(mockDao).save(entidade);
        verify(mockView).handleModelChange(entidade);
    }

    @Test
    public void testCreateEntidade_Exception() throws Exception {
        Entidade entidade = new Entidade();
        entidade.setNome("Entidade A");

        doThrow(new RuntimeException("Erro ao salvar")).when(mockDao).save(entidade);

        try {
            entidadeBO.createEntidade(entidade);
            fail("Deveria lançar uma Exception");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Desculpe, ocorreu um erro desconhecido ao salvar a entidade."));
        }
    }

    @Test
    public void testListAll_Success() throws Exception {
        Entidade entidade1 = new Entidade();
        entidade1.setNome("Entidade A");

        Entidade entidade2 = new Entidade();
        entidade2.setNome("Entidade B");

        List<Entidade> entidades = Arrays.asList(entidade1, entidade2);

        when(mockDao.list()).thenReturn(entidades);

        List<Entidade> result = entidadeBO.listAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(mockDao).list();
    }

    @Test
    public void testListAll_Exception() throws Exception {
        doThrow(new RuntimeException("Erro ao listar")).when(mockDao).list();

        try {
            entidadeBO.listAll();
            fail("Deveria lançar uma Exception");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Desculpe, ocorreu um erro desconhecido ao listar entidades."));
        }
    }

    @Test
    public void testSearchEntidade_Success() throws Exception {
        Entidade entidade = new Entidade();
        entidade.setNome("Entidade A");

        List<Entidade> entidades = Arrays.asList(entidade);

        when(mockDao.search(entidade)).thenReturn(entidades);

        List<Entidade> result = entidadeBO.searchEntidade(entidade);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(mockDao).search(entidade);
    }

    @Test
    public void testSearchEntidade_Exception() throws Exception {
        Entidade entidade = new Entidade();
        entidade.setNome("Entidade A");

        doThrow(new RuntimeException("Erro ao buscar")).when(mockDao).search(entidade);

        try {
            entidadeBO.searchEntidade(entidade);
            fail("Deveria lançar uma Exception");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Desculpe, ocorreu um erro desconhecido ao buscar entidades."));
        }
    }

    @Test
    public void testUpdateEntidade_Success() throws Exception {
        Entidade entidade = new Entidade();
        entidade.setNome("Entidade A");

        entidadeBO.updateEntidade(entidade);

        verify(mockDao).save(entidade);
        verify(mockView).handleModelChange(entidade);
    }

    @Test
    public void testUpdateEntidade_Exception() throws Exception {
        Entidade entidade = new Entidade();
        entidade.setNome("Entidade A");

        doThrow(new RuntimeException("Erro ao atualizar")).when(mockDao).save(entidade);

        try {
            entidadeBO.updateEntidade(entidade);
            fail("Deveria lançar uma Exception");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Desculpe, ocorreu um erro desconhecido ao atualizar a entidade."));
        }
    }
}

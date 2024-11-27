package org.fpij.jitakyoei.business;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.fpij.jitakyoei.model.beans.Entidade;
import org.fpij.jitakyoei.model.dao.DAO;
import org.fpij.jitakyoei.view.AppView;
import org.junit.Before;
import org.junit.Test;

public class EntidadeBOImplTest {

    private EntidadeBOImpl entidadeBO;
    private DAO<Entidade> mockDAO;
    private AppView mockView;

    @Before
    public void setUp() {
        mockDAO = mock(DAO.class);
        mockView = mock(AppView.class);

        EntidadeBOImpl.dao = mockDAO;

        entidadeBO = new EntidadeBOImpl(mockView);
    }

   @Test
    public void testCreateEntidadeSuccess() throws Exception {
        Entidade entidade = new Entidade();
        entidadeBO.createEntidade(entidade);

        verify(mockDAO, times(1)).save(entidade);
        verify(mockView, times(1)).handleModelChange(entidade);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testCreateEntidadeWithInvalidData() throws Exception {
        doThrow(new IllegalArgumentException()).when(mockDAO).save(any(Entidade.class));

        entidadeBO.createEntidade(new Entidade());
    }

    @Test
    public void testListAllSuccess() throws Exception {
        List<Entidade> mockEntidades = Arrays.asList(new Entidade(), new Entidade());
        when(mockDAO.list()).thenReturn(mockEntidades);

        List<Entidade> result = entidadeBO.listAll();
        verify(mockDAO, times(1)).list();

        assertEquals(mockEntidades, result);
    }

    @Test(expected = Exception.class)
    public void testListAllWithException() throws Exception {
        when(mockDAO.list()).thenThrow(new Exception());

        entidadeBO.listAll();
    }

    @Test
    public void testSearchEntidadeSuccess() throws Exception {
        Entidade entidade = new Entidade();
        List<Entidade> mockResult = Arrays.asList(new Entidade());
        when(mockDAO.search(entidade)).thenReturn(mockResult);

        List<Entidade> result = entidadeBO.searchEntidade(entidade);

        verify(mockDAO, times(1)).search(entidade);

        assertEquals(mockResult, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSearchEntidadeWithInvalidData() throws Exception {
        when(mockDAO.search(any(Entidade.class))).thenThrow(new IllegalArgumentException());

        entidadeBO.searchEntidade(new Entidade());
    }

    @Test
    public void testUpdateEntidadeSuccess() throws Exception {
        Entidade entidade = new Entidade();
        entidadeBO.updateEntidade(entidade);

        verify(mockDAO, times(1)).save(entidade);

        verify(mockView, times(1)).handleModelChange(entidade);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateEntidadeWithInvalidData() throws Exception {
        doThrow(new IllegalArgumentException()).when(mockDAO).save(any(Entidade.class));

        entidadeBO.updateEntidade(new Entidade());
    }
}

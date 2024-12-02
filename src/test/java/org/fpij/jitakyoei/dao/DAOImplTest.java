package org.fpij.jitakyoei.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.fpij.jitakyoei.model.beans.ProfessorEntidade;
import org.fpij.jitakyoei.model.dao.DAOImpl;
import org.fpij.jitakyoei.model.validator.Validator;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.db4o.ext.ExtObjectContainer;

public class DAOImplTest {

    private DAOImpl<ProfessorEntidade> dao;
    private ExtObjectContainer mockDb;
    private Validator<ProfessorEntidade> mockValidator;
    private ProfessorEntidade mockEntity;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        mockDb = mock(ExtObjectContainer.class);
        mockValidator = mock(Validator.class);
        
        dao = new DAOImpl<>(ProfessorEntidade.class, mockValidator, true);

        Field dbField = DAOImpl.class.getDeclaredField("db");
        dbField.setAccessible(true); 
        dbField.set(dao, mockDb);

        mockEntity = mock(ProfessorEntidade.class);
    }

    @Test
    public void save_DeveSalvarEntidade_QuandoValida() {
        when(mockValidator.validate(mockEntity)).thenReturn(true);

        boolean resultado = dao.save(mockEntity);

        verify(mockDb).store(mockEntity);
        verify(mockDb).commit();
        assertTrue("Esperado que a operação de salvar retorne true.", resultado);
    }

    @Test
    public void save_NaoDeveSalvarEntidade_QuandoInvalida() {
        when(mockValidator.validate(mockEntity)).thenReturn(false);

        boolean resultado = dao.save(mockEntity);

        verify(mockDb, never()).store(mockEntity);
        verify(mockDb, never()).commit();
        assertFalse("Esperado que a operação de salvar retorne false.", resultado);
    }

    @Test
    public void delete_DeveRemoverEntidade() {
        dao.delete(mockEntity);

        verify(mockDb).delete(mockEntity);
        verify(mockDb).commit();
    }

    private List<ProfessorEntidade> mockList() {
        List<ProfessorEntidade> list = new ArrayList<>();
        list.add(mockEntity);
        return list;
    }
}

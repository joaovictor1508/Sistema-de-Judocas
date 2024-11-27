package org.fpij.jitakyoei.dao;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.fpij.jitakyoei.model.beans.ProfessorEntidade;
import org.fpij.jitakyoei.model.dao.DAOImpl;
import org.fpij.jitakyoei.model.validator.Validator;
import org.junit.Before;
import org.junit.Test;
import com.db4o.ext.ExtObjectContainer;
import java.lang.reflect.Field;

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
    public void testSave_ValidEntity() {
        when(mockValidator.validate(mockEntity)).thenReturn(true);

        boolean result = dao.save(mockEntity);

        verify(mockDb).store(mockEntity);
        verify(mockDb).commit();
        assertTrue(result);
    }

    @Test
    public void testSave_InvalidEntity() {
        when(mockValidator.validate(mockEntity)).thenReturn(false);

        boolean result = dao.save(mockEntity);

        verify(mockDb, never()).store(mockEntity);
        verify(mockDb, never()).commit();
        assertFalse(result);
    }
}


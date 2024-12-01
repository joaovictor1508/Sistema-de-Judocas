package org.fpij.jitakyoei.dao;

import java.lang.reflect.Field;
import org.fpij.jitakyoei.model.beans.ProfessorEntidade;
import org.fpij.jitakyoei.model.dao.DAOImpl;
import org.fpij.jitakyoei.model.validator.Validator;
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

        // Reflection para acessar o campo estático 'db' da classe DAOImpl
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
        assert resultado : "Esperado que a operação de salvar retorne true.";
    }

    @Test
    public void save_NaoDeveSalvarEntidade_QuandoInvalida() {
        when(mockValidator.validate(mockEntity)).thenReturn(false);

        boolean resultado = dao.save(mockEntity);

        verify(mockDb, never()).store(mockEntity);
        verify(mockDb, never()).commit();
        assert !resultado : "Esperado que a operação de salvar retorne false.";
    }

    @Test
    public void get_DeveRetornarEntidade_QuandoUsarEquals() {
        when(mockDb.queryByExample(mockEntity.getClass())).thenReturn(mockList());
        when(mockEntity.equals(mockEntity)).thenReturn(true);

        ProfessorEntidade resultado = dao.get(mockEntity);

        assert resultado.equals(mockEntity) : "Esperado que o método get retorne a mesma entidade quando o método equals for usado.";
    }

    @Test
    public void get_DeveRetornarEntidade_QuandoNaoUsarEquals() {
        when(mockDb.queryByExample(mockEntity.getClass())).thenReturn(mockList());

        ProfessorEntidade resultado = dao.get(mockEntity);

        assert resultado == mockEntity : "Esperado que o método get retorne a mesma entidade quando o método equals não for usado.";
    }

    @Test
    public void delete_DeveRemoverEntidade() {
        dao.delete(mockEntity);

        verify(mockDb).delete(mockEntity);
        verify(mockDb).commit();
        assert true : "Esperado que a operação de deletar remova a entidade e faça o commit.";
    }

    // Lista mockada para teste
    private Object mockList() {
        return new java.util.ArrayList<>();
    }
}

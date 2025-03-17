package entities;

import com.fornempr.demo.entities.Empresa;
import com.fornempr.demo.entities.Fornecedor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MockFornecedor {
    static MockEmpresa mockEmpresa = new MockEmpresa();

    public static Fornecedor getMockedFornecedor() {
        Fornecedor mockedFornecedor = Mockito.mock(Fornecedor.class);

        Mockito.when(mockedFornecedor.getId()).thenReturn(1);
        Mockito.when(mockedFornecedor.getCNPJ()).thenReturn("78174244000188");
        Mockito.when(mockedFornecedor.getNome()).thenReturn("Teste Forn");
        Mockito.when(mockedFornecedor.getEmail()).thenReturn("exemplo@exem.com");

        Mockito.when(mockedFornecedor.getCPF()).thenReturn("74797318090");
        Mockito.when(mockedFornecedor.getRg()).thenReturn("173918773");
        Mockito.when(mockedFornecedor.getData_nascimento()).thenReturn(new Date("15/05/1998"));
        Mockito.when(mockedFornecedor.getRg()).thenReturn("173918773");
        Mockito.when(mockedFornecedor.getIs_pessoa_fisica()).thenReturn(Boolean.TRUE);

        return mockedFornecedor;
    }


    public static Fornecedor getMockedFornecedorWithEmpresa() {
        Fornecedor mockedFornecedor = getMockedFornecedor();
        Empresa mockedEmpresa = mockEmpresa.getMockedEmpresa();
        List<Empresa> empresas = new ArrayList<>();
        empresas.add(mockedEmpresa);

        Mockito.when(mockedFornecedor.getEmpresa()).thenReturn(empresas);

        return mockedFornecedor;
    }

}

package entities;

import com.fornempr.demo.entities.Empresa;
import com.fornempr.demo.entities.Fornecedor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;



public class MockEmpresa {
    static MockFornecedor mockFornecedor = new MockFornecedor();

    public static Empresa getMockedEmpresa() {
        // Create a mock instance of Employee
        Empresa mockedEmpresa = Mockito.mock(Empresa.class);

        // Define default behavior for the mock
        Mockito.when(mockedEmpresa.getId()).thenReturn(1);
        Mockito.when(mockedEmpresa.getCNPJ()).thenReturn("78174244000188");
        Mockito.when(mockedEmpresa.getNomeFantasia()).thenReturn("Teste Emp");
        Mockito.when(mockedEmpresa.getCEP()).thenReturn("13082690");
        Mockito.when(mockedEmpresa.getEstado()).thenReturn("São Paulo");

        return mockedEmpresa;
    }


    public static Empresa getMockedEmpresaWithFornecedor() {
        Empresa mockedEmpresa = getMockedEmpresa();
        Fornecedor mockedFornecedor = mockFornecedor.getMockedFornecedor();
        List<Fornecedor> fornecedores = new ArrayList<>();
        fornecedores.add(mockedFornecedor);

        Mockito.when(mockedEmpresa.getFornecedor()).thenReturn(fornecedores);

        return mockedEmpresa;
    }

}

package com.fornempr.demo;

import com.fornempr.demo.entities.Fornecedor;
import com.fornempr.demo.repositories.FornecedorRepository;
import com.fornempr.demo.services.FornecedorService;
import entities.MockFornecedor;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class FornecedorServiceTests {


	@Mock
	private FornecedorRepository fornecedorRepository;

	@InjectMocks
	private FornecedorService fornecedorService;

	@Test
	void getAll()
	{
		Fornecedor fornecedor1 = MockFornecedor.getMockedFornecedor();
		Fornecedor fornecedor2 = MockFornecedor.getMockedFornecedor();
		fornecedor2.setId(2);

		given(fornecedorRepository.findAll())
				.willReturn(List.of(fornecedor1,fornecedor2));
		var personList = fornecedorService.findAllFornecedor();

		assertThat(personList).isNotNull();
		assertThat(personList.size()).isEqualTo(2);
	}

}

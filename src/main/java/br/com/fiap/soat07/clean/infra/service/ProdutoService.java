package br.com.fiap.soat07.clean.infra.service;

import br.com.fiap.soat07.clean.core.usecase.produto.CreateProdutoUseCase;
import br.com.fiap.soat07.clean.core.usecase.produto.DeleteProdutoUseCase;
import br.com.fiap.soat07.clean.core.usecase.produto.SearchProdutoUseCase;
import br.com.fiap.soat07.clean.core.usecase.produto.UpdateProdutoUseCase;
import br.com.fiap.soat07.clean.infra.repository.mysql.ProdutoRepository;
import org.springframework.stereotype.Component;

@Component
public class ProdutoService {

    private final CreateProdutoUseCase createProdutoUseCase;
    private final DeleteProdutoUseCase deleteProdutoUseCase;
    private final UpdateProdutoUseCase updateProdutoUseCase;
    private final SearchProdutoUseCase searchProdutoUseCase;

    public ProdutoService(final ProdutoRepository produtoGateway) {
        this.createProdutoUseCase = new CreateProdutoUseCase(produtoGateway);
        this.deleteProdutoUseCase = new DeleteProdutoUseCase(produtoGateway);
        this.updateProdutoUseCase = new UpdateProdutoUseCase(produtoGateway);
        this.searchProdutoUseCase = new SearchProdutoUseCase(produtoGateway);
    }

    public CreateProdutoUseCase getCreateProdutoUseCase() {
        return createProdutoUseCase;
    }

    public DeleteProdutoUseCase getDeleteProdutoUseCase() {
        return deleteProdutoUseCase;
    }

    public UpdateProdutoUseCase getUpdateProdutoUseCase() {
        return updateProdutoUseCase;
    }

    public SearchProdutoUseCase getSearchProdutoUseCase() {
        return searchProdutoUseCase;
    }
}

package br.edu.fatec.sjc;

public class CheckoutService {
    private EstoqueService estoqueService;
    private PagamentoGateway pagamentoGateway;

    public CheckoutService(EstoqueService estoqueService, PagamentoGateway pagamentoGateway) {
        this.estoqueService = estoqueService;
        this.pagamentoGateway = pagamentoGateway;
    }

    public boolean finalizarCompra(Pedido pedido) {
        if (!estoqueService.temEstoque(pedido.getProdutoId(), pedido.getQuantidade())) {
            return false; 
        }

        if (!pagamentoGateway.processar(pedido.getValorTotal())) {
            return false; 
        }

        estoqueService.baixarEstoque(pedido.getProdutoId(), pedido.getQuantidade());

        return true;
    }
}

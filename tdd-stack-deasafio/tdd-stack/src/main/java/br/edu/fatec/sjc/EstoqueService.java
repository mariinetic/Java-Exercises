package br.edu.fatec.sjc;

public interface EstoqueService {
    boolean temEstoque(String produtoId, int qtd);
    void baixarEstoque(String produtoId, int qtd);
}

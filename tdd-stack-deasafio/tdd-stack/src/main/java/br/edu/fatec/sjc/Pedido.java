package br.edu.fatec.sjc;

public class Pedido {
    private String produtoId;
    private int quantidade;
    private double valorTotal;

    public Pedido(String produtoId, int quantidade, double valorTotal) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
    }

    public String getProdutoId() {
        return produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValorTotal() {
        return valorTotal;
    }
}

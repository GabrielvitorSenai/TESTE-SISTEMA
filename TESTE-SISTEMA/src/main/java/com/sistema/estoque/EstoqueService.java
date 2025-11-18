package com.sistema.estoque;

import java.util.HashMap;
import java.util.Map;

public class EstoqueService {
    // Mapa para simular um "banco de dados" ou repositório de dados.
    private final Map<String, Produto> produtos;

    public EstoqueService() {
        this.produtos = new HashMap<>();
    }

    public void adicionarProduto(String nome, int quantidadeInicial) {
        Produto novoProduto = new Produto(nome, quantidadeInicial);
        produtos.put(nome, novoProduto);
    }
    
    public Produto buscarProduto(String nome) {
        return produtos.get(nome);
    }
    
    // Método que simula uma transação do sistema: entrada de mercadoria
    public boolean entradaEstoque(String nomeProduto, int quantidade) {
        Produto produto = buscarProduto(nomeProduto);
        if (produto != null) {
            produto.adicionar(quantidade);
            return true;
        }
        return false;
    }

    // Método que simula uma transação do sistema: saída de mercadoria (venda)
    public boolean saidaEstoque(String nomeProduto, int quantidade) {
        Produto produto = buscarProduto(nomeProduto);
        if (produto != null) {
            return produto.remover(quantidade);
        }
        return false;
    }
    
    public int getQuantidadeAtual(String nomeProduto) {
        Produto produto = buscarProduto(nomeProduto);
        return produto != null ? produto.getQuantidade() : -1;
    }
}
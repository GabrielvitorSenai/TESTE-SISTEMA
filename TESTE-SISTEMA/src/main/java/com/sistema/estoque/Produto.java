package com.sistema.estoque;

public class Produto {
    private final String nome;
    private int quantidade;

    public Produto(String nome, int quantidade) {
        this.nome = nome;
        this.quantidade = quantidade;
    }

    public String getNome() { return nome; }
    public int getQuantidade() { return quantidade; }

    public void adicionar(int valor) { 
        if (valor > 0) {
            this.quantidade += valor;
        }
    }
    
    public boolean remover(int valor) {
        if (valor > 0 && this.quantidade >= valor) {
            this.quantidade -= valor;
            return true;
        }
        return false; // Falha na remoção
    }
}
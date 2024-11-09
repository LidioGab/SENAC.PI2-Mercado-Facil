package com.model;

public class Produto {
    private int idProduto;
    private String nome;
    private int codigoBarra;
    private double valorProduto;  
    private int categoria;
    private String descricaoProduto;

    public Produto() {
    }

    public Produto(int idProduto, String nome, int codigoBarra, double valorProduto, int categoria, String descricaoProduto) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.codigoBarra = codigoBarra;
        this.valorProduto = valorProduto;
        this.categoria = categoria;
        this.descricaoProduto = descricaoProduto;
    }


    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(int codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public double getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(double valorProduto) {
        this.valorProduto = valorProduto;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }
}

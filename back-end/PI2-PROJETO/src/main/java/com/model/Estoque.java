package com.model;

import java.util.Date;

public class Estoque {

	private Integer idEstoque;
	private Integer idProduto;
	private Integer quantidade;
	private Date dataValidade;
	private Date dataSolicitacao;
	private Date dataEntrada;
	private Double custo;
	private Integer idFornecedor;
	
	private Enum statusEstoque;

	public Estoque(Integer idEstoque, Integer idProduto, Integer quantidade, Date dataValidade, Date dataSolicitacao,
			Date dataEntrada, Double custo, Integer idFornecedor) {

		this.idEstoque = idEstoque;
		this.idProduto = idProduto;
		this.quantidade = quantidade;
		this.dataValidade = dataValidade;
		this.dataSolicitacao = dataSolicitacao;
		this.dataEntrada = dataEntrada;
		this.custo = custo;
		this.idFornecedor = idFornecedor;
	}

	public Estoque(int int1, String string, int int2, double double1) {
		// TODO Auto-generated constructor stub
	}

	public Estoque() {
		// TODO Auto-generated constructor stub
	}

	public Integer getIdEstoque() {
		return idEstoque;
	}

	public void setIdEstoque(Integer idEstoque) {
		this.idEstoque = idEstoque;
	}

	public Integer getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		quantidade = quantidade;
	}

	public Date getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}

	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public Date getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public Double getCusto() {
		return custo;
	}

	public void setCusto(Double custo) {
		this.custo = custo;
	}

	public Integer getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(Integer idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

}

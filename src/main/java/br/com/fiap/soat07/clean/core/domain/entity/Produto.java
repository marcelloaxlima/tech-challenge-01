package br.com.fiap.soat07.clean.core.domain.entity;

import br.com.fiap.soat07.clean.Utils;
import br.com.fiap.soat07.clean.core.domain.enumeration.TipoProdutoEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Produto {

	private Long id;
	private String codigo;
	private String nome;
	private BigDecimal valor;
	private TipoProdutoEnum tipoProduto;
	private LocalDateTime dataCriacao;
	private LocalDateTime ultimaModificacao;
	private LocalDateTime dataExclusao;

	public Produto() {
		this.dataCriacao = Utils.now();
	}


	public Long getId() {
		return this.id;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public String getNome() {
		return this.nome;
	}

	public BigDecimal getValor() {
		if (valor == null)
			return BigDecimal.ZERO;
		return this.valor;
	}

	public TipoProdutoEnum getTipoProduto() {
		return this.tipoProduto;
	}

	public LocalDateTime getDataCriacao() {
		return this.dataCriacao;
	}

	public LocalDateTime getUltimaModificacao() {
		return this.ultimaModificacao;
	}

	public LocalDateTime getDataExclusao() {
		return this.dataExclusao;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setValor(BigDecimal valor) {
		if (valor == null)
			valor = BigDecimal.ZERO;
		this.valor = valor;
	}

	public void setTipoProduto(TipoProdutoEnum tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public void setUltimaModificacao(LocalDateTime ultimaModificacao) {
		this.ultimaModificacao = ultimaModificacao;
	}

	public void setDataExclusao(LocalDateTime dataExclusao) {
		this.dataExclusao = dataExclusao;
	}

	public boolean isExcluido() {
		return getDataExclusao() != null;
	}

	public String toString() {
		return "Produto(id=" + this.getId() + ", codigo=" + this.getCodigo() + ", nome=" + this.getNome() + ", valor=" + this.getValor() + ", tipoProduto=" + this.getTipoProduto() + ", dataCriacao=" + this.getDataCriacao() + ", ultimaModificacao=" + this.getUltimaModificacao() + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Produto produto)) return false;
        return Objects.equals(getCodigo(), produto.getCodigo()) && Objects.equals(getNome(), produto.getNome()) && Objects.equals(getValor(), produto.getValor()) && getTipoProduto() == produto.getTipoProduto();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCodigo(), getNome(), getValor(), getTipoProduto());
	}

}

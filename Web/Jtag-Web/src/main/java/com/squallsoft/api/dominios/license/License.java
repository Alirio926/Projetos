package com.squallsoft.api.dominios.license;

import java.time.LocalDate;

public class License {

	/**
	 * Código gerado e enviado ao cliente.
	 */
	private String license;
	
	/**
	 * Data de ativação da licença.
	 */
	private LocalDate dataAtivacao;
	
	/**
	 * Quantidade de copias liberada.
	 */
	private Integer qtdInicial;
	
	/**
	 * Quantidade disponivel.
	 */
	private Integer qtdDisponivel;
	
	/**
	 * Produto atrelado a esta licença.
	 */
	private Produto produto;
	
	/**
	 * Valor da licensa em reais.
	 */
	private Double valor;
	
	
}

package com.sistema.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "enderecos")
public class Endereco extends AbstractEntity<Long> {
    
    @Column(name = "logradouro", nullable = false)
    private String logradouro;

    @Column(name = "bairro", nullable = false)
    private String bairro;

    @NotBlank(message = "Preencha o campo cidade")
    @NotNull(message = "Preencha o campo cidade")
	@Size(min = 3, max = 255)
    @Column(name = "cidade", nullable = false)
    private String cidade;

    @NotNull(message = "Selecione um estado")
    @Column(name = "estado", length = 2, nullable = false)
    @Enumerated(EnumType.STRING)
    private UF uf;

    @NotBlank(message = "Preencha o campo CEP")
    @Column(name = "cep", nullable = false, length = 9)
    private String cep;

    @Column(name = "numero", length = 5)
    private String numero;

    @Column(name = "complemento")
    private String complemento;

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public UF getUf() {
        return uf;
    }

    public void setUf(UF uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}

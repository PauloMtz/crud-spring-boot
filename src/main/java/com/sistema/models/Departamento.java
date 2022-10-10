package com.sistema.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "departamentos")
public class Departamento extends AbstractEntity<Long> {

    /*
     * @NotNull valida campos nulos
     * @NotBlank valida campos vazios ou com espaços em branco
     */
    
    @NotNull(message = "O campo não pode ser nulo")
    @NotBlank(message = "O campo nome deve ser preenchido.")
	@Size(min = 3, max = 60, message = "O nome do departamento deve ter entre {min} e {max} caracteres.")
    @Column(name = "nome", nullable = false, unique = true, length = 60)
    private String nome;

    // um departamento tem vários cargos
    // mapeado por departamento lá em Cargo
    @OneToMany(mappedBy = "departamento")
    private List<Cargo> cargos;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }
}

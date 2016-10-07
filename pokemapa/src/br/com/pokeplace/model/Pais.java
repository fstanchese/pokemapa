package br.com.pokeplace.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "pais")
@NamedQueries({ 
	@NamedQuery(name = "Pais.listar", query = "select p from Pais p order by p.nome"),
	@NamedQuery(name = "Pais.buscaPorId", query = "select p from Pais p where p.id=:id"), 
	@NamedQuery(name = "Pais.buscaPorNome", query = "select p from Pais p where p.nome=:nome") 
})
public class Pais implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@NotBlank(message="O campo Nome é obrigatório.")
	@Column(name="nome",length=100,nullable=false,unique=true)
	@Size(min = 3, max = 100, message = "Nome minimo é 3 caracteres.")
	private String nome;

	@NotBlank(message="O campo Sigla é obrigatório.")
	@Column(name="sigla",length=3,nullable=false,unique=true)
	@Size(min = 3, max = 3, message = "Sigla é obrigatório 3 caracteres.")
	private String sigla;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	public String getSigla() {
		return sigla;
	}
	
	@Override
	public String toString() {
		return "Pais [id=" + id + ", nome=" + nome + ", sigla=" + sigla + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pais other = (Pais) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

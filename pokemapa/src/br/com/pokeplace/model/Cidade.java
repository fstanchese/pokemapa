package br.com.pokeplace.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="cidade")
@NamedQueries({ 
	@NamedQuery(name = "Cidade.listar", query = "select c from Cidade c order by c.nome"),
	@NamedQuery(name = "Cidade.listarPorEstado", query = "select c from Cidade c where c.estado = :estado order by c.nome"),
	@NamedQuery(name = "Cidade.buscaPorId", query = "select c from Cidade c where c.id=:id"),
	@NamedQuery(name = "Cidade.buscaPorNome", query = "select c from Cidade c where c.estado = :estado and c.nome=:nome") 
})
public class Cidade implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)  
	private Long id;
	
	@NotBlank(message="O campo Nome é obrigatório")
	@Column(name="nome")
	private String nome;
	
	@NotNull(message="O campo Estado é obrigatório.")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "estado_id")
	@JsonManagedReference
	private Estado estado;

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

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Cidade [id=" + id + ", nome=" + nome + ", estado=" + estado + "]";
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
		Cidade other = (Cidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

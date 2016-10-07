package br.com.pokeplace.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","imagem"})
@Entity
@Table(name="pokeplace")
@NamedQueries({ 
	@NamedQuery(name = "Pokeplace.listar", query = "select p from Pokeplace p order by p.nome"),
	@NamedQuery(name = "Pokeplace.listarPorCidade", query = "select p from Pokeplace p where p.cidade = :cidade order by p.nome"),
	@NamedQuery(name = "Pokeplace.listarCidades", query = "select cidade from Pokeplace p group by p.cidade order by p.cidade.nome"),
	@NamedQuery(name = "Pokeplace.buscaPorId", query = "select p from Pokeplace p where p.id=:id")
})
public class Pokeplace implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@NotBlank(message="O campo Nome é obrigatório")
	@Column(name="nome")
	private String nome;
	
	@NotNull(message="O campo Latitude é obrigatório")
	@Column(name="latitude")
	private double latitude; 
	
	@NotNull(message="O campo Longitude é obrigatório")
	@Column(name="longitude")
	private double longitude;
	
	@NotNull(message="O campo Cidade é obrigatório.")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "cidade_id")
	@JsonManagedReference
	Cidade cidade;
	
	@NotNull(message="O campo Tipo é obrigatório.")
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "tipo_id")
	@JsonManagedReference
	private Tipo tipo;

	@Column(name="nomeimagem")
	private String nomeImagem;

	@Column(name="imagem",length = 20971520)
	@Lob
	private byte[] imagem;

	transient String logradouro;

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

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public byte[] getImagem() {
		return imagem;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

	public String getNomeImagem() {
		return nomeImagem;
	}

	public void setNomeImagem(String nomeImagem) {
		this.nomeImagem = nomeImagem;
	}

	public String getLogradouro() {
		return logradouro;
	}
	
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	
	@Override
	public String toString() {
		return "Pokeplace [id=" + id + ", nome=" + nome + ", latitude=" + latitude + ", longitude=" + longitude
				 + ", cidade=" + cidade + ", tipo=" + tipo + "]";
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
		Pokeplace other = (Pokeplace) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

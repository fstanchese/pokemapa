package br.com.pokeplace.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.pokeplace.model.Cidade;
import br.com.pokeplace.model.Estado;
import br.com.pokeplace.model.Pais;

@Repository
public class EstadoDAO {

	@PersistenceContext
	EntityManager manager;

	public void adicionar(Estado estado) {
		manager.persist(estado);
	}

	public void alterar(Estado estado) {
		manager.merge(estado);
	}

	public void remover(Estado estado) {
		Estado remover = buscaPorId(estado.getId());
		manager.remove(remover);
	}
	
	public Estado buscaPorId(Long id) {
		return manager.find(Estado.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Estado> listar() {
		return manager.createNamedQuery("Estado.listar").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Cidade> listaPorCidade(Estado estado) {
		return manager.createNamedQuery("Cidade.listarPorEstado").
        		setParameter("estado", estado).getResultList();
	}

	public Estado buscaPorNome(Pais pais, String stringEstado) {
		return (Estado) manager.createNamedQuery("Estado.buscaPorNome").
					setParameter("pais", pais).setParameter("nome",	stringEstado).getSingleResult();
	}
}

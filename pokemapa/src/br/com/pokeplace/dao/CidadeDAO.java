package br.com.pokeplace.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.pokeplace.model.Cidade;
import br.com.pokeplace.model.Estado;

@Repository
public class CidadeDAO {

	@PersistenceContext
	EntityManager manager;

	public void adicionar(Cidade cidade) {
		manager.persist(cidade);
	}

	public void alterar(Cidade cidade) {
		manager.merge(cidade);
	}

	public void remover(Cidade cidade) {
		Cidade remover = buscaPorId(cidade.getId());
		manager.remove(remover);
	}
	
	public Cidade buscaPorId(Long id) {
		return manager.find(Cidade.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Cidade> listar() {
		return manager.createNamedQuery("Cidade.listar").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Cidade> listarPorCidade(Estado estado) {
        return manager.createNamedQuery("Cidade.listarPorEstado").
        		setParameter("estado", estado).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Cidade> listarCidades() {
		return manager.createNamedQuery("Pokeplace.listarCidades").getResultList();
	}

	public Cidade buscaPorNome(Estado estado,String stringCidade) {
		return (Cidade) manager.createNamedQuery("Cidade.buscaPorNome")
				.setParameter("estado", estado)
				.setParameter("nome", stringCidade)
				.getSingleResult();
	}
}

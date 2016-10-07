package br.com.pokeplace.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import br.com.pokeplace.model.Cidade;
import br.com.pokeplace.model.Pokeplace;

@Repository
public class PokeplaceDAO {

	@PersistenceContext
	EntityManager manager;

	public void adicionar(Pokeplace pokeplace) {
		manager.persist(pokeplace);
	}

	public void alterar(Pokeplace pokeplace) {
		manager.merge(pokeplace);
	}

	public void remover(Pokeplace pokeplace) {
		Pokeplace remover = buscaPorId(pokeplace.getId());
		manager.remove(remover);
	}
	
	public Pokeplace buscaPorId(Long id) {
		return manager.find(Pokeplace.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Pokeplace> listar() {
		List<Pokeplace> pokeplaces = manager.createNamedQuery("Pokeplace.listar").getResultList();
		for (Pokeplace pokeplace : pokeplaces) {
			Hibernate.initialize(pokeplace.getTipo());
		}		
		return pokeplaces;
	}

	@SuppressWarnings("unchecked")
	public List<Pokeplace> listarPorCidade(Cidade cidade) {
		List<Pokeplace> pokeplaces = manager.createNamedQuery("Pokeplace.listarPorCidade").setParameter("cidade", cidade).getResultList();
		for (Pokeplace pokeplace : pokeplaces) {
			Hibernate.initialize(pokeplace.getTipo());
		}
		return pokeplaces;	
	}

}

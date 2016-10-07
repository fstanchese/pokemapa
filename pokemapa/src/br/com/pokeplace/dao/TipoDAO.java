package br.com.pokeplace.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.pokeplace.model.Tipo;

@Repository
public class TipoDAO {

	@PersistenceContext
	EntityManager manager;

	@SuppressWarnings("unchecked")
	public List<Tipo> listar() {
		return manager.createNamedQuery("Tipo.listar").getResultList();
	}

	public Tipo buscaPorId(Long id) {
		return manager.find(Tipo.class, id);
	}

}

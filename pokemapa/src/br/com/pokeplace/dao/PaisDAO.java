package br.com.pokeplace.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.pokeplace.model.Estado;
import br.com.pokeplace.model.Pais;

@Repository
public class PaisDAO {

	@PersistenceContext
	EntityManager manager;

	public void adicionar(Pais pais) {
		manager.persist(pais);
	}

	public void alterar(Pais pais) {
		manager.merge(pais);
	}

	public void remover(Pais pais) {
		Pais remover = buscaPorId(pais.getId());
		manager.remove(remover);
	}
	
	public Pais buscaPorId(Long id) {
		return manager.find(Pais.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Pais> listar() {
		return manager.createNamedQuery("Pais.listar").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Estado> listarPorEstado(Pais pais) {
        return manager.createNamedQuery("Estado.listarPorPais").
        		setParameter("pais", pais).getResultList();			
	}

	public Pais buscaPorNome(String nome) {
		return (Pais) manager.createNamedQuery("Pais.buscaPorNome").
				setParameter("nome", nome).getSingleResult();
	}

}

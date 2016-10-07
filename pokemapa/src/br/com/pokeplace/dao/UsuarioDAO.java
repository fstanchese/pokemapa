package br.com.pokeplace.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.pokeplace.model.Usuario;

@Repository
public class UsuarioDAO {

	@PersistenceContext
	EntityManager manager;

	public Usuario buscaUsuario(Usuario usuario) {
		try {
			return (Usuario) manager.createNamedQuery("Usuario.buscaUsuario").
					setParameter("login", usuario.getLogin()).
					setParameter("senha", usuario.getSenha()).getSingleResult();
		} catch (NoResultException nre) {
			return new Usuario();
		}
	}
}

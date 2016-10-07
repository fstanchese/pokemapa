package br.com.pokeplace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pokeplace.dao.PokeplaceDAO;
import br.com.pokeplace.model.Cidade;
import br.com.pokeplace.model.Pokeplace;

@Service
public class PokeplaceService {

	private PokeplaceDAO daoPokeplace;

	@Autowired
	public PokeplaceService(PokeplaceDAO daoPokeplace) {
		this.daoPokeplace = daoPokeplace;
	}
	
	public void adicionar(Pokeplace pokeplace) {
		daoPokeplace.adicionar(pokeplace);
	}
	
	public void alterar(Pokeplace pokeplace) {
		daoPokeplace.alterar(pokeplace);
	}
	
	public void remover(Pokeplace pokeplace) {
		daoPokeplace.remover(pokeplace);
	}
	
	public Pokeplace buscaPorId(Long id) {
		return daoPokeplace.buscaPorId(id);
	}
	
	public List<Pokeplace> listar() {
		return daoPokeplace.listar();
	}
	
	public List<Pokeplace> listarPorCidade(Cidade cidade) {
		return daoPokeplace.listarPorCidade(cidade);
	}
	
}

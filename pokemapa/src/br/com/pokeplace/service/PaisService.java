package br.com.pokeplace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pokeplace.dao.PaisDAO;
import br.com.pokeplace.model.Estado;
import br.com.pokeplace.model.Pais;

@Service
public class PaisService {

	private PaisDAO daoPais;

	@Autowired
	public PaisService(PaisDAO daoPais) {
		this.daoPais = daoPais;
	}
	
	public void adicionar(Pais pais) {
		daoPais.adicionar(pais);
	}
	
	public void alterar(Pais pais) {
		daoPais.alterar(pais);
	}
	
	public void remover(Pais pais) {
		daoPais.remover(pais);
	}
	
	public Pais buscaPorId(Long id) {
		return daoPais.buscaPorId(id);
	}
	
	public List<Pais> listar() {
		return daoPais.listar();
	}
	
	public List<Estado> listarPorEstado(Pais pais) {
		return daoPais.listarPorEstado(pais);
	}

	public Pais buscaPorNome(String nome) {
		return daoPais.buscaPorNome(nome);
	}
}
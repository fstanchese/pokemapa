package br.com.pokeplace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pokeplace.dao.EstadoDAO;
import br.com.pokeplace.model.Cidade;
import br.com.pokeplace.model.Estado;
import br.com.pokeplace.model.Pais;

@Service
public class EstadoService {

	private EstadoDAO daoEstado;

	@Autowired
	public EstadoService(EstadoDAO daoEstado) {
		this.daoEstado = daoEstado;
	}
	
	public void adicionar(Estado estado) {
		daoEstado.adicionar(estado);
	}
	
	public void alterar(Estado estado) {
		daoEstado.alterar(estado);
	}
	
	public void remover(Estado estado) {
		daoEstado.remover(estado);
	}
	
	public Estado buscaPorId(Long id) {
		return daoEstado.buscaPorId(id);
	}
	
	public List<Estado> listar() {
		return daoEstado.listar();
	}
	
	public List<Cidade> listaPorCidade(Estado estado) {
		return daoEstado.listaPorCidade(estado);
	}

	public Estado buscaPorNome(Pais pais, String stringEstado) {
		return daoEstado.buscaPorNome(pais, stringEstado);
	}
}

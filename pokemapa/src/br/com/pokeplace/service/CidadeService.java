package br.com.pokeplace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pokeplace.dao.CidadeDAO;
import br.com.pokeplace.model.Cidade;
import br.com.pokeplace.model.Estado;

@Service
public class CidadeService {

	private CidadeDAO daoCidade;

	@Autowired
	public CidadeService(CidadeDAO daoCidade) {
		this.daoCidade = daoCidade;
	}
	
	public void adicionar(Cidade cidade) {
		daoCidade.adicionar(cidade);
	}
	
	public void alterar(Cidade cidade) {
		daoCidade.alterar(cidade);
	}
	
	public void remover(Cidade cidade) {
		daoCidade.remover(cidade);
	}
	
	public Cidade buscaPorId(Long id) {
		return daoCidade.buscaPorId(id);
	}
	
	public List<Cidade> listar() {
		return daoCidade.listar();
	}
	
	public List<Cidade> listarPorCidade(Estado estado) {
		return daoCidade.listarPorCidade(estado);
	}

	public List<Cidade> listarCidades() {
		return daoCidade.listarCidades();
	}

	public Cidade buscaPorNome(Estado estado, String stringCidade) {
		return daoCidade.buscaPorNome(estado, stringCidade);
	}
}

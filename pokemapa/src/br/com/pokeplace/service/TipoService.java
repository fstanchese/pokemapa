package br.com.pokeplace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pokeplace.dao.TipoDAO;
import br.com.pokeplace.model.Tipo;

@Service
public class TipoService {

	private TipoDAO daoTipo;
	
	@Autowired
	public TipoService(TipoDAO daoTipo) {
		this.daoTipo = daoTipo;
	}

	public List<Tipo> listar() {
		return daoTipo.listar();
	}

	public Tipo buscaPorId(Long id) {
		return daoTipo.buscaPorId(id);
	}
}

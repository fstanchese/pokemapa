package br.com.pokeplace.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.pokeplace.dao.CidadeDAO;
import br.com.pokeplace.dao.PokeplaceDAO;
import br.com.pokeplace.model.Cidade;
import br.com.pokeplace.model.Pokeplace;

@Controller
@Transactional
@RequestMapping("/mapas")
public class MapaController {
	
	private PokeplaceDAO daoPokeplace;
	private CidadeDAO daoCidade;
	
	@Autowired
	public MapaController(PokeplaceDAO daoPokeplace, CidadeDAO daoCidade) {
		this.daoPokeplace = daoPokeplace;
		this.daoCidade = daoCidade;
	}


	@RequestMapping
	public String listarTodos(Model model, @RequestParam(defaultValue = "0") Long cidade_id) {
		Cidade cidade = daoCidade.buscaPorId(cidade_id);
		List<Pokeplace> pokeplaces = daoPokeplace.listarPorCidade(cidade);
		
		model.addAttribute("pokeplaces", pokeplaces);
		if(pokeplaces.size() > 0)
			model.addAttribute("pokeplace", pokeplaces.get(0));
		model.addAttribute("cidades", daoCidade.listarCidades());
		model.addAttribute("cidadeFiltro", cidade);
		return "mapa/lista";
	}
}

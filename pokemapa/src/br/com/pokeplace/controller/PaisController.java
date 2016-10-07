package br.com.pokeplace.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.pokeplace.model.Estado;
import br.com.pokeplace.model.Pais;
import br.com.pokeplace.service.PaisService;

@Transactional
@Controller
@RequestMapping("/paises")
public class PaisController {
	
	private PaisService servicePais;
	
	@Autowired
	public PaisController(PaisService servicePais) {
		this.servicePais = servicePais;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String listaTodos(Model model) {
		model.addAttribute("paises",servicePais.listar());
		return "pais/lista";
	}
	
	@RequestMapping(value="/novo")
	public String addPais(Model model) {
		model.addAttribute("pais",new Pais());
		return "pais/crud";
	}

	@RequestMapping(value="/edit/{id}",method = RequestMethod.GET)
	public String editPais(Model model, @PathVariable("id") Long id) {
		model.addAttribute("pais",servicePais.buscaPorId(id));
		return "pais/crud";
	}

	@RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
	public String deletePais(Model model, @PathVariable("id") Long id) {
		Pais pais = servicePais.buscaPorId(id);
		servicePais.remover(pais);
		model.addAttribute("paises",servicePais.listar());
		return "redirect:/paises";
	}
	
	@RequestMapping(value="/crudPais",method = RequestMethod.POST)
	public String crudPais(@Valid Pais pais,BindingResult result, Model model) {
		if (!result.hasErrors()) {
			String sigla = pais.getSigla().toUpperCase();
			pais.setSigla(sigla);
			if(pais.getId() == null) {
				servicePais.adicionar(pais);
			} else {
				servicePais.alterar(pais);
			}
			return "redirect:/paises";
		} else {
			model.addAttribute("pais",pais);
			return "pais/crud";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/selecionaEstado/{idPais}")
	@ResponseBody
	public Iterable<Estado> buscarModelos(@PathVariable Long idPais) {
		Pais pais = servicePais.buscaPorId(idPais);
		Iterable<Estado> estados = servicePais.listarPorEstado(pais);
		return estados;
	}
}

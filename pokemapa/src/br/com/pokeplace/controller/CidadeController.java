package br.com.pokeplace.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.pokeplace.model.Cidade;
import br.com.pokeplace.model.Estado;
import br.com.pokeplace.model.Pais;
import br.com.pokeplace.properties.EstadoPropertyEditor;
import br.com.pokeplace.service.CidadeService;
import br.com.pokeplace.service.EstadoService;
import br.com.pokeplace.service.PaisService;

@Controller
@Transactional
@RequestMapping("/cidades")
public class CidadeController {
	
	private CidadeService serviceCidade;
	private EstadoService serviceEstado;
	private PaisService servicePais;
	private EstadoPropertyEditor estadoPropertyEditor;
	
	@Autowired
	public CidadeController(CidadeService serviceCidade, EstadoService serviceEstado, PaisService servicePais,
			EstadoPropertyEditor estadoPropertyEditor) {
		this.serviceCidade = serviceCidade;
		this.serviceEstado = serviceEstado;
		this.servicePais = servicePais;
		this.estadoPropertyEditor = estadoPropertyEditor;
	}

	@RequestMapping
	public String listarTodos(Model model, @RequestParam(defaultValue = "0") Long pais_id, @RequestParam(defaultValue = "0") Long estado_id) {
		Estado estado = serviceEstado.buscaPorId(estado_id);
		Pais pais = servicePais.buscaPorId(pais_id);
		model.addAttribute("cidades",serviceCidade.listarPorCidade(estado));
		model.addAttribute("estados",servicePais.listarPorEstado(pais));
		model.addAttribute("paises",servicePais.listar());
		model.addAttribute("paisFiltro",pais);
		model.addAttribute("estadoFiltro",estado);
		return "cidade/lista";
	}

	@RequestMapping("/novo")
	public String addCidade(Model model) {
		model.addAttribute("cidade",new Cidade());
		model.addAttribute("paises",servicePais.listar());
		return "cidade/crud";
	}
	
	@RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
	public String editCidade(Model model, @PathVariable("id") Long id) {
		Cidade cidade = serviceCidade.buscaPorId(id);
		model.addAttribute("cidade",cidade);
		model.addAttribute("paises",servicePais.listar());
		model.addAttribute("estados",servicePais.listarPorEstado(cidade.getEstado().getPais()));
		return "cidade/crud";
	}
	
	@RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
	public String deleteCidade(Model model, @PathVariable("id") Long id) {
		Cidade cidade = serviceCidade.buscaPorId(id);
		serviceCidade.remover(cidade);
		return "redirect:/cidades";
	}
	
	@RequestMapping(value = "/crudCidade",method = RequestMethod.POST)
	public String crudCidade(@Valid Cidade cidade, BindingResult result, Model model) {
		if (! result.hasErrors()) {
			if( cidade.getId() == null) {
				serviceCidade.adicionar(cidade);
				return "redirect:/cidades/novo";
			} else {
				serviceCidade.alterar(cidade);
				return "redirect:/cidades";
			}
		} else {
			model.addAttribute("paises",servicePais.listar());
			model.addAttribute("cidade", cidade);
			return "cidade/crud";
		}
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(Estado.class, estadoPropertyEditor);
	}
}

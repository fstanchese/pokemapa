package br.com.pokeplace.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.hibernate.Hibernate;
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
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.pokeplace.model.Cidade;
import br.com.pokeplace.model.Estado;
import br.com.pokeplace.model.Pais;
import br.com.pokeplace.properties.PaisPropertyEditor;
import br.com.pokeplace.service.EstadoService;
import br.com.pokeplace.service.PaisService;

@Controller
@Transactional
@RequestMapping("/estados")
public class EstadoController {
	
	private EstadoService serviceEstado;
	private PaisService servicePais;
	private PaisPropertyEditor paisPropertyEditor;
	
	@Autowired
	public EstadoController(EstadoService serviceEstado, PaisService servicePais, PaisPropertyEditor paisPropertyEditor) {
		this.serviceEstado = serviceEstado;
		this.servicePais = servicePais;
		this.paisPropertyEditor = paisPropertyEditor;
	}

	@RequestMapping
	public String listarTodos(Model model, @RequestParam(defaultValue = "0") Long pais_id) {
		Pais pais = servicePais.buscaPorId(pais_id);
		model.addAttribute("estados",servicePais.listarPorEstado(pais));
		model.addAttribute("paises",servicePais.listar());
		model.addAttribute("paisFiltro",pais);
		return "estado/lista";
	}
	
	@RequestMapping("/novo")
	public String addEstado(Model model) {
		model.addAttribute("estado",new Estado());
		model.addAttribute("paises",servicePais.listar());
		return "estado/crud";
	}
	
	@RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
	public String editEstado(Model model, @PathVariable("id") Long id) {
		model.addAttribute("estado",serviceEstado.buscaPorId(id));
		model.addAttribute("paises",servicePais.listar());
		return "estado/crud";
	}
	
	@RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
	public String deleteEstado(Model model, @PathVariable("id") Long id) {
		Estado estado = serviceEstado.buscaPorId(id);
		serviceEstado.remover(estado);
		return "redirect:/estados";
	}
	
	@RequestMapping(value = "/crudEstado",method = RequestMethod.POST)
	public String crudEstado(@Valid Estado estado, BindingResult result, Model model) {
		if (! result.hasErrors()) {
			String uf = estado.getUf().toUpperCase();
			estado.setUf(uf);
			if( estado.getId() == null) {
				serviceEstado.adicionar(estado);
				return "redirect:/estados/novo";
			} else {
				serviceEstado.alterar(estado);
				return "redirect:/estados";
			}
		} else {
			model.addAttribute("paises",servicePais.listar());
			model.addAttribute("estado", estado);
			return "estado/crud";
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/selecionaCidade/{idEstado}")
	@ResponseBody
	public List<Cidade> buscarCidades(@PathVariable Long idEstado) {
		Estado estado = serviceEstado.buscaPorId(idEstado);
		List<Cidade> cidades = serviceEstado.listaPorCidade(estado);
		for (Cidade cidade : cidades) {
			Hibernate.initialize(cidade.getEstado().getPais());
		}
		return cidades;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(Pais.class, paisPropertyEditor);
	}
}

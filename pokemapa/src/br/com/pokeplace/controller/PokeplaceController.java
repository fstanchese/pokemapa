package br.com.pokeplace.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import br.com.pokeplace.googlemaps.AddressConverter;
import br.com.pokeplace.googlemaps.GoogleResponse;
import br.com.pokeplace.googlemaps.Result;
import br.com.pokeplace.model.Cidade;
import br.com.pokeplace.model.Estado;
import br.com.pokeplace.model.Pais;
import br.com.pokeplace.model.Pokeplace;
import br.com.pokeplace.model.Tipo;
import br.com.pokeplace.properties.CidadePropertyEditor;
import br.com.pokeplace.properties.TipoPropertyEditor;
import br.com.pokeplace.service.CidadeService;
import br.com.pokeplace.service.EstadoService;
import br.com.pokeplace.service.PaisService;
import br.com.pokeplace.service.PokeplaceService;
import br.com.pokeplace.service.TipoService;

@Controller
@Transactional
@RequestMapping("/pokeplaces")
public class PokeplaceController {

	private PokeplaceService servicePokeplace;
	private CidadeService serviceCidade;
	private EstadoService serviceEstado;
	private PaisService servicePais;
	private TipoService serviceTipo;
	private CidadePropertyEditor cidadePropertyEditor;
	private TipoPropertyEditor tipoPropertyEditor;

	@Autowired
	public PokeplaceController(PokeplaceService servicePokeplace, CidadeService serviceCidade,
			EstadoService serviceEstado, PaisService servicePais, TipoService serviceTipo,
			CidadePropertyEditor cidadePropertyEditor, TipoPropertyEditor tipoPropertyEditor) {
		this.servicePokeplace = servicePokeplace;
		this.serviceCidade = serviceCidade;
		this.serviceEstado = serviceEstado;
		this.servicePais = servicePais;
		this.serviceTipo = serviceTipo;
		this.cidadePropertyEditor = cidadePropertyEditor;
		this.tipoPropertyEditor = tipoPropertyEditor;
	}

	@RequestMapping
	public String listarTodos(Model model, @RequestParam(defaultValue = "0") Long cidade_id) {
		Cidade cidade = serviceCidade.buscaPorId(cidade_id);
		model.addAttribute("pokeplaces", servicePokeplace.listarPorCidade(cidade));
		model.addAttribute("cidades", serviceCidade.listarCidades());
		model.addAttribute("cidadeFiltro", cidade);
		return "pokeplace/lista";
	}

	@RequestMapping("/novo")
	public String addPokeplace(Model model) {
		model.addAttribute("pokeplace", new Pokeplace());
		model.addAttribute("paises", servicePais.listar());
		model.addAttribute("tipos", serviceTipo.listar());
		return "pokeplace/crud";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editPokeplace(Model model, @PathVariable("id") Long id) throws IOException {
		Pokeplace pokeplace = servicePokeplace.buscaPorId(id);

		model.addAttribute("paises", servicePais.listar());
		model.addAttribute("estados", servicePais.listarPorEstado(pokeplace.getCidade().getEstado().getPais()));
		model.addAttribute("cidades", serviceEstado.listaPorCidade(pokeplace.getCidade().getEstado()));
		model.addAttribute("tipos", serviceTipo.listar());
		List<String> lista = listaEnderecoGoogleMaps(pokeplace.getLatitude(),pokeplace.getLongitude());
		model.addAttribute("enderecos", lista);
		model.addAttribute("pokeplace", pokeplace);
		return "pokeplace/crud";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deletePokeplace(Model model, @PathVariable("id") Long id) {
		Pokeplace pokeplace = servicePokeplace.buscaPorId(id);
		servicePokeplace.remover(pokeplace);
		return "redirect:/pokeplaces";
	}

	@RequestMapping(value = "/crudPokeplace", method = RequestMethod.POST)
	public String crudPokeplace(@Valid @ModelAttribute("pokeplace") Pokeplace pokeplace, BindingResult result,
			Model model, @RequestParam("fileName") MultipartFile fileName) throws IOException {

		if (!result.hasErrors()) {
			if (!fileName.isEmpty() && fileName.getSize() <= 20971520) {
				pokeplace.setImagem(fileName.getBytes());
				pokeplace.setNomeImagem(fileName.getOriginalFilename());
			}
			if (pokeplace.getId() == null) {
				servicePokeplace.adicionar(pokeplace);
				return "redirect:/pokeplaces/novo";
			} else {
				servicePokeplace.alterar(pokeplace);
				return "redirect:/pokeplaces";
			}
		} else {
			model.addAttribute("pokeplace", pokeplace);
			model.addAttribute("paises", servicePais.listar());
			if (pokeplace.getId() == null) {
				model.addAttribute("estados", servicePais.listar());
				model.addAttribute("cidades", serviceEstado.listar());
			} else {
				if (pokeplace.getCidade().getEstado().getPais() != null) {
					model.addAttribute("estados",
							servicePais.listarPorEstado(pokeplace.getCidade().getEstado().getPais()));
					model.addAttribute("cidades", serviceEstado.listaPorCidade(pokeplace.getCidade().getEstado()));
				} else {
					model.addAttribute("estados", servicePais.listar());
					model.addAttribute("cidades", serviceEstado.listar());
				}
			}
			model.addAttribute("tipos", serviceTipo.listar());
			return "pokeplace/crud";
		}
	}

	@RequestMapping(value = "/imageDisplay", method = RequestMethod.GET)
	public void showImage(@RequestParam("id") Long id, HttpServletResponse response, HttpServletRequest request)
			throws ServletException, IOException {

		if (id > 0) {
			Pokeplace pokeplace = servicePokeplace.buscaPorId(id);
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
			response.getOutputStream().write(pokeplace.getImagem());
			response.getOutputStream().close();
		}
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.registerCustomEditor(Cidade.class, cidadePropertyEditor);
		webDataBinder.registerCustomEditor(Tipo.class, tipoPropertyEditor);
	}
	
	private List<String> listaEnderecoGoogleMaps(Double latitude,Double longitude) throws IOException {
		String latlog = latitude + "," + longitude;
		List<String> lista = new ArrayList<String>();
		GoogleResponse res = new AddressConverter().convertFromLatLong(latlog);
		String logradouro = "";
		if (res.getStatus().equals("OK")) {
			for (Result result : res.getResults()) {
				logradouro = result.getFormatted_address().replaceFirst("R. ", "Rua ");
				logradouro = logradouro.replaceFirst("Av. ", "Avenida ");
				lista.add(logradouro);
			}
		}
		return lista;
	}
	
	@RequestMapping(value = "/updatePokeplace/{logradouro}", method = RequestMethod.GET)
	@ResponseBody
	public Pokeplace googleMaps(@PathVariable("logradouro") String logradouro) throws IOException {
		int contador = 0;
		String stringReplace = "";
		String stringPais = "";
		String stringEstado = "";
		String stringCidade = "";
		
		Pokeplace pokeplace = new Pokeplace();
		GoogleResponse res = new AddressConverter().convertToLatLong(logradouro);
		
		if (res.getStatus().equals("OK")) {
			for (Result result : res.getResults()) {
				pokeplace.setLatitude(Double.parseDouble(result.getGeometry().getLocation().getLat()));
				pokeplace.setLongitude(Double.parseDouble(result.getGeometry().getLocation().getLng()));
			}
		}
		
		List<String> lista = listaEnderecoGoogleMaps(pokeplace.getLatitude(),pokeplace.getLongitude());
		pokeplace.setLogradouro(lista.get(0));
		
		Collections.reverse(lista);
		
		Cidade cidade = new Cidade();
		Estado estado = new Estado();
		Pais pais = new Pais();
		
		for (String string : lista) {
			if (contador == 0) {
				stringPais = string;
				stringReplace = ", "+string;
			}
			if (contador == 1) { // Estado
				stringReplace = ", "+stringPais;
				stringEstado = " - "+string.replaceAll(stringReplace, "");
				stringEstado = stringEstado.replaceAll(" - State of ","");
				stringReplace = " - "+string;
			}
			if (contador == 2) { // Cidade
				int pos = string.indexOf("- ");
				string = string.substring(0, pos);
				stringCidade = string;
			}	
			contador++;
		}
		
		System.out.println(stringPais);
		pais = servicePais.buscaPorNome(stringPais);
		System.out.println(pais.toString());

		System.out.println(stringEstado);
		estado = serviceEstado.buscaPorNome(pais, stringEstado);
		System.out.println(estado.toString());

		System.out.println(stringCidade);
		cidade = serviceCidade.buscaPorNome(estado,stringCidade);
		System.out.println(cidade.toString());

		estado.setPais(pais);
		cidade.setEstado(estado);
		pokeplace.setCidade(cidade);
		return pokeplace;
	}
	
}

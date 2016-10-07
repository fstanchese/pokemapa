package br.com.pokeplace.properties;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pokeplace.dao.CidadeDAO;
import br.com.pokeplace.model.Cidade;

@Component
public class CidadePropertyEditor extends PropertyEditorSupport {

	@Autowired 
	private CidadeDAO daoCidade;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id;
		Cidade cidade;
		try {
			id = Long.parseLong(text);
			cidade = daoCidade.buscaPorId(id);
		} catch (Exception e) {
			cidade = null;
		}
		setValue(cidade);
	}
}

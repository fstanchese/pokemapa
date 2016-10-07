package br.com.pokeplace.properties;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pokeplace.dao.PaisDAO;
import br.com.pokeplace.model.Pais;

@Component
public class PaisPropertyEditor extends PropertyEditorSupport {

	@Autowired 
	private PaisDAO daoPais;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id;
		Pais pais;
		try {
			id = Long.parseLong(text);
			pais = daoPais.buscaPorId(id);
		} catch (Exception e) {
			pais = null;
		}
		setValue(pais);
	}
}

package br.com.pokeplace.properties;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pokeplace.dao.TipoDAO;
import br.com.pokeplace.model.Tipo;

@Component
public class TipoPropertyEditor extends PropertyEditorSupport {

	@Autowired 
	private TipoDAO daoTipo;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id;
		Tipo tipo;
		try {
			id = Long.parseLong(text);
			tipo = daoTipo.buscaPorId(id);
		} catch (Exception e) {
			tipo = null;
		}
		setValue(tipo);
	}
}

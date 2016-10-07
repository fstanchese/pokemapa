package br.com.pokeplace.properties;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pokeplace.dao.EstadoDAO;
import br.com.pokeplace.model.Estado;

@Component
public class EstadoPropertyEditor extends PropertyEditorSupport {

	@Autowired 
	private EstadoDAO daoEstado;
	
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		Long id;
		Estado estado;
		try {
			id = Long.parseLong(text);
			estado = daoEstado.buscaPorId(id);
		} catch (Exception e) {
			estado = null;
		}
		setValue(estado);
	}
}

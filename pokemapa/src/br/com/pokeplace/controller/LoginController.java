package br.com.pokeplace.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.pokeplace.dao.UsuarioDAO;
import br.com.pokeplace.model.Usuario;

@Controller
public class LoginController {

	private UsuarioDAO daoUsuario;

	@Autowired
	public LoginController(UsuarioDAO daoUsuario) {
		this.daoUsuario = daoUsuario;
	}

	@RequestMapping("loginForm")
	public String loginForm() {
		return "formulario-login";
	}

	@RequestMapping("menu")
	public String menuForm() {
		return "menuPrincipal";
	}
	
	@RequestMapping("efetuaLogin")
	public String efetuaLogin(Usuario usuario, HttpSession session) {
		Usuario login = daoUsuario.buscaUsuario(usuario);
		if (login.getId() != null) {
			session.setAttribute("usuarioLogado", usuario);
			return "redirect:menu";
		}
		return "redirect:loginForm";
	}

	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:loginForm";
	}
}

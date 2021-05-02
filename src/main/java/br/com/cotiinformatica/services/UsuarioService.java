package br.com.cotiinformatica.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.interfaces.IUsuarioRepository;

@Service
@Transactional
public class UsuarioService {

	@Autowired
	private IUsuarioRepository usuarioRepository;

	// método para cadastrar um usuário
	public void saveOrUpdate(Usuario usuario) throws Exception {
		usuarioRepository.save(usuario);
	}

	// método para buscar 1 usuario atraves do email
	public Usuario findByEmail(String email) throws Exception {
		return usuarioRepository.findByEmail(email);
	}

	// método para buscar 1 usuario atraves do email e senha
	public Usuario findByEmailAndSenha(String email, String senha) throws Exception {
		return usuarioRepository.findByEmailAndSenha(email, senha);
	}
}



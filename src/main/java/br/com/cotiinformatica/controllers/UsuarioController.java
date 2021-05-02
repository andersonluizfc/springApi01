package br.com.cotiinformatica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.cotiinformatica.adapters.DTOEntityAdapter;
import br.com.cotiinformatica.adapters.EntityResponseAdapter;
import br.com.cotiinformatica.dtos.UsuarioPostDTO;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.responses.UsuarioPostResponse;
import br.com.cotiinformatica.services.UsuarioService;

@Controller
public class UsuarioController {

	private static final String RESOURCE = "/api/usuarios";

	@Autowired
	private UsuarioService usuarioService;

	// Método para disponibilizar um serviço de cadastro de usuario na API.
	@CrossOrigin
	@RequestMapping(value = RESOURCE, method = RequestMethod.POST)
	@ResponseBody // indica que o método irá retornar dados no serviço
	public ResponseEntity<UsuarioPostResponse> post(@RequestBody UsuarioPostDTO dto) {

		try {

			//verificar se as senhas são iguais
			if( ! dto.getSenha().equals(dto.getSenhaConfirmacao())) {
				throw new IllegalArgumentException();
			}
			//verificar se o email informado já está cadastrado
			else if(usuarioService.findByEmail(dto.getEmail()) != null) {
				throw new IllegalArgumentException();
			}
			
			Usuario usuario = DTOEntityAdapter.getUsuario(dto);			
			usuarioService.saveOrUpdate(usuario); //cadastrar o usuário

			return ResponseEntity
					.status(HttpStatus.CREATED) //201 - Criado!
					.body(EntityResponseAdapter.getUsuarioPostResponse(usuario));

		} 
		catch(IllegalArgumentException e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST) //400 (validação!)
					.body(null);
		}
		catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(null);
		}
	}
}




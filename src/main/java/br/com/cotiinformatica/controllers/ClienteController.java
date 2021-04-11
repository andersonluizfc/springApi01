package br.com.cotiinformatica.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.cotiinformatica.adapters.DTOEntityAdapter;
import br.com.cotiinformatica.adapters.EntityDTOAdapter;
import br.com.cotiinformatica.adapters.EntityResponseAdapter;
import br.com.cotiinformatica.dtos.ClienteGetDTO;
import br.com.cotiinformatica.dtos.ClientePostDTO;
import br.com.cotiinformatica.dtos.ClientePutDTO;
import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.responses.ClienteDeleteResponse;
import br.com.cotiinformatica.responses.ClientePostResponse;
import br.com.cotiinformatica.responses.ClientePutResponse;
import br.com.cotiinformatica.services.ClienteService;

@Controller
public class ClienteController {

	private static final String RESOURCE = "/api/clientes";

	@Autowired
	private ClienteService clienteService;

	@RequestMapping(value = RESOURCE, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ClientePostResponse> post(@RequestBody ClientePostDTO dto) {

		try {

			Cliente cliente = DTOEntityAdapter.getCliente(dto);
			clienteService.saveOrUpdate(cliente);

			return ResponseEntity.status(HttpStatus.CREATED).body(EntityResponseAdapter.getPostResponse(cliente));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@RequestMapping(value = RESOURCE, method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<ClientePutResponse> put(@RequestBody ClientePutDTO dto) {

		try {

			Cliente cliente = clienteService.findById(dto.getIdCliente());

			if (cliente == null)
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);

			cliente.setNome(dto.getNome());

			clienteService.saveOrUpdate(cliente);

			return ResponseEntity.status(HttpStatus.OK).body(EntityResponseAdapter.getPutResponse(cliente));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@RequestMapping(value = RESOURCE + "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<ClienteDeleteResponse> delete(@PathVariable("id") Integer id) {

		try {
			Cliente cliente = clienteService.findById(id);

			if (cliente == null)
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);

			clienteService.delete(id);

			return ResponseEntity.status(HttpStatus.OK).body(EntityResponseAdapter.getDeleteResponse(cliente));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@RequestMapping(value = RESOURCE, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<ClienteGetDTO>> getAll() {
		try {
			List<ClienteGetDTO> result = new ArrayList<ClienteGetDTO>();

			for (Cliente cliente : clienteService.findAll()) {
				result.add(EntityDTOAdapter.getDTO(cliente));

			}

			if (result == null || result.size() == 0) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
			} else {
				return ResponseEntity.status(HttpStatus.OK).body(result);
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

		}
	}

	@RequestMapping(value = RESOURCE + "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ClienteGetDTO> get(@PathVariable("id") Integer id) {
		try {
			Cliente cliente = clienteService.findById(id);

			if (cliente == null)
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(EntityDTOAdapter.getDTO(cliente));

			return ResponseEntity.status(HttpStatus.OK).body(EntityDTOAdapter.getDTO(cliente));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@RequestMapping(value = RESOURCE + "?t={text}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ClienteGetDTO> get(@PathVariable("text") String text) {
		try {
			Cliente cliente;

			if (text.contains("@")) {
				cliente = clienteService.findByEmail(text);
			} else {
				cliente = clienteService.findByCpf(text);
			}

			if (cliente == null)
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(EntityDTOAdapter.getDTO(cliente));

			return ResponseEntity.status(HttpStatus.OK).body(EntityDTOAdapter.getDTO(cliente));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}

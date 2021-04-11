package br.com.cotiinformatica.adapters;

import br.com.cotiinformatica.dtos.ClienteGetDTO;
import br.com.cotiinformatica.entities.Cliente;

public class EntityDTOAdapter {
	public static ClienteGetDTO getDTO(Cliente cliente) {
		ClienteGetDTO dto = new ClienteGetDTO();
		
		dto.setCpf(cliente.getCpf());
		dto.setEmail(cliente.getEmail());
		dto.setIdCliente(cliente.getIdCliente());
		dto.setNome(cliente.getNome());
		
		return dto;
	}
}

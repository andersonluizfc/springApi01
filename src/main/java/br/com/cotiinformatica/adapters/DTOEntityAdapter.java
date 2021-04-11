package br.com.cotiinformatica.adapters;

import br.com.cotiinformatica.dtos.ClientePostDTO;
import br.com.cotiinformatica.dtos.ClientePutDTO;
import br.com.cotiinformatica.entities.Cliente;

public class DTOEntityAdapter {
	
	public static Cliente getCliente(ClientePostDTO dto) {
		
		Cliente cliente = new Cliente();
		
		cliente.setNome(dto.getNome());
		cliente.setCpf(dto.getCpf());
		cliente.setEmail(dto.getEmail());
		
		return cliente;
		
	}
	
	public static Cliente getCliente(ClientePutDTO dto) {
		
		Cliente cliente = new Cliente();
		
		cliente.setNome(dto.getNome());
		cliente.setIdCliente(dto.getIdCliente());
		
		return cliente;
		
	}

}

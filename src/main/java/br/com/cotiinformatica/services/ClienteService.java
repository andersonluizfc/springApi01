package br.com.cotiinformatica.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.interfaces.IClienteRepository;

@Service
@Transactional
public class ClienteService {
	
	@Autowired
	private IClienteRepository clienteRepository;
	
	public void saveOrUpdate(Cliente cliente) throws Exception{
		
		if(cliente.getIdCliente() == null || cliente.getIdCliente() == 0) {
			if(clienteRepository.findByEmail(cliente.getEmail()) != null)
				throw new Exception("O e-mail informado já encontra-se cadastrado");
			
			if(clienteRepository.findByEmail(cliente.getCpf()) != null)
				throw new Exception("O CPF informado já encontra-se cadastrado");
		}
		
		clienteRepository.save(cliente);
	}
	
	public void delete(Integer idCliente) throws Exception{
		
		Cliente cliente = clienteRepository.findById(idCliente).get();
		
		if (cliente == null)
			throw new Exception("Cliente não encontrado.");
		
		clienteRepository.delete(cliente);
		
	}
	
	public List<Cliente> findAll() throws Exception {

		return (List<Cliente>) clienteRepository.findAll();
	}

	public Cliente findById(Integer id) throws Exception {

		Optional<Cliente> result = clienteRepository.findById(id); 
		
		if(!result.isEmpty())
			return result.get(); 
		
		return null;
	}
	
	public Cliente findByEmail(String email) throws Exception {

		return clienteRepository.findByEmail(email);
	}
	
	public Cliente findByCpf(String cpf) throws Exception {

		return clienteRepository.findByCpf(cpf);
	}

}

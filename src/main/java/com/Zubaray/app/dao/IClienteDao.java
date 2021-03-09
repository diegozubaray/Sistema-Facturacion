package com.Zubaray.app.dao;



import org.springframework.data.repository.CrudRepository;

import com.Zubaray.app.models.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long> {
	


}

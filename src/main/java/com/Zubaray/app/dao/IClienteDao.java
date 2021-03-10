package com.Zubaray.app.dao;




import org.springframework.data.repository.PagingAndSortingRepository;

import com.Zubaray.app.models.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {
	


}

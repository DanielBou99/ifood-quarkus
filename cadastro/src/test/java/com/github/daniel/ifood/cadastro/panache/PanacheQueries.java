package com.github.daniel.ifood.cadastro.panache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.github.daniel.ifood.cadastro.Restaurante;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import io.quarkus.panache.common.Sort.Direction;

public class PanacheQueries {
	
	public void exemploSelects() {
	
		// Find All
		PanacheQuery<Restaurante> findAll = Restaurante.findAll();
		
		// Find All ordenado
		PanacheQuery<Restaurante> findAllSorted = Restaurante.findAll(Sort.by("nome").and("id", Direction.Ascending));
		
		// Pegar todo o resultado
		List<Restaurante> list = findAll.list();
		
		// Pegar por página
		PanacheQuery<Restaurante> page = findAll.page(Page.of(3, 10));
		
		// Executar query passando parâmetros por Map
		Map<String, Object> params = new HashMap<>();
		params.put("nome", "Jose");
		Restaurante.find("select r from Restaurante where nome = :nome", params);
		
		// Executar query passando parâmetros
		String nome = "Jose";
		Restaurante.find("select r from Restaurante where nome = $1", nome);
		
		// Executar query passando parâmetros com parameters
		Restaurante.find("select r from Restaurante where nome = :nome and id = :id", 
				Parameters.with("nome", "Jose").and("id", 1L));
		
		// Atalho para findAll.stream mas precisa incluir @Transactional no método
		Restaurante.stream("select r from Restaurante where nome = :nome", params);
		Restaurante.stream("select r from Restaurante where nome = $1", nome);
		Restaurante.stream("select r from Restaurante where nome = :nome and id = :id", 
				Parameters.with("nome", "Jose").and("id", 1L));
		
		// FindById
		Restaurante findById = Restaurante.findById(1L);
		
		// Persist
		Restaurante.persist(findById,findById);
		
		// Delete
		Restaurante.delete("delete Restaurante where nome = :nome", params);
		Restaurante.delete("delete Restaurante where nome = $1", nome);
		Restaurante.delete("nome = :nome and id = :id", 
				Parameters.with("nome", "Jose").and("id", 1L));
		
		// Update
		Restaurante.update("update Restaurante where nome = :nome", params);
		Restaurante.update("update Restaurante where nome = $1", nome);
		
		// Count
		Restaurante.count();
		
		// Metodos da instância
		Restaurante restaurante = new Restaurante();
		restaurante.persistAndFlush();
		restaurante.isPersistent();
		restaurante.delete();
	}
}

@Entity
class MinhaEntidade1 extends PanacheEntity {
	
	public String nome;
}

@Entity
class MinhaEntidade2 extends PanacheEntityBase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	public String nome;
}

@Entity
class MinhaEntidade3 {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	public String nome;
}

@ApplicationScoped
class MeuRepositorio implements PanacheRepository<MinhaEntidade3> {
	
}







package edu.uark.registerapp.models.repositories;
//Here is my initial commit for sprint 4
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import edu.uark.registerapp.models.entities.ProductEntity;

public interface ProductRepository extends CrudRepository<ProductEntity, UUID> {
	Optional<ProductEntity> findById(UUID id);
	Optional<ProductEntity> findByLookupCode(String lookupCode);
}

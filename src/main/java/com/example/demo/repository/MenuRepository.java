package com.example.demo.repository;

import com.example.demo.entity.Menu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface  MenuRepository extends JpaRepository<Menu, String> {

	@Query("SELECT m FROM Menu m JOIN FETCH m.categories")
	List<Menu> findAllWithCategories();
	
	Optional<Menu> findByMenuName(String menuName);
	
}

package com.tutorial.cloudinaryrest.repository;

import com.tutorial.cloudinaryrest.entity.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Long> {
    List<Imagen> findByOrderById();
    Imagen findByName(String name);
	
	@Query(value="select * from imagen u where u.name LIKE %:name%",nativeQuery=true)
	List<Imagen> findPoductsByName(@Param("name") String name);
}

package com.tutorial.cloudinaryrest.service;

import com.tutorial.cloudinaryrest.entity.Imagen;
import com.tutorial.cloudinaryrest.repository.ImagenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ImagenService {

    @Autowired
    ImagenRepository imagenRepository;

    public List<Imagen> list(){
        return imagenRepository.findByOrderById();
    }
    
    public List<Imagen> findProductsByName(String name) {
		List<Imagen> result = new ArrayList<Imagen>();
		imagenRepository.findPoductsByName(name).forEach(result::add);
		return result;
	}
    public Optional<Imagen> findById(Long id){
        return imagenRepository.findById(id);
    }

    public void save(Imagen imagen){
        imagenRepository.save(imagen);
    }

    public void delete(Long id){
        imagenRepository.deleteById(id);
    }
    
    public Imagen Update(Imagen newProduct, Long id) {
    	Imagen oldProduct = imagenRepository.findById(id).get();
		if (!newProduct.getName().isEmpty())
			oldProduct.setName(newProduct.getName());
		if (!newProduct.getDescription().isEmpty())
			oldProduct.setDescription(newProduct.getDescription());
		/*
		if(! newProduct.getImagen().isEmpty())
			oldProduct.setImagen(newProduct.getImagen());
		*/
		return imagenRepository.save(oldProduct);
	}
    public boolean exists(Long id){
        return imagenRepository.existsById(id);
    }
}

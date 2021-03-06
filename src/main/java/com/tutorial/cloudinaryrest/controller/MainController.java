package com.tutorial.cloudinaryrest.controller;
import com.tutorial.cloudinaryrest.service.ServiceFile;
import com.tutorial.cloudinaryrest.dto.Mensaje;
import com.tutorial.cloudinaryrest.entity.Imagen;
import com.tutorial.cloudinaryrest.service.CloudinaryService;
import com.tutorial.cloudinaryrest.service.ImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class MainController {
	String subPath = "products";

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    ImagenService imagenService;
    
	@Autowired
	ServiceFile serviceFile;
	
    @GetMapping("/list")
    public ResponseEntity<List<Imagen>> list(){
        List<Imagen> list = imagenService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }
   
    @GetMapping("/get")
	public ResponseEntity<List<Imagen>> findAll(@RequestParam(value = "name", defaultValue = "") String name) {
		return ResponseEntity.ok(imagenService.findProductsByName(name));
	} 
   
    @GetMapping("/aa")
	public ResponseEntity<String> aaa() {
		return ResponseEntity.ok(System.getProperty("user.dir") + "src\\main\\resources\\static\\images\\");
	}
    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam(name = "imagen",required = false) MultipartFile file, 
			@RequestParam("name") String name,
			@RequestParam("description") String description ) {
    	
        String imageName = "product.jpg";
        if (file != null) {
			imageName = serviceFile.saveFile(file, this.subPath);
		}

		Imagen imagen = new Imagen();
		imagen.setName(name);
		imagen.setDescription(description);
		imagen.setImagen(imageName);
		imagenService.save(imagen);
		
        return new ResponseEntity(new Mensaje("imagen subida"), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
	public ResponseEntity<Imagen> findById(@PathVariable Long id) {
		Optional<Imagen> imagen = imagenService.findById(id);
		if (!imagen.isPresent()) {
			ResponseEntity.badRequest().build();
		}

		return ResponseEntity.ok(imagen.get());
	}
    
    @PutMapping("/{id}/update")
	public ResponseEntity<Imagen> updateDetails(@PathVariable Long id,@RequestBody Imagen image) {
		if (!imagenService.findById(id).isPresent()) {
			ResponseEntity.badRequest().build();
		}
		System.out.println(image.getName());
		
		return ResponseEntity.ok(imagenService.Update(image, id));
	}

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id)throws IOException {
    	Optional<Imagen> imagen = imagenService.findById(id);
    	if(!imagen.isPresent()) {
    		ResponseEntity.badRequest().build();
    	}
    	String imageName =imagen.get().getImagen();
    	
		if (!imageName.equals("product.jpg")) {

    		serviceFile.deleteFile(this.subPath + "\\" + imageName);
    	}
    	imagenService.delete(id);
    	
    	return ResponseEntity.ok().build();
    }
}

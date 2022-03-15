package cat.dbuenov.app.controlador;

import java.io.IOException;
import java.net.MalformedURLException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cat.dbuenov.app.bean.*;
import cat.dbuenov.app.dades.BaseDeDades;
import cat.dbuenov.app.errors.*;

@RestController
public class ControladorEmpleats {

	private final BaseDeDades baseDeDades;

	public ControladorEmpleats(BaseDeDades baseDeDades) {
		this.baseDeDades = baseDeDades;
	}

	// Mostra tots els empleats

	@GetMapping("/empleats")
	public ResponseEntity<List<Empleat>> mostraTots() {
		return new ResponseEntity<List<Empleat>> (baseDeDades.getEmpleats(), HttpStatus.OK);
	}

	// Crea un empleat

	@PostMapping("/empleats")
	public ResponseEntity<Empleat> nouEmpleat(@RequestBody Empleat nouEmpleat) {
		baseDeDades.add(nouEmpleat);
		return new ResponseEntity<Empleat> (nouEmpleat, HttpStatus.OK);
	}

	/*
	 * @PostMapping("/empleats") public Empleat
	 * nouEmpleat(@RequestParam(name="nom",required=true) String
	 * nom, @RequestParam(name="feina",required=true) String feina) { Empleat
	 * empleat = new Empleat(nom, new Feina(feina)); baseDeDades.add(empleat);
	 * return empleat;
	 * 
	 * }
	 */

	// Mostra un empleat

	@GetMapping("/empleats/{id}")
	public Empleat mostraEmpleat(@PathVariable int id) {

		Empleat empleat = baseDeDades.buscaId(id);

		if (empleat == null) {
			throw new EmpleatNotFoundException(id);
		}
		return empleat;
	}
	

	// Mostra empleats per feina

	@GetMapping("/feines/{feina}")
	public ResponseEntity<List<Empleat>> mostraEmpleatsFeina(@PathVariable String feina) {

		ArrayList<Empleat> empleatsFeina = baseDeDades.buscaPerFeina(feina);

		if (empleatsFeina.size() == 0) {
			throw new FeinaNotFoundException(feina);
		}
		return new ResponseEntity<List<Empleat>> (empleatsFeina, HttpStatus.OK);

	}

	// Actualitza un empleat
	@PutMapping("/empleats/{id}")
	public ResponseEntity<Empleat> canviarEmpleat(@RequestBody Empleat nouEmpleat, @PathVariable int id) {

		Empleat empleat = baseDeDades.buscaId(id);

		if (empleat != null) {
			empleat.setNom(nouEmpleat.getNom());
			empleat.setFeina(nouEmpleat.getFeina());
		} else {
			baseDeDades.add(nouEmpleat);
		}

		return new ResponseEntity<Empleat> (nouEmpleat, HttpStatus.OK);

	}

	/*
	 * @PutMapping("/empleats/{id}") public Empleat
	 * canviarEmpleat( @RequestParam(name="nom",required=true) String nom,
	 * 
	 * @RequestParam(name="feina",required=true) String feina,
	 * 
	 * @PathVariable int id) {
	 * 
	 * Empleat empleat = baseDeDades.buscaId(id); Empleat nouEmpleat = new
	 * Empleat(nom, new Feina(feina));
	 * 
	 * 
	 * if (empleat != null) { empleat.setNom(nouEmpleat.getNom());
	 * empleat.setFeina(nouEmpleat.getFeina()); }else { baseDeDades.add(nouEmpleat);
	 * }
	 * 
	 * return nouEmpleat;
	 * 
	 * }
	 */

	// Esborra un empleat

	@DeleteMapping("/empleats/{id}")
	public void esborraEmpleat(@PathVariable int id) {
		baseDeDades.esborraPerId(id);
	}

	// Puja foto empleat

	@PostMapping("/empleats/{id}/foto")
	public ResponseEntity<String> pujaFoto(@RequestParam("file") MultipartFile foto, @PathVariable int id) {

		String missatge = null;
		Empleat empleat = baseDeDades.buscaId(id); 
		if(empleat.isFoto()) {
			missatge = "Ja te foto";
		}else {		
			try {
				if (!foto.isEmpty()) {
					//Path rootPath = Paths.get("uploads").resolve(foto.getOriginalFilename()).toAbsolutePath();
					Path rootPath = Paths.get("uploads/empleat"+id+".jpg").toAbsolutePath();
					Files.copy(foto.getInputStream(), rootPath);
					missatge = "Foto pujada:" + foto.getOriginalFilename();
					empleat.setFoto(true);
					//empleat.setNomFoto(foto.getOriginalFilename());
					empleat.setNomFoto("empleat"+id+".jpg");
				} else {
					missatge = "Fitxer buit";
				}
			} catch (IOException e) {
				missatge = e.getMessage();
			}
		}
		return new ResponseEntity<String> (missatge, HttpStatus.OK);
	}

	// Descarrega foto

	@GetMapping ("empleats/{id}/foto")
	public ResponseEntity<Resource> decarregaFoto(@PathVariable int id){
		
		Empleat empleat = baseDeDades.buscaId(id);
		Resource recurs = null;
		Path pathFoto = null;
		
		if (empleat.isFoto()) {
			String nomFoto = empleat.getNomFoto();
			try {
				pathFoto = Paths.get("uploads").resolve(nomFoto).toAbsolutePath();
				recurs = new UrlResource(pathFoto.toUri());
				if(!recurs.exists() || !recurs.isReadable()){
					throw new RuntimeException("Error: no es pot carregar l'imatge: "+pathFoto.toString());
				}
			}catch(MalformedURLException e) {
				e.printStackTrace();
			}
		}else {
			throw new FotoNotFoundException(id);
		}
			
		return new ResponseEntity<Resource>(recurs, HttpStatus.OK);
	}
}

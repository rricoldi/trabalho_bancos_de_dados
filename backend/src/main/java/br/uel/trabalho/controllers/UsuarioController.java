package br.uel.trabalho.controllers;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.Base64.Encoder;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.uel.trabalho.models.Usuario;
import br.uel.trabalho.models.Podcast;
import br.uel.trabalho.repositories.UsuarioRepository;
import br.uel.trabalho.repositories.PodcastRepository;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONArray;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	PodcastRepository podcastRep;

    Encoder encoder = Base64.getEncoder();
    org.slf4j.Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    
    @RequestMapping(value="/", method=RequestMethod.GET)
	public List<Usuario> listUsers() {
		List<Usuario> lista = new ArrayList<>();
		JSONObject response = new JSONObject();

		try {
            lista = usuarioRepository.findAll();
            if(lista == null) {
                throw new Exception("Null");
            }
			response.put("users", lista);
		} catch(Exception e) {
			logger.error(e.getMessage());
			response.put("code", "400");
			response.put("status", "No Users found.");
		}
		
		return lista;
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
	public JSONObject findUser(@PathVariable("id") String id) { 
		Usuario usuario;
		JSONObject response = new JSONObject();
		try {
            usuario = usuarioRepository.find(id);
            if(usuario == null) {
                throw new Exception("Null");
            }
			response.put("code", "200");
			response.put("user", usuario);
		} catch(Exception e) {
			logger.error(e.getMessage());
			response.put("code", "400");
			response.put("status", "User not found with the provided id.");
		}
		return response;
	}

	@RequestMapping(value="/pegaPodcastsInscritos/{usr_id}", method=RequestMethod.GET)
	public JSONArray pegaPodcastsInscritos(@PathVariable("usr_id") String usr_id) {
		JSONArray response = new JSONArray();

		List<Podcast> podList = podcastRep.podcastsInscritosByUsr(usr_id);

		for(Podcast p : podList) {
			JSONObject podcast = new JSONObject();
			podcast.put("id", p.getId());
			podcast.put("rss_feed", p.getRss_feed());
			response.add(podcast);
		}

		return response;
	}

	@CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value="/", method=RequestMethod.POST)
	public JSONObject createUser(@RequestBody Usuario usuario) {
		JSONObject response = new JSONObject();
		Usuario created;

		UUID uuid = UUID.randomUUID();

		try {
            created = usuarioRepository.save(uuid.toString(), usuario.getEmail(), usuario.getNome(), usuario.getSexo(), usuario.getIdade(), encoder.encodeToString(usuario.getSenha().getBytes()), usuario.getPais());
			if(created.getNome().equals(usuario.getNome())) {
				response.put("code", "201");
				response.put("created", created);
			} else {
				response.put("status", "User Created Wrong.");
				response.put("code", "400");
			}
			
			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "User Create Failed.");
			response.put("code", "400");
			
			return response;
		}
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public JSONObject updateUser(@RequestBody Usuario usuario, @PathVariable("id") String id) {
		JSONObject response = new JSONObject();
		Usuario updated;

		try {

            updated = usuarioRepository.update(id, usuario.getEmail(), usuario.getNome(), usuario.getSexo(), usuario.getIdade(), encoder.encodeToString(usuario.getSenha().getBytes()), usuario.getPais());

			if(updated.getNome().equals(usuario.getNome())) {
				response.put("code", "200");
				response.put("updated", updated);
			} else {
				response.put("status", "User updated wrong.");
				response.put("code", "400");
			}
			
			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "User update failed.");
			response.put("code", "400");
			
			return response;
		}
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public JSONObject deleteUser(@PathVariable("id") String id) {
		JSONObject response = new JSONObject();
		Usuario deleted;

		try {
			deleted = usuarioRepository.delete(id);
			
			if(deleted != null) {
				response.put("code", "200");
				response.put("deleted", deleted);
			} else {
				response.put("status", "User not found with the provided id.");
				response.put("code", "404");
			}

			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "User delete failed.");
			response.put("code", "400");
			
			return response;
		}
	}

	@RequestMapping(value="/", method=RequestMethod.DELETE)
	public JSONObject deleteAllUsers() {
		JSONObject response = new JSONObject();
		List<Usuario> deleteds;

		try {
			deleteds = usuarioRepository.deleteAllUsers();
				
			response.put("code", "200");
			response.put("deleted", deleteds);

			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "User delete failed.");
			response.put("code", "400");
			
			return response;
		}
	}
}

package br.uel.trabalho.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import net.minidev.json.JSONObject;

import br.uel.trabalho.models.*;
import br.uel.trabalho.repositories.ComentarioRepository;
import br.uel.trabalho.services.RestService;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {
	@Autowired
	ComentarioRepository comentarioRepository;

	org.slf4j.Logger logger = LoggerFactory.getLogger(ComentarioController.class);
	RestService restService = new RestService();

	@RequestMapping(value="/", method=RequestMethod.GET)
	public JSONObject listComentarios() {
		List<Comentario> lista = new ArrayList<>();
		JSONObject response = new JSONObject();

		try {
			lista = comentarioRepository.findAll();
			if(lista == null) {
                throw new Exception("Null");
            }
			response.put("code", "200");
			response.put("comments", lista);
		} catch(Exception e) {
			logger.error(e.getMessage());
			response.put("code", "400");
			response.put("status", "No Comments found.");
		}
		
		return response;
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public JSONObject findComentario(@PathVariable("id") String id) { 
		Comentario comentario;
		JSONObject response = new JSONObject();
		try {
			comentario = comentarioRepository.find(id);
			if(comentario == null) {
                throw new Exception("Null");
            }
			response.put("code", "200");
			response.put("comment", comentario);
		} catch(Exception e) {
			logger.error(e.getMessage());
			response.put("code", "400");
			response.put("status", "Comment not found with the provided id.");
		}
		return response;
	}

	@RequestMapping(value="/", method=RequestMethod.POST)
	public JSONObject createComentario(@RequestBody Comentario comentario) {
		JSONObject response = new JSONObject();
		Comentario created;


		UUID uuid = UUID.randomUUID();

		try {
			created = comentarioRepository.save(uuid.toString(), comentario.getPod_id(), comentario.getUsr_id(), comentario.getComentario());
						
			if(created.getPod_id().equals(comentario.getPod_id())) {
				response.put("code", "201");
				response.put("created", created);
			} else {
				response.put("status", "Comment created wrong.");
				response.put("code", "400");
			}
			
			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Comment create failed.");
			response.put("code", "400");
			
			return response;
		}
	}

	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public JSONObject updateComentario(@RequestBody Comentario comentario, @PathVariable("id") String id) {
		JSONObject response = new JSONObject();
		Comentario updated;

		try {

            updated = comentarioRepository.update(id, comentario.getPod_id(), comentario.getUsr_id(), comentario.getComentario());
            
            if(updated.getPod_id().equals(comentario.getPod_id())) {
				response.put("code", "200");
				response.put("updated", updated);
			} else {
				response.put("status", "Comment updated wrong.");
				response.put("code", "400");
			}
			
			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Comment update failed.");
			response.put("code", "400");
			
			return response;
		}
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public JSONObject deleteComentario(@PathVariable("id") String id) {
		JSONObject response = new JSONObject();
		Comentario deleted;

		try {
			deleted = comentarioRepository.delete(id);
			
			if(deleted != null) {
				response.put("code", "200");
				response.put("deleted", deleted);
			} else {
				response.put("status", "Comment not found with the provided id.");
				response.put("code", "404");
			}

			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Comment delete failed.");
			response.put("code", "400");
			
			return response;
		}
	}
}

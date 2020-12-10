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
import br.uel.trabalho.repositories.ComentarioEpRepository;
import br.uel.trabalho.services.RestService;

@RestController
@RequestMapping("/comentarioEp")
public class ComentarioEpController {
	@Autowired
	ComentarioEpRepository comentarioEpRepository;

	org.slf4j.Logger logger = LoggerFactory.getLogger(ComentarioEpController.class);
	RestService restService = new RestService();

	@RequestMapping(value="/", method=RequestMethod.GET)
	public JSONObject listComentarioEps() {
		List<ComentarioEp> lista = new ArrayList<>();
		JSONObject response = new JSONObject();

		try {
			lista = comentarioEpRepository.findAll();
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
	public JSONObject findComentarioEp(@PathVariable("id") String id) { 
		ComentarioEp comentarioEp;
		JSONObject response = new JSONObject();
		try {
			comentarioEp = comentarioEpRepository.find(id);
			if(comentarioEp == null) {
                throw new Exception("Null");
            }
			response.put("code", "200");
			response.put("comment", comentarioEp);
		} catch(Exception e) {
			logger.error(e.getMessage());
			response.put("code", "400");
			response.put("status", "Comment not found with the provided id.");
		}
		return response;
	}

	@RequestMapping(value="/findByEpiId/{epi_id}", method=RequestMethod.GET)
	public JSONObject findComentarioEpByEpiId(@PathVariable("epi_id") String epi_id) { 
		List<ComentarioEp> lista = new ArrayList<>();
		JSONObject response = new JSONObject();
		try {
			lista = comentarioEpRepository.findByEpi(epi_id);
			if(lista == null) {
                throw new Exception("Null");
            }
			response.put("code", "200");
			response.put("comment", lista);
		} catch(Exception e) {
			logger.error(e.getMessage());
			response.put("code", "400");
			response.put("status", "Comment not found with the provided id.");
		}
		return response;
	}

	@RequestMapping(value="/", method=RequestMethod.POST)
	public JSONObject createComentarioEp(@RequestBody ComentarioEp comentarioEp) {
		JSONObject response = new JSONObject();
		ComentarioEp created;


		UUID uuid = UUID.randomUUID();

		try {
			created = comentarioEpRepository.save(uuid.toString(), comentarioEp.getPod_id(), comentarioEp.getEp_id(), comentarioEp.getUsr_id(), comentarioEp.getComentarioEp());
						
			if(created.getPod_id().equals(comentarioEp.getPod_id())) {
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
	public JSONObject updateComentarioEp(@RequestBody ComentarioEp comentarioEp, @PathVariable("id") String id) {
		JSONObject response = new JSONObject();
		ComentarioEp updated;

		try {

            updated = comentarioEpRepository.update(id, comentarioEp.getComentarioEp());
            
            if(updated.getPod_id().equals(comentarioEp.getPod_id())) {
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
	public JSONObject deleteComentarioEp(@PathVariable("id") String id) {
		JSONObject response = new JSONObject();
		ComentarioEp deleted;

		try {
			deleted = comentarioEpRepository.delete(id);
			
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

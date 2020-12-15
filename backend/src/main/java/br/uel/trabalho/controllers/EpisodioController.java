package br.uel.trabalho.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.uel.trabalho.models.Episodio;
import br.uel.trabalho.repositories.EpisodioRepository;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/episodio")
public class EpisodioController {
    @Autowired
	EpisodioRepository episodioRepository;
    org.slf4j.Logger logger = LoggerFactory.getLogger(EpisodioController.class);

    @RequestMapping(value="/", method=RequestMethod.GET)
	public List<Episodio> listEpisodes() {
		List<Episodio> lista = new ArrayList<>();
		JSONObject response = new JSONObject();

		try {
            lista = episodioRepository.findAll();
            if(lista == null) {
                throw new Exception("Null");
            }
			response.put("episodes", lista);
		} catch(Exception e) {
			logger.error(e.getMessage());
			response.put("code", "400");
			response.put("status", "No Episodes found.");
		}
		
		return lista;
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public JSONObject findEpisode(@PathVariable("id") String id) { 
		Episodio episodio;
		JSONObject response = new JSONObject();
		try {
            episodio = episodioRepository.find(id);
            if(episodio == null) {
                throw new Exception("Null");
            }
			response.put("code", "200");
			response.put("episode", episodio);
		} catch(Exception e) {
			logger.error(e.getMessage());
			response.put("code", "400");
			response.put("status", "Episode not found with the provided id.");
		}
		return response;
	}

	public Episodio createEpisode(String id, String podcast_id) {
		Episodio created;
		try {
            created = episodioRepository.save(id, podcast_id);
			if(podcast_id.equals(created.getPod_id())) {
				return created;
			} else {
				return null;
			}
		} catch(Exception e) {
			logger.error(e.getMessage());
			
			return null;
		}
	}
	
	public Episodio likeEpisode(String id) {
		Episodio updated, episodio;

		try {
			episodio = episodioRepository.find(id);
            if(episodio == null) {
                throw new Exception("Null");
			}

			updated = episodioRepository.update(episodio.getId(), episodio.getPod_id(), episodio.getCurtidas() + 1);
			updated.setCurtidas(updated.getCurtidas() + 1);

			return updated;
		} catch(Exception e) {		
			return null;
		}
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public JSONObject deleteEpisode(@PathVariable("id") String id) {
		JSONObject response = new JSONObject();
		Episodio deleted;

		try {
			deleted = episodioRepository.delete(id);
			
			if(deleted != null) {
				response.put("code", "200");
				response.put("deleted", deleted);
			} else {
				response.put("status", "Episode not found with the provided id.");
				response.put("code", "404");
			}

			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Episode delete failed.");
			response.put("code", "400");
			
			return response;
		}
	}
}
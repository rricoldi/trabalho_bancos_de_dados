package br.uel.trabalho.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tomcat.util.json.JSONParser;
import org.json.XML;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import net.minidev.json.JSONObject;

import br.uel.trabalho.models.*;
import br.uel.trabalho.repositories.PodcastRepository;
import br.uel.trabalho.services.RestService;

@RestController
@RequestMapping("/podcast")
public class PodcastController {
	@Autowired
	PodcastRepository podcastRepository;

	org.slf4j.Logger logger = LoggerFactory.getLogger(PodcastController.class);
	RestService restService = new RestService();

	@RequestMapping(value="/", method=RequestMethod.GET)
	public List<Podcast> listPodcasts() {
		List<Podcast> lista = new ArrayList<>();
		JSONObject response = new JSONObject();

		try {
			lista = podcastRepository.findAll();
			if(lista == null) {
                throw new Exception("Null");
            }
			response.put("podcasts", lista);
		} catch(Exception e) {
			logger.error(e.getMessage());
			response.put("code", "400");
			response.put("status", "No Podcasts found.");
		}
		
		return lista;
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public JSONObject findPodcast(@PathVariable("id") String id) { 
		Podcast podcast;
		JSONObject response = new JSONObject();
		try {
			podcast = podcastRepository.find(id);
			if(podcast == null) {
                throw new Exception("Null");
            }
			response.put("code", "200");
			response.put("podcast", podcast);
		} catch(Exception e) {
			logger.error(e.getMessage());
			response.put("code", "400");
			response.put("status", "Podcast not found with the provided id.");
		}
		return response;
	}

	@RequestMapping(value="/", method=RequestMethod.POST)
	public JSONObject createPodcast(@RequestBody Podcast podcast) {
		JSONObject response = new JSONObject();
		Podcast created;


		UUID uuid = UUID.randomUUID();

		try {
			if(podcast.getEmail() == null) {
				created = podcastRepository.saveNoEmail(uuid.toString(), podcast.getRss_feed(), podcast.getNome(), podcast.getSite());
			} else {
				created = podcastRepository.save(uuid.toString(), podcast.getRss_feed(), podcast.getNome(), podcast.getSite(), podcast.getEmail());
			}
			
			org.json.JSONObject episodes = XML.toJSONObject(restService.getPodcastXML(created.getRss_feed()));

			if(created.getNome().equals(podcast.getNome())) {
				response.put("code", "201");
				response.put("created", created);
				response.put("episodes", new JSONParser(episodes.toString()));
			} else {
				response.put("status", "Podcast created wrong.");
				response.put("code", "400");
			}
			
			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Podcast create failed.");
			response.put("code", "400");
			
			return response;
		}
	}

	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public JSONObject updatePodcast(@RequestBody Podcast podcast, @PathVariable("id") String id) {
		JSONObject response = new JSONObject();
		Podcast updated;

		try {
			if(podcast.getEmail() == null) {
				updated = podcastRepository.updateNoEmail(id, podcast.getRss_feed(), podcast.getNome(), podcast.getSite());
			} else {
				updated = podcastRepository.update(id, podcast.getRss_feed(), podcast.getNome(), podcast.getSite(), podcast.getEmail());
			}
			if(updated.getNome().equals(podcast.getNome())) {
				response.put("code", "200");
				response.put("updated", updated);
			} else {
				response.put("status", "Podcast updated wrong.");
				response.put("code", "400");
			}
			
			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Podcast update failed.");
				response.put("code", "200");
			response.put("code", "400");
			
			return response;
		}
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public JSONObject deletePodcast(@PathVariable("id") String id) {
		JSONObject response = new JSONObject();
		Podcast deleted;

		try {
			deleted = podcastRepository.delete(id);
			
			if(deleted != null) {
				response.put("code", "200");
				response.put("deleted", deleted);
			} else {
				response.put("status", "Podcast not found with the provided id.");
				response.put("code", "404");
			}

			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Podcast delete failed.");
			response.put("code", "400");
			
			return response;
		}
	}

	@RequestMapping(value="/", method=RequestMethod.DELETE)
	public JSONObject deleteAllPodcasts() {
		JSONObject response = new JSONObject();
		List<Podcast> deleteds;

		try {
			deleteds = podcastRepository.deleteAllPodcasts();
				
			response.put("code", "200");
			response.put("deleted", deleteds);

			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Podcast delete failed.");
			response.put("code", "400");
			
			return response;
		}
	}
}

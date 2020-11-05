package br.uel.trabalho.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.uel.trabalho.model.*;
import br.uel.trabalho.repositories.PodcastRepository;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/podcast")
public class PodcastController {
	@Autowired
	PodcastRepository podcastRepository;

	org.slf4j.Logger logger = LoggerFactory.getLogger(PodcastController.class);

	@GetMapping("/")
	public List<Podcast> listPodcasts() {
		List<Podcast> lista = new ArrayList<>();
		try {
			lista = podcastRepository.findAll();
		} catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		return lista;
	}

	@GetMapping("/find")
	@ResponseBody
	public JSONObject findPodcast(@RequestParam(required = true) String rss_feed) { 
		Podcast podcast;
		JSONObject response = new JSONObject();
		try {
			podcast = podcastRepository.find(rss_feed);
			response.put("rss_feed", rss_feed);
			response.put("nome", podcast.getNome());
			response.put("site", podcast.getSite());
		} catch(Exception e) {
			logger.error(e.getMessage());
			response.put("status", "Nenhum Podcast encontrado com este rss_feed.");
		}
		return response;
	}

	@PostMapping("/")
	public JSONObject createPodcast(@RequestBody Podcast podcast) {
		JSONObject response = new JSONObject();
		Podcast created;
		logger.info(podcast.getNome());

		try {
			created = podcastRepository.save(podcast.getRss_feed(), podcast.getNome(), podcast.getSite());
			if(created.getNome().equals(podcast.getNome())) {
				response.put("status", "Podcast Created.");
			} else {
				response.put("status", "Podcast Created Wrong.");
			}
			
			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Podcast Create Failed.");
			
			return response;
		}
	}

	@PutMapping("/update")
	public JSONObject updatePodcast(@RequestBody Podcast podcast, @RequestParam(required = true) String rss_feed) {
		JSONObject response = new JSONObject();
		Podcast updated;
		logger.info(podcast.getNome());

		try {
			updated = podcastRepository.update(rss_feed, podcast.getRss_feed(), podcast.getNome(), podcast.getSite());
			if(updated.getNome().equals(podcast.getNome())) {
				response.put("status", "Podcast Updated.");
			} else {
				response.put("status", "Podcast Updated Wrong.");
			}
			
			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Podcast Update Failed.");
			
			return response;
		}
	}

	@DeleteMapping("/delete")
	public JSONObject deletePodcast(@RequestParam(required = true) String rss_feed) {
		JSONObject response = new JSONObject();
		Podcast deleted;

		try {
			deleted = podcastRepository.delete(rss_feed);
			
			if(deleted != null) {
				response.put("status", "Podcast Deleted.");
			} else {
				response.put("status", "Podcast not found.");
			}

			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Podcast Delete Failed.");
			
			return response;
		}
	}

}

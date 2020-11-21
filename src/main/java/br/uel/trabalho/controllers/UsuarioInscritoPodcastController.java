package br.uel.trabalho.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.uel.trabalho.models.UsuarioInscritoPodcast;
import br.uel.trabalho.repositories.UsuarioInscritoPodcastRepository;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/inscrever")
public class UsuarioInscritoPodcastController {
    @Autowired
	UsuarioInscritoPodcastRepository usuarioInscritoPodcastRepository;
    org.slf4j.Logger logger = LoggerFactory.getLogger(UsuarioInscritoPodcastController.class);

    @RequestMapping(value="/", method=RequestMethod.GET)
	public List<UsuarioInscritoPodcast> listTags() {
		List<UsuarioInscritoPodcast> lista = new ArrayList<>();
		JSONObject response = new JSONObject();

		try {
            lista = usuarioInscritoPodcastRepository.findAll();
            if(lista == null) {
                throw new Exception("Null");
            }
			response.put("subscriptions", lista);
		} catch(Exception e) {
			logger.error(e.getMessage());
			response.put("code", "400");
			response.put("status", "No Subscriptions found.");
		}
		
		return lista;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public JSONObject findTag(@PathVariable("id") String id) {
		List<UsuarioInscritoPodcast> subscription;
		JSONObject response = new JSONObject();
		try {
            subscription = usuarioInscritoPodcastRepository.find(id);
            if(subscription == null) {
                throw new Exception("Null");
            }
			response.put("code", "200");
			response.put("subscription", subscription);
		} catch(Exception e) {
			logger.error(e.getMessage());
			response.put("code", "400");
			response.put("status", "Subscription not found with the provided id.");
		}
		return response;
	}

    @RequestMapping(value="/", method=RequestMethod.POST)
	public JSONObject createTag(@RequestBody UsuarioInscritoPodcast subscription) {
		JSONObject response = new JSONObject();
        UsuarioInscritoPodcast created;
        
		try {
            created = usuarioInscritoPodcastRepository.save(subscription.getPod_id(), subscription.getUsr_id(), subscription.getClassificacao());
			if(created.getTag().equals(subscription.getTag())) {
				response.put("code", "201");
				response.put("subscription", created);
			} else {
				response.put("status", "Subscription Created Wrong.");
				response.put("code", "400");
			}
			
			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Subscription Create Failed.");
			response.put("code", "400");
			
			return response;
		}
    }

	@RequestMapping(value="/{id}/{pod_id}", method=RequestMethod.PUT)
	public JSONObject likeTag(@PathVariable("id") String id, @PathVariable("pod_id") String pod_id, @RequestBody UsuarioInscritoPodcast newSub) {
		JSONObject response = new JSONObject();
		UsuarioInscritoPodcast updated;

		try {
			updated = usuarioInscritoPodcastRepository.update(pod_id, id, newSub.getClassificacao());
			updated.setTag(newSub.getTag());
			
			response.put("code", "200");
			response.put("updated", updated);

			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Subscription update failed.");
			response.put("code", "400");
			
			return response;
		}
	}
	
	@RequestMapping(value="/{id}/{pod_id}", method=RequestMethod.DELETE)
	public JSONObject deleteTag(@PathVariable("id") String id, @PathVariable("pod_id") String pod_id) {
		JSONObject response = new JSONObject();
		UsuarioInscritoPodcast deleted;

		try {
			deleted = usuarioInscritoPodcastRepository.delete(pod_id, id);
			
			if(deleted != null) {
				response.put("code", "200");
				response.put("deleted", deleted);
			} else {
				response.put("status", "Subscription not found with the provided id.");
				response.put("code", "404");
			}

			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Subscription delete failed.");
			response.put("code", "400");
			
			return response;
		}
	}
}
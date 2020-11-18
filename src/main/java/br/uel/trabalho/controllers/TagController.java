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

import br.uel.trabalho.models.Tag;
import br.uel.trabalho.repositories.TagRepository;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
	TagRepository tagRepository;
    org.slf4j.Logger logger = LoggerFactory.getLogger(TagController.class);

    @RequestMapping(value="/", method=RequestMethod.GET)
	public List<Tag> listTags() {
		List<Tag> lista = new ArrayList<>();
		JSONObject response = new JSONObject();

		try {
            lista = tagRepository.findAll();
            if(lista == null) {
                throw new Exception("Null");
            }
			response.put("tags", lista);
		} catch(Exception e) {
			logger.error(e.getMessage());
			response.put("code", "400");
			response.put("status", "No Tags found.");
		}
		
		return lista;
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public JSONObject findTag(@PathVariable("id") String id) {
		List<Tag> tag;
		JSONObject response = new JSONObject();
		try {
            tag = tagRepository.find(id);
            if(tag == null) {
                throw new Exception("Null");
            }
			response.put("code", "200");
			response.put("tag", tag);
		} catch(Exception e) {
			logger.error(e.getMessage());
			response.put("code", "400");
			response.put("status", "Tag not found with the provided id.");
		}
		return response;
	}

    @RequestMapping(value="/", method=RequestMethod.POST)
	public JSONObject createTag(@RequestBody Tag tag) {
		JSONObject response = new JSONObject();
        Tag created;
        
		try {
            created = tagRepository.save(tag.getTag(), tag.getPod_id());
			if(created.getTag().equals(tag.getTag())) {
				response.put("code", "201");
				response.put("tag", created);
			} else {
				response.put("status", "Tag Created Wrong.");
				response.put("code", "400");
			}
			
			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Tag Create Failed.");
			response.put("code", "400");
			
			return response;
		}
    }

	@RequestMapping(value="/{id}/{pod_tag}", method=RequestMethod.PUT)
	public JSONObject likeTag(@PathVariable("id") String id, @PathVariable("pod_tag") String pod_tag, @RequestBody Tag newTag) {
		JSONObject response = new JSONObject();
		Tag updated;

		try {
			updated = tagRepository.update(pod_tag, id, newTag.getTag());
			updated.setTag(newTag.getTag());
			
			response.put("code", "200");
			response.put("updated", updated);

			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Tag update failed.");
			response.put("code", "400");
			
			return response;
		}
	}
	
	@RequestMapping(value="/{id}/{pod_tag}", method=RequestMethod.DELETE)
	public JSONObject deleteTag(@PathVariable("id") String id, @PathVariable("pod_tag") String pod_tag) {
		JSONObject response = new JSONObject();
		Tag deleted;

		try {
			deleted = tagRepository.delete(pod_tag, id);
			
			if(deleted != null) {
				response.put("code", "200");
				response.put("deleted", deleted);
			} else {
				response.put("status", "Tag not found with the provided id.");
				response.put("code", "404");
			}

			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Tag delete failed.");
			response.put("code", "400");
			
			return response;
		}
	}
}
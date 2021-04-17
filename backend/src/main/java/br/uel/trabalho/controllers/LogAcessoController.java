package br.uel.trabalho.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.uel.trabalho.models.*;
import br.uel.trabalho.repositories.LogAcessoRepository;
import br.uel.trabalho.repositories.PodcastRepository;
import br.uel.trabalho.repositories.UsuarioRepository;
import net.minidev.json.JSONObject;

@RestController
@RequestMapping("/logAcesso")
public class LogAcessoController {
    @Autowired
	LogAcessoRepository logAcessoRepository;

	@Autowired
	PodcastRepository podcastRepository;

	@Autowired
	UsuarioRepository usuarioRepository;

    org.slf4j.Logger logger = LoggerFactory.getLogger(LogAcessoController.class);

    @RequestMapping(value="/", method=RequestMethod.GET)
	public List<LogAcesso> listAcessos() {
		List<LogAcesso> lista = new ArrayList<>();
		JSONObject response = new JSONObject();

		try {
            lista = logAcessoRepository.findAll();
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
	
	@RequestMapping(value="/findByPodcast/{id}", method=RequestMethod.GET)
	public JSONObject findAcessoByPodcast(@PathVariable("id") String id) {
		List<LogAcesso> tag;
		JSONObject response = new JSONObject();
		try {
            tag = logAcessoRepository.findByPodcast(id);
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

    @RequestMapping(value="/findByUsuario/{id}", method=RequestMethod.GET)
	public JSONObject findAcessoByUsuario(@PathVariable("id") String id) {
		List<LogAcesso> tag;
		JSONObject response = new JSONObject();
		try {
            tag = logAcessoRepository.findByUsuario(id);
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

    @RequestMapping(value="/findByUsuarioPodcast/{usr_id}/{pod_id}", method=RequestMethod.GET)
	public JSONObject findAcessoByUsuarioPodcast(@PathVariable("usr_id") String usr_id, @PathVariable("pod_id") String pod_id) {
		List<LogAcesso> tag;
		JSONObject response = new JSONObject();
		try {
            tag = logAcessoRepository.findByUsuarioPodcast(usr_id, pod_id);
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
	public JSONObject createAcesso(@RequestBody LogAcesso lAcesso) {
		JSONObject response = new JSONObject();
        LogAcesso created;
        
		try {
            created = logAcessoRepository.save(UUID.randomUUID().toString(), lAcesso.getPod_id(), lAcesso.getUsr_id());
			if(created.getId().equals(lAcesso.getId())) {
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

	@RequestMapping(value="/mediaIdadesPodcast", method=RequestMethod.GET)
	public JSONObject mediaIdadesByPodcast() {
		JSONObject response = new JSONObject();
        
		try {
			String rtn = logAcessoRepository.getMediaIdadeByPodcast();
			JSONParser jsonParser = new JSONParser(rtn);
			JSONObject obj = new JSONObject(jsonParser.parseObject());

			response.put("code", "201");
			response.put("medias", obj.get("medias"));
			
			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Averages creation failed.");
			response.put("code", "400");
			
			return response;
		}
    }

	@RequestMapping(value="/podcastsMaisVistos", method=RequestMethod.GET)
	public JSONObject podcastsMaisVistos() {
		JSONObject response = new JSONObject();
        
		try {
			String rtn = logAcessoRepository.getMostViewedPodcasts();
			JSONParser jsonParser = new JSONParser(rtn);
			JSONObject obj = new JSONObject(jsonParser.parseObject());

			response.put("code", "201");
			response.put("acessos", obj.get("acessos"));
			
			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Averages creation failed.");
			response.put("code", "400");
			
			return response;
		}
    }

	@RequestMapping(value="/tagsMaisVistas", method=RequestMethod.GET)
	public JSONObject tagsMaisVistos() {
		JSONObject response = new JSONObject();
        
		try {
			String rtn = logAcessoRepository.getMostViewedTags();
			JSONParser jsonParser = new JSONParser(rtn);
			JSONObject obj = new JSONObject(jsonParser.parseObject());

			response.put("code", "201");
			response.put("acessos", obj.get("acessos"));
			
			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Averages creation failed.");
			response.put("code", "400");
			
			return response;
		}
    }

	@RequestMapping(value="/mediaIdadesTag", method=RequestMethod.GET)
	public JSONObject mediaIdadesByTag() {
		JSONObject response = new JSONObject();
        
		try {
			String rtn = logAcessoRepository.getMediaIdadeByTag();
			JSONParser jsonParser = new JSONParser(rtn);
			JSONObject obj = new JSONObject(jsonParser.parseObject());

			response.put("code", "201");
			response.put("medias", obj.get("medias"));
			
			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Averages creation failed.");
			response.put("code", "400");
			
			return response;
		}
    }
}
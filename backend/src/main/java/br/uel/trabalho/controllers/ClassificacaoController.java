package br.uel.trabalho.controllers;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import net.minidev.json.JSONObject;

import br.uel.trabalho.models.*;
import br.uel.trabalho.repositories.ClassificacaoRepository;
import br.uel.trabalho.services.RestService;


@RestController
@RequestMapping("/classificacao")
public class ClassificacaoController {
	@Autowired
	ClassificacaoRepository classificacaoRepository;

	org.slf4j.Logger logger = LoggerFactory.getLogger(ClassificacaoController.class);
	RestService restService = new RestService();

	@RequestMapping(value="/{pod_id}/{ep_id}", method=RequestMethod.GET)
	public JSONObject findClassificacao(@PathVariable("pod_id") String pod_id, @PathVariable("ep_id") String ep_id) { 
		List<Classificacao> classificacao;
		JSONObject response = new JSONObject();
		try {
			classificacao = classificacaoRepository.find(pod_id, ep_id);
			if(classificacao == null) {
                throw new Exception("Null");
            }
			response.put("code", "200");
			response.put("comments", classificacao);
		} catch(Exception e) {
			logger.error(e.getMessage());
			response.put("code", "400");
			response.put("status", "Classifications not found with the provided ids.");
		}
		return response;
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value="/", method=RequestMethod.POST)
	public JSONObject createClassificacao(@RequestBody Classificacao classificacao) {
		JSONObject response = new JSONObject();
		Classificacao created;
		EpisodioController episodioController = new EpisodioController();
		Episodio episodio;

		try {
            created = classificacaoRepository.save(classificacao.getEp_id(), classificacao.getPod_id(), classificacao.getUsr_id());
			episodio = episodioController.likeEpisode(classificacao.getEp_id());

			if(created.getPod_id().equals(classificacao.getPod_id())) {
				response.put("code", "201");
				response.put("created", created);
				response.put("episodio", episodio);
			} else {
				response.put("status", "Classification created wrong.");
				response.put("code", "400");
			}
			
			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Classification create failed.");
			response.put("code", "400");
			
			return response;
		}
	}
	
	@RequestMapping(value="/{pod_id}/{ep_id}/{usr_id}", method=RequestMethod.DELETE)
	public JSONObject deleteClassificacao(@PathVariable("pod_id") String pod_id, @PathVariable("ep_id") String ep_id, @PathVariable("usr_id") String usr_id) {
		JSONObject response = new JSONObject();
		Classificacao deleted;

		try {
			deleted = classificacaoRepository.delete(pod_id, ep_id, usr_id);
			
			if(deleted != null) {
				response.put("code", "200");
				response.put("deleted", deleted);
			} else {
				response.put("status", "Classification not found with the provided id.");
				response.put("code", "404");
			}

			return response;
		} catch(Exception e) {
			logger.error(e.getMessage());

			response.put("status", "Classification delete failed.");
			response.put("code", "400");
			
			return response;
		}
	}
}

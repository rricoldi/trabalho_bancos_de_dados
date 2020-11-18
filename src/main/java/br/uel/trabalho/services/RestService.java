package br.uel.trabalho.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {
    private final RestTemplate restTemplate;

    public RestService() {
        this.restTemplate = new RestTemplate();
    }

    public String getPodcastXML(String url) {
        return this.restTemplate.getForObject(url, String.class);
    }
}

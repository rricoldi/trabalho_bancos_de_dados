package br.uel.trabalho.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "podcast")
public class Podcast {
    @Id
    String id;
    String rss_feed;
    String nome;
    String site;
    String email;
    int vizualizacoes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRss_feed() {
        return rss_feed;
    }

    public void setRss_feed(String rss_feed) {
        this.rss_feed = rss_feed;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getVizualizacoes() {
        return vizualizacoes;
    }

    public void setVizualizacoes(int vizualizacoes) {
        this.vizualizacoes = vizualizacoes;
    }
    
}

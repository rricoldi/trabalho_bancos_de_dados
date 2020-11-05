package br.uel.trabalho.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "podcast")
public class Podcast {
    @Id
    String rss_feed;
    String nome;
    String site;

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
    
}

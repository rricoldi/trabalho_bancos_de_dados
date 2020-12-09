package br.uel.trabalho.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "episodio")
public class Episodio {
    @Id
    String id;
    String pod_id;
    int curtidas;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPod_id() {
        return this.pod_id;
    }

    public void setPod_id(String pod_id) {
        this.pod_id = pod_id;
    }

    public int getCurtidas() {
        return this.curtidas;
    }

    public void setCurtidas(int curtidas) {
        this.curtidas = curtidas;
    }
    
}

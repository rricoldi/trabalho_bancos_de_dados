package br.uel.trabalho.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuario_comenta_episodio")
public class ComentarioEp {
    @Id
    String id;

    String usr_id;

    String pod_id;

    String ep_id;

    String comentario;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsr_id() {
        return this.usr_id;
    }

    public void setUsr_id(String usr_id) {
        this.usr_id = usr_id;
    }

    public String getPod_id() {
        return this.pod_id;
    }

    public void setPod_id(String pod_id) {
        this.pod_id = pod_id;
    }

    public String getEp_id() {
        return this.ep_id;
    }

    public void setEp_id(String ep_id) {
        this.ep_id = ep_id;
    }

    public String getComentarioEp() {
        return this.comentario;
    }

    public void setComentarioEp(String comentario) {
        this.comentario = comentario;
    }

}
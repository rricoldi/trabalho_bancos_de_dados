package br.uel.trabalho.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "log_acesso")
public class LogAcesso {
    @Id
    String id;
    String usr_id;
    String pod_id;

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

}
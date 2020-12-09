package br.uel.trabalho.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tags_podcast")
public class Tag {
    @Id
    String tag;

    String pod_id;

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPod_id() {
        return this.pod_id;
    }

    public void setPod_id(String pod_id) {
        this.pod_id = pod_id;
    }

}
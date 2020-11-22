package br.uel.trabalho.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuario_esta_inscrito_no_podcast")
public class UsuarioInscritoPodcast {
    @Id
    String usr_id;

    String pod_id;

    int classificacao;

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

	public int getClassificacao() {
		return this.classificacao;
	}

	public void setClassificacao(int classificacao) {
		this.classificacao = classificacao;
	}
}
package br.uel.trabalho.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.uel.trabalho.models.Classificacao;

@Repository
public interface ClassificacaoRepository extends JpaRepository<Classificacao, String>  {
    @Query(value="INSERT INTO trabalho.usuario_classifica_episodio(ep_id, pod_id, usr_id) VALUES(?1, ?2, ?3) RETURNING *", nativeQuery = true)
    Classificacao save(String ep_id, String pod_id, String usr_id);

    @Query(value="SELECT * FROM trabalho.usuario_classifica_episodio WHERE ep_id = ?1 and pod_id = ?2", nativeQuery = true)
	List<Classificacao>  find(String ep_id, String pod_id);
	
	@Query(value="SELECT * FROM trabalho.usuario_classifica_episodio WHERE ep_id = ?1 and pod_id = ?2 and usr_id = ?3", nativeQuery = true)
    Classificacao findByEpiPodUsr(String ep_id, String pod_id, String usr_id);

    @Query(value="DELETE FROM trabalho.usuario_classifica_episodio WHERE ep_id = ?1 and pod_id = ?2 and usr_id = ?3 RETURNING *", nativeQuery = true)
    Classificacao delete(String ep_id, String pod_id, String usr_id);
}

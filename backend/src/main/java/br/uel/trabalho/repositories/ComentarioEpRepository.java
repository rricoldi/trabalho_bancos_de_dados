package br.uel.trabalho.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.uel.trabalho.models.ComentarioEp;

@Repository
public interface ComentarioEpRepository extends JpaRepository<ComentarioEp, String>  {
    @Query(value = "SELECT * FROM trabalho.usuario_comenta_episodio", nativeQuery = true)
    List<ComentarioEp> findAll();

    @Query(value="INSERT INTO trabalho.usuario_comenta_episodio(id, pod_id, ep_id, usr_id, comentario) VALUES(?1, ?2, ?3, ?4, ?5) RETURNING *", nativeQuery = true)
    ComentarioEp save(String id, String pod_id, String ep_id, String usr_id, String comentario);

    @Query(value="SELECT * FROM trabalho.usuario_comenta_episodio WHERE id = ?1", nativeQuery = true)
	ComentarioEp find(String id);
	
	@Query(value="SELECT COALESCE(COUNT(*),0) FROM trabalho.usuario_comenta_episodio WHERE ep_id = ?1", nativeQuery = true)
    int countCmtByEpi(String epi_id);

    @Query(value="UPDATE trabalho.usuario_comenta_episodio SET comentario = ?2 WHERE id = ?1 RETURNING *", nativeQuery = true)
    ComentarioEp update(String id, String comentario);

    @Query(value="DELETE FROM trabalho.usuario_comenta_episodio WHERE id = ?1 RETURNING *", nativeQuery = true)
    ComentarioEp delete(String id);
}

package br.uel.trabalho.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.uel.trabalho.models.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, String>  {
    @Query(value = "SELECT * FROM trabalho.comentarios_podcast_usuario", nativeQuery = true)
    List<Comentario> findAll();

    @Query(value="INSERT INTO trabalho.comentarios_podcast_usuario(id, pod_id, usr_id, comentario) VALUES(?1, ?2, ?3, ?4) RETURNING *", nativeQuery = true)
    Comentario save(String id, String pod_id, String usr_id, String comentario);

    @Query(value="SELECT * FROM trabalho.comentarios_podcast_usuario WHERE pod_id = ?1", nativeQuery = true)
	List<Comentario> find(String id);
	
	@Query(value="SELECT COALESCE(COUNT(*),0) FROM trabalho.comentarios_podcast_usuario WHERE pod_id = ?1", nativeQuery = true)
	int countCmtsByPod(String pod_id);

    @Query(value="UPDATE trabalho.comentarios_podcast_usuario SET pod_id = ?2, usr_id = ?3, comentario = ?4 WHERE id = ?1 RETURNING *", nativeQuery = true)
    Comentario update(String id, String pod_id, String usr_id, String comentario);

    @Query(value="DELETE FROM trabalho.comentarios_podcast_usuario WHERE id = ?1 RETURNING *", nativeQuery = true)
    Comentario delete(String id);

    @Query(value="DELETE FROM trabalho.comentarios_podcast_usuario WHERE id LIKE '%' RETURNING *", nativeQuery = true)
    List<Comentario> deleteAllComentarios();
}

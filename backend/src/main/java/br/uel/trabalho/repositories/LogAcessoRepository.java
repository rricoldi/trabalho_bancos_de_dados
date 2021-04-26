package br.uel.trabalho.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.uel.trabalho.models.LogAcesso;

@Repository
public interface LogAcessoRepository extends JpaRepository<LogAcesso, String>  {
    @Query(value = "SELECT * FROM trabalho.log_acesso", nativeQuery = true)
    List<LogAcesso> findAll();

    @Query(value = "SELECT DISTINCT la.usr_id, la.pod_id FROM trabalho.log_acesso la", nativeQuery = true)
    List<LogAcesso> findAllDistinctUser();

    @Query(value = "select trabalho.getMediaIdadeByPodcast()", nativeQuery = true)
    String getMediaIdadeByPodcast();

    @Query(value = "select trabalho.getMostViewedPodcasts()", nativeQuery = true)
    String getMostViewedPodcasts();

    @Query(value = "select trabalho.getMostViewedTags()", nativeQuery = true)
    String getMostViewedTags();

    @Query(value = "select trabalho.getMediaIdadeByTag()", nativeQuery = true)
    String getMediaIdadeByTag();

    @Query(value="INSERT INTO trabalho.log_acesso(id, pod_id, usr_id) VALUES(?1, ?2, ?3) RETURNING *", nativeQuery = true)
    LogAcesso save(String id, String pod_id, String usr_id);

    @Query(value="SELECT * FROM trabalho.log_acesso WHERE usr_id = ?1", nativeQuery = true)
	List<LogAcesso> findByUsuario(String id);

    @Query(value="SELECT * FROM trabalho.log_acesso WHERE pod_id = ?1", nativeQuery = true)
	List<LogAcesso> findByPodcast(String id);
	
	@Query(value="SELECT * FROM trabalho.log_acesso WHERE usr_id = ?1 AND pod_id = ?2", nativeQuery = true)
	List<LogAcesso> findByUsuarioPodcast(String usr_id, String pod_id);

    @Query(value="DELETE FROM trabalho.log_acesso WHERE id = ?1 RETURNING *", nativeQuery = true)
    LogAcesso delete(String id);



    @Query(value="DELETE FROM trabalho.log_acesso WHERE pod_id LIKE '%' RETURNING *", nativeQuery = true)
    List<LogAcesso> deleteAllUsers();
}

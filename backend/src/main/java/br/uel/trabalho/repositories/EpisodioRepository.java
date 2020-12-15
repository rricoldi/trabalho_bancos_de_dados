package br.uel.trabalho.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.uel.trabalho.models.Episodio;

@Repository
public interface EpisodioRepository extends JpaRepository<Episodio, String>  {
    @Query(value = "SELECT * FROM trabalho.episodio", nativeQuery = true)
    List<Episodio> findAll();

    @Query(value="INSERT INTO trabalho.episodio(id, pod_id, curtidas) VALUES(?1, ?2, 0) RETURNING *", nativeQuery = true)
    Episodio save(String id, String pod_id);

    @Query(value="SELECT * FROM trabalho.episodio WHERE id = ?1", nativeQuery = true)
	Episodio find(String id);
	
	@Query(value="SELECT * FROM trabalho.episodio WHERE pod_id = ?1", nativeQuery = true)
	List<Episodio> findByPod(String pod_id);

    @Query(value="UPDATE trabalho.episodio SET pod_id = ?2, curtidas = ?3 WHERE id = ?1 RETURNING *", nativeQuery = true)
    Episodio update(String id, String pod_id, int curtidas);

    @Query(value="DELETE FROM trabalho.episodio WHERE id = ?1 RETURNING *", nativeQuery = true)
    Episodio delete(String id);

    @Query(value="DELETE FROM trabalho.episodio WHERE id LIKE '%' RETURNING *", nativeQuery = true)
    List<Episodio> deleteAllEpisodios();
}

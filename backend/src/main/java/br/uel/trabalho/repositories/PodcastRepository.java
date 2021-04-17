package br.uel.trabalho.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.uel.trabalho.models.Podcast;

@Repository
public interface PodcastRepository extends JpaRepository<Podcast, String> {
    @Query(value = "SELECT * FROM trabalho.podcast", nativeQuery = true)
    List<Podcast> findAll();

    @Query(value="INSERT INTO trabalho.podcast(id, rss_feed, nome, site, email) VALUES(?1, ?2, ?3, ?4, ?5) RETURNING *", nativeQuery = true)
    Podcast save(String id, String rss_feed, String nome, String site, String email);

    @Query(value="INSERT INTO trabalho.podcast(id, rss_feed, nome, site) VALUES(?1, ?2, ?3, ?4) RETURNING *", nativeQuery = true)
    Podcast saveNoEmail(String id, String rss_feed, String nome, String site);

    @Query(value="SELECT * FROM trabalho.podcast WHERE id = ?1", nativeQuery = true)
	Podcast find(String id);

	@Query(value="SELECT * FROM trabalho.podcast WHERE nome LIKE %?1%", nativeQuery = true)
	List<Podcast> findByKeyword(String keyword);
	
	@Query(value="SELECT pc.* FROM trabalho.podcast pc JOIN trabalho.tags_podcast tp ON tp.pod_id = pc.id AND tp.tag = ?1", nativeQuery = true)
	List<Podcast> findByTag(String tag);

	@Query(value="SELECT p.* FROM trabalho.podcast p JOIN trabalho.usuario_esta_inscrito_no_podcast uip ON p.id = uip.pod_id WHERE uip.usr_id=?1", nativeQuery = true)
    List<Podcast> podcastsInscritosByUsr(String usr_id);
	
	@Query(value="SELECT trabalho.geraRelatorioPodcast(?1);", nativeQuery = true)
	String relatorioPodcast(String pod_id);

    @Query(value="UPDATE trabalho.podcast SET rss_feed = ?2, nome = ?3, site = ?4, email = ?5 WHERE id = ?1 RETURNING *", nativeQuery = true)
    Podcast update(String id, String new_rss_feed, String new_nome, String new_site, String new_email);

    @Query(value="UPDATE trabalho.podcast SET rss_feed = ?2, nome = ?3, site = ?4 WHERE id = ?1 RETURNING *", nativeQuery = true)
    Podcast updateNoEmail(String id, String new_rss_feed, String new_nome, String new_site);

    @Query(value="UPDATE trabalho.podcast SET vizualizacoes = vizualizacoes+1 WHERE id = ?1 RETURNING *", nativeQuery = true)
    Podcast addVizualizacao(String id);

    @Query(value="DELETE FROM trabalho.podcast WHERE id = ?1 RETURNING *", nativeQuery = true)
    Podcast delete(String id);

    @Query(value="DELETE FROM trabalho.podcast WHERE id LIKE '%' RETURNING *", nativeQuery = true)
    List<Podcast> deleteAllPodcasts();
}

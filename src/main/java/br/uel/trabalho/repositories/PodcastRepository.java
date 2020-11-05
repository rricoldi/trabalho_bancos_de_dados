package br.uel.trabalho.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.uel.trabalho.model.Podcast;

@Repository
public interface PodcastRepository extends JpaRepository<Podcast, String> {
    @Query(value = "SELECT rss_feed, nome, site FROM trabalho.podcast ORDER BY rss_feed", nativeQuery = true)
    List<Podcast> findAll();

    @Query(value="INSERT INTO trabalho.podcast(rss_feed, nome, site) VALUES(?1, ?2, ?3) RETURNING rss_feed, nome, site", nativeQuery = true)
    Podcast save(String rss_feed, String nome, String site);

    @Query(value="SELECT rss_feed, nome, site FROM trabalho.podcast WHERE rss_feed = ?1", nativeQuery = true)
    Podcast find(String rss_feed);

    @Query(value="UPDATE trabalho.podcast SET rss_feed = ?2, nome = ?3, site = ?4 WHERE rss_feed = ?1 RETURNING rss_feed, nome, site", nativeQuery = true)
    Podcast update(String rss_feed, String new_rss_feed, String new_nome, String new_site);

    @Query(value="DELETE FROM trabalho.podcast WHERE rss_feed = ?1 RETURNING rss_feed, nome, site", nativeQuery = true)
    Podcast delete(String rss_feed);
}

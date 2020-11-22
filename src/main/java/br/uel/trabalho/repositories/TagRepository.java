package br.uel.trabalho.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.uel.trabalho.models.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, String>  {
    @Query(value = "SELECT * FROM trabalho.tags_podcast", nativeQuery = true)
    List<Tag> findAll();

    @Query(value="INSERT INTO trabalho.tags_podcast(tag, pod_id) VALUES(?1, ?2) RETURNING *", nativeQuery = true)
    Tag save(String tag, String pod_id);

    @Query(value="SELECT * FROM trabalho.tags_podcast WHERE pod_id = ?1", nativeQuery = true)
    List<Tag> find(String pod_id);

    @Query(value="UPDATE trabalho.tags_podcast SET tag = ?3 WHERE tag = ?1 and pod_id = ?2 RETURNING *", nativeQuery = true)
    Tag update(String tag, String pod_id, String newTag);

    @Query(value="DELETE FROM trabalho.tags_podcast WHERE tag = ?1 and pod_id = ?2 RETURNING *", nativeQuery = true)
    Tag delete(String tag, String pod_id);

    @Query(value="DELETE FROM trabalho.tags_podcast WHERE pod_id LIKE '%' RETURNING *", nativeQuery = true)
    List<Tag> deleteAllTags();
}

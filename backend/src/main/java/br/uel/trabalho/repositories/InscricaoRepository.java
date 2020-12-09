package br.uel.trabalho.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.uel.trabalho.models.Inscricao;

@Repository
public interface InscricaoRepository extends JpaRepository<Inscricao, String>  {
    @Query(value = "SELECT * FROM trabalho.usuario_esta_inscrito_no_podcast", nativeQuery = true)
    List<Inscricao> findAll();

    @Query(value="INSERT INTO trabalho.usuario_esta_inscrito_no_podcast(pod_id, usr_id, classificacao) VALUES(?1, ?2, ?3) RETURNING *", nativeQuery = true)
    Inscricao save(String pod_id, String usr_id, int classificacao);

    @Query(value="SELECT * FROM trabalho.usuario_esta_inscrito_no_podcast WHERE usr_id = ?1", nativeQuery = true)
    List<Inscricao> find(String id);

    @Query(value="UPDATE trabalho.usuario_esta_inscrito_no_podcast SET classificacao = ?3 WHERE pod_id = ?1 and usr_id = ?2 RETURNING *", nativeQuery = true)
    Inscricao update(String pod_id, String usr_id, int classificacao);

    @Query(value="DELETE FROM trabalho.usuario_esta_inscrito_no_podcast WHERE pod_id = ?1 and usr_id = ?2 RETURNING *", nativeQuery = true)
    Inscricao delete(String pod_id, String usr_id);

    @Query(value="DELETE FROM trabalho.usuario_esta_inscrito_no_podcast WHERE pod_id LIKE '%' RETURNING *", nativeQuery = true)
    List<Inscricao> deleteAllUsers();
}

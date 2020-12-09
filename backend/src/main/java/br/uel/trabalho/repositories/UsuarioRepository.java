package br.uel.trabalho.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.uel.trabalho.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>  {
    @Query(value = "SELECT * FROM trabalho.usuario", nativeQuery = true)
    List<Usuario> findAll();

    @Query(value="INSERT INTO trabalho.usuario(id, email, nome, sexo, idade, senha, pais) VALUES(?1, ?2, ?3, ?4, ?5, ?6, ?7) RETURNING *", nativeQuery = true)
    Usuario save(String id, String email, String nome, String sexo, int idade, String senha, String pais);

    @Query(value="SELECT * FROM trabalho.usuario WHERE id = ?1", nativeQuery = true)
    Usuario find(String id);

    @Query(value="UPDATE trabalho.usuario SET email = ?2, nome = ?3, sexo = ?4, idade = ?5, senha = ?6, pais = ?7 WHERE id = ?1 RETURNING *", nativeQuery = true)
    Usuario update(String id, String email, String nome, String sexo, int idade, String senha, String pais);

    @Query(value="DELETE FROM trabalho.usuario WHERE id = ?1 RETURNING *", nativeQuery = true)
    Usuario delete(String id);

    @Query(value="DELETE FROM trabalho.usuario WHERE id LIKE '%' RETURNING *", nativeQuery = true)
    List<Usuario> deleteAllUsers();
}

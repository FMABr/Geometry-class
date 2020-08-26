package br.com.geometryclass.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.geometryclass.data.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

}

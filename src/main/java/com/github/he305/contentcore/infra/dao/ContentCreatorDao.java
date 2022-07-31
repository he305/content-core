package com.github.he305.contentcore.infra.dao;

import com.github.he305.contentcore.infra.jpa.JpaContentCreator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContentCreatorDao extends JpaRepository<JpaContentCreator, UUID> {
}

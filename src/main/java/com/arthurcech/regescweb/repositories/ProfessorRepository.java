package com.arthurcech.regescweb.repositories;

import com.arthurcech.regescweb.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}

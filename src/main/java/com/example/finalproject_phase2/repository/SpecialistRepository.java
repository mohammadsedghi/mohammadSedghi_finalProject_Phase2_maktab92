package com.example.finalproject_phase2.repository;

import com.example.finalproject_phase2.entity.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface SpecialistRepository extends JpaRepository<Specialist,Long> {
    Optional<Specialist> findByEmail(String email);
    Optional<Specialist> findByEmailAndPassword(String email,String password);
    Collection<Specialist> showUnConfirmSpecialist();
    Collection<Specialist> showConfirmSpecialist();
}

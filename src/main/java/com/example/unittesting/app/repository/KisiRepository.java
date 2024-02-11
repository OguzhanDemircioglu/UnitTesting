package com.example.unittesting.app.repository;

import com.example.unittesting.app.model.Kisi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KisiRepository extends JpaRepository<Kisi,Long> {
}

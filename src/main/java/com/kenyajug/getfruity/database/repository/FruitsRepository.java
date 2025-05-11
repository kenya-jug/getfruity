package com.kenyajug.getfruity.database.repository;

import com.kenyajug.getfruity.database.entities.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FruitsRepository extends JpaRepository<Fruit,Long> {
}

package com.kenyajug.getfruity.database.repository;
import com.kenyajug.getfruity.database.entities.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface FruitsRepository extends JpaRepository<Fruit,Long> {
    @Query(value = """
        WITH fruits AS (
            SELECT id, ROW_NUMBER() OVER (PARTITION BY name, image_url ORDER BY id) AS rn
            FROM fruit
        )
        SELECT id FROM fruits WHERE rn > 1
        """, nativeQuery = true)
    List<Long> findDuplicateIds();
}

package com.kenyajug.getfruity;

import com.kenyajug.getfruity.database.entities.Fruit;
import com.kenyajug.getfruity.database.repository.FruitsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
@ExtendWith(SpringExtension.class)
public class FruitsRepositoryTest {
    @Autowired
    private FruitsRepository fruitsRepository;
    @Test
    public void shouldSaveFruitsTest(){
        var fruit = new Fruit(
                null,
                "Apple",
                "Crunchy",
                "🍎",
                BigDecimal.valueOf(45.12),
                BigDecimal.valueOf(90.12)
        );
        fruitsRepository.save(fruit);
        var entities = fruitsRepository.findAll();
        assertThat(entities).isNotEmpty();
        assertThat(entities.size()).isEqualTo(1);
    }
    @Test
    @DisplayName(
    """
    Should detect and remove one duplicate Apple entry based on identical name, category, emoji, price, and market value
    """
    )
    public void shouldFindDuplicateApplesTest(){
        var fruit = new Fruit(
                null,
                "Apple",
                "Crunchy",
                "🍎",
                BigDecimal.valueOf(45.12),
                BigDecimal.valueOf(90.12)
        );
        var duplicateFruit = new Fruit(
                null,
                "Apple",
                "Crunchy",
                "🍎",
                BigDecimal.valueOf(45.12),
                BigDecimal.valueOf(90.12)
        );
        fruitsRepository.save(fruit);
        fruitsRepository.save(duplicateFruit);
        var entities = fruitsRepository.findAll();
        assertThat(entities).isNotEmpty();
        assertThat(entities.size()).isEqualTo(2);
        var duplicateIds = fruitsRepository.findDuplicateIds();
        assertThat(duplicateIds).isNotEmpty();
        assertThat(duplicateIds.size()).isEqualTo(1);
        var duplicateFruitId = duplicateIds.getFirst();
        var duplicateEntity = fruitsRepository.findById(duplicateFruitId);
        assertThat(duplicateEntity).isNotEmpty();
        assertThat(duplicateEntity.get().getName()).isEqualTo("Apple");
        assertThat(duplicateEntity.get().getCategory()).isEqualTo("Crunchy");
        fruitsRepository.deleteAllById(duplicateIds);
        entities = fruitsRepository.findAll();
        assertThat(entities).isNotEmpty();
        assertThat(entities.size()).isEqualTo(1);
    }
    @Test
    @DisplayName(
            """
            Should detect and remove two duplicate Apple records when three identical entries exist with same name, category, emoji, price, and market value
            """
    )
    public void shouldFindDuplicateApples_Case2_Test(){
        var fruit = new Fruit(
                null,
                "Apple",
                "Crunchy",
                "🍎",
                BigDecimal.valueOf(45.12),
                BigDecimal.valueOf(90.12)
        );
        var duplicateFruit = new Fruit(
                null,
                "Apple",
                "Crunchy",
                "🍎",
                BigDecimal.valueOf(45.12),
                BigDecimal.valueOf(90.12)
        );
        var duplicateDuplicateFruit = new Fruit(
                null,
                "Apple",
                "Crunchy",
                "🍎",
                BigDecimal.valueOf(45.12),
                BigDecimal.valueOf(90.12)
        );
        fruitsRepository.save(fruit);
        fruitsRepository.save(duplicateFruit);
        fruitsRepository.save(duplicateDuplicateFruit);
        var entities = fruitsRepository.findAll();
        assertThat(entities).isNotEmpty();
        assertThat(entities.size()).isEqualTo(3);
        var duplicateIds = fruitsRepository.findDuplicateIds();
        assertThat(duplicateIds).isNotEmpty();
        assertThat(duplicateIds.size()).isEqualTo(2);
        var duplicateFruitId = duplicateIds.getFirst();
        var duplicateEntity = fruitsRepository.findById(duplicateFruitId);
        assertThat(duplicateEntity).isNotEmpty();
        assertThat(duplicateEntity.get().getName()).isEqualTo("Apple");
        assertThat(duplicateEntity.get().getCategory()).isEqualTo("Crunchy");
        fruitsRepository.deleteAllById(duplicateIds);
        entities = fruitsRepository.findAll();
        assertThat(entities).isNotEmpty();
        assertThat(entities.size()).isEqualTo(1);
    }
    @DisplayName(
    """
    Should detect and remove two duplicate Apple entries while retaining one unique Orange entry and one original Apple
    """
    )
    @Test
    public void shouldFindDuplicateApples_Case3_Test(){
        var uniqueFruit = new Fruit(
                null,
                "Orange",
                "Crunchy",
                "🍎",
                BigDecimal.valueOf(45.12),
                BigDecimal.valueOf(90.12)
        );
        var fruit = new Fruit(
                null,
                "Apple",
                "Crunchy",
                "🍎",
                BigDecimal.valueOf(45.12),
                BigDecimal.valueOf(90.12)
        );
        var duplicateFruit = new Fruit(
                null,
                "Apple",
                "Crunchy",
                "🍎",
                BigDecimal.valueOf(45.12),
                BigDecimal.valueOf(90.12)
        );
        var duplicateDuplicateFruit = new Fruit(
                null,
                "Apple",
                "Crunchy",
                "🍎",
                BigDecimal.valueOf(45.12),
                BigDecimal.valueOf(90.12)
        );
        fruitsRepository.save(fruit);
        fruitsRepository.save(duplicateFruit);
        fruitsRepository.save(duplicateDuplicateFruit);
        fruitsRepository.save(uniqueFruit);
        var entities = fruitsRepository.findAll();
        assertThat(entities).isNotEmpty();
        assertThat(entities.size()).isEqualTo(4);
        var duplicateIds = fruitsRepository.findDuplicateIds();
        assertThat(duplicateIds).isNotEmpty();
        assertThat(duplicateIds.size()).isEqualTo(2);
        var duplicateFruitId = duplicateIds.getFirst();
        var duplicateEntity = fruitsRepository.findById(duplicateFruitId);
        assertThat(duplicateEntity).isNotEmpty();
        assertThat(duplicateEntity.get().getName()).isEqualTo("Apple");
        assertThat(duplicateEntity.get().getCategory()).isEqualTo("Crunchy");
        fruitsRepository.deleteAllById(duplicateIds);
        entities = fruitsRepository.findAll();
        assertThat(entities).isNotEmpty();
        assertThat(entities.size()).isEqualTo(2);
    }
}

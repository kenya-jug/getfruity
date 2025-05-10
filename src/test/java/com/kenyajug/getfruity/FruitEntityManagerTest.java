package com.kenyajug.getfruity;

import com.kenyajug.getfruity.database.entities.Fruit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class FruitEntityManagerTest {
    @Autowired
    private TestEntityManager entityManager;
    @Test
    public void shouldEnsureFruitIsEntity_Test(){
        var fruit = new Fruit(null,
                "Cherry",
                "Small Fruit",
                "🍒",
                BigDecimal.valueOf(50L),
                BigDecimal.valueOf(1000L));
        var entity = entityManager.persistAndFlush(fruit);
        //Assertions
        assertThat(entity).isNotNull();
        assertThat(entity.getName()).isEqualTo("Cherry");
    }
}

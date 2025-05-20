package com.kenyajug.getfruity;

import com.kenyajug.getfruity.database.repository.FruitsRepository;
import com.kenyajug.getfruity.jobs.StoreCleaner;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

@SpringBootTest
public class StoreCleanerTest {
    @MockitoSpyBean
    private StoreCleaner storeCleaner;
    @MockitoBean
    private FruitsRepository fruitsRepository;
    @Test
    public void shouldRunStoreCleanerCronTest() throws InterruptedException{
        TimeUnit.SECONDS.sleep(3);
        when(fruitsRepository.findDuplicateIds()).thenReturn(List.of(1L,2L,3L,5L));
        verify(storeCleaner, atLeastOnce()).cleanStore();
    }
}

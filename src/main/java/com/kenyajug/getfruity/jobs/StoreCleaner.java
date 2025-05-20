package com.kenyajug.getfruity.jobs;
import com.kenyajug.getfruity.database.repository.FruitsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class StoreCleaner {
    private final FruitsRepository fruitsRepository;
    public StoreCleaner(FruitsRepository fruitsRepository) {
        this.fruitsRepository = fruitsRepository;
    }
    @Scheduled(fixedDelay = 1_000)
    public void cleanStore(){
        log.info("Cleaning up the store...");
        var duplicateIds = fruitsRepository.findDuplicateIds();
        fruitsRepository.deleteAllById(duplicateIds);
    }
}

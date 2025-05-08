package finki.ukim.mk.jobs;

import finki.ukim.mk.repository.AccommodationsPerHostViewRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
public class ScheduledTasks {

    private final AccommodationsPerHostViewRepository accommodationsPerHostViewRepository;

    public ScheduledTasks(AccommodationsPerHostViewRepository accommodationsPerHostViewRepository) {
        this.accommodationsPerHostViewRepository = accommodationsPerHostViewRepository;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void refreshMaterializedView(){
        accommodationsPerHostViewRepository.refreshMaterializedView();
    }
}

package maciej.grochowski.fightmetric.pinnacle.repository;

import maciej.grochowski.fightmetric.pinnacle.entity.EventDB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventDB, Integer> {
}

package codegym.c10.webservice.model.repository;

import codegym.c10.webservice.model.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IScheduleRepository extends JpaRepository<Schedule, Integer> {
}

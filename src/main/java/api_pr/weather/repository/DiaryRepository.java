package api_pr.weather.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api_pr.weather.domain.Diary;

@Repository
public interface DiaryRepository extends JpaRepository<Diary,Long> {

	List<Diary> findAllByLocalDate(LocalDate localDate);

	List<Diary> findAllByLocalDateBetween(LocalDate startDate, LocalDate endDate);
	Diary getFirstByLocalDate(LocalDate localDate);


}

package api_pr.weather.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api_pr.weather.domain.Memo;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {
}

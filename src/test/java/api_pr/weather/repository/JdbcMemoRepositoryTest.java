package api_pr.weather.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import api_pr.weather.domain.Memo;

@SpringBootTest
@Transactional // 테스트에서 아무리 저장해도 복구함
class JdbcMemoRepositoryTest {

	@Autowired
	JdbcMemoRepository jdbcMemoRepository;

	@Test
	void insertMemoTest() {

		//given
		Memo thisIsIsNewMemo = new Memo(1, "this is is new Memo");

		//when
		jdbcMemoRepository.save(thisIsIsNewMemo);

		//then
		Memo memo = jdbcMemoRepository.findById(1L).get();
		Assertions.assertThat(memo.getId()).isEqualTo(1L);
		Assertions.assertThat(memo.getText()).isEqualTo("this is is new Memo");
	}

	@Test
	void findAllMemoTest() {
		//given
		Memo thisIsIsNewMemo = new Memo(1, "this is is new Memo");
		Memo thisIsIsNewMemo2 = new Memo(2, "this is is new Memo2");


		//when
		jdbcMemoRepository.save(thisIsIsNewMemo);
		jdbcMemoRepository.save(thisIsIsNewMemo2);

		//then
		List<Memo> all = jdbcMemoRepository.findAll();
		Assertions.assertThat(all.size()).isEqualTo(2);



	}


}
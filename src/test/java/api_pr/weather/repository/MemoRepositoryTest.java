package api_pr.weather.repository;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import api_pr.weather.domain.Memo;

@SpringBootTest
@Transactional
class MemoRepositoryTest {

	@Autowired
	MemoRepository memoRepository;

	@Test
	void insertMemoTest() {
		//given
		Memo thisIsIsNewMemo = new Memo(1, "this is is new Memo");

		//when
		memoRepository.save(thisIsIsNewMemo);

		//then
		assertFalse(memoRepository.findAll().isEmpty());
	}

	@Test
	void findByIdTest() {
		//given
		Memo thisIsIsNewMemo = new Memo(123, "jpa memo");

		//when
		memoRepository.save(thisIsIsNewMemo);

		//then
		Memo memo = memoRepository.findById(123L).get();

		assertEquals(memo.getText(),"jpa mo");

	}
}
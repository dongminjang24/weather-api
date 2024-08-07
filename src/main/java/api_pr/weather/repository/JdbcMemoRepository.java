package api_pr.weather.repository;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import api_pr.weather.domain.Memo;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JdbcMemoRepository {
	private final JdbcTemplate jdbcTemplate;

	// public JdbcMemoRepository(DataSource dataSource) {
	// 	this.jdbcTemplate = new JdbcTemplate(dataSource);
	// }

	public Memo save(Memo memo) {
		String sql = "insert into memo values(?,?)";
		jdbcTemplate.update(sql, memo.getId(), memo.getText());
		return memo;
	}

	public List<Memo> findAll() {
		String sql = "select * from memo";
		return jdbcTemplate.query(sql, memoRowMapper());
	}

	public Optional<Memo> findById(Long id) {
		String sql = "select * from memo where id = ?";
		return jdbcTemplate.query(sql, memoRowMapper(), id).stream().findFirst();
	}
	private RowMapper<Memo> memoRowMapper() {
		return ((rs, rowNum) -> new Memo(rs.getInt("id"), rs.getString("text")));
	}
}

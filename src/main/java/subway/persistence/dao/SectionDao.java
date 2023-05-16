package subway.persistence.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import subway.persistence.row.SectionRow;

import java.util.List;

@Repository
public class SectionDao {

    private static final int BATCH_SIZE = 50;

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<SectionRow> rowMapper = (rs, cn) -> new SectionRow(
            rs.getLong("id"),
            rs.getLong("line_id"),
            rs.getString("up_bound"),
            rs.getString("down_bound"),
            rs.getInt("distance")
    );

    public SectionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SectionRow> selectAllOfLinePropertyId(Long linePropertyId) {
        String sectionSql = "select id, line_id, up_bound, down_bound, distance from section where line_id = ?";
        return jdbcTemplate.query(sectionSql, rowMapper, linePropertyId);
    }

    public void insertAll(List<SectionRow> rows) {
        String sql = "insert into section (line_id, up_bound, down_bound, distance) values (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, rows, BATCH_SIZE,
                (ps, entity) -> {
                    ps.setLong(1, entity.getLineId());
                    ps.setString(2, entity.getUpBound());
                    ps.setString(3, entity.getDownBound());
                    ps.setInt(4, entity.getDistance());
                });
    }

    public void removeSections(Long lineId) {
        String sql = "delete from section where line_id = ?";
        jdbcTemplate.update(sql, lineId);
    }

    public List<SectionRow> selectAll() {
        String sql = "select * from section";
        return jdbcTemplate.query(sql, rowMapper);
    }
}

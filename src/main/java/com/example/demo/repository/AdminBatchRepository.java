package com.example.demo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.example.demo.entity.Admin;

@Repository
@RequiredArgsConstructor
public class AdminBatchRepository {

	private final JdbcTemplate jdbcTemplate;

	public void batchInsert(List<Admin> admin) {

		String sql = "INSERT INTO admin_details " + "(id,first_name,last_name,email,mobile_number) "
				+ "VALUES(?,?,?,?,?) " + "ON CONFLICT DO NOTHING";

		jdbcTemplate.batchUpdate(sql, admin, admin.size(), (ps, employee) -> {
			ps.setObject(1, employee.getId());
			ps.setString(2, employee.getFirstName());
			ps.setString(3, employee.getLastName());
			ps.setString(4, employee.getEmail());
			ps.setString(5, employee.getMobileNumber());
		});

	}

	public void streamAllAdmins(AdminRowCallback callback) {

		String sql = """
				SELECT id,
				       first_name,
				       last_name,
				       email,
				       mobile_number
				FROM admin_details
				""";

		jdbcTemplate.query(sql, rs -> {

			callback.processRow(rs);

		});

	}

	@FunctionalInterface
	public interface AdminRowCallback {

		void processRow(ResultSet rs) throws SQLException;
	}

}

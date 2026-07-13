package com.example.demo.writer;
import com.example.demo.entity.Admin;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class AdminWriter implements ItemWriter<Admin> {

private final JdbcTemplate jdbcTemplate;

    @Override
    public void write(
            Chunk<? extends Admin> chunk) {

        String sql =
                """
                INSERT INTO admin_details
                (
                    id,
                    first_name,
                    last_name,
                    email,
                    mobile_number
                )
                VALUES (?,?,?,?,?)
                ON CONFLICT DO NOTHING
                """;

        jdbcTemplate.batchUpdate(
                sql,
                chunk.getItems(),
                chunk.size(),
                (ps, admin) -> {

                    ps.setObject(1, admin.getId());
                    ps.setString(2, admin.getFirstName());
                    ps.setString(3, admin.getLastName());
                    ps.setString(4, admin.getEmail());
                    ps.setString(5, admin.getMobileNumber());

                });
    }


}

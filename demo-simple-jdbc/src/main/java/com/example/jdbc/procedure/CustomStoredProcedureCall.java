package com.example.jdbc.procedure;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class CustomStoredProcedureCall {
    private final JdbcTemplate jdbcTemplate;

    /**
     *
     * @param employeeId is in param and name is the out param
     * @return
     */
    public Map<String, String> getEmployeeName(int employeeId) {
        return jdbcTemplate.execute(
                (Connection connection) -> {
                    try (CallableStatement cs = connection.prepareCall("{call get_employee_name(?, ?)}")) {
                        cs.setInt(1, employeeId);
                        cs.registerOutParameter(2, Types.VARCHAR);
                        cs.execute();
                        Map<String, String> result = new HashMap<>();
                        result.put("employeeName", cs.getString(2));
                        return result;
                    } catch (SQLException e) {
                        throw new RuntimeException("Error calling stored procedure");
                    }
                }
        );
    }
}

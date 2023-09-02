/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.example.postgres;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CustomPostgreSQLProcedure {
    private final Connection connection;
    public CustomPostgreSQLProcedure() throws SQLException {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "postgres";
        connection = DriverManager.getConnection(jdbcUrl,username,password);
    }

    /**
     * Simple JDBC call
     * @param id
     * @return
     * @throws SQLException
     */
    public String getNameUsingSimpleJDBCCallProcedure(int id) throws SQLException {
        PreparedStatement preparedStatement=connection.prepareStatement("select get_employee_name_by_id(?) as e_name");
        preparedStatement.setInt(1,id);
        ResultSet resultSet=preparedStatement.executeQuery();
        String employeeName=resultSet.next() ? resultSet.getString("e_name") : null;
        resultSet.close();
        return employeeName;
    }

    /**
     * Using CallableStatement for stored procedure call
     * @param id
     * @return
     * @throws SQLException
     */
    public String getNameUsingCallableStatementCallProcedure(int id) throws SQLException {
        String call = "{call get_employee_name_by_id(?, ?)}";
        CallableStatement callableStatement = connection.prepareCall(call);
        callableStatement.setInt(1, id);
        callableStatement.registerOutParameter(2, Types.VARCHAR);
        callableStatement.execute();
        String employeeName = callableStatement.getString(2);
        callableStatement.close();
        connection.close();
        return employeeName;
    }

    public static void main(String[] args) throws SQLException {
//        System.out.println(new CustomPostgreSQLProcedure().getNameUsingCallableStatementCallProcedure(1));
//        System.out.println(new CustomPostgreSQLProcedure().getNameUsingSimpleJDBCCallProcedure(2));
        System.out.println(longestValidParentheses(")("));
    }

    public static int longestValidParentheses(String s) {
        if((!s.contains("(") && !s.contains(")")) || (s.contains("(") && !s.contains(")")) || (!s.contains("(") && s.contains(")")))
            return 0;
        Map<String,Integer> map=new HashMap<>();
        Arrays.stream(s.split("")).forEach(ch->{
            if(map.containsKey(ch))
                map.put(ch,map.get(ch)+1);
            else
                map.put(ch,1);
        });
        if(map.get(")")==1 && map.get("(")==1)
            return 0;
        return map.get("(") <= map.get(")") ? map.get("(") *2 : map.get(")") *2;
    }
}


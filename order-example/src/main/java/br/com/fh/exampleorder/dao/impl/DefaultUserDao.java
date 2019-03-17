package br.com.fh.exampleorder.dao.impl;

import br.com.fh.exampleorder.dao.UserDao;
import br.com.fh.exampleorder.model.user.Login;
import br.com.fh.exampleorder.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DefaultUserDao implements UserDao {

    @Autowired
    private DataSource datasource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User register(final User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("username", user.getUsername())
                .addValue("password", user.getPassword())
                .addValue("firstname", user.getFirstname())
                .addValue("lastname", user.getLastname())
                .addValue("email", user.getEmail())
                .addValue("address", user.getAddress())
                .addValue("phone", user.getPhone());

        Number number = simpleJdbcInsert.executeAndReturnKey(params);

        user.setId(number.intValue());
        return user;
    }

    @Override
    public void update(User user) {
        String sql = "update users " +
                "set username = ?," +
                "    password = ?," +
                "    firstname = ?," +
                "    lastname = ?," +
                "    email = ?," +
                "    address = ?," +
                "    phone = ? " +
                " where id = ?";
        jdbcTemplate.update(sql, new Object[]{user.getUsername(),
                user.getPassword(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getAddress(),
                user.getPhone(),
                user.getId()});
    }

    @Override
    public void delete(Integer id) {
        String sql = "delete from users where id=?";
        jdbcTemplate.update(sql, new Object[]{id});
    }


    @Override
    public User load(Integer id) {
        String sql = "select * from users where id='" + id + "'";
        List<User> users = jdbcTemplate.query(sql, new UserMapper());
        return users.size() > 0 ? users.get(0) : null;
    }

    @Override
    public List<User> findAll() {
        String sql = "select * from users ";
        List<User> users = jdbcTemplate.query(sql, new UserMapper());
        return users;
    }

    public User validateUser(Login login) {
        String sql = "select * from users where username='" + login.getUsername() + "' and password='" + login.getPassword()
                + "'";
        List<User> users = jdbcTemplate.query(sql, new UserMapper());
        return users.size() > 0 ? users.get(0) : null;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DataSource getDatasource() {
        return datasource;
    }

    public void setDatasource(DataSource datasource) {
        this.datasource = datasource;
    }
}

class UserMapper implements RowMapper<User> {
    public User mapRow(ResultSet rs, int arg1) throws SQLException {
        User user = new User();
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setFirstname(rs.getString("firstname"));
        user.setLastname(rs.getString("lastname"));
        user.setEmail(rs.getString("email"));
        user.setAddress(rs.getString("address"));
        user.setPhone(rs.getInt("phone"));
        user.setId(rs.getInt("id"));
        return user;
    }
}

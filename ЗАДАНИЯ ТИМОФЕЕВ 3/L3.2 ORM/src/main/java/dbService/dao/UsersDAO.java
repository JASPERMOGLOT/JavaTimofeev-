package dbService.dao;

import dbService.dataSets.UsersDataSet;
import dbService.executor.Executor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersDAO {

    private Executor executor;

    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public UsersDataSet get(long id) throws SQLException {
        return executor.execQuery("select * from users where id=" + id, result -> {
            result.next();
            return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3));
        });
    }

    public long getUserId(String name) throws SQLException {
        return executor.execQuery("select * from users where login='" + name + "'", result -> {
            result.next();
            return result.getLong(1);
        });
    }

    public List<UsersDataSet> getAllUsers() throws SQLException {
        List<UsersDataSet> results = new ArrayList<>();
        return executor.execQuery("select * from users", result -> {
            while (result.next()) {
                results.add(new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3)));
            }
            return results;
        });
    }

    public UsersDataSet getUserByLogin(String login) throws SQLException {
        return executor.execQuery("select * from users where login = '" + login + "'", result -> {
            if (result.next())
                return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3));
            else
                return null;
        });
    }


    public void insertUser(String name, String password) throws SQLException {
        executor.execUpdate("insert into users (login, password) values ('" + name + "', '" + password + "')");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users (id bigint auto_increment, login varchar(256), password varchar(256), primary key (id))");
    }

    public void dropTable() throws SQLException {
        executor.execUpdate("drop table users");
    }
}

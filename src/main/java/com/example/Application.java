package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

@Repository
class Repo1 {

    @Autowired JdbcTemplate jdbcTemplate;

    /**
     * fails at some batch if list contains a null
     */
    public void saveIntsInBatchesBy2(List<Integer> ints) {
        jdbcTemplate.batchUpdate("insert into some_table values (?) ", ints, 2, (ps, value) -> {
            ps.setInt(1, value);
        });
    }
}

@Repository
class Repo2 {

    @Autowired JdbcTemplate jdbcTemplate;

    public int count() {
        return jdbcTemplate.queryForObject("select my_count()", Integer.class);
    }
}

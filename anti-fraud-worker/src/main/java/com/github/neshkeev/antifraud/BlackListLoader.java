package com.github.neshkeev.antifraud;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class BlackListLoader {
    private final AtomicReference<Set<String>> blacklist;
    private final JdbcTemplate jdbcTemplate;

    public BlackListLoader(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        blacklist = new AtomicReference<>(Set.of());
    }

    public Set<String> getBlackList() {
        return blacklist.get();
    }

    @PostConstruct
    public void postConstruct() {
        final Set<String> newBlackList = new HashSet<>();

        jdbcTemplate.query("SELECT value FROM black_list", rs -> {
            newBlackList.add(rs.getString(1));
        });

        blacklist.set(newBlackList);
    }
}

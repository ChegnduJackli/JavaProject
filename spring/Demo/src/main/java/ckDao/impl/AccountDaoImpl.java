package ckDao.impl;

import ckDao.AccountDao;
import org.springframework.jdbc.core.JdbcTemplate;


public class AccountDaoImpl implements AccountDao {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void out(String outUser, int money) {
        this.jdbcTemplate.update("update account set money =money-?"
                + " where username =?", money, outUser);
    }

    @Override
    public void in(String inUser, int money) {
        this.jdbcTemplate.update("update account set money =money+?"
                + " where username =?", money, inUser);
    }
}

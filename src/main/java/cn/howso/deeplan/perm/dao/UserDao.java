package cn.howso.deeplan.perm.dao;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import cn.howso.deeplan.perm.model.User;
@Repository
public class UserDao {
	@Resource(name="jdbcTemplate")
	private NamedParameterJdbcTemplate template;
	public User getById(Long id){
		User u = new User();
		u.setId(-1);
		SqlParameterSource sps = new BeanPropertySqlParameterSource(u);
		return (User) template.queryForObject("select * from sys_user where id=:id",sps,new BeanPropertyRowMapper(User.class));
	}
}

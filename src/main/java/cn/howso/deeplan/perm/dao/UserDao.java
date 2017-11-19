package cn.howso.deeplan.perm.dao;

import javax.annotation.Resource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.howso.deeplan.perm.model.User;
@Repository
public class UserDao {
	@Resource
	private NamedParameterJdbcTemplate template;
	public User getById(Long id){
		return template.getJdbcOperations().queryForObject("select * from sys_user where id=:id", User.class, id);
	}
}

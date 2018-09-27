package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.dal.dao.UserDao;
import com.example.demo.dal.entity.UserEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMongoApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired // 测试mongodb数据库功能
	private UserDao userDao;

	@Test
	public void testSaveUser() throws Exception {
		UserEntity user = new UserEntity();
		user.setId(7L);
		user.setUserName("abcdfdf");
		user.setPassWord("123321");
		userDao.saveUser(user);
	}

	@Test
	public void findUserByUserName() {
		UserEntity user = userDao.findUserByUserName("小红");
		System.out.println("user is " + user.getUserName() + "---" + user.getPassWord());
	}

	// @Test
	public void updateUser() {
		UserEntity user = new UserEntity();
		user.setId(2l);
		user.setUserName("天空");
		user.setPassWord("fffxxxx");
		userDao.updateUser(user);
	}

//	 @Test
	public void deleteUserById() {
		userDao.deleteUserById(6l);
	}

}

package com.test.redflower2.service.impl;

import com.test.redflower2.constant.UserConstant;
import com.test.redflower2.dao.UserDao;
import com.test.redflower2.pojo.dto.ResultBuilder;
import com.test.redflower2.pojo.entity.User;
import com.test.redflower2.service.UserService;
import com.test.redflower2.utils.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 更新用户
     * @param user 修改后的user
     */
    @Override
    public void update(User user) {
        userDao.save(user);
    }

    /**
     * 通过id查询user
     * @param id
     * @return
     */
    @Override
    public User getUserById(Integer id) {
        return userDao.getUserById(id);
    }

    /**
     * 用户登录
     *
     * @param openId
     * @return
     */
    @Override
    public Map<Integer, String> isLoginSuccess(String openId, HttpSession session) {
        Map<Integer, String> datas = new HashMap<>();
        if (ObjectUtil.isStringEmpty(openId)) {
            int status;
            status = UserConstant.FAILED_CODE;
            datas.put(status, UserConstant.OPENID_NULL);
        }
        User user = userDao.getUserByOpenid(openId);
        //user为空，则新建一个user并保存
        if (ObjectUtil.isEmpty(user)) {
            int status;
            User user1 = new User();
            user1.setOpenid(openId);
            user1.setState(UserConstant.USER_INFO_INCOMPLETED);
            userDao.save(user1);
            status = UserConstant.SUCCESS_CODE;
            session.setAttribute(UserConstant.USER_ID, user1.getId());
            datas.put(status, UserConstant.SUCCESS_MSG);
        }
        return datas;
    }

    /**
     * 显示人脉网界面随机某一个用户的信息，并且显示与主用户的亲密度
     *
     * @param user
     * @param session
     * @return
     */
    @Override
    public Map<String, List<User>> getNetworkUserInfo(User user, HttpSession session) {
        Map<String, List<User>> datas = new HashMap<>();
        List<User> userList = new ArrayList<>();
        Integer uid = user.getId();
        User sUser = userDao.getUserById(uid);
        userList.add(sUser);
        datas.put(UserConstant.USER_LIST, userList);
        return datas;
    }

    /**
     * 更新用户个性签名
     * @param definition
     * @param session
     * @return
     */
    @Override
    public String updateDefinition(String definition, HttpSession session) {
        if (definition.length()>10){
            return UserConstant.USER_DEFINITION_LENGTH;
        }else {
            Integer uid = (Integer) session.getAttribute(UserConstant.USER_ID);
            User user = userDao.getUserById(uid);
            user.setDefinition(definition);
            userDao.save(user);
            return UserConstant.SUCCESS_MSG;
        }
    }

    /**
     * 更新用户名称
     * @param uid
     * @param username
     * @return
     */
    @Override
    public String updateName(Integer uid, String username) {
        User user = userDao.getUserById(uid);
        String username1 = user.getName();
        if (username1.equals(username)) {
            return UserConstant.FAIL_MSG;
        } else {
            user.setName(username);
            userDao.save(user);
            return UserConstant.SUCCESS_MSG;
        }
    }
}

package com.test.redflower2.dao;

import com.test.redflower2.pojo.entity.Network;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>Created on 18-11-5</p>
 *
 * @author:StormWangxhu
 * @description: <p>描述</p>
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class NetworkDaoTest {

    @Autowired
    private NetworkDao networkDao;

    private static  final Logger LOGGER= LoggerFactory.getLogger(NetworkDaoTest.class);

    @Test
    public void testGetNetworkByUid(){
        Integer uid = 1;
        Network result = networkDao.getNetworkByUid(uid);
        LOGGER.info("getNetworkByUid()测试结果{}",result);
    }

    @Test
    public void testGetNetworkById(){
        Integer id =3;
        Network result = networkDao.getNetworkById(id);
        LOGGER.info("getNetworkById()测试结果{}",result);
    }
}
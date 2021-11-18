package com.anyfork.task;

import com.anyfork.enums.GenderEnum;
import com.anyfork.modules.userInfo.entity.UserInfoEntity;
import com.anyfork.modules.userInfo.service.IUserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * @PackageName: com.anyfork.task
 * @ClassName: ScheduleTask
 * @Description: 定时任务
 * @Author: 小紫念沁
 * @Date: 2021/10/28 11:48
 * @Version 1.0
 */
@Component
@Slf4j
public class ScheduleTask {

    private final IUserInfoService userInfoService;

    @Autowired
    public ScheduleTask( IUserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Async
    @Scheduled(fixedDelay = 60000)
    public void taskOne(){
        //创建用户信息
        UserInfoEntity userInfo = UserInfoEntity.builder().userBirth(LocalDate.now()).userName("小紫念沁").userGender(GenderEnum.MALE).userPhone("15389074965")
                .userStatus(0).userPassword("123456").build();
        userInfoService.save(userInfo);
    }



    @Async
    @Scheduled(fixedDelay = 30000)
    public void taskTwo(){
        Page<UserInfoEntity> userInfo =  userInfoService.page(new Page<>(1,10));
        userInfo.getRecords().forEach(System.out::println);
    }
}

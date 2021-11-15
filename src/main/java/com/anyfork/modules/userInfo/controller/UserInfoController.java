package com.anyfork.modules.userInfo.controller;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.system.UserInfo;
import com.anyfork.modules.userInfo.entity.UserInfoEntity;
import com.anyfork.modules.userInfo.service.IUserInfoService;
import com.anyfork.utils.ResponseBody;
import com.anyfork.utils.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @PackageName: com.anyfork.modules.userInfo.controller
 * @ClassName: UserInfoController
 * @Description: 用户管理
 * @Author: 小紫念沁
 * @Date: 2021/10/29 17:26
 * @Version 1.0
 */
@RestController
@Validated
@Slf4j
@RequestMapping("/userInfo")
public class UserInfoController {

   private final IUserInfoService userInfoService;

    public UserInfoController(IUserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/list")
    public ResultEntity<Map<String, List<UserInfoEntity>>> list(){
        List<UserInfoEntity> list = userInfoService.list();
        Map<String, List<UserInfoEntity>> userInfo = Collections.singletonMap("userInfo", list);
        return  ResponseBody.success(userInfo);
    }

    @PostMapping("/getUserInfoByRequestBody")
    public ResultEntity<UserInfo> getUserInfoByRequestBody(@RequestBody UserInfo userInfo) {
        return ResponseBody.success(userInfo);

    }

    @PostMapping("/getUserInfoByRequestParam")
    public ResultEntity<UserInfo> getUserInfoByRequestParam(UserInfo userInfo) {
        log.info("{}",userInfo.getCurrentDir());
        return ResponseBody.success(userInfo);
    }
    @PostMapping("/addUserInfo")
    public ResultEntity<JSON> addUserInfo(@Validated  @RequestBody JSONObject map) {
        return ResponseBody.success(map);
    }

    @PostMapping("/createOrder426")
    public ResultEntity<JSON> createOrder1235332(@RequestBody JSONObject map){
        return ResponseBody.success(map);
    }

    @PostMapping("/updateUserInfo")
    public ResultEntity<UserInfo> updateUserInfo(@Validated  @RequestBody UserInfo userInfo) {
        return ResponseBody.success(userInfo);
    }

    /**
     * 获取授权企业信息
     * @param corpId 授权企业cropId
     * @return ResultBody
     */
    @GetMapping("/cropDataSync")
    public ResultEntity<String> cropDataSync(@RequestParam(value = "corpId",required = false) String corpId){
        //获取授权方企业信息
        return ResponseBody.success(corpId);
    }
}


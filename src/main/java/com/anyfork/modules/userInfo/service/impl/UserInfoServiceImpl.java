package com.anyfork.modules.userInfo.service.impl;

import com.anyfork.modules.userInfo.entity.UserInfoEntity;
import com.anyfork.modules.userInfo.mapper.UserInfoMapper;
import com.anyfork.modules.userInfo.service.IUserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 小紫念沁
 * @since 2021-10-27 15:33:06
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfoEntity> implements IUserInfoService {
}

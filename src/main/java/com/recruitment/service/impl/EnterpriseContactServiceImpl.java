package com.recruitment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.recruitment.entity.DO.EnterpriseContact;
import com.recruitment.mapper.EnterpriseContactMapper;
import com.recruitment.service.EnterpriseContactService;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseContactServiceImpl extends ServiceImpl<EnterpriseContactMapper, EnterpriseContact>
        implements EnterpriseContactService {
    // 如果有自定义业务逻辑，可在这里扩展实现
}

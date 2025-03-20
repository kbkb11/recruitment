package com.recruitment.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.recruitment.entity.DO.EnterpriseContact;
import com.recruitment.service.EnterpriseContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enterpriseContact")
public class EnterpriseContactController {

    @Autowired
    private EnterpriseContactService enterpriseContactService;

    // 新增联系人
    @PostMapping("/add")
    public boolean addEnterpriseContact(@RequestBody EnterpriseContact contact) {
        return enterpriseContactService.save(contact);
    }

    // 删除联系人
    @DeleteMapping("/delete/{id}")
    public boolean deleteEnterpriseContact(@PathVariable Long id) {
        return enterpriseContactService.removeById(id);
    }

    // 更新联系人
    @PutMapping("/update")
    public boolean updateEnterpriseContact(@RequestBody EnterpriseContact contact) {
        return enterpriseContactService.updateById(contact);
    }

    // 获取联系人详情
    @GetMapping("/get/{id}")
    public EnterpriseContact getEnterpriseContact(@PathVariable Long id) {
        return enterpriseContactService.getById(id);
    }

    // 分页获取联系人列表
    @GetMapping("/list")
    public List<EnterpriseContact> getEnterpriseContacts(@RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        Page<EnterpriseContact> contactPage = new Page<>(page, size);
        return enterpriseContactService.page(contactPage).getRecords();
    }
}

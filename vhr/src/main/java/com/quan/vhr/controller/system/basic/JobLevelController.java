package com.quan.vhr.controller.system.basic;

import com.quan.vhr.bean.Joblevel;
import com.quan.vhr.bean.RespBean;
import com.quan.vhr.service.JobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/basic/joblevel")
public class JobLevelController {
    @Autowired
    JobLevelService jobLevelService;
    // 查询所有职称
    @GetMapping("/")
    public List<Joblevel> getAllJobLevels(){
       return jobLevelService.getAllJobLevels();
    }

    // 添加职称
    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody Joblevel joblevel){
        System.out.println(joblevel.getId());
        if(jobLevelService.addJobLevel(joblevel)==1){
            return RespBean.ok("添加成功");
        }else {
            return RespBean.error("添加失败");
        }
    }

    // 修改职称
    @PutMapping("/")
    public RespBean updateJobLevelById(@RequestBody Joblevel joblevel){
        if (jobLevelService.updateJobLevelById(joblevel) == 1){
            return RespBean.ok("更新成功");
        }else{
            return RespBean.error("更新失败");
        }
    }

    // 删除职称
    @RequestMapping("/{id}")
    public RespBean deleteJobLevelById(@PathVariable Integer id){
         if(jobLevelService.deleteJobLevelById(id)==1){
             return RespBean.ok("删除成功");
         }else{
             return RespBean.error("删除失败");
         }
    }
}

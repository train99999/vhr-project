package com.quan.vhr.service;

import com.quan.vhr.bean.Joblevel;
import com.quan.vhr.mapper.JoblevelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JobLevelService {
    @Autowired
    JoblevelMapper joblevelMapper;
    public List<Joblevel> getAllJobLevels() {
       return joblevelMapper.getAllJobLevels();
    }

    public int addJobLevel(Joblevel joblevel) {
        joblevel.setCreatedate(new Date());
        joblevel.setEnabled(true);
       return joblevelMapper.insertSelective(joblevel);
    }

    public int updateJobLevelById(Joblevel joblevel) {
        return joblevelMapper.updateByPrimaryKeySelective(joblevel);
    }

    public int deleteJobLevelById(Integer id) {
        return joblevelMapper.deleteByPrimaryKey(id);
    }

    public int deleteJobLevelByIds(Integer[] ids) {
        return joblevelMapper.deleteJobLevelByIds(ids);
    }
}

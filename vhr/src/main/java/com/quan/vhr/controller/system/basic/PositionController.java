package com.quan.vhr.controller.system.basic;

import com.quan.vhr.bean.Position;
import com.quan.vhr.bean.RespBean;
import com.quan.vhr.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/basic/pos")
public class PositionController {

    @Autowired
    PositionService positionService;
    @GetMapping("/")
    public List<Position> getAllPositions(){
        return positionService.getAllPositions();
    }

    // 添加接口
    // RequestBody 以JSON的形式来传递
    @PostMapping("/")
    public RespBean addPosition( @RequestBody Position position){
        if(positionService.addPosition(position) == 1){
            return RespBean.ok("添加成功");
        }else{
            return RespBean.error("添加失败");
        }
    }

    // 更新接口
    @PutMapping("/")
    public RespBean updatePositions(@RequestBody Position position){
        if(positionService.updatePositions(position) == 1){
            return RespBean.ok("更新成功");
        }else{
            return RespBean.error("更新失败");
        }
    }

    // 删除接口
    @DeleteMapping("/{id}")
    public RespBean deletePositionById(@PathVariable Integer id){
        if(positionService.deletePositionById(id) == 1){
            return RespBean.ok("删除成功");
        }else{
            return RespBean.error("删除失败");
        }
    }
}

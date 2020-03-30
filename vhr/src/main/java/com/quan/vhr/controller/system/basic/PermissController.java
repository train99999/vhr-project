package com.quan.vhr.controller.system.basic;

import com.quan.vhr.bean.Menu;
import com.quan.vhr.bean.RespBean;
import com.quan.vhr.bean.Role;
import com.quan.vhr.service.MenuService;
import com.quan.vhr.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/basic/permiss")
public class PermissController {
    @Autowired
    RoleService roleService;
    @Autowired
    MenuService menuService;
    // 查询所有角色
    @GetMapping("/")
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }

    // 查询所有菜单
    @GetMapping("/menus")
    public List<Menu> getAllMenus(){
        return  menuService.getAllMenus();
    }

    // 根据角色ID，找出拥有的菜单权限
    @GetMapping("/mids/{rid}")
    public List<Integer> getMidsByRid(@PathVariable Integer rid){
        return menuService.getMidsByRid(rid);
    }

    // 更新角色菜单
    // 更新角色业务逻辑：先根据 rid 把所有跟角色相关的菜单资源都删除了，然后根据 mids 在重新插入该角色的菜单资源
    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid,Integer[] mids){
        if(menuService.updateMenuRole(rid,mids)){
            return RespBean.ok("更新成功");
        }
        return RespBean.error("更新失败");
    }

    // 添加角色
    @PostMapping("/role")
    public RespBean addRole(@RequestBody Role role){
        if(roleService.addRole(role)==1){
            return RespBean.ok("添加成功");
        }
        return RespBean.error("添加失败");
    }

    // 删除角色
    @DeleteMapping("/role/{rid}")
    public RespBean deleteRoleById(@PathVariable Integer rid){
        if(roleService.deleteRoleById(rid)==1){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }
}

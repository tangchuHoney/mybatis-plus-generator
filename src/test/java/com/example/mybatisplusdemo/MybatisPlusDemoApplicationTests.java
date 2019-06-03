//package com.example.mybatisplusdemo;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.example.mybatisplusdemo.entity.SysAccess;
//import com.example.mybatisplusdemo.entity.TbUser;
//import com.example.mybatisplusdemo.mapper.SysAccessMapper;
//import com.example.mybatisplusdemo.mapper.TbUserMapper;
//import com.example.mybatisplusdemo.service.impl.TbUserServiceImpl;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class MybatisPlusDemoApplicationTests {
//    @Autowired
//    private SysAccessMapper sysAccessMapper;
//
//    @Autowired
//    private TbUserServiceImpl tbUserService;
//
//    @Autowired
//    private TbUserMapper tbUserMapper; //在对应的mapper上加上@Repository就不会爆红
//
//    //====================基于mapper的查询========================
//    @Test
//    //根据id查询
//    //生成的sql:  SELECT * FROM sys_access WHERE id = '1'
//    public void test01() {
//        SysAccess sysAccess = sysAccessMapper.selectById("1");
//        System.out.println(sysAccess);
//
//    }
//
//    @Test
//    //根据id批量查询 注意你的数据库id是 String   还是int   我这里表id是String
//   //生成的sql:  SELECT * FROM sys_access WHERE id IN ( '1' , '2' , '3' )
//    // @param idList 主键ID列表(不能为 null 以及 empty)
//    public void test02() {
//        List<String> list = Arrays.asList("1", "2", "3");
//        List<SysAccess> sysAccessList = sysAccessMapper.selectBatchIds(list);
//        for (SysAccess sysAccess : sysAccessList) {
//            System.out.println(sysAccess);
//        }
//    }
//
//    @Test
//    // 查询（根据 columnMap 条件）
//    // @param columnMap 表字段 map 对象
//    //生成的sql: SELECT * FROM sys_access WHERE id = 2 AND acc_method = 'post'
//    public void test03() {
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("id", 2);
//        map.put("acc_method", "post");
//        List<SysAccess> sysAccessList = sysAccessMapper.selectByMap(map);
//        for (SysAccess sysAccess : sysAccessList) {
//            System.out.println(sysAccess);
//        }
//    }
//
//    /**
//     * <p>
//     * 根据 entity 条件，查询一条记录
//     * </p>
//     * 生成的sql: SELECT  * FROM sys_access WHERE id = 1
//     *
//     * @param queryWrapper 实体对象
//     * @return 实体
//     */
//    @Test
//    public void test04() {
//        QueryWrapper<SysAccess> queryWrapper = new QueryWrapper<>();
//        SysAccess sysAccess = sysAccessMapper.selectOne(queryWrapper.eq("id", 1));
//        System.out.println(sysAccess);
//    }
//
//    /**
//     * <p>
//     * 根据 Wrapper 条件，查询总记录数
//     * </p>
//     * SELECT COUNT(1) FROM sys_access WHERE id = 1 OR acc_disp LIKE '%权限%'
//     *
//     * @param queryWrapper 实体对象
//     * @return 满足条件记录数
//     */
//    @Test
//    public void test05() {
//        QueryWrapper<SysAccess> queryWrapper = new QueryWrapper<>();
//        int count = sysAccessMapper.selectCount(queryWrapper.eq("id", 1).or()
//                .like("acc_disp", "权限"));
//        System.out.println(count);
//    }
//
//    /**
//     * <p>
//     * 根据 entity 条件，查询全部记录
//     * </p>
//     * sql   SELECT * FROM sys_access WHERE id = 1 OR acc_disp LIKE '%权限%'
//     *
//     * @param queryWrapper 实体对象封装操作类（可以为 null）
//     * @return 实体集合
//     */
//    @Test
//    public void test06() {
//        QueryWrapper<SysAccess> queryWrapper = new QueryWrapper<>();
//        List<SysAccess> sysAccessList = sysAccessMapper.selectList(queryWrapper.eq("id", 1).or()
//                .like("acc_disp", "权限"));
//        for (SysAccess sysAccess : sysAccessList) {
//            System.out.println(sysAccess);
//        }
//    }
//
//    /**
//     * <p>
//     * 根据 Wrapper 条件，查询全部记录
//     * </p>
//     * SELECT * FROM sys_access WHERE acc_disp LIKE '%权限%'
//     *
//     * @param queryWrapper 实体对象封装操作类（可以为 null）
//     * @return 字段映射对象 Map 集合
//     * map里面:   列名->值  一个map就是数据库一行   key value形式存储
//     */
//    @Test
//    public void test07() {
//        QueryWrapper<SysAccess> queryWrapper = new QueryWrapper<>();
//        List<Map<String, Object>> mapList = sysAccessMapper.selectMaps(queryWrapper.like("acc_disp", "权限"));
//        for (Map<String, Object> map : mapList) {
//            System.out.println(map);
//        }
//    }
//
//    /**
//     * <p>
//     * 根据 Wrapper 条件，查询全部记录
//     * 注意： 只返回第一个字段的值
//     * </p>
//     * sql   SELECT * FROM sys_access WHERE id = 1 OR acc_disp LIKE '%权限%'
//     *
//     * @param queryWrapper 实体对象封装操作类（可以为 null）
//     * @return 字段映射对象集合
//     * 默认返回类型就是object   一个object对应数据库一条记录
//     */
//    @Test
//    public void test08() {
//        QueryWrapper<SysAccess> queryWrapper = new QueryWrapper<>();
//        List<Object> objectList = sysAccessMapper.selectObjs(queryWrapper.like("acc_disp", "权限"));
//        for (Object o : objectList) {
//            System.out.println(o);
//        }
//    }
//
//    /**
//     * <p>
//     * 根据 entity 条件，查询全部记录（并翻页）
//     * </p>
//     *
//     * @param page         分页查询条件（可以为 RowBounds.DEFAULT）
//     * @param queryWrapper 实体对象封装操作类（可以为 null）
//     * @return 实体分页对象
//     * SELECT * FROM sys_access LIMIT 0,10
//     * SELECT * FROM sys_access LIMIT 40,10 第五页
//     */
//    @Test
//    public void test09() {
//        QueryWrapper<SysAccess> queryWrapper = new QueryWrapper<>();
//        Long page = 5L;
//        Long pageSize = 10L;
//        //默认第一页   每页10条
//        Page p = new Page(page, pageSize);
//        IPage<SysAccess> iPage = sysAccessMapper.selectPage(p, queryWrapper);//不使用@SuppressWarnings注解会报警告
////        IPage iPage = sysAccessMapper.selectPage(p, queryWrapper);
//        List<SysAccess> records = iPage.getRecords();
//        System.out.println(records);//当前页数据
//        System.out.println(iPage.getTotal());//总条数
//    }
//
//    /**
//     * <p>
//     * 根据 Wrapper 条件，查询全部记录
//     * </p>
//     * SELECT * FROM sys_access LIMIT 40,10
//     *
//     * @param page         分页查询条件
//     * @param queryWrapper 实体对象封装操作类
//     * @return 字段映射对象 Map 分页对象
//     */
//    @Test
//    @SuppressWarnings("unchecked")//使用这段注解可以让idea不报警告
//    public void test10() {
//        QueryWrapper<SysAccess> queryWrapper = new QueryWrapper<>();
//        Long page = 5L;
//        Long pageSize = 10L;
//        //默认第一页   每页10条
//        Page p = new Page(page, pageSize);
//        IPage<Map<String, Object>> mapIPage = sysAccessMapper.selectMapsPage(p, queryWrapper);
////        IPage mapsPage  = sysAccessMapper.selectMapsPage(p, queryWrapper);
//        List<Map<String, Object>> records = mapIPage.getRecords();
//        System.out.println(records);//当前页数据
//        System.out.println(mapIPage.getTotal());//总条数
//    }
//
//
//    //=====================基于mapper的insert操作=================
//
//    /**
//     * <p>
//     * 插入一条记录 如果该列有值就插入 没有值就忽略  相当于mybatis 的insertSelective
//     * </p>
//     * sql   INSERT INTO tb_user ( name ) VALUES ( 'aaa' )
//     * @param entity 实体对象
//     * @return 插入成功记录数
//     */
//    @Test
//    public void test11() {
//        TbUser tbUser = new TbUser();
//        tbUser.setName("bbb");
//        int i = tbUserMapper.insert(tbUser);
//    }
//
//    //==================基于mapper的删除操作========================
//    /**
//     * <p>
//     * 根据 ID 删除
//     * </p>
//     *DELETE FROM tb_user WHERE id = 1
//     * @param id 主键ID
//     * @return 删除成功记录数
//     */
//    @Test
//    public void test12() {
//        int i = tbUserMapper.deleteById(1);
//    }
//
//    /**
//     * <p>
//     * 根据 columnMap 条件，删除记录
//     * </p>
//     *DELETE FROM tb_user WHERE name = 'bbb'
//     * @param columnMap 表字段 map 对象
//     * @return 删除成功记录数
//     */
//    @Test
//    public void test13() {
//        Map map = new HashMap<>();
//        map.put("name", "bbb");
//        tbUserMapper.deleteByMap(map);
//    }
//
//    /**
//     * <p>
//     * 根据 entity 条件，删除记录
//     * </p>
//     *
//     * @param wrapper 实体对象封装操作类（可以为 null）
//     * @return 删除成功记录数
//     */
//
//    @Test
//    public void test14() {
//        QueryWrapper<TbUser> queryWrapper = new QueryWrapper<>();
//        tbUserMapper.delete(queryWrapper.eq("id",1));
//    }
//
//    /**
//     * <p>
//     * 删除（根据ID 批量删除）
//     * </p>
//     *
//     * @param idList 主键ID列表(不能为 null 以及 empty)
//     * @return 删除成功记录数
//     */
//    @Test
//    public void test15() {
//        List<Integer> list = Arrays.asList(1, 2, 3);
//        tbUserMapper.deleteBatchIds(list);
//    }
//
//    //======================基于mapper的update操作====================
//    /**
//     * <p>
//     * 根据 ID 修改
//     * </p>
//     *UPDATE tb_user SET name='ccc' WHERE id=1
//     * @param entity 实体对象
//     * @return 修改成功记录数
//     */
//    @Test
//    public void test16() {
//        TbUser user = new TbUser();
//        user.setId(1);
//        user.setName("ccc");
//        tbUserMapper.updateById(user);
//    }
//
//    /**
//     * <p>
//     * 根据 whereEntity 条件，更新记录
//     * </p>
//     * UPDATE tb_user SET age=30 WHERE id = 1
//     * @param entity        实体对象 (set 条件值,可为 null)
//     * @param updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
//     * @return 修改成功记录数
//     */
//    @Test
//    public void test17() {
//        TbUser user = new TbUser();
//        user.setAge(30);
//        UpdateWrapper<TbUser> updateWrapper = new UpdateWrapper<>();
//        tbUserMapper.update(user, updateWrapper.eq("id",1));
//    }
//
//    //==============================基于service的save操作==================
//    /**
//     * <p>
//     * 插入一条记录（选择字段，策略插入）
//     * </p>
//     *
//     * @param entity 实体对象
//     */
//    @Test
//    public void test18() {
//        TbUser user = new TbUser();
//        user.setName("测试");
//        user.setAge(20);
//        /**
//         * 这里有个坑INSERT INTO tb_user ( name ) VALUES ( ？ )
//         * 提示找不到name 列
//         */
//        tbUserService.save(user);
//    }
//
//
//    /**
//     * 插入（批量）
//     *
//     * @param entityList 实体对象集合
//     * @param batchSize  插入批次数量
//     */
//    @Test
//    public void test19() {
//        TbUser user = new TbUser();
//        user.setName("测试");
//        ArrayList<TbUser> list = new ArrayList<>();
//        list.add(user);
//        list.add(user);
//        list.add(user);
//        list.add(user);
//        /**
//         * 批量插入  依然有这个坑
//         * 提示找不到name 列
//         */
//        tbUserService.saveBatch(list);
//    }
//
//    /**
//     * <p>
//     * 批量修改插入
//     * </p>
//     *
//     * @param entityList 实体对象集合
//     */
//    @Test
//    public void test20() {
//        TbUser user = new TbUser();
//        TbUser user2 = new TbUser();
//        user.setName("测试");
//        ArrayList<TbUser> list = new ArrayList<>();
//        list.add(user);
//        list.add(user);
//        list.add(user);
//        user2.setId(1);
//        user2.setName("ddd");
//        list.add(user2);
//        //依然是上面的问题 找不到name列
//        tbUserService.saveOrUpdateBatch(list);
//    }
//
//    //=================基于service的remove操作===============
//
//
//    //=================基于service其他操作=============
//
//    //==================基于mapper层选装件=================
//}
//
//

package com.example.mybatisplusdemo;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * <p>
 * mysql 代码生成器演示例子
 * </p>
 *
 */
public class MysqlGenerator {
    /**
     * 一款基于mybatis-plus的代码生成器
     * 在原有的官方基础上做了简单的修改，支持多表同时生成，基本上做到了开箱即用
     * 目前遇到的难题
     * 1.比如，你的表名为 tb_user  那么生成的实体类  xml  service controller 都会以tb开头
     * 2.生成的实体类需要格式化
     * 希望有余力的小伙伴可以帮忙设计 解决  谢谢！
     * 使用方法: 运行main 在输入框输入表名称  多表以 逗号 隔开
     *
     */
    /**
     * 可能需要修改自己数据库时区
     * 修改数据库时区：
     *
     *  set global time_zone = '+8:00'; ##修改mysql全局时区为北京时间，即我们所在的东8区
     *   set time_zone = '+8:00'; ##修改当前会话时区
     *   flush privileges; #立即生效
     */
    private static final String AUTHOR = "jklove";//作者
    private static final String DATA_BASE_URL = "jdbc:mysql://127.0.0.1:3306/mybatis_plus_demo?useUnicode=true&useSSL=false&characterEncoding=utf8";
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String USER_NAME = "root";
    private static final String PASS_WORD = "root";
    private static final String PARENT_PACKAGE = "com.example.mybatisplusdemo";//父类包位置，将在该包下生成对应的controller，service等

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    /**
     * RUN THIS
     */
    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(AUTHOR);
        gc.setFileOverride(true);//是否覆盖
        gc.setActiveRecord(true);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML ColumnList
        gc.setOpen(false);
        // 自定义文件命名，注意 %s 会自动填充表实体属性！ 可以不设置 以下为默认设置
//         gc.setMapperName("%sMapper");
//         gc.setXmlName("%sMapper");
//         gc.setServiceName("%sService");
//         gc.setServiceImplName("%sServiceImpl");
//         gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        /**
         * 可能需要修改数据库时区
         * 修改数据库时区：
         *
         *  set global time_zone = '+8:00'; ##修改mysql全局时区为北京时间，即我们所在的东8区
         *   set time_zone = '+8:00'; ##修改当前会话时区
         *   flush privileges; #立即生效
         */
        dsc.setUrl(DATA_BASE_URL);
        // dsc.setSchemaName("public");
        dsc.setDriverName(DRIVER_NAME);
        dsc.setUsername(USER_NAME);
        dsc.setPassword(PASS_WORD);
        dsc.setDbType(DbType.MYSQL);
//        dsc.setTypeConvert(new MySqlTypeConvert(){
//            // 自定义数据库表字段类型转换【可选】
//            @Override
//            public DbColumnType processTypeConvert(String fieldType) {
//                System.out.println("转换类型：" + fieldType);
//                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
//                return super.processTypeConvert(fieldType);
//            }
//        });

        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
//                pc.setMapper("mapper") 设置mapper包名
//                .setService("service")
//                .setController("controller")
//                .setEntity("beans")
//                .setXml("mapper");
        pc.setParent(PARENT_PACKAGE);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                return projectPath + "/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        mpg.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setInclude(scanner("表名"));
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        strategy.setTablePrefix("tb_");
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");

        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        strategy.setEntityColumnConstant(true);

        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        //strategy.setEntityBuilderModel(true);
        mpg.setStrategy(strategy);
        // 选择 freemarker 引擎需要指定如下加，注意 pom 依赖必须有！
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        // 关闭默认 xml 生成，调整生成 至 根目录
        /*TemplateConfig tc = new TemplateConfig();
        tc.setXml(null);
        mpg.setTemplate(tc);*/

        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
//        TemplateConfig tc = new TemplateConfig();
//        tc.setController("/templatesMybatis/controller.java.vm");
//        tc.setService("/templatesMybatis/service.java.vm");
//        tc.setServiceImpl("/templatesMybatis/serviceImpl.java.vm");
//        tc.setEntity("/templatesMybatis/entity.java.vm");
//        tc.setMapper("/templatesMybatis/mapper.java.vm");
//        tc.setXml("/templatesMybatis/mapper.xml.vm");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。
        //  mpg.setTemplate(tc);
        mpg.execute();
    }

}

package com.ruoyi.project.tool;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.converts.OracleTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.IOException;
import java.util.*;

public class MysqlGenerator {

	private static final String PACKAGE_NAME = "com.ruoyi.project.unionhelp";//包名
	private static final String OUT_PATH = "D:\\zhgh-help";//输出文件的路径
	private static final String AUTHOR = "crl";//代码生成者
	/**
	 * JDBC相关配置
	 */
	//private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/gonghui?useUnicode=true&useSSL=false&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "A968064BCd!?";

	/**
	 * 模块名称
	 */
	private static final String MODULE_NAME = "";

	// @Autowired
	// private IDictionariesService dictionariesService;
	// public static MysqlGenerator mysqlGenerator;
	//
	// @PostConstruct
	// public void init() {
	// mysqlGenerator = this;
	// }

	/**
	 * <p>
	 * MySQL 生成演示
	 * </p>
	 *
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
//		// 自定义需要填充的字段
//		// 如 每张表都有一个创建时间、修改时间
//		// 而且这基本上就是通用的了，新增时，创建时间和修改时间同时修改
//		// 修改时，修改时间会修改，
//		// 虽然像Mysql数据库有自动更新机制，但像ORACLE的数据库就没有了，
//		// 使用公共字段填充功能，就可以实现，自动按场景更新了。
//		// 如下是配置
		List<TableFill> tableFillList = new ArrayList<>();
		TableFill createField = new TableFill("insert_time", FieldFill.INSERT);
		TableFill modifiedField = new TableFill("upd_time", FieldFill.INSERT_UPDATE);
		tableFillList.add(createField);
		tableFillList.add(modifiedField);

		final GlobalConfig config = new GlobalConfig().setOutputDir(OUT_PATH)// 输出目录
				.setFileOverride(true)// 是否覆盖文件
				.setActiveRecord(true)// 开启 activeRecord 模式
				.setEnableCache(false)// XML 二级缓存
				.setBaseResultMap(false)// XML ResultMap
				.setBaseColumnList(true)// XML columList
				.setAuthor(AUTHOR)
				// 自定义文件命名，注意 %s 会自动填充表实体属性！
				.setXmlName("%sMapper").setMapperName("%sDao");
		// 代码生成器
		AutoGenerator mpg = new AutoGenerator().setGlobalConfig(
				// 全局配置
				config
				// .setServiceName("MP%sService")
				// .setServiceImplName("%sServiceDiy")
				// .setControllerName("%sAction")
		).setDataSource(
				// 数据源配置
				new DataSourceConfig().setDbType(DbType.MYSQL)// 数据库类型
						.setTypeConvert(new MySqlTypeConvert() {
							// 自定义数据库表字段类型转换【可选】
							public IColumnType processTypeConvert(String fieldType) {
//								if ( fieldType.toLowerCase().contains("int" ) ) {
//									return DbColumnType.INTEGER;
//								}
//								if ( fieldType.toLowerCase().contains("bigint" ) ) {
//									return DbColumnType.LONG;
//								}
								return super.processTypeConvert(config, fieldType);
							}
						}).setDriverName(DRIVER).setUsername(USER_NAME).setPassword(PASSWORD).setUrl(URL))
				.setStrategy(
						// 策略配置
						new StrategyConfig()
								// .setCapitalMode(true)// 全局大写命名

//								 .setDbColumnUnderline(true)// 全局下划线命名
								// .setTablePrefix(new String[]{"unionpay_"})//
								// 此处可以修改为您的表前缀
								.setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
								.setInclude(new String[] {"user_profile"}) // 需要生成的表
								// .setExclude(new String[]{"test"}) // 排除生成的表
								// 自定义实体，公共字段
								// .setSuperEntityColumns(new
								// String[]{"test_id"})
								.setTableFillList(tableFillList)
								// 自定义实体父类
								// .setSuperEntityClass("com.baomidou.demo.base.BsBaseEntity")
								// // 自定义 mapper 父类
								// .setSuperMapperClass("com.baomidou.demo.base.BsBaseMapper")
								// // 自定义 service 父类
								// .setSuperServiceClass("com.baomidou.demo.base.BsBaseService")
								// // 自定义 service 实现类父类
								// .setSuperServiceImplClass("com.baomidou.demo.base.BsBaseServiceImpl")
								// 自定义 controller 父类
								// .setSuperControllerClass("com.baomidou.demo.BController")
								// 【实体】是否生成字段常量（默认 false）
								// public static final String ID = "test_id";
								.setEntityColumnConstant(true)
								// 【实体】是否为构建者模型（默认 false）
								// public User setName(String name) {this.name =
								// name; return this;}
								.setEntityBuilderModel(true)
								// 【实体】是否为lombok模型（默认 false）<a
								// href="https://projectlombok.org/">document</a>
								.setEntityLombokModel(true)
						// Boolean类型字段是否移除is前缀处理
						// .setEntityBooleanColumnRemoveIsPrefix(true)
						// .setRestControllerStyle(true)
						// .setControllerMappingHyphenStyle(true)
				)
				.setPackageInfo(
						// 包配置
						new PackageConfig().setModuleName(MODULE_NAME).setParent(PACKAGE_NAME)// 自定义包路径
								.setController("controller")// 这里是控制器包名，默认 web
								.setXml("mapper").setMapper("mapper").setService("service")

				).setCfg(
						// 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
						new InjectionConfig() {
							@Override
							public void initMap() {
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
								this.setMap(map);
							}
						}.setFileOutConfigList(
								Collections.<FileOutConfig>singletonList(new FileOutConfig("/templates/mapper.xml.vm") {
									// 自定义输出文件目录
									@Override
									public String outputFile(TableInfo tableInfo) {
										return OUT_PATH + "/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
									}
								})))
				.setTemplate(
						// 关闭默认 xml 生成，调整生成 至 根目录
						new TemplateConfig().setXml(null)
						// 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
						// 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
						// .setController("...");
						// .setEntity("...");
						// .setMapper("...");
						// .setXml("...");
						// .setService("...");
						// .setServiceImpl("...");
				);

		// 执行生成
		mpg.execute();

	}

}

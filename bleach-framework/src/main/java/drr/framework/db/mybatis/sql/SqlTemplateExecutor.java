
package drr.framework.db.mybatis.sql;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;

import drr.constant.db.DataSourceEnum;
import drr.framework.db.datasource.DataSourceContextHolder;
import drr.framework.db.mybatis.DRRLinkedHashMap;
import drr.framework.db.mybatis.sql.dao.SqlTemplateDao;
import drr.framework.db.mybatis.sql.parsing.SqlTemplateParser;
import drr.framework.db.mybatis.sql.template.SqlTemplateBase;
import drr.framework.service.BaseService;

@Component
public class SqlTemplateExecutor extends BaseService {

	@Autowired
	private SqlTemplateDao sqlTemplateDao;

	@Autowired
	private SqlTemplateParser sqlTemplateParser;

	@SuppressWarnings("rawtypes")
	public List<DRRLinkedHashMap> execute(SqlTemplateBase sqlTemplate) throws Exception {
		DataSourceContextHolder.setDataSourceType(sqlTemplate.getDataSource());

		if (sqlTemplate.getPageSize() > 0 && sqlTemplate.getPageNum() > 0) {
			PageHelper.startPage(sqlTemplate.getPageNum(), sqlTemplate.getPageSize());
		}

		try {
			SqlTemplateContext context = sqlTemplateParser.parse(sqlTemplate);
			return sqlTemplateDao.executeSqlTemplate(context);
		} catch (Exception e) {
			// 记录日志
			logger.error("sql执行出错！", e);
			throw e;
		} finally {
			DataSourceContextHolder.clearDataSourceType();
		}
	}

	@SuppressWarnings("rawtypes")
	public List<DRRLinkedHashMap> execute(DataSourceEnum dataSource, Integer pageSize, Integer pageNum, SqlTemplateContext context) throws Exception {
		DataSourceContextHolder.setDataSourceType(dataSource);

		if (pageSize > 0 && pageNum > 0) {
			PageHelper.startPage(pageNum, pageSize);
		}

		try {
			return sqlTemplateDao.executeSqlTemplate(context);
		} catch (Exception e) {
			// 记录日志
			logger.error("sql执行出错！", e);
			throw e;
		} finally {
			DataSourceContextHolder.clearDataSourceType();
		}
	}
}

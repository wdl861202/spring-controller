
package drr.framework.db.mybatis.sql.dao;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

import drr.framework.db.mybatis.DRRLinkedHashMap;
import drr.framework.db.mybatis.sql.SqlTemplateContext;
import drr.framework.db.mybatis.sql.SqlTemplateProvider;

public interface SqlTemplateDao {

	@SuppressWarnings("rawtypes")
	@SelectProvider(type = SqlTemplateProvider.class, method = "generateSql")
	List<DRRLinkedHashMap> executeSqlTemplate(SqlTemplateContext context);
}


package drr.framework.db.mybatis.sql.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
public @interface SqlTemplateToken {

	/** 单个token使用 */
	public String value() default "";

	/** 参数处理方法 */
	public String[] handler() default { "param-simple-handler" };
}

package com.fast.condition;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

/**
 * Created by xjt520 on 16/6/26.
 */
public class ConditionProvider extends MapperTemplate {

    public static final String NEW_LINE_BREAK = "\r\n";

    public ConditionProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String findByCondition(MappedStatement ms) {
        final Class entityClass = this.getEntityClass(ms);
        //设置返回类型
        this.setResultType(ms,entityClass);

        StringBuilder findSql = new StringBuilder();
        String tableName = this.tableName(entityClass);
        findSql.append("select * from ").append(tableName);
        findSql.append(NEW_LINE_BREAK).append("<where>");
        findSql.append(buildCondition());
        findSql.append(NEW_LINE_BREAK).append("</where>");
        if (entityClass.isAnnotationPresent(OrderBy.class)){
            String orderBy = ((OrderBy) entityClass.getAnnotation(OrderBy.class)).value();
            if (StringUtils.isNotBlank(orderBy)){
                findSql.append(NEW_LINE_BREAK).append(" ORDER BY ").append(orderBy);
            }
        }
//        System.out.println("=======================findByCondition========================");
//        System.out.println(findSql.toString());
//        System.out.println("=======================findByCondition========================");
        return findSql.toString();
    }

    private String buildCondition(){
        StringBuilder sb = new StringBuilder();
        sb.append(NEW_LINE_BREAK).append("<if test=\"conditions!=null\">");
        sb.append(NEW_LINE_BREAK).append("		<foreach collection=\"conditions\" item=\"condition\" index=\"index\">");
        sb.append(NEW_LINE_BREAK).append("			<if test=\"condition.class.simpleName=='SingleFieldCondition'\">");
        sb.append(NEW_LINE_BREAK).append("				<if test=\"condition.newValue!=null and  condition.newValue.toString().length()>0\">");
        sb.append(NEW_LINE_BREAK).append("					AND ");
        sb.append(NEW_LINE_BREAK).append("					<if test=\"condition.operator!=null and condition.operator=='in'\">");
        sb.append(NEW_LINE_BREAK).append("						${condition.field} ${condition.operator}");
        sb.append(NEW_LINE_BREAK).append("						<foreach item=\"one\" index=\"index\" collection=\"condition.newValue\"");
        sb.append(NEW_LINE_BREAK).append("							open=\"(\" separator=\",\" close=\")\">");
        sb.append(NEW_LINE_BREAK).append("							#{one}");
        sb.append(NEW_LINE_BREAK).append("						 </foreach>");
        sb.append(NEW_LINE_BREAK).append("					</if>");
        sb.append(NEW_LINE_BREAK).append("					<if test=\"condition.operator!=null and condition.operator!='in'\">");
        sb.append(NEW_LINE_BREAK).append("						${condition.field} ${condition.operator}");
        sb.append(NEW_LINE_BREAK).append("						#{condition.newValue}");
        sb.append(NEW_LINE_BREAK).append("					</if>");
        sb.append(NEW_LINE_BREAK).append("				</if>");
        sb.append(NEW_LINE_BREAK).append("			</if>");
        sb.append(NEW_LINE_BREAK).append("			<if test=\"condition.class.simpleName=='MultiFieldCondition'\">");
        sb.append(NEW_LINE_BREAK).append("				<if test=\"condition.newValue!=null and  condition.newValue.toString().length()>0\">");
        sb.append(NEW_LINE_BREAK).append("					AND (");
        sb.append(NEW_LINE_BREAK).append("					<foreach collection=\"condition.singleConditions\" item=\"orCondition\" index=\"orIndex\">");
        sb.append(NEW_LINE_BREAK).append("						<if test=\"orIndex>0\"> OR </if>");
        sb.append(NEW_LINE_BREAK).append("						<if test=\"orCondition.operator!=null and orCondition.operator=='in'\">");
        sb.append(NEW_LINE_BREAK).append("							${orCondition.field} ${orCondition.operator}");
        sb.append(NEW_LINE_BREAK).append("							<foreach item=\"one\" index=\"index\" collection=\"orCondition.newValue\"");
        sb.append(NEW_LINE_BREAK).append("								open=\"(\" separator=\",\" close=\")\">");
        sb.append(NEW_LINE_BREAK).append("								#{one}");
        sb.append(NEW_LINE_BREAK).append("							 </foreach>");
        sb.append(NEW_LINE_BREAK).append("						</if>");
        sb.append(NEW_LINE_BREAK).append("						<if test=\"orCondition.operator!=null and orCondition.operator!='in'\">");
        sb.append(NEW_LINE_BREAK).append("							${orCondition.field} ${orCondition.operator}");
        sb.append(NEW_LINE_BREAK).append("							#{orCondition.newValue}");
        sb.append(NEW_LINE_BREAK).append("						</if>");
        sb.append(NEW_LINE_BREAK).append("					</foreach>");
        sb.append(NEW_LINE_BREAK).append("					)");
        sb.append(NEW_LINE_BREAK).append("				</if>");
        sb.append(NEW_LINE_BREAK).append("			</if>");
        sb.append(NEW_LINE_BREAK).append("		</foreach>");
        sb.append(NEW_LINE_BREAK).append("	</if>");
        return sb.toString();
    }
}

package com.fast.condition;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * SingleFieldCondition
 */
public class SingleFieldCondition extends Condition implements Serializable {

	private static final long serialVersionUID = -4466041047921086476L;

	private String field;
	private Object value;
	private Object newValue;
	private String operator;
	private String newOperator = null;
	private String type;
	private String orderBy;

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getValue() {
		return value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getNewValue() {
		return newValue;
	}
	public String getNewOperator() {
		return newOperator == null ? getOperator() : newOperator;
	}

	/**
	 * "id desc"
	 */
	@Override
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	@Override
	public String getOrderBy() {
		return this.orderBy;
	}

	/**
	 *
	 * 校验是否 操作符
	 */
	private static boolean isOperator(String str) {
		return "gt".equals(str)				// >= [arg]
			|| "lt".equals(str)				// <= [arg]
			|| "eq".equals(str)				// = [arg]
			|| "nq".equals(str)				// != [arg]
			|| "contains".equals(str) 		// like %[arg]%
			|| "notContains".equals(str)	// not like %[arg]%
			|| "like".equals(str) 			// like [arg]
			|| "notLike".equals(str)		// not like [arg]
			|| "in".equals(str) 			// in [arg]
			|| "notIn".equals(str) 			// not in [arg]
			|| "isNull".equals(str)			// is null
			|| "notNull".equals(str)		// is not null
				;
	}

	/**
	 *
	 * 返回sql操作符
	 */
	private static String getOperator(String str) {
		if ("gt".equals(str)) {
			return ">=";
		} else if ("lt".equals(str)) {
			return "<=";
		} else if ("in".equals(str)) {
			return "in";
		} else if ("notIn".equals(str)) {
			return "not in";
		} else if ("nq".equals(str)) {
			return "!=";
		} else if ("like".equals(str)) {
			return "like";
		} else if ("notLike".equals(str)) {
			return "not like";
		} else if ("contains".equals(str)) {
			return "contains";
		} else if ("notContains".equals(str)) {
			return "not contains";
		}else if ("isNull".equals(str)) {
			return "is null";
		} else if ("notNull".equals(str)) {
			return "is not null";
		} else {// eq 或其他
			return "=";
		}
	}

    @Override
    public void setValue(Object value) {
        this.setValue(value, false);
    }

    /**
	 *
	 * 设置value值
	 */
	public void setValue(Object value, boolean raw) {
		//raw 为 true 设置原始值
        if(raw){
            this.newValue = value;
            return;
        }
		this.value = value;
		if (value == null || value.toString().trim().equals("")) {
			return;
		}
		// 字符串类型
		if ("string".equals(type)) {
			if ("contains".equals(operator) || "not contains".equals(operator)) {
				this.newOperator = operator.replace("contains","like");
				// 对通配符进行转移
				String thisVal = value.toString().trim()
						.replaceAll("_", "\\\\_") // 替换'_'
						.replaceAll("%", "\\\\%"); // 替换'%'
				this.newValue = "%" + thisVal + "%";
			}  else if ("in".equals(operator) || "not in".equals(operator)) {
				if (this.value instanceof String) {
					this.newValue = value.toString().trim().split(",");
				} else {
					this.newValue = this.value;
				}
			} else {
				this.newValue = value.toString().trim();
			}
		}
		// 日期类型
		else if ("date".equals(type)) {
			Date date = null;
			try {
				String tmpValue = value.toString().trim();
				if (value instanceof Date){
					tmpValue = DateKit.formatToDay((Date) value);
				}
				// gt 大于一个日期 + " 00:00:00"
				if (getOperator("gt").equals(operator)) {
					date = DateKit.parse(tmpValue + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
					// 减一秒操作
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.SECOND, -1);
					date = calendar.getTime();
				} else if (getOperator("lt").equals(operator)) { // lt 小于一个日期 + " 23:59:59"
					date = DateKit.parse(tmpValue + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
				} else {
					date = DateKit.parse(tmpValue, "yyyy-MM-dd");
				}
			} catch (ParseException e) {
				throw new RuntimeException("date setValue is Exception !!!"+e.getMessage());
			}
			this.newValue = date;
		} else if ("float".equals(type)) {
			this.newValue = Float.parseFloat(value.toString().trim());
		} else { // 整型
			// 其他 int 或 string 用,隔开的 字符串 转化为 String [] 类型
			if ("in".equals(operator) || "not in".equals(operator)) {
				if (this.value instanceof String) {
					//this.newValue = value.toString().trim().split(",");
					String[] arr  = value.toString().trim().split(",");
					List<Object> ins = new ArrayList<Object>();
					for (String string : arr){
						ins.add(string);
					}
					this.newValue = ins;
				} else { // list
					this.newValue = this.value;
				}
			} else {
				this.newValue = Integer.valueOf(this.value.toString().trim());
			}

		}
	}

	@Override
	protected void parse(String parameter) {
		//"user_id,integer,eq" or "userId,integer,eq"
		String[] array = parameter.split(Condition.FIELD_INTERNAL_DELIMETER);
		String field = array[0];
		setField(field);

		switch (array.length){
			case 1:
				// 字段
				setType("string");
				setOperator("=");
				break;
			case 2:
				if (isOperator(array[1])){
					// 字段,操作符
					setType("string");
					setOperator(getOperator(array[1]));
				}else{
					// 字段,数据类型
					setType(array[1]);
					setOperator("=");
				}
				break;
			case 3:
				// 字段,操作符,数据类型
				setType(array[1]);
				setOperator(getOperator(array[2]));
				break;
			default:
				break;
		}

	}

	@Override
	public String toString() {
		return "SingleFieldCondition [field=" + field + ", value=" + value
				+ ", newValue=" + newValue + ", operator=" + operator+", newOperator=" + newOperator
				+ ", type=" + type + "]";
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

}

package com.fast.base;

import com.fast.condition.Condition;
import com.fast.condition.PageCondition;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by xjt520 on 16/6/28.
 */
public class BaseController {

    private static final Logger log = LoggerFactory.getLogger(BaseController.class);

    public final static String LOGIN_USER = "LOGIN_USER";

    protected PageCondition bindPageCondition(HttpServletRequest request) {

        PageCondition pageCondition = new PageCondition();
        Map<String, String[]> map = request.getParameterMap();
        for (String key : map.keySet()) {
            log.debug("key:" + key + " value:" + map.get(key)[0]);
            if (StringUtils.isNotBlank(key)) {
                if ("page".equalsIgnoreCase(key)) {
                    pageCondition.setPage(Integer.valueOf(map.get(key)[0]));
                    continue;
                }
                if ("rows".equalsIgnoreCase(key)) {
                    pageCondition.setRows(Integer.valueOf(map.get(key)[0]));
                    continue;
                }
                if ("order".equalsIgnoreCase(key)) {
                    continue;
                }
                if ("limit".equalsIgnoreCase(key)) {
                    continue;
                }
                if ("offset".equalsIgnoreCase(key)) {
                    continue;
                }
                if ("v".equalsIgnoreCase(key)) {
                    continue;
                }
                Condition condition = Condition.parseCondition(key);
                condition.setValue(map.get(key)[0]);
                pageCondition.getConditions().put(key, condition);
            }

        }
        return pageCondition;
    }
}

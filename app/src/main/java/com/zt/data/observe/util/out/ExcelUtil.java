package com.zt.data.observe.util.out;

import android.os.Environment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author dmrfcoder
 * @date 2018/8/9
 */
public class ExcelUtil {

    /**
     * 列名解析
     *
     * @return
     */
    public  static String[] colName(Class c) {
        Field[] fields = c.getDeclaredFields();
        Map<Integer,String> valueMap = new TreeMap();
        for (Field fielditem : fields) {
            String oValue = null;
            if (!fielditem.isAnnotationPresent(ExcelCount.class)) {
                continue;
            }
            ExcelCount count = fielditem.getAnnotation(ExcelCount.class);
            if (count != null) {
                int order = count.order();
                String name = count.name();
                valueMap.put(order,name);
            }
        }
        List<String> names = new ArrayList<>();
        for (Integer name : valueMap.keySet()) {
            names.add(valueMap.get(name));
        }
        String[] ns = names.toArray(new String[names.size()]);
        return ns;
    }


    private static String m_sWorkDir;

    public static String getWorkDir() {
        if (m_sWorkDir == null) {
            m_sWorkDir = Environment.getExternalStorageDirectory() + "/excel";
        }
        return m_sWorkDir;
    }


}

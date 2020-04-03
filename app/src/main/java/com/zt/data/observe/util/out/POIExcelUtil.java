package com.zt.data.observe.util.out;

import android.content.Context;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.WorkbookUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cn.faker.repaymodel.util.error.ErrorUtil;

/**
 * @author dmrfcoder
 * @date 2018/8/9
 */
public class POIExcelUtil {




    public   <T> void writeObjListToExcelddd(List<T> objList, String fileName, Context c,String[] colName) {
        if (objList != null && objList.size() > 0) {
            File file = null;
            try {

                file = new File(fileName);
                if (!file.exists()) {
                    file.createNewFile();
                }

                HSSFWorkbook workbook = new HSSFWorkbook();
                HSSFSheet sheet = workbook.createSheet(WorkbookUtil.createSafeSheetName("sheet1"));

                Row row_father = sheet.createRow(0);
                for (int col = 0; col < colName.length; col++) {
                    Cell cell = row_father.createCell(col);
                    cell.setCellValue(colName[col]);
                }
                //设置行高

                for (int j = 0; j < objList.size(); j++) {
                    List<String> list = new ArrayList<>();
                    Map<Integer, String> orderVs = new TreeMap<>();

                    Object obj = objList.get(j);
                    Field[] fields = obj.getClass().getDeclaredFields();
                    for (Field fielditem : fields) {
                        String name = fielditem.getName();
                        name = name.substring(0, 1).toUpperCase() + name.substring(1);
                        String type = fielditem.getType().toString();
                        String oValue = null;

                        Annotation[] ats = fielditem.getAnnotations();
                        if (!fielditem.isAnnotationPresent(ExcelCount.class)) {
                            continue;
                        }
                        if (type.equals("class java.lang.String")) {
                            Method m = obj.getClass().getMethod("get" + name);
                            String value = (String) m.invoke(obj); // 调用getter方法获取属性值
                            oValue = value;
                        } else if (type.equals("class java.lang.Integer")||type.equals("int")) {
                            Method m = obj.getClass().getMethod("get" + name);
                            Integer value = (Integer) m.invoke(obj);
                            if (value == null) {
                                value = 0;
                            }
                            oValue = value.toString();
                        } else if (type.equals("class java.lang.Double")||type.equals("double")) {
                            Method m = obj.getClass().getMethod("get" + name);
                            Double value = (Double) m.invoke(obj);
                            if (value == null) {
                                value = 0d;
                            }
                            oValue = value.toString();
                        } else if (type.equals("class java.lang.Long")||type.equals("long")) {
                            Method m = obj.getClass().getMethod("get" + name);
                            Long value = (Long) m.invoke(obj);
                            if (value == null) {
                                value = 0l;
                            }
                            oValue = value.toString();
                        }else if (type.equals("class java.lang.Float")||type.equals("float")) {
                            Method m = obj.getClass().getMethod("get" + name);
                            Float value = (Float) m.invoke(obj);
                            if (value == null) {
                                value = 0.0F;
                            }
                            oValue = value.toString();
                        } else {
                            oValue = null;
                        }
//                        list.add(oValue);
                        int order = fielditem.getAnnotation(ExcelCount.class).order();
                        orderVs.put(order, oValue);
                    }

                    for (Integer key : orderVs.keySet()) {
                        list.add(orderVs.get(key));
                    }

                    Row row = sheet.createRow(j+1);
                    for (int i = 0; i < list.size(); i++) {
                        Cell cell = row.createCell(i);
                        cell.setCellValue(list.get(i));
                    }

                }
                OutputStream outputStream = new FileOutputStream(file.getAbsolutePath());
                workbook.write(outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                ErrorUtil.showError(e);
            }

        }
    }



    private  static String getMethodName(String fieldName) {
        String head = fieldName.substring(0, 1).toUpperCase();
        String tail = fieldName.substring(1);
        return "get" + head + tail;
    }

    private static String setMethodName(String fieldName) {
        String head = fieldName.substring(0, 1).toUpperCase();
        String tail = fieldName.substring(1);
        return "set" + head + tail;
    }

}

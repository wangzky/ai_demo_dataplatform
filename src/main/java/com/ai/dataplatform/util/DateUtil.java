package com.ai.dataplatform.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Title: DateUtil
 * @ProjectName: ai_demo_dataplatform
 * @PackageName: com.ai.dataplatform.util
 * @Description: TODO
 * @author: wangzk
 * @date: 2019-10-08 17:46
 */
public class DateUtil {

    public static String getYMD(){
//        Calendar now = Calendar.getInstance();
//        System.out.println("年: " + now.get(Calendar.YEAR));
//        System.out.println("月: " + (now.get(Calendar.MONTH) + 1) + "");
//        System.out.println("日: " + now.get(Calendar.DAY_OF_MONTH));
//        System.out.println("时: " + now.get(Calendar.HOUR_OF_DAY));
//        System.out.println("分: " + now.get(Calendar.MINUTE));
//        System.out.println("秒: " + now.get(Calendar.SECOND));
//        System.out.println("当前时间毫秒数：" + now.getTimeInMillis());
//        System.out.println(now.getTime());

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateNowStr = sdf.format(d);
        return dateNowStr;

//        String str = "2012-1-13 17:26:33";  //要跟上面sdf定义的格式一样
//        Date today = sdf.parse(str);
//        System.out.println("字符串转成日期：" + today);
    }

    public static void main(String[] args) {
        getYMD();
    }
}

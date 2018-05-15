package com.ximi.wendashequ.util;

import java.io.InputStream;

/**
 * Created by 陶世磊 on 2018/4/19.
 *
 * @Description:
 */

public class Test {

    ClassLoader classLoader = Thread.currentThread()
            .getContextClassLoader();
    InputStream inputStream = classLoader.getResourceAsStream("hundsun.png");

}

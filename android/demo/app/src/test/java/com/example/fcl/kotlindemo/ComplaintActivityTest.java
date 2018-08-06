package com.example.fcl.kotlindemo;

import com.example.fcl.kotlindemo.test.AppUtil;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * Created by galio.fang on 18-8-6
 */
public class ComplaintActivityTest extends BaseRobolectricTestCase {

    @Test
    @PrepareForTest({ AppUtil.class})
    public void jumpCompensate() {
        PowerMockito.mockStatic(AppUtil.class);
        PowerMockito.when(AppUtil.getVersionName()).thenReturn("1.4.0");

        
    }

}

package com.example.fcl.kotlindemo;

import com.example.fcl.kotlindemo.test.AppUtil;
import java.util.List;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by galio.fang on 18-8-6
 */
public class ComplaintActivityTest extends BaseRobolectricTestCase {

    @Test
    @PrepareForTest({ AppUtil.class})
    public void jumpCompensate() {
        List list = mock(List.class);

        list.add("one");
        list.clear();

        verify(list).add("one");
        verify(list).clear();

    }

}

package com.example.fcl.kotlindemo;

import android.app.Application;
import android.content.Context;
import io.reactivex.plugins.RxJavaPlugins;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

/**
 * Created by galio.fang on 18-8-6
 */
@RunWith(RobolectricTestRunner.class)
@Config(shadows = { ShadowLog.class}, constants = BuildConfig.class, sdk = 23)
public class BaseRobolectricTestCase {

    @Rule
    public PowerMockRule rule=new PowerMockRule();

    private static boolean hasInited = false;

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        if (!hasInited) {
            initRxJava();
            hasInited=true;
        }
        MockitoAnnotations.initMocks(this);
    }

    public Application getApplication() {
        return RuntimeEnvironment.application;
    }

    public Context getContext() {
        return RuntimeEnvironment.application;
    }

    private void initRxJava() {
    }

}

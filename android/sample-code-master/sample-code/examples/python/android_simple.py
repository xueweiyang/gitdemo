import os
from time import sleep

import unittest

from appium import webdriver

# Returns abs path relative to this file and not cwd
PATH = lambda p: os.path.abspath(
    os.path.join(os.path.dirname(__file__), p)
)

class SimpleAndroidTests(unittest.TestCase):
    def setUp(self):
        desired_caps = {}
        desired_caps['platformName'] = 'Android'
        desired_caps['platformVersion'] = '7.1.1'
        desired_caps['deviceName'] = '75b4ca9'
	desired_caps['appPackage'] = 'com.dadaabc.zhuozan.dadaabcstudent'
        desired_caps['appActivity'] = 'default'
        desired_caps['app'] = PATH(
            '/home/fcl/pro/abc-android-stu/app/build/outputs/apk/debug/app-debug.apk'
        )

        self.driver = webdriver.Remote('http://localhost:4723/wd/hub', desired_caps)

    def tearDown(self):
        # end the session
        self.driver.quit()

    def test_find_elements(self):
        el = self.driver.find_element_by_id('splashSkipText')
        el.click()
        

        




if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(SimpleAndroidTests)
    unittest.TextTestRunner(verbosity=2).run(suite)

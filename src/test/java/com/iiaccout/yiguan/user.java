package com.iiaccout.yiguan;

import com.iiaccount.common.RunCaseJson;
import com.iiaccount.common.SetUpTearDown;
import com.iiaccount.data.DataProviders;
import com.iiaccount.data.RequestData;
import com.iiaccount.utils.YamlUtil;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;


/*
 *一贯开立II/III类户
 * 环境参数在SetUpTearDown 父类定义
 */
@Feature("用户管理")
public class user extends SetUpTearDown {


    @BeforeClass
    public void envSetUp() {
        //获取类的名称
        String classname = this.getClass().getSimpleName();
        //根据类名获取到yaml的文件名
        System.out.println(classname);
        String yamlname = classname+".yaml";
        DataProviders.ymldata = YamlUtil.readYml("/Users/lcj/code/lrg/ApiAutoTest/src/main/resources/yaml/"+yamlname);
    }

    @Story("登录")
    @Test(dataProvider = "yamldata", dataProviderClass = DataProviders.class
            , description = "用户管理相关接口")
    public void login(RequestData requestData) {
        System.out.println(requestData.getStory());
        Response response = RunCaseJson.runCaseTest(requestData);
    }

//    @Story("添加用户")
//    @Test(dataProvider = "yamldata", dataProviderClass = DataProviders.class
//            , description = "添加用户")
//    public void save(RequestData requestData) {
//        System.out.println(requestData.getStory());
//        Response response = RunCaseJson.runCaseTest(requestData);
//    }

    @Story("获取用户列表")
    @Test(dataProvider = "yamldata", dataProviderClass = DataProviders.class
            , description = "获取用户列表")
    public void userlist(RequestData requestData){
        System.out.println(requestData.getStory());
        Response response = RunCaseJson.runCaseTest(requestData);
    }

    @Story("获取用户详情信息")
    @Test(dataProvider = "yamldata", dataProviderClass = DataProviders.class
            , description = "获取用户详情信息")
    public void  userinfo(RequestData requestData){
        System.out.println(requestData.getStory());
        Response response = RunCaseJson.runCaseTest(requestData);
    }


}

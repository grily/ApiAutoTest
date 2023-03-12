package com.iiaccount.common;

import com.iiaccount.data.RequestData;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.Allure.description;
import static io.qameta.allure.Allure.attachment;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RunCaseJson {
    public static Map<String,Object> globaldata = new HashMap<>();

    /*
     *post或get方式请求,返回响应报文（json格式）
     *@bodyString:json格式的请求报文体
     *@para:requestType post或get
     */
    public static Response runCase(String bodyString,String requestType){
        Response response = null;
        if(requestType.toLowerCase().equals("get"))
            response = given()
                    .contentType("application/json;charset=UTF-8")
                    .request()
                    .body(bodyString)

                    .header("cookie","username=admin; password=B8d9yZZu6BpGRuVsCFooJmf7L0hEvEaKCAd1EmnhF0RDXmrGHWZPEkt4mlsrLXbob1ectJFtp2k0n0wc4wnsZQ==; JSESSIONID=38fee64a-ac72-4fd0-8b13-72cc4006a7bf")
                    .header("token","8c0e5400c1e0b7d4b2c327c99e413cbf")
                    .get("http://localhost:8080/sys/user/list?_search=false&nd=1676809845171&limit=10&page=1&sidx=&order=asc&username=&_=1676809804728");
        else
            response = given()
                    .contentType("application/json;charset=UTF-8")
                    .request()
                    .body(bodyString)
                    .post();

        //打印格式化的参数
        //response.prettyPrint(); ////去掉部分日志 add by lrb 20181029

        return response;
    }

    public static Response runCaseTest(RequestData requestData){
        Response response = null;
        description(requestData.getDescription());
        LinkedHashMap<String, Object> reqdata = requestData.getRequestdata();
        //url method params headers
        step("1.请求信息");
        requestData.getRequestdata().put("url",requestData.getUrl());
        attachment("params",requestData.getRequestdata().toString());
        Map<String,String> requestHeaders = new HashMap<>();
        if(requestData.getDependon() != null && requestData.getDependon().size() >0 ){
            step("2.判断依赖");
            attachment("dependon",requestData.getDependon().get("case_id").toString());
            LinkedHashMap<String, Object> dependonmap = requestData.getDependon();
            String caseid = dependonmap.get("case_id").toString();

            ArrayList<String> dependKeys = (ArrayList<String>)dependonmap.get("depend_key");
            ArrayList<String> replaceKeys = (ArrayList<String>)dependonmap.get("replace_key");
            String dependType = dependonmap.get("type").toString();
            if(dependType.equals("header")){
                for(int i=0;i<replaceKeys.size();i++){
                    requestHeaders.put(replaceKeys.get(i),globaldata.get(caseid+dependKeys.get(i)).toString());
                }
            }else if(dependType == "request"){

            }


        }


        if(requestData.getMethod().toLowerCase().equals("get"))
            response = given()
                    .headers(requestHeaders)
                    .contentType("application/json;charset=UTF-8")
                    .request()
                    .queryParams((LinkedHashMap<String,Object>)reqdata.get("params"))
//                    .body(reqdata.get("params"))
                    .get(requestData.getUrl());
        else
            response = given()
                    .headers(requestHeaders)
                    .contentType("application/json;charset=UTF-8")
                    .request()
                    .body(reqdata.get("params"))
                    .post(requestData.getUrl());

//        response.prettyPrint();
        step("3.获取响应值");
        attachment("响应值",response.prettyPrint());
        step("4.响应值提取");
        responseExtract(requestData.getRespdata(),response);

        ArrayList<Object> assertList = (ArrayList<Object>)requestData.getAssertdata();
        if(assertList != null && assertList.size()>0){
            step("断言");
            for(int i=0;i<assertList.size();i++){
                LinkedHashMap<String,Object> ass = (LinkedHashMap<String,Object>)assertList.get(i);
                attachment("断言设置:",ass.get("assertKey")+ " "+ ass.get("assertType")+ " "+ ass.get("assertValue"));
                if(ass.get("assertType").equals("equalTo")){
                    response.then().assertThat().body(ass.get("assertKey").toString(), equalTo(ass.get("assertValue")));
                }else if(ass.get("assertType").equals("greaterThan")){
                    response.then().assertThat().body(ass.get("assertKey").toString(), greaterThan(Integer.parseInt(ass.get("assertValue").toString())));
                }

            }
        }
        return response;

    }

    public static void responseExtract(LinkedHashMap<String, Object> responsedata,Response response){
        if(responsedata == null || responsedata.get("filepath")==null){
            return;
        }
        String resp_keys_path = responsedata.get("filepath").toString();
        ArrayList<String> resp_keys = (ArrayList<String>)responsedata.get("resp_keys");
        ArrayList<String> keys = (ArrayList<String>)responsedata.get("keys");

        for(int i=0;i<resp_keys.size();i++){
            Object resp_keys_result = JsonPath.with(response.asString()).get(resp_keys.get(i));
            if( resp_keys_result instanceof String){
            }
            if(resp_keys_result instanceof Integer){
            }
            attachment("提取值key",resp_keys.get(i));
            attachment("提取值value",resp_keys_result.toString());
            globaldata.put(resp_keys_path+keys.get(i),resp_keys_result);



        }


    }
}

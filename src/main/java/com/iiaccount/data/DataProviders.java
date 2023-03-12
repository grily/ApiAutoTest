package com.iiaccount.data;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iiaccount.common.RunCaseJson;
import com.iiaccount.utils.AssembledMessForJson;
import com.iiaccount.utils.YamlUtil;
import jdk.nashorn.internal.parser.JSONParser;
import jxl.read.biff.BiffException;
import org.testng.annotations.*;
import com.iiaccount.utils.YamlUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataProviders {

    public static String feature;
    public static Map<String, Map<String, Object>> ymldata;


//    public static void zhunbei(){
//        //1.根据类的名字user.java 获取 user.yaml文件
//        //根据函数名称  获取要执行的模块
//
//        List<Map<String, Object>> list = new ArrayList();
//        ymldata = YamlUtil.readYml("path");
//        feature = ymldata.get("feature").toString();
//    }


    /*
     *map包含两部分json，key为caseNo等信息，value为接口入参
     */
    @DataProvider(name = "dataprovider",parallel = true)
    public static Object[][] dataP(Method method) throws IOException, BiffException, URISyntaxException {
        String className = method.getDeclaringClass().getSimpleName(); //获取类名
        String caseFileName = className+".xls"; //测试案例名称为：类名.xls

        Object[][] objects = null;
        Map<String,String> map = new HashMap<String, String>();
        map = AssembledMessForJson.assembleMess(caseFileName,""); //""表示读取所有的为Y的case
        objects = new Object[map.size()][2];
        int i=0;
        for(Map.Entry<String, String> entry : map.entrySet()){
            objects[i][0] = entry.getKey();
            objects[i][1] = entry.getValue();
            i++;
        }
        map.clear();  //需清空map，否则案例会不断叠加 2018-10-19 add by lrb
        return objects;
    }




    @DataProvider(name="yamldata")
    public Iterator<Object[]> dataProvider(Method method) {
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) ymldata.get(method.getName());
        ArrayList<Map<String, Object>> reqlist = (ArrayList<Map<String, Object>>) map.get("request_params");
        ArrayList<Map<String, Object>> responselist = (ArrayList<Map<String, Object>>) map.get("response");
        ArrayList<Map<String, Object>> assertlist = (ArrayList<Map<String, Object>>) map.get("assert");
        ArrayList<String> descriptionlist = (ArrayList<String>) map.get("description");
        ArrayList<Map<String, Object>> depends_onlist = (ArrayList<Map<String, Object>>) map.get("depends_on");
        List<Object[]> result = new ArrayList<>();

        String path = map.get("path").toString();
        if(path.contains("$")){
            path = DataProviders.dealPath(path);
        }
        String url = "http://" + map.get("host").toString() + path;

        for (int i = 0; i < reqlist.size(); i++) {
            String methodflag = reqlist.get(i).get("method").toString();
            RequestData requestParam = new RequestData();
            requestParam.setUrl(url);
            requestParam.setMethod(methodflag);
            requestParam.setRun(true);
            requestParam.setStory(map.get("story").toString());
            requestParam.setRequestdata((LinkedHashMap<String, Object>) reqlist.get(i));

            if (responselist != null && i < responselist.size()) {
                requestParam.setRespdata((LinkedHashMap<String, Object>) responselist.get(i));
            }
            if (assertlist != null && i < assertlist.size()) {
                requestParam.setAssertdata((ArrayList<Object>) assertlist.get(i));
            }
            if (descriptionlist != null && i < descriptionlist.size()) {
                requestParam.setDescription(descriptionlist.get(i));
            }
            if (depends_onlist != null && i < depends_onlist.size()) {
                requestParam.setDependon((LinkedHashMap<String, Object>) depends_onlist.get(i));
            }
            result.add(new Object[]{requestParam});
        }


        return result.iterator();
    }

    private static String dealPath(String path){
        String[] arr = path.split("/");
        StringBuilder stringBuilder = new StringBuilder();
        RunCaseJson.globaldata.put("userId",5);
        for(int i=0;i<arr.length;i++){
            if(arr[i].startsWith("$") && RunCaseJson.globaldata.containsKey(arr[i].substring(1,arr[i].length()))){ //需要进行变量替换
                arr[i] = RunCaseJson.globaldata.get(arr[i].substring(1,arr[i].length())).toString();
            }
            if(i==0){
                stringBuilder.append(arr[i]);
            }else{
                stringBuilder.append("/"+ arr[i]);
            }

        }
        return stringBuilder.toString();

    }

    public static void main(String[] args) {
        String r = DataProviders.dealPath("/user/info/$userId");
        System.out.println(r);
        r = DataProviders.dealPath("/user/info/$userId/44");
        System.out.println(r);
        r = DataProviders.dealPath("/user/info/$userId/hac");
        System.out.println(r);

    }




}

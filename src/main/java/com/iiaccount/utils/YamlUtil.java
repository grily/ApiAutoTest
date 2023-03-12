package com.iiaccount.utils;

import org.apache.ibatis.io.Resources;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 读取 yml 工具类
 *
 * @author: ChenRuiYao
 * @since: 2020-07-30 09:35
 * @Version: 1.0
 */
public class YamlUtil {

    /**
     * 读取 ymal
     *
     * @param path 需要读取的文件路径
     * @return Map
     */
    public static Map getReadAbleYaml(String path) {
        Yaml yaml = new Yaml();
        Map obj = null;
        InputStream ins = null;
        try {
            ins = Resources.getResourceAsStream(path);
            obj = (Map) yaml.load(ins);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ins != null) {
                    ins.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    public static Map<String,Map<String,Object>> readYml(String path) {
        Yaml yaml = new Yaml();
        File f = new File(path);
        Iterable<Object> result = null;
        try {
            result = yaml.loadAll(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        Map<String,Map<String,Object>> msg = null;
        for (Object obj : result) {
            msg = (LinkedHashMap) obj;
        }
        return msg;
    }


    public static void main(String[] args) {
        String path = "/Users/lcj/code/lrg/ApiAutoTest/src/main/resources/yaml/user.yaml";
        YamlUtil.readYml(path);
    }
}


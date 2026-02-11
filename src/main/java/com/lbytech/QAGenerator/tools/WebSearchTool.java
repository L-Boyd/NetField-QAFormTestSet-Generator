package com.lbytech.QAGenerator.tools;


import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.lbytech.QAGenerator.properties.WebSearchProperties;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 网络搜索工具(利用https://www.searchapi.io)
 */
@Component
public class WebSearchTool {

    @Autowired
    private WebSearchProperties webSearchProperties;

    @Tool("Search for information from Baidu Search Engine")
    public String searchWeb(@P("Search query keyword") String keyword) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("q", keyword);
        paramMap.put("api_key", webSearchProperties.getApiKey());
        paramMap.put("engine", "baidu");

        try {
            String response = HttpUtil.get(webSearchProperties.getSearchApiUrl(), paramMap);

            // 取出结果的前三条
            JSONObject jsonObject = JSONUtil.parseObj(response);
            JSONArray organicResults = jsonObject.getJSONArray("organic_results");
            List<Object> objects = organicResults.subList(0, 3);

            String result = objects.stream()
                    .map(obj -> {
                        JSONObject tempJsonObject = (JSONObject) obj;
                        return tempJsonObject.toString();
                    })
                    .collect(Collectors.joining(","));
            return result;
        } catch (Exception e) {
            return "Error searching web: " + e.getMessage();
        }
    }

}

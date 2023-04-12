package top.canoe0.tracing.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

import top.canoe0.tracing.entity.Chat;

public class GptApiService {
    private static final String gptUrl = "https://api.openai.com/v1/chat/completions";
    private static final String gptModel = "gpt-3.5-turbo";
    private static final String apiKey = "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";

    Chat chat;

    public GptApiService(Chat chat) {
        this.chat = chat;
    }

    public HttpResponse sendPostRequest() throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(gptUrl);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("Authorization", "Bearer " + apiKey);

        JSONObject httpPostBody = new JSONObject();
        httpPostBody.put("model", gptModel);

        JSONObject messagesBody = new JSONObject();
        messagesBody.put("role", chat.getRole());
        messagesBody.put("content", chat.getChatArray());

        httpPostBody.put("messages", messagesBody);

        StringEntity requestEntity = new StringEntity(httpPostBody.toString(), "UTF-8");
        httpPost.setEntity(requestEntity);

        return httpClient.execute(httpPost);
    }

    public String getRes(HttpResponse httpResponse) throws IOException {
        String responseString = EntityUtils.toString(httpResponse.getEntity());
        JSONObject responseJson = JSONObject.parseObject(responseString);

        JSONArray choicesArray = responseJson.getJSONArray("choices");
        JSONObject choicesJson = JSONObject.from(choicesArray.get(0));
        JSONObject resMsgJson = choicesJson.getJSONObject("message");
        //添加到chat中，记录回复
        chat.getChatArray().add(resMsgJson);

        //最终回复结果
        String resContent = resMsgJson.getString("content");
        return resContent;
    }
}

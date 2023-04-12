package top.canoe0.tracing.entity;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.util.List;


public class Chat {
    private String role;
    private JSONArray chatArray;

    public Chat(String role, String prompt) {
        this.role = role;
        JSONObject chatJson = new JSONObject();
        chatJson.put("role", role);
        chatJson.put("content", prompt);
        this.chatArray.add(chatJson);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public JSONArray getChatArray() {
        return chatArray;
    }

    public void setChatArray(JSONArray chatArray) {
        this.chatArray = chatArray;
    }
}

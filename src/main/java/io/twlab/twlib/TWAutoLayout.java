package io.twlab.twlib;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TWAutoLayout {
    public static final int WIDGET_TYPE_TEXT = 1;
    public static final int WIDGET_TYPE_BUTTON = 2;

    public static final int WIDTH_WRAP_CONTENT = 0;
	/* Widget 的宽度为屏幕宽度 */
    public static final int WIDTH_FILL_ONE_ROW = 1;
	/* Widget 的宽度为屏幕宽度的一半 */
    public static final int WIDTH_FILL_HALF_ROW = 2;

    public ArrayList<Widget> widgetList = new ArrayList<>();

	/* 添加 Widget */
    public void addWidget(Widget w) {
        widgetList.add(w);
    }



    public static class Widget {
        public int id = 0;
        public int type;
        public String text = "";
        public int widthMode = WIDTH_WRAP_CONTENT;

        Widget(int t) {
            type = t;
        }

        Widget() {

        }
		
		/* 创建 Text Widget */
        public static Widget makeText(String text) {
            Widget w = new Widget(WIDGET_TYPE_TEXT);
            w.text = text;
            return w;
        }
		
		/* 创建 Button Widget */
        public static Widget makeButton(String text, int id) {
            Widget w = new Widget(WIDGET_TYPE_BUTTON);
            w.text = text;
            w.id = id;
            w.widthMode = WIDTH_WRAP_CONTENT;
            return w;
        }

        public JSONObject toJSONObject() throws JSONException {
            JSONObject json = new JSONObject();


                json.put("id", id);
                json.put("type", type);
                json.put("text", text);
                json.put("widthMode", widthMode);

            return json;
        }

        public static Widget fromJSONObject(JSONObject json) {
            if (json == null) {
                return null;
            }
            Widget w = new Widget();
            w.id = json.optInt("id", 0);
            w.type = json.optInt("type", 0);
            w.text = json.optString("text", "");;
            w.widthMode = json.optInt("widthMode",WIDTH_WRAP_CONTENT);
            return w;
        }

    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject json = new JSONObject();
        JSONArray arr = new JSONArray();
        for (Widget w: widgetList) {
            arr.put(w.toJSONObject());
        }

            json.put("widgetList", arr);

        return json;
    }

    public String toJSON() throws JSONException {
        return toJSONObject().toString();

    }

    public static TWAutoLayout fromJSONObject(JSONObject json) {
        if (json == null) {
            return null;
        }
        TWAutoLayout al = new TWAutoLayout();
        JSONArray arr = json.optJSONArray("widgetList");
        for (int i = 0; i < arr.length(); i++) {
            al.addWidget(Widget.fromJSONObject(arr.optJSONObject(i)));
        }
        return al;
    }

    public static TWAutoLayout fromJSON(String jsonStr) throws JSONException {
        JSONObject json = new JSONObject(jsonStr);
        return TWAutoLayout.fromJSONObject(json);
    }




}

package xyz.aungpyaephyo.padc.firebase.data.models;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import xyz.aungpyaephyo.padc.firebase.data.vo.NewsFeedVO;

/**
 * Created by aung on 8/18/17.
 */

public class NewsFeedModel {

    private static final String PATH_SAMPLE_DATA = "sample_data";
    private static final String OFFLINE_NEWSFEED = "news_feed.json";

    private static NewsFeedModel objInstance;

    private NewsFeedModel() {

    }

    public static NewsFeedModel getInstance() {
        if (objInstance == null) {
            objInstance = new NewsFeedModel();
        }
        return objInstance;
    }

    public List<NewsFeedVO> getSampleNewsFeed(Context context) {
        try {
            String attractions = loadDummyData(context, OFFLINE_NEWSFEED);
            Type listType = new TypeToken<List<NewsFeedVO>>() {
            }.getType();
            List<NewsFeedVO> attractionList = new Gson().fromJson(attractions, listType);
            return attractionList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Read text from assets folder.
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    private byte[] readJsonFile(Context context, String fileName) throws IOException {
        InputStream inStream = context.getAssets().open(fileName);
        int size = inStream.available();
        byte[] buffer = new byte[size];
        inStream.read(buffer);
        inStream.close();
        return buffer;
    }

    /**
     * @param fileName - name of Json File.
     * @return JSONObject from loaded file.
     * @throws IOException
     * @throws JSONException
     */
    private String loadDummyData(Context context, String fileName) throws IOException, JSONException {
        byte[] buffer = readJsonFile(context, PATH_SAMPLE_DATA + "/" + fileName);
        return new String(buffer, "UTF-8").toString();
    }
}

package com.example.astrand.hangman.Services;
import com.loopj.android.http.*;

/**
 * Created by strand117 on 21.08.2017.
 */

@Deprecated
public class GenerateRandomWordService {

    private static final String BASE_URL = "http://setgetgo.com/randomword/get.php";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, ResponseHandlerInterface responseHandler){
        client.get(BASE_URL,params,responseHandler);
    }

    public static void get(ResponseHandlerInterface httpResponseHandler){
        get(BASE_URL,null,httpResponseHandler);
    }
}

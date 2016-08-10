package com.rudie.severin.data_from_api.REST;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by erikrudie on 8/9/16.
 */
public class ApiClient {

//    public static final String BASE_URL = "http://api.myservice.com/";
    public static final String BASE_URL = "http://api.giphy.com/v1/gifs/";
    public static Retrofit retrofit;


    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}

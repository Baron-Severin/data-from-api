package com.rudie.severin.data_from_api.REST;

import com.rudie.severin.data_from_api.Model.GiphyResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by erikrudie on 8/9/16.
 */
public interface ApiInterface {

    @GET("translate")
    Call<GiphyResponse> getGiphy(@Query("s") String query, @Query("api_key") String API_KEY);



//    @GET("discover/movie")
//    Call<MoviesResponse> getTopRatedMovies(@Query("with_people") int id, @Query("sort_by") String sortBy,
//                                           @Query("api_key") String apiKey, @Query("page") int page);


}

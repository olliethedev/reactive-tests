package com.beastpotato.reactivetests.endpoints;

import com.beastpotato.reactivetests.BuildConfig;
import com.beastpotato.reactivetests.models.Configuration;
import com.beastpotato.reactivetests.models.MoviesData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Oleksiy on 3/16/2017.
 */

public interface EndpointInterface {
    @GET("/3/configuration?api_key=" + BuildConfig.API_KEY)
    Observable<Configuration> getConfig();

    @GET("/3/movie/popular?api_key=" + BuildConfig.API_KEY)
    Observable<MoviesData> getPopular();

    @GET("/3/movie/now_playing?api_key=" + BuildConfig.API_KEY)
    Observable<MoviesData> getCurrent();

    @GET("/3/movie/upcoming?api_key=" + BuildConfig.API_KEY)
    Observable<MoviesData> getUpcoming();

    @GET("/3/search/movie?api_key=" + BuildConfig.API_KEY)
    Observable<Object> search(@Query("query") String query);

}

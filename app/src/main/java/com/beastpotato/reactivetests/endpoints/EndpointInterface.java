package com.beastpotato.reactivetests.endpoints;

import com.beastpotato.reactivetests.models.Configuration;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Oleksiy on 3/16/2017.
 */

public interface EndpointInterface {
    @GET("/3/configuration")
    Observable<Configuration> getConfig(@Query("api_key") String apiKey);
}

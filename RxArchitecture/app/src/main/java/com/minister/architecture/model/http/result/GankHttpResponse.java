package com.minister.architecture.model.http.result;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by codeest on 2016/8/3.
 */
public class GankHttpResponse<T> {

    private boolean error;
    private T results;

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    public boolean getError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public GankHttpResponse(Response<T> response) {
        if(response.isSuccessful()) {
            results = response.body();
            error = false;
        }else {
            String message = null;
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException ignored) {
                    Logger.e(ignored, "error while parsing response");
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            error = true;
            results = null;
        }
    }

    public GankHttpResponse(Throwable throwable) {
        results = null;
        error = true;
    }

    public GankHttpResponse(boolean error, T results) {
        this.error = error;
        this.results = results;
    }
}

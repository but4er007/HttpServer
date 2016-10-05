package com.povechkin.httpserver;

import android.util.Log;

import java.io.IOException;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by povechkin on 04.10.2016.
 * Custis production
 */
public class HttpServer extends NanoHTTPD {
    public static final String TAG = HttpServer.class.getCanonicalName();

    public static final int PORT = 1488;

    private IRequestCallback callback;

    public HttpServer(IRequestCallback callback) throws IOException {
        super(PORT);
        Log.d(TAG, "start");
        this.callback = callback;
        start();
    }
    @Override
    public Response serve(IHTTPSession session) {
        Map<String, String> parms = session.getParms();

        if(callback.isBusy()) {
            return unsuccessResponse("notepad is busy");
        }

        if(parms.containsKey("shop")
                && parms.containsKey("id_client")
                && parms.containsKey("id_seller")) {
            String shop = parms.get("shop");
            String id_client = parms.get("id_client");
            String id_seller = parms.get("id_seller");

            callback.openAnketForm(shop, id_seller, id_client);
            return successResponse();
        } else {
            callback.closeAnketForm();
            return successResponse();
        }
    }

    Response successResponse(){
        return newFixedLengthResponse(new Response.IStatus() {
            @Override
            public String getDescription() {
                return "success";
            }

            @Override
            public int getRequestStatus() {
                return 0;
            }
        }, MIME_PLAINTEXT, "success");
    }
    Response unsuccessResponse(final String errorMsg){
        return newFixedLengthResponse(new Response.IStatus() {
            @Override
            public String getDescription() {
                return errorMsg;
            }

            @Override
            public int getRequestStatus() {
                return 1;
            }
        }, MIME_PLAINTEXT, errorMsg);
    }

    interface IRequestCallback {
        boolean isBusy();
        void openAnketForm(String shop, String id_seller, String id_client);
        void closeAnketForm();
    }
}

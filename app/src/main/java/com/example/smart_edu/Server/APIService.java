package com.example.smart_edu.Server;

public class APIService {

    private static String base_url = "https://da4-btl-webapi.conveyor.cloud/api/";

    public static Dataservice getService(){
        return APIRetrfitClient.getClient(base_url).create(Dataservice.class);
    }
}

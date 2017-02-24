package com.example.wistart.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by pdomokos on 2/23/17.
 */
@Component
//@ConfigurationProperties
public class WiDataServiceBean {

    @Value("${sheet_id}")
    private String sheet_id;
    @Value("${gjson_name}")
    private String gjson_name;
    @Value("${udata1_name}")
    private String udata1_name;
    @Value("${udata2_name}")
    private String udata2_name;

    public String getSheet_id() {
        return sheet_id;
    }

    public String getGjson_name() {
        return gjson_name;
    }

    public String getUdata1_name() {
        return udata1_name;
    }

    public String getUdata2_name() {
        return udata2_name;
    }

}

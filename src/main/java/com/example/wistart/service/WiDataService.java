package com.example.wistart.service;

import au.com.bytecode.opencsv.CSVReader;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by pdomokos on 2/17/17.
 */

@Service
public class WiDataService {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static HttpTransport HTTP_TRANSPORT;

    private final WiDataServiceBean wiDataServiceBean;

    @Autowired
    public WiDataService(WiDataServiceBean wiDataServiceBean) {
        this.wiDataServiceBean = wiDataServiceBean;
    }

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public List<int[]> extractCSV(int interval, int[] userIds, Date date) {
        CSVReader reader;
        Resource resource1 = new PathResource(wiDataServiceBean.getUdata1_name());
        Resource resource2 = new PathResource(wiDataServiceBean.getUdata2_name());
        List<int[]> result = new ArrayList();

        try {
            final File[] userFiles = {resource1.getFile(), resource2.getFile()};
            if (userIds.length <= userFiles.length) {
                for (int i = 0; i < userIds.length; i++) {
                    reader = new CSVReader(new FileReader(userFiles[i]));
                    List<String[]> list = reader.readAll();
                    List<String[]> dayList = new ArrayList();
                    if (list.size() > 1) {
                        for (int j = 1; j < list.size(); j++) {
                            if (checkDate(date, list.get(j)[0])) {
                                dayList.add(list.get(j));
                            }
                        }
                        if (list.size() > 1) {
                            result.add(getColumn(dayList, interval));
                        }
                    }
                    reader.close();
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return result;
    }

    private int[] getColumn(List<String[]> list, int interval) {
        int a = 1440 / interval;
        int[] result = new int[a];
        for (int i = 0; i < result.length; i++) {
            result[i] = 0;
        }
        String[] line;
        int index;
        if (list.size() > 1) {
            for (int i = 1; i < list.size(); i++) {
                line = list.get(i);
                index = getIndex(line[0], a);
                result[index] += Integer.valueOf(line[1]);
            }
        }
        return result;
    }

    private int getIndex(String date, int interval) {
        double i;
        LocalDateTime ldt = LocalDateTime.parse(date, DTF);
        int hour = ldt.getHour();
        int min = ldt.getMinute();
        int mins = hour * 60 + min;
        i = interval / 1440.0D * mins;
        return (int) i;
    }

    private boolean checkDate(Date wanted, String actual) {
        LocalDateTime wLdt = LocalDateTime.parse(SDF.format(wanted), DTF);
        LocalDateTime aLdt = LocalDateTime.parse(actual, DTF);
        if ((wLdt.getYear() == aLdt.getYear()) && (wLdt.getMonth() == aLdt.getMonth()) && (wLdt.getDayOfMonth() == aLdt.getDayOfMonth())) {
            return true;
        }
        return false;
    }

    public List<List<Object>> writeData(List<int[]> days, Date date) {
        List<List<Object>> writeData = new ArrayList();
        try {
            Resource resource1 = new PathResource(wiDataServiceBean.getGjson_name());
            GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(resource1.getFile()))
                    .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
            Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                    .setApplicationName("test")
                    .build();

            String id = wiDataServiceBean.getSheet_id();
            if (days.size() > 0) {
                int intervalMs = 1440 / days.get(0).length * 60 * 1000;
                for (int i = 0; i < days.get(0).length; i++) {
                    List<Object> dataRow = new ArrayList();
                    dataRow.add(SDF.format(new Date(date.getTime() + (i * intervalMs))));
                    for (int j = 0; j < days.size(); j++) {
                        dataRow.add(days.get(j)[i]);
                    }
                    writeData.add(dataRow);
                }

                ClearValuesRequest cvr = new ClearValuesRequest();
                ClearValuesResponse cvresp = service.spreadsheets().values()
                        .clear(id, "A1:C96", cvr)
                        .execute();

                logRes(cvresp.values());

                ValueRange vr = new ValueRange().setValues(writeData).setMajorDimension("ROWS");
                UpdateValuesResponse uvr = service.spreadsheets().values()
                        .update(id, "A1", vr)
                        .setValueInputOption("RAW")
                        .execute();

                logRes(uvr.values());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return writeData;
    }

    private void logRes(Collection<Object> values) {
        if (values == null || values.size() == 0) {
            log.info("No data found");
        } else {
            log.info("response: ");
            for (Object o : values) {
                log.info(o.toString());
            }
        }
    }

}





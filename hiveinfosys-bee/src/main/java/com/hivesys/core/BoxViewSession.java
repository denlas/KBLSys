/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hivesys.core;

import com.box.view.BoxViewClient;
import com.box.view.BoxViewException;
import com.box.view.Session;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author swoorup
 */
public class BoxViewSession {

    private static final BoxViewSession singleton = new BoxViewSession();
    HashMap<String, Session> map;
    private BoxViewClient boxClient;

    public BoxViewSession() {
        map = new HashMap<>();
    }

    public String getViewURL(String docID) {
        Session session = map.get(docID);
        if (session == null || (new Date().after(session.getExpiresAt()))) {
            try {

                Map<String, Object> params = new HashMap<>();
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.HOUR, 10);
                params.put("expiresAt", cal.getTime());
                params.put("isDownloadable", true);
                params.put("isTextSelectable", true);
                session = Session.create(boxClient, docID, params);

                map.put(docID, session);
            } catch (BoxViewException | NullPointerException | ParseException ex) {
                return "";
            } 
        } // if expired
        return session.getViewUrl();
    }

    /**
     * @return the boxClient
     */
    public BoxViewClient getBoxClient() {
        return boxClient;
    }

    /**
     * @param boxClient the boxClient to set
     */
    public void setBoxClient(BoxViewClient boxClient) {
        this.boxClient = boxClient;
    }

    public static BoxViewSession getInstance() {
        return singleton;
    }
}

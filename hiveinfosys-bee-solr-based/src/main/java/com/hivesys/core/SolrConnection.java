package com.hivesys.core;

import com.hivesys.dashboard.domain.FileInfo;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.ContentStreamBase;
import org.apache.solr.common.util.ContentStreamBase.FileStream;

public class SolrConnection {

    private static final SolrConnection singleton = new SolrConnection();
    private HttpSolrClient mHttpSolrClient = null;
    private String mUrl = "";

    SolrConnection() 
    {
    }

    public void connect(String serverUrl) {
        mUrl = serverUrl;
        mHttpSolrClient = new HttpSolrClient(mUrl);

        // this fixes all the missing stream http header issues. Eg:
        // Content.Length = null causing stream_size=null
        mHttpSolrClient.setUseMultiPartPost(true);
        
        mHttpSolrClient.setSoTimeout(3000);
        mHttpSolrClient.setConnectionTimeout(7000);

    }

    public void close() {

    }

    public SolrDocumentList query(String queryString)
            throws SolrServerException, IOException {
        SolrQuery parameters = new SolrQuery();
        parameters.set("q", queryString);
        QueryResponse query = mHttpSolrClient.query(parameters);
        return query.getResults();
    }

    public SolrClient getHttpSolrClient() {
        return this.mHttpSolrClient;
    }

    public void indexFile(FileInfo fInfo) {
        ContentStreamUpdateRequest req = new ContentStreamUpdateRequest("/update/extract");

        ContentStreamBase.FileStream fs = new FileStream(new File(fInfo.getContentFilepath()));
        req.addContentStream(fs);
        req.setMethod(METHOD.POST);

        SimpleDateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = new Date();

        req.setParam("resource.name", fInfo.getContentFilepath());
        req.setParam("literal.id", fInfo.getCrcHash());
        req.setParam("literal.hive_uploaded_date", dateFormatUTC.format(date));

        try {
            UpdateResponse result = req.process(mHttpSolrClient);
            mHttpSolrClient.commit();
        } catch (SolrServerException | IOException ex) {
            Logger.getLogger(SolrConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Done uploading");
    }

    // return the default instance of the client
    /* Static 'instance' method */
    public static SolrConnection getInstance() {
        return singleton;
    }

}

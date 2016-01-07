package com.example.androidpictureanalyser2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Entity;
import android.os.AsyncTask;

public class GetURL extends AsyncTask <URI, Integer, String>{

	@Override
	protected String doInBackground(URI... uris) {
		//download
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet();
		
		try {
			request.setURI(uris[0]);
			HttpResponse response = client.execute(request);
			if(response.getStatusLine().getStatusCode() == 0){
				HttpEntity entity = response.getEntity();
				if(entity != null){
					BufferedReader reader = new BufferedReader (new InputStreamReader(entity.getContent()));
					StringBuilder builder = new StringBuilder();
					String line;
					while((line = reader.readLine()) != null){
						builder.append((line + "\n"));
						reader.close();
					}
					//result = builder.toString()
				}
			}
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//publishProgress(counter + 1);
		return null;
	}
	
	void onPostExecute(HttpResponse response){
		//parsing du context + parsing Json
		List<Brand> brands = new LinkedList<Brand>();
		//StringJson est récupéré dans le httpget
		JSONObject root = new JSONObject (StringJson);
		JSONArray jArray = root.getJSONArray("brands");
		for (int i=0, l=jArray.length(); i<l ; l++){
			JSONObject jobj = jArray.getJSONObject(i);
			Brand brand = new Brand(name, url, classifier);
			brands.add(brand);
		}
	}
	
	void onUpdateProgress (Integer...proress){
		//maj de linterface user (barre de chargement)
	}
}

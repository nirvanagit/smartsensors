package main.java.edu.cmu.sv.sdsp.senseControllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class GetCreditController extends AsyncTask<String, String, String> {

	/** The ctx. */
	public Context ctx;

	public GetCreditController(Context context) {

		this.ctx = context;
	}

	private static final String URL = "http://cmu-app-server.herokuapp.com/sensor/credit/getUserCredit";

	@Override
	protected String doInBackground(String... arguments) {

		String urlStr = URL;
		String jsonString = arguments[0];
		URL url = null;
		HttpURLConnection conn = null;
		OutputStream out = null;
		Writer writer = null;

		try {
			url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setDoOutput(true);
			out = conn.getOutputStream();
			writer = new OutputStreamWriter(out, "UTF-8");
			// String data = (new Gson()).toJson(jsonString);
			writer.write(jsonString);
		} catch (Exception e) {
			Log.e("Test", "Exception" + e.getMessage());
		}

		finally {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("Test", "Exception" + e.getMessage());
			}
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("Test", "Exception" + e.getMessage());
			}
		}

		try {
			if (conn.getResponseCode() != 200) {
				throw new IOException(conn.getResponseMessage());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("Test", "Exception" + e.getMessage());
		}
		StringBuilder sb = null;
		BufferedReader rd = null;
		try {
			rd = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("Test", "Exception" + e.getMessage());
		} finally {
			try {
				rd.close();
			} catch (IOException e) {
				// TODO Auto-geneTorated catch block
				e.printStackTrace();
				Log.e("Test", "Exception" + e.getMessage());
			}
		}

		conn.disconnect();
		String str = sb.toString();

		return str;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(String result) {

		Log.d("test_2", result);

		Toast.makeText(this.ctx, result, Toast.LENGTH_LONG).show();

	}

}
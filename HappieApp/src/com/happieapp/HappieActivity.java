package com.happieapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.happieapp.model.CompanyResponse;
import com.happieapp.network.HttpRequestTask;
import com.happieapp.network.HttpRequestTask.ICallBack;
import com.happieapp.network.ImageDownloader;

public class HappieActivity extends Activity {
	
	private final String url = "http://happie-app.com/index.php?r=compa/api&apikey=2feb18d38e87e3713cb522b8df085f8e&id=%s";
	
	private TextView companyName, companyFullName, phone, city;
	
	private ImageView companyLogo;
	
	private CompanyResponse response;
	
	private HappieApplication mApp;
	
	private String companyId;
	
	private SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main_new);
		preferences = getSharedPreferences(getString(R.string.pref_file_name), Context.MODE_PRIVATE);
		
		mApp = (HappieApplication) getApplication();
		companyName = (TextView) findViewById(R.id.textViewCompanyName);
		companyFullName = (TextView) findViewById(R.id.textViewCompanyNameFull);
		phone = (TextView) findViewById(R.id.textViewCompanyTel);
		phone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + response.getPhone()));
				startActivity(intent);
				
			}
		});
		city = (TextView) findViewById(R.id.textViewCompanyCity);
		city.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HappieActivity.this, HappieMapActivity.class);
				startActivity(intent);
				
			}
		});
		companyLogo = (ImageView) findViewById(R.id.imageViewCompanyLogo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		ICallBack callBack = new ICallBack() {
			
			@Override
			public void onSuccess(CompanyResponse result) {
				// TODO Auto-generated method stub
				if (result != null){
					response = result;
					mApp.setCompanyResponse(response);
					companyName.setText(result.getCompanyName());
					companyFullName.setText(result.getCompanyName());
					phone.setText(result.getPhone());
					city.setText(result.getCity());
					String urlLogo = result.getLogo();
					ImageDownloader downloader = new ImageDownloader(companyLogo, urlLogo);
					downloader.start();
					
					
					
				}
				
			}
			
			@Override
			public void onError(CompanyResponse result) {
				// TODO Auto-generated method stub
				
			}
		};
		companyId = preferences.getString(getString(R.string.pref_company_id), null);
		if (companyId != null){
			HttpRequestTask task = new HttpRequestTask(this, callBack);
			Object[] params = new Object[1];
			String localUrl = String.format(url, companyId);
			params[0] = localUrl;
			task.execute(params);
		}
	}

}

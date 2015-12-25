package com.thoughtworks.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.thoughtworks.myapplication.domain.PM25;
import com.thoughtworks.myapplication.service.AirServiceClient;
import com.thoughtworks.myapplication.service.MessageCreator;
import com.thoughtworks.myapplication.service.MyAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends Activity {

    private EditText cityEditText;
    private TextView pm25TextView;
    private ProgressDialog loadingDialog;


    private ListView lv;

    private List<PM25> data;
    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems;

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
//    ArrayAdapter<String> adapter;

    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityEditText = (EditText) findViewById(R.id.edit_view_input);
        pm25TextView = (TextView) findViewById(R.id.text_view_pm25);
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage(getString(R.string.loading_message));




        lv = (ListView) findViewById(R.id.listView);
        cleanListView();


        findViewById(R.id.button_query_pm25).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleanListView();
                onQueryPM25Click();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new  AlertDialog.Builder(MainActivity.this)
                        .setTitle("详细信息" )
                        .setMessage(MessageCreator.initMessage(data.get(position)))
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }



    private void onQueryPM25Click() {
        final String city = cityEditText.getText().toString();
        if (!TextUtils.isEmpty(city)) {
            showLoading();
            AirServiceClient.getInstance().requestPM25(city, new Callback<List<PM25>>() {
                @Override
                public void onResponse(Response<List<PM25>> response, Retrofit retrofit) {
                    showSuccessScreen(response);
                }

                @Override
                public void onFailure(Throwable t) {
                    showErrorScreen();
                }
            });
        }
    }

    private void showSuccessScreen(Response<List<PM25>> response) {
        hideLoading();
        if (response != null) {
            this.data = response.body();
            populate(response.body());
        }
    }

    private void showErrorScreen() {
        hideLoading();
        pm25TextView.setText(R.string.error_message_query_pm25);
    }

    private void showLoading() {
        loadingDialog.show();
    }

    private void hideLoading() {
        loadingDialog.dismiss();
    }

    private void populate(List<PM25> data) {
        if (data != null && !data.isEmpty()) {
            pm25TextView.setText("共有" + data.size() + "条记录");
            for (int i = 0; i < data.size(); i++) {
                addItems(data.get(i));
            }
        }
    }

    public void addItems(PM25 pm25) {
        String positionName = pm25.getPositionName();
        String quality = pm25.getQuality();
        String defaultMessage = "未知";
        if (positionName == null) positionName = defaultMessage;
        if (quality == null) quality = defaultMessage;
        listItems.add(positionName + "    " + quality);
        adapter.notifyDataSetChanged();
    }

    public void cleanListView(){
        listItems=new ArrayList<String>();
//        adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1,
//                listItems);
        adapter = new MyAdapter(this,
        android.R.layout.simple_list_item_1,
                listItems);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}

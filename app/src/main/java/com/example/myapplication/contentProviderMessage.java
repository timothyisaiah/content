package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.util.ArrayList;

public class contentProviderMessage extends AppCompatActivity {

    EditText edAddress, edMessage, edDate;
    Button btnsave;
    MysqlConnection mysqlConnection;
    ProgressDialog progressDialog;
    ArrayList<String> SmsM = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_message);

        fetchContacts();

        mysqlConnection = new MysqlConnection();

        progressDialog = new ProgressDialog(this);

        btnsave = findViewById(R.id.btnsave);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveToMysql saveToMysql = new SaveToMysql();
                saveToMysql.execute("");

            }
        });
    }

    private void fetchContacts(){
        ArrayList<String> contacts = new ArrayList<>();

//        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Uri uriMessages = Telephony.Sms.CONTENT_URI;
//        Uri uri = null;
//        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER};
        String[] newProjection = {Telephony.Sms.ADDRESS,Telephony.Sms.BODY,Telephony.Sms.DATE};

        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;

        ContentResolver resolver = getContentResolver();
//        Cursor cursor = resolver.query(uri,projection,selection,selectionArgs,sortOrder);
        Cursor newCursor = resolver.query(uriMessages,newProjection,selection,selectionArgs,sortOrder);

        while(newCursor.moveToNext()){

            String address = newCursor.getString(newCursor.getColumnIndex(Telephony.Sms.ADDRESS));
            String body = newCursor.getString(newCursor.getColumnIndex(Telephony.Sms.BODY));
            String date = newCursor.getString(newCursor.getColumnIndex(Telephony.Sms.DATE));
//            String newDate ="";

            Log.i("Message","address" + address + " BODY "+ body + "date" + date);
//            Log.i("Date",newDate);
             SmsM.add(address +"\n" + body +"\n" +  date);
        }
//        while(cursor.moveToNext()){
//            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//            String num = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//
//            Log.i("Contact","Name - "+ name + " Num - "+ num);
//
//            contacts.add(name + "\n" + " Num - " +num);
//        }
//
        ((ListView)findViewById(R.id.messageList)).setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,SmsM));
    }

    private class SaveToMysql extends AsyncTask<String,String,String> {
        String z = "";
        boolean isSuccess = false;


        @Override
        protected void onPreExecute(){

//            progressDialog.setMessage("Loading.....");
//            progressDialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {

            try{
                Connection con = mysqlConnection.CONN();
                if(con == null){
                    z = "Please Check Your Internet Connection";
                }else {
                    Log.i("Saved1","saved success1");
                    progressDialog.setMessage("Loading.....");
                    progressDialog.show();
                    String query = "insert into trialTable values("+ SmsM.get(4)+","+SmsM.get(5)+","+SmsM.get(6)+")";
                    Log.i("Saved2","saved success2");

                    Statement statement = con.createStatement();

                    Log.i("Saved3","saved success3");

                    statement.executeUpdate(query);
                    Log.i("Saved4","saved success4");

                    z = "Successfully saved!";
                    isSuccess = true;
                }
            }catch (Exception e){
                isSuccess = false;
                z = "exception caught "+ e;
            }

            return z;
        }

        @Override
        protected void onPostExecute(String s){
            if(isSuccess){
                Toast.makeText(getBaseContext(), ""+z, Toast.LENGTH_LONG).show();
            }

            progressDialog.hide();
        }

    }
}

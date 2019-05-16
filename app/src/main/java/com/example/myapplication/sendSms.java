package com.example.myapplication;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class sendSms extends AppCompatActivity {

    ArrayList<String> contacts = new ArrayList<>();

    Button send,viewContacts;
    EditText senderId,Message,phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        fetchContacts();

        senderId = (EditText) findViewById(R.id.senderId);
        phoneNumber = findViewById(R.id.phoneNumber);
        Message = findViewById(R.id.Message);
        send = findViewById(R.id.send);

        phoneNumber.setText(contacts.get(4));
        viewContacts = findViewById(R.id.viewContacts);
        viewContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),contentProviderMessage.class));
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSms();
            }
        });

    }
    public void sendSms(){


        new Thread(new Runnable() {
            @Override
            public void run() {


                try{
                    //Construct data
                    String apiKey = "mJu1L05+CJ4-8euBTyKQvWbdT03mBcdcA01OMTbRqq";
                    String Senderid=  senderId.getText().toString();
                    String Phone = phoneNumber.getText().toString();
                    String message = Message.getText().toString();

                    String APIKEY = "apikey="+apiKey;
                    String Message = "&message="+message;
                    String sender = "&sender="+Senderid;
                    String numbers = "&numbers="+Phone;

                    // Send data
                    HttpURLConnection con = (HttpURLConnection) new URL( "https://api.txtlocal.com/send/?").openConnection();
                    String data = APIKEY + numbers + Message + sender;
                    con.setDoOutput(true);
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Content-length",Integer.toString(data.length()));
                    con.getOutputStream().write(data.getBytes("UTF-8"));
                    final BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    final StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line = rd.readLine()) != null ){
                        stringBuffer.append(line);
                    }
                    rd.close();


                }catch (Exception e){
                    Toast.makeText(sendSms.this, "error occured!!"+e, Toast.LENGTH_LONG).show();
                    // return "Error"+e;
                }
            }
        }).start();
    }

    private void fetchContacts(){

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

//        Uri uri = null;
        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER};
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri,projection,selection,selectionArgs,sortOrder);

        while(cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String num = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            Log.i("Contact", "Name - " + name + " Num - " + num);
            contacts.add(num);


        }


//        ((ListView)findViewById(R.id.contactList)).setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,contacts));
    }
}

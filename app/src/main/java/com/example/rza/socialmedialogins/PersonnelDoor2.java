package com.example.rza.socialmedialogins;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.Toast;

import java.nio.charset.Charset;

public class PersonnelDoor2 extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback, NfcAdapter.OnNdefPushCompleteCallback{

    private String personnelvalue;
    NfcAdapter mNfcAdapter;
    private String TExtInfo;
    private static final int MESSAGE_SENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_door2);

        Intent i = getIntent();
        personnelvalue = i.getExtras().getString("PersonnelValue");

        Toast.makeText(getApplicationContext(),personnelvalue,Toast.LENGTH_SHORT).show();

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(mNfcAdapter == null){
            TExtInfo = "NFC bu cihazda desteklenmemektedir";
            Toast.makeText(getApplicationContext(),TExtInfo,Toast.LENGTH_SHORT).show();
        }

        // Register callback to set NDEF message
        mNfcAdapter.setNdefPushMessageCallback(this, this);
        // Register callback to listen for message-sent success
        mNfcAdapter.setOnNdefPushCompleteCallback(this, this);

    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        Time time = new Time();
        time.setToNow();
        String text = (time.format("%H:%M:%S") +" "+ personnelvalue + " ");
        NdefMessage msg = new NdefMessage(
                new NdefRecord[] { createMimeRecord(
                        "application/com.example.rza.socialmedialogins", text.getBytes())
                });
        return msg;
    }

    @Override
    public void onNdefPushComplete(NfcEvent event) {
        mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_SENT:
                    Toast.makeText(getApplicationContext(), "Mesaj gönderilmiştir!", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
    }

    private void processIntent(Intent intent) {
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
        TExtInfo = new String(msg.getRecords()[0].getPayload());
    }

    public NdefRecord createMimeRecord(String mimeType, byte[] payload) {
        byte[] mimeBytes = mimeType.getBytes(Charset.forName("US-ASCII"));
        NdefRecord mimeRecord = new NdefRecord(
                NdefRecord.TNF_MIME_MEDIA, mimeBytes, new byte[0], payload);
        return mimeRecord;
    }
}

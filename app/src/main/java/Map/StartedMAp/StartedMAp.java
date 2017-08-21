package Map.StartedMAp;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.android.playservices.R;
import com.google.android.gms.maps.MapFragment;
/*
* MAP Instructions
* 1-Getting SHA Certificate :
  C:\Program Files\Java\jre1.8.0_131\bin>keytool -exportcert -alias androiddebugke
    y -keystore C:\Users\Abdulrhman\.android\debug.keystore -list -v
    password : android

    Result :
    Serial number: 1
    Valid from: Sat Jul 30 11:44:58 EET 2016 until: Mon Jul 23 11:44:58 EET 2046
    Certificate fingerprints:
         MD5:  85:D7:DD:0A:F8:94:E0:D3:59:8B:A9:DD:57:78:1F:D9
         SHA1: 84:FC:B4:BB:CF:96:2A:C0:2F:F6:F7:77:9A:A9:94:4C:00:22:A9:04
         SHA256: E9:2C:77:85:1C:63:F4:2B:7C:05:C8:14:05:FA:0D:43:05:6F:CD:91:C8:59:C4:DC:8C:EE:C1:C6:13:FA:A3:6A
         Signature algorithm name: SHA1withRSA
         Version: 1

 MAP API KEY : AIzaSyBDXjj1edtpEc9JktFw8ddVPJYUMc8YwqA    (Unrestricted Key)

 Autovia Map Key : AIzaSyCHvi3LH0fG-3KGOGMC-9he5MMWC1XWsfM

* 2- adding Permissions (internet, network state , external storage )
* 3- adding Meta-Data for OpenGl2 along side with Meta-Data for ( Playservice , API Key )
*
* */

public class StartedMAp extends AppCompatActivity {
    MapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started_map);

        mapFragment = MapFragment.newInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.map,mapFragment,null).commit();


    }
}

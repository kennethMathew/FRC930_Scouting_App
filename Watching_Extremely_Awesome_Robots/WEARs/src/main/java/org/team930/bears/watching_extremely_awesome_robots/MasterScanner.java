package org.team930.bears.watching_extremely_awesome_robots;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MasterScanner extends AppCompatActivity {
    Button generateCSV, scanQRCode;
    TextView numDataSets;

    String otherPreferences;
    boolean showToast;

    SharedPreferences otherSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_scanner);

        otherPreferences = getString(R.string.otherPreferences);
        otherSettings = getSharedPreferences(otherPreferences, 0);

        showToast = true;

        generateCSV = findViewById(R.id.generateCSV);
        scanQRCode = findViewById(R.id.scanQRCode);
        numDataSets = findViewById(R.id.numDataSets);


        if (otherSettings.getInt("scannedID", 6) == 6) {
            numDataSets.setTextSize(45);
            numDataSets.setText("Generate A CSV");
            scanQRCode.setVisibility(View.INVISIBLE);

        } else {
            numDataSets.setTextSize(100);
            numDataSets.setText(Integer.toString(otherSettings.getInt("scannedID", 6)));

        }

        if (otherSettings.getInt("scannedID", 0) == 0) {
            SharedPreferences.Editor SPOS = otherSettings.edit();

            SPOS.putInt("scannedID", 0);
            SPOS.commit();
        }

        if (otherSettings.getBoolean("csVisible", false)) {
            generateCSV.setVisibility(View.VISIBLE);
        }


    }

    public void setGenerateCSV(View v) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        String fullCSVExport = otherSettings.getString("csvMatch1", "") + otherSettings.getString("csvMatch2", "") +
                otherSettings.getString("csvMatch3", "") + otherSettings.getString("csvMatch4", "") +
                otherSettings.getString("csvMatch5", "") + otherSettings.getString("csvMatch6", "");
        try {
            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + "/FRCMATCHDATA");
            dir.mkdirs();
            File file = new File(dir, "MatchData.csv");

            if (file.exists()) {
                file.delete();
            } else {
                file.createNewFile();
            }

            FileOutputStream f = new FileOutputStream(file);

            f.write(fullCSVExport.getBytes());
            f.flush();
            f.close();
            SharedPreferences.Editor SPOS = otherSettings.edit();
            SPOS.putInt("csvCreated", otherSettings.getInt("csvCreated", 0) + 1);
            SPOS.commit();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "FileNotFound", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {

            Toast.makeText(this, "IO", Toast.LENGTH_SHORT).show();
        }
        if(showToast) {
            Toast.makeText(this, "CSV created, playa", Toast.LENGTH_SHORT).show();
            showToast = false;
        }

    }

    public void setScanQRCode(View v) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        generateCSV.setVisibility(View.VISIBLE);


        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            // handle scan result
            SharedPreferences.Editor SPOS = otherSettings.edit();

            switch (otherSettings.getInt("scannedID", 5)) {
                case 0:
                    SPOS.putString("csvMatch1", scanResult.getContents());
                    SPOS.commit();
                    break;
                case 1:
                    SPOS.putString("csvMatch2", scanResult.getContents());
                    SPOS.commit();
                    break;
                case 2:
                    SPOS.putString("csvMatch3", scanResult.getContents());
                    SPOS.commit();
                    break;
                case 3:
                    SPOS.putString("csvMatch4", scanResult.getContents());
                    SPOS.commit();
                    break;
                case 4:
                    SPOS.putString("csvMatch5", scanResult.getContents());
                    SPOS.commit();
                    break;
                case 5:
                    SPOS.putString("csvMatch6", scanResult.getContents());
                    SPOS.commit();
                    break;
                default:
                    SPOS.putString("csvMatch6", scanResult.getContents());
                    SPOS.commit();
            }

            SPOS.putInt("qrScanned", otherSettings.getInt("qrScanned", 0) + 1);
            SPOS.commit();
            SPOS.putInt("scannedID", otherSettings.getInt("scannedID", 5) + 1);
            SPOS.putBoolean("deleteData", true);
            SPOS.putBoolean("csVisible", true);
            SPOS.commit();


            if (otherSettings.getInt("scannedID", 6) == 6) {
                numDataSets.setTextSize(45);
                numDataSets.setText("Generate A CSV");
                scanQRCode.setVisibility(View.INVISIBLE);


            } else {
                numDataSets.setText(Integer.toString(otherSettings.getInt("scannedID", 6)));
            }

        }

    }
}

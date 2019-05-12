package com.nqc.tracuukaraoke;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FirstActivity extends AppCompatActivity {
    public static String DATABASE_NAME = "Arirang.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        doCoppyDatabse();
        Thread timeS=new Thread(){
            public void run()
            {
                try
                {
                    sleep(5000); // thoi gian chuyen quan man hinh khac
                } catch (Exception e) {

                }
                finally
                {
                   Intent intent= new Intent(FirstActivity.this,MainActivity.class);
                   startActivity(intent);
                }
            }
        };
        timeS.start();
    }
    // ket thuc man hinh dau tien, sau khi chuyen man hinh activity
    protected void onPause(){
        super.onPause();
        finish();
    }
    private void doCoppyDatabse() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Sao chep CSDL vao he thong thanh cong", Toast.LENGTH_LONG).show();


            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
    private void CopyDataBaseFromAsset() {
        try {
            InputStream myInput = getAssets().open(DATABASE_NAME);
            String outFileName = layDuongDanLuuTru();
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists()) {
                f.mkdir();
            }

            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();


        } catch (Exception e) {
            Log.e("Loi_SaoChep", e.toString());

        }
    }
    private String layDuongDanLuuTru() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }
}

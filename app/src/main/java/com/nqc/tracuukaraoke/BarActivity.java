/*
        Copyright 2018 Gaurav Kumar

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/
package com.nqc.tracuukaraoke;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.gauravk.audiovisualizer.visualizer.BarVisualizer;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nqc.animation.AudioPlayer;
import com.nqc.firebase.QuanKaraFirebase;
import com.nqc.firebase.SongFirebase;
import com.nqc.model.QuanKaraoke;
import com.nqc.tracuukaraoke.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static com.nqc.tracuukaraoke.ChiTietBaiHatActivity.song;


public class BarActivity extends AppCompatActivity {

    private BarVisualizer mVisualizer;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
    private AudioPlayer mAudioPlayer;
    public static String DATABASE_NAME = "Arirang.sqlite";
    String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        doCoppyDatabse();
        DongBoDuLieuFirebaseTask task = new DongBoDuLieuFirebaseTask();
        task.execute();
        mVisualizer = findViewById(R.id.bar);
        mAudioPlayer = new AudioPlayer();
        Thread timeS = new Thread() {
            public void run() {
                try {
                    sleep(3000); // thoi gian chuyen quan man hinh khac
                } catch (Exception e) {

                } finally {
                    Intent intent = new Intent(BarActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timeS.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopPlayingAudio();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startPlayingAudio(R.raw.sample);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayingAudio();
    }

    private void startPlayingAudio(int resId) {
        mAudioPlayer.play(this, resId, new AudioPlayer.AudioPlayerEvent() {
            @Override
            public void onCompleted() {
                if (mVisualizer != null)
                    mVisualizer.hide();
            }
        });
        int audioSessionId = mAudioPlayer.getAudioSessionId();
        if (audioSessionId != -1)
            mVisualizer.setAudioSessionId(audioSessionId);
    }

    private void stopPlayingAudio() {
        Intent intent = new Intent(BarActivity.this, MainActivity.class);
        startActivity(intent);
        if (mAudioPlayer != null)
            mAudioPlayer.stop();
        if (mVisualizer != null)
            mVisualizer.release();
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

    class DongBoDuLieuFirebaseTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<QuanKaraoke> karaokes = new ArrayList<>();
            mData.child("QuanKaraoke").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    QuanKaraFirebase quanKaraFirebase = dataSnapshot.getValue(QuanKaraFirebase.class);
                    if (quanKaraFirebase.getTonTai() == 0) {
                        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("TEN", quanKaraFirebase.getTen());
                        contentValues.put("URLHINH", quanKaraFirebase.getUrlHinh());
                        contentValues.put("GIO", quanKaraFirebase.getGioMoCua());
                        contentValues.put("DIACHI", quanKaraFirebase.getDiaChi());
                        contentValues.put("KINHDO", quanKaraFirebase.getKinhdo());
                        contentValues.put("VIDO", quanKaraFirebase.getVido());
                        contentValues.put("TONTAI", 1);
                        database.insert("QuanKaraoke", null, contentValues);
                        mData.child("QuanKaraoke").child(dataSnapshot.getKey()).child("tonTai").setValue(1);
                    }
                }


                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            mData.child("SongEdit").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
                    SongFirebase songFirebase = dataSnapshot.getValue(SongFirebase.class);
                    if (songFirebase.getDuyet() == 1) {
                        SendEmailTask task= new SendEmailTask();
                        task.execute(songFirebase.getEmail());
                        String sql = "UPDATE ArirangSongList SET LOIBH = '" + songFirebase.getLoiBaiHat() + "' WHERE MABH = " + songFirebase.getMaBH();
                        database.execSQL(sql);
                        String sql1 = "UPDATE ArirangSongList SET TACGIA = '" + songFirebase.getTacGia() + "' WHERE MABH = " + songFirebase.getMaBH();
                        database.execSQL(sql1);
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            return null;
        }
    }
    class SendEmailTask extends AsyncTask<String,Void,Void>{
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(String... strings) {
            final String username = "gocong360@gmail.com";
            final String password = "Cuongngan20151";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(strings[0]));
                message.setSubject("Testing Subject");
                message.setText("Dear Mail Crawler,"
                        + "\n\n No spam to my email, please!");

                Transport.send(message);

                System.out.println("Done");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }
}

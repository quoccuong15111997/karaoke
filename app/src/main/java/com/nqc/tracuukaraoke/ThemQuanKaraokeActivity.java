package com.nqc.tracuukaraoke;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nqc.constan.Const;
import com.nqc.firebase.QuanKaraFirebase;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class ThemQuanKaraokeActivity extends AppCompatActivity {
    DatabaseReference mData= FirebaseDatabase.getInstance().getReference();
    EditText edtName, edtDiaChi, edtGioLamViec, edtKinhdo, edtviDo;
    Button btnSave, btnBack;
    ImageView imgHinh;
    int REQUEST_CODE_IMAGE = 1;
    int REQUEST_CODE_IMAGE_STORAGE = 2;
    Bitmap bitmapCamera;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://tracuukaraoke-4a625.appspot.com/");
    String KEY="";
    String urlImage = "";
    QuanKaraFirebase quanKaraFirebase;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_quan_karaoke);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyLuuQuanKaraoke();
            }
        });
        imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyDoi();
            }
        });
    }

    private void xuLyLuuQuanKaraoke() {
        String ten=edtName.getText().toString();
        String dc=edtDiaChi.getText().toString();
        String gio=edtGioLamViec.getText().toString();
        String kd=edtKinhdo.getText().toString();
        String vd=edtviDo.getText().toString();
        if(!ten.equals("") && !dc.equals("") && !gio.equals("") && !kd.equals("") && !vd.equals("")){
            quanKaraFirebase= new QuanKaraFirebase(ten, Const.defaulURLImage,gio,dc,Double.parseDouble(vd),Double.parseDouble(kd),0);
            if (bitmapCamera==null){
                Toast.makeText(ThemQuanKaraokeActivity.this,"Vui lòng thêm ảnh",Toast.LENGTH_LONG).show();
            }
            else
                xuLyUpload();
        }
        else
            Toast.makeText(ThemQuanKaraokeActivity.this,"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
    }

    private void addControls() {
        edtName=findViewById(R.id.edtNameKaraoke);
        edtDiaChi=findViewById(R.id.edtDiaChi);
        edtGioLamViec=findViewById(R.id.edtGioLamViec);
        edtKinhdo=findViewById(R.id.edtKinhDo);
        edtviDo=findViewById(R.id.edtViDo);
        btnBack=findViewById(R.id.btnBack);
        btnSave=findViewById(R.id.btnLuuQuanKaraoke);
        imgHinh=findViewById(R.id.imgHinh);
        Picasso.with(ThemQuanKaraokeActivity.this).load(Const.defaulURLImage).into(imgHinh);
    }
    private void xuLyDoi() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ThemQuanKaraokeActivity.this);
        builder.setTitle("Ảnh từ");
        builder.setNegativeButton("Mở máy ảnh", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        }).setPositiveButton("Bộ sưu tập", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent,REQUEST_CODE_IMAGE_STORAGE);
            }
        }).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_IMAGE && resultCode == RESULT_OK && data != null) {
            bitmapCamera = (Bitmap) data.getExtras().get("data");
        }
        else if (requestCode == REQUEST_CODE_IMAGE_STORAGE && resultCode == RESULT_OK && data != null) {
            Uri uri=data.getData();
            String path=getRealPathFromURI(uri);
            bitmapCamera=getThumbnail(path);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    public Bitmap getThumbnail(String pathHinh)
    {
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathHinh, bounds);
        if ((bounds.outWidth == -1) || (bounds.outHeight == -1))
            return null;
        int originalSize = (bounds.outHeight > bounds.outWidth) ?
                bounds.outHeight
                : bounds.outWidth;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = originalSize / 500;
        return BitmapFactory.decodeFile(pathHinh, opts);
    }
    private void xuLyUpload() {
        progressDialog= new ProgressDialog(ThemQuanKaraokeActivity.this);
        progressDialog.setTitle("Đang xử lý");
        progressDialog.setMessage("Vui lòng chờ...");
        progressDialog.show();
        String child = removeAccent(quanKaraFirebase.getTen().trim());
        final StorageReference mountainsRef = storageRef.child(child);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapCamera.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            }
        });
        final StorageReference ref = storageRef.child(child);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    final Uri downloadUri = task.getResult();
                    urlImage=downloadUri.toString();
                    quanKaraFirebase.setUrlHinh(urlImage);
                    mData.child("QuanKaraoke").push().setValue(quanKaraFirebase, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            if (databaseError == null) {
                                //Toast.makeText(ThemQuanKaraokeActivity.this, "Lưu databse Thành công", Toast.LENGTH_SHORT).show();
                                Picasso.with(ThemQuanKaraokeActivity.this).load(urlImage).into(imgHinh);
                                progressDialog.dismiss();
                                android.app.AlertDialog.Builder alertDialog= new android.app.AlertDialog.Builder(ThemQuanKaraokeActivity.this);
                                alertDialog.setTitle("Lưu thành công, đang chờ phê duyệt");
                                alertDialog.setIcon(R.drawable.ic_ok);
                                alertDialog.setNegativeButton("OK",new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).show();
                            } else {
                                 //Toast.makeText(ThemQuanKaraokeActivity.this, "Lưu dadabase thất bại", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }
    private static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}

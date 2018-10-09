package ifrs.canoas.ifhelper;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ifrs.canoas.model.Course;

public class NotesActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rvNotes;
    private FloatingActionButton fab;
    private static final int CAMERA = 100;
    private static final int STORAGE = 200;
    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        setTitle("Anotações");

        rvNotes = (RecyclerView) findViewById(R.id.rvNotes);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        course = getIntent().getParcelableExtra("course");

        fab.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case STORAGE:
                if (grantResults.length == 2) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                            grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        onClick(fab);
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            Toast.makeText(this, "No data found.", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (requestCode) {
            case CAMERA:
                if (resultCode == RESULT_OK) {
                    if (data.getExtras() != null) {
                        Bitmap img = (Bitmap) data.getExtras().get("data");

                        System.out.println(saveToSd(img));
                    }
                }

                break;
        }
    }

    private String save(Bitmap img) {
        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());
        File directory = wrapper.getDir("imageDir", Context.MODE_PRIVATE);

        return save(img, directory);
    }

    private String saveToSd(Bitmap img) {
        File path = Environment.getExternalStorageDirectory();
        //File finalDirectory = new File(path, "IfHelper");

        return save(img, path);
    }

    private String save(Bitmap img, File directory) {
        File file = new File(directory, course.getFullname()+"-"+getDate()+".jpg");

        FileOutputStream stream = null;

        try {
            stream = new FileOutputStream(file);

            img.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return file.getAbsolutePath();
    }

    private String getDate() {
        Date d = new Date();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.getDefault());

        return df.format(d);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int readPerm = PermissionChecker.checkSelfPermission(
                            this, Manifest.permission.READ_EXTERNAL_STORAGE);
                    int writePerm = PermissionChecker.checkSelfPermission(
                            this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    if (readPerm == PermissionChecker.PERMISSION_GRANTED
                            && writePerm == PermissionChecker.PERMISSION_GRANTED) {
                        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA);
                    } else {
                        ActivityCompat.requestPermissions(
                                this, new String[]{
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                                }, STORAGE);
                    }
                }
                break;
        }
    }
}

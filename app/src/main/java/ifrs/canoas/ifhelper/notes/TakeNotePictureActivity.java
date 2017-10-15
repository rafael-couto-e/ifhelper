package ifrs.canoas.ifhelper.notes;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URLDecoder;

import ifrs.canoas.ifhelper.DefaultActivity;
import ifrs.canoas.ifhelper.R;

public class TakeNotePictureActivity extends DefaultActivity {

    public final static int RETRATO = 1;//Constante para request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_note_picture);
        setToolbar();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void tirarFoto(View view){
        Intent intent =
                new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, RETRATO);
    }



    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent intentReturn){
        //if(requestCode == RETRATO)...
        if(intentReturn != null){
            Bundle bundle = intentReturn.getExtras();
            if(bundle != null){
                Bitmap img = (Bitmap) bundle.get("data");


                saveToInternalStorage(img);

                ImageView iv = (ImageView) findViewById(R.id.verNotaIV);
                iv.setImageBitmap(img);
            }
        }
    }

    //Ver link https://developer.android.com/training/basics/data-storage/files.html?hl=pt-br
    private String saveToSD(Bitmap bitmapImage){
        File sd = Environment.getExternalStorageDirectory();
        return save(bitmapImage, sd);
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // para pegar o path de instalação to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        return save(bitmapImage, directory);
    }

    private String save(Bitmap bitmapImage, File directory){

        // Create imageDir
        //TODO Gerar nome a partir de um inser em banco
        File mypath = new File(directory, "profile.jpg");//Gerar nome a partir

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    private Uri getImage(){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // para pegar o path de instalação to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File file = new File(directory, "profile.jpg");
        Log.d("oi", file.getAbsolutePath());
        return Uri.parse(file.getAbsolutePath());
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.compartilharBT).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //T.
        int id = item.getItemId();
        Log.d("oi", ""+id);
        if(id == R.id.compartilharBT){
            compartilha();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //ADD Botao Compartilhar
    //Saiba + https://developer.android.com/training/sharing/send.html
    private void compartilha(){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
       // shareIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        shareIntent.putExtra(Intent.EXTRA_STREAM, getImage());
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
    }

}

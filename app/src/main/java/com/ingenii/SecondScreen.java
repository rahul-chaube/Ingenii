package com.ingenii;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.florent37.materialtextfield.MaterialTextField;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import info.hoang8f.widget.FButton;

public class SecondScreen extends AppCompatActivity {
    EditText name, company, designation, companyLogo, url;
    String strName, strCompany, strDesignation, strLogo, strUrl;
    boolean allFilled = true;
    FButton fButton;
    RelativeLayout relativeLayout;
    Uri captureImageUri, selectedImageUri;
    CircleImageView image;
    String imageUrl1,logoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        relativeLayout = (RelativeLayout) findViewById(R.id.editProfilePic);
        name = (EditText) findViewById(R.id.userName);
        company = (EditText) findViewById(R.id.company);
        designation = (EditText) findViewById(R.id.designation);
        companyLogo = (EditText) findViewById(R.id.companyLogo);
        url = (EditText) findViewById(R.id.url);
        companyLogo.setEnabled(false);
        fButton = (FButton) findViewById(R.id.done);
        image = (CircleImageView) findViewById(R.id.imgProfileView);
        if (!Pref.getProfile(this).isEmpty()) {
            image.setImageURI(Uri.parse(Pref.getProfile(this)));

            if(image.getDrawable() == null) image.setImageResource(R.drawable.profile);
        }
        initUI();
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allFilled = !name.getText().toString().isEmpty();

                allFilled = !company.getText().toString().isEmpty();

                if (designation.getText().toString().isEmpty())
                    allFilled = false;
                else if (companyLogo.getText().toString().isEmpty())
                    allFilled = false;
                else if (url.getText().toString().isEmpty())
                    allFilled = false;

                if (allFilled) {
                    save();
                } else
                    Toast.makeText(SecondScreen.this, "all field are mandotaory", Toast.LENGTH_SHORT).show();

            }
        });
        final MaterialTextField mLogo= (MaterialTextField) findViewById(R.id.mLogo);
        mLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(
                        Intent.createChooser(intent, "Select File"),
                        202);
                mLogo.expand();
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker();
            }
        });
        companyLogo.setSingleLine();
        companyLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SecondScreen.this, "Hello", Toast.LENGTH_SHORT).show();

            }
        });
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Now user should be able to use camera
                dispatchTakePictureIntent();
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                            5);
                }
                // Your app will not have this permission. Turn off all functions
                // that require this permission or it will force close like your
                // original question
            }
        }
    }

    private void dispatchTakePictureIntent() {
        if( ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        5);
            }
        }
        else {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //result ok

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 201: {
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
//                        image.setImageBitmap(selectedImage);
                        uploadImage(201, imageUri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(SecondScreen.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                        break;
                }
                case REQUEST_IMAGE_CAPTURE: {
                    final Uri imageUri = data.getData();

//                    Bundle extras = data.getExtras();
//                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    try {
                        uploadImage(200, imageUri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Some Thing Went Wrong ", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case 202:
                {
                    final Uri imageUri = data.getData();
                    logoUrl=imageUri.toString();
                    companyLogo.setText(logoUrl);
                    break;
                }
            }
        } else {
            Toast.makeText(SecondScreen.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }


    }

    private void uploadImage(int requestCode, Uri imageUri) throws FileNotFoundException {
        imageUrl1 = imageUri.toString();
         image.setImageURI(imageUri);
//
//        Uri imageUri12=null;
//        try {
//            imageUri12 = Uri.parse(Uri.decode(imageUri.toString()));
//        } catch (IllegalArgumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        image.setImageURI(imageUri12);
// /        Log.e("Image Url", imageUri.toString());
//        Glide.with(this)
//                .load(selectedImage).centerCrop()
//                .placeholder(R.drawable.profile)
//                .error(R.drawable.profile)
//                .into(image);

    }

    public String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            // Source is Dropbox or other similar local file path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private void imagePicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SecondScreen.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.image_picker_popup, null);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        builder.setTitle("Add Photo!");
        ((TextView) dialogView.findViewById(R.id.camera)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ContentValues values = new ContentValues();
//                values.put(MediaStore.Images.Media.TITLE, "New Picture");
//                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
//                captureImageUri = getContentResolver().insert(
//                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                long currentTime = System.currentTimeMillis();
//                Date date = new Date(currentTime);
//
//                SimpleDateFormat sdf = new SimpleDateFormat("MMM_d_yyyy", Locale.US);
//                String currentDate = sdf.format(date);
//                File rootPath = new File(Environment.getExternalStorageDirectory(), "ingenii");
//                if (!rootPath.exists()) {
//                    rootPath.mkdirs();
//                }
//                File image = new File(rootPath, "ingenii" + currentDate + "_" + currentTime / 1000 + ".jpg");
//                captureImageUri = FileProvider.getUriForFile(SecondScreen.this, getApplicationContext().getPackageName() + ".provider", image);
//
////                Debug.e("captured", captureImageUri.toString());
//
//
//                List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//                for (ResolveInfo resolveInfo : resInfoList) {
//                    String packageName = resolveInfo.activityInfo.packageName;
//                    grantUriPermission(packageName, captureImageUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                }
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, captureImageUri);
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                startActivityForResult(intent, 200);
                dispatchTakePictureIntent();
                alertDialog.dismiss();
            }
        });
        ((TextView) dialogView.findViewById(R.id.gallery)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(
                        Intent.createChooser(intent, "Select File"),
                        201);
                alertDialog.dismiss();
            }
        });

        ((TextView) dialogView.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    void initUI() {
        MaterialTextField mName = (MaterialTextField) findViewById(R.id.mname);
        MaterialTextField mDesi = (MaterialTextField) findViewById(R.id.mdesignation);
        MaterialTextField mCompany = (MaterialTextField) findViewById(R.id.mCompany);
        MaterialTextField mLogo = (MaterialTextField) findViewById(R.id.mLogo);
        MaterialTextField mUrl = (MaterialTextField) findViewById(R.id.mUrl);


        if (!Pref.getName(this).isEmpty()) {
            mName.expand();
            name.setText(Pref.getName(this));
        }
        if (!Pref.getCompany(this).isEmpty()) {
            mCompany.expand();
            company.setText(Pref.getCompany(this));
        }
        if (!Pref.getUrl(this).isEmpty()) {
            mUrl.expand();
            url.setText(Pref.getUrl(this));
        }
        if (!Pref.getDesignation(this).isEmpty()) {
            mDesi.expand();
            designation.setText(Pref.getDesignation(this));
        }
        if (!Pref.getLogo(this).isEmpty()) {
            mLogo.expand();
            companyLogo.setText(Pref.getLogo(this));
        }

    }

    void save() {
        Pref.setProfile(this, imageUrl1);
        Pref.setCompanyLogo(this, logoUrl);
        Pref.setName(this, name.getText().toString());
        Pref.setDesignation(this, designation.getText().toString());
        Pref.setCompany(this, company.getText().toString());
        Pref.setLogo(this, companyLogo.getText().toString());
        Pref.setUrl(this, url.getText().toString());

        Toast.makeText(this, " Data Saved ", Toast.LENGTH_SHORT).show();
        finish();
    }
}

package com.deepak.utacommunityservice;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class CreateEventActivity extends Activity implements View.OnClickListener {

    EditText etTitle,etDescription,etEventDate,etEventTime,etEventImage;
    ImageView ivDate, ivTime,ivUploadImage;
    TextView tvCreateAnEvent;
    Calendar mCalendar;
    byte[] dataBitMap = null;
    DBHelper mDbHelper = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_event_create);

        mDbHelper           = new DBHelper(this);
        mCalendar           = Calendar.getInstance();
        etTitle             = findViewById(R.id.etTitle);
        etDescription       = findViewById(R.id.etDescription);
        etEventDate         = findViewById(R.id.etEventDate);
        etEventTime         = findViewById(R.id.etEventTime);
        etEventImage        = findViewById(R.id.etEventImage);
        ivDate              = findViewById(R.id.ivDate);
        ivTime              = findViewById(R.id.ivTime);
        ivUploadImage       = findViewById(R.id.ivUploadImage);
        tvCreateAnEvent     = findViewById(R.id.tvCreateAnEvent);

        ivDate.setOnClickListener(this);
        ivTime.setOnClickListener(this);
        ivUploadImage.setOnClickListener(this);
        tvCreateAnEvent.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ivDate:
                    openDateDialog();
                break;
            case R.id.ivTime:
                    openTimeDialog();
                break;
            case R.id.ivUploadImage:
                    openGallery();
                break;
            case R.id.tvCreateAnEvent:

                String eventTitle = etTitle.getText().toString();
                String eventDescription = etDescription.getText().toString();
                String eventDate = etEventDate.getText().toString();
                String eventTime = etEventTime.getText().toString();
                String eventImage = etEventImage.getText().toString();

                if (eventTitle.isEmpty()) {
                    Toast.makeText(this,"Please Enter Event Title",Toast.LENGTH_SHORT).show();
                } else if (eventDescription.isEmpty()) {
                    Toast.makeText(this,"Please Enter Event Description",Toast.LENGTH_SHORT).show();
                } else if (eventDate.isEmpty()) {
                    Toast.makeText(this,"Please Enter Event Date",Toast.LENGTH_SHORT).show();
                } else if (eventTime.isEmpty()) {
                    Toast.makeText(this,"Please Enter Event Time",Toast.LENGTH_SHORT).show();
                } else if (eventImage.isEmpty()) {
                    Toast.makeText(this,"Please Enter Event Image",Toast.LENGTH_SHORT).show();
                } else {

                    Event mEvent = new Event();
                    mEvent.setEventTitle(eventTitle);
                    mEvent.setEventDescription(eventDescription);
                    mEvent.setEventDate(eventDate);
                    mEvent.setEventTime(eventTime);
                    mEvent.setEventImage(dataBitMap);

                    long id = mDbHelper.insertEventDetails(mEvent);

                    if (id != -1){
                        Toast.makeText(this,"Event is Created is Successfully",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this,"Event is Not Created ",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void openGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                if (bitmap != null){
                    dataBitMap = getBitmapAsByteArray(bitmap);
                    etEventImage.setText("Image is Saved");
                }

            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void openTimeDialog() {
        TimePickerDialog mTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String selectedTime = ""+hourOfDay + ":" + minute;
                etEventTime.setText(""+selectedTime);
            }
        },mCalendar.get(Calendar.HOUR),mCalendar.get(Calendar.MINUTE),true);
        mTimePickerDialog.show();
    }

    private void openDateDialog() {

        DatePickerDialog mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Integer monthData = month + 1;
                String selectedDate = dayOfMonth + "/" + monthData +"/" + year;
                etEventDate.setText(""+selectedDate);
            }
        },mCalendar.get(Calendar.YEAR),mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.show();
    }


    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
}

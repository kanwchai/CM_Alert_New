package com.saikaew_rus.cm_alert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class A_AboutRus extends AppCompatActivity {

    TextView title, uni, faculty, branch, name1, email1, name2, email2;
    ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_rus);
        this.setTitle(R.string.title_about);

        setLayout();
        setValue();
    }

    public void setLayout() {
        imgLogo = (ImageView) findViewById(R.id.imageView9);

        title = (TextView) findViewById(R.id.textView14);
        uni = (TextView) findViewById(R.id.textView15);
        faculty = (TextView) findViewById(R.id.textView16);
        branch = (TextView) findViewById(R.id.textView17);
        name1 = (TextView) findViewById(R.id.textView18);
        email1 = (TextView) findViewById(R.id.textView19);
        name2 = (TextView) findViewById(R.id.textView20);
        email2 = (TextView) findViewById(R.id.textView21);
    }

    public void setValue() {
        imgLogo.setBackgroundResource(A_Word_App.about_logo[A_Word_App.language]);

        title.setText(A_Word_App.about_title[A_Word_App.language]);
        uni.setText(A_Word_App.about_uni_name[A_Word_App.language]);
        faculty.setText(A_Word_App.about_uni_faculty[A_Word_App.language]);
        branch.setText(A_Word_App.about_uni_branch[A_Word_App.language]);
        name1.setText(A_Word_App.about_name_1[A_Word_App.language]);
        email1.setText(A_Word_App.about_email_1[A_Word_App.language]);
        name2.setText(A_Word_App.about_name_2[A_Word_App.language]);
        email2.setText(A_Word_App.about_email_2[A_Word_App.language]);
    }
}

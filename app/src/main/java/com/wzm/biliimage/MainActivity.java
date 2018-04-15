package com.wzm.biliimage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ImageView mImage;
    private EditText mEdit;
    private TextView mText;
    private Button mBt_find;
    private Button mBt_save;
    private Button mBt_http;
    private Button mBt_del;
    private Button mBt_cls;
    private Button mBt_0;
    private Button mBt_1;
    private Button mBt_2;
    private Button mBt_3;
    private Button mBt_4;
    private Button mBt_5;
    private Button mBt_6;
    private Button mBt_7;
    private Button mBt_8;
    private Button mBt_9;

    private static final String Bilibili = "https://m.bilibili.com/video/";
    private static final String ECODING = "UTF-8";
    private String Number;
    private String HTML;
    private String webUrl;
    private String imgUrl;
    private Bitmap bitmap;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (imgUrl == null) {
                Toast.makeText(getApplicationContext(), "抱歉，未找到",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            mImage.setImageBitmap(bitmap);
            mText.setText("URL:" + "\n" + "\n");
            SpannableString url = new SpannableString(imgUrl);
            url.setSpan(new URLSpan(imgUrl), 0, url.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mText.append(url);
            mText.setMovementMethod(new LinkMovementMethod());
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();

    }

    private void initView() {
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mImage = (ImageView) findViewById(R.id.img_show);
        mEdit = (EditText) findViewById(R.id.et_input);
        mText = (TextView) findViewById(R.id.tv_link);
        mBt_find = (Button) findViewById(R.id.bt_find);
        mBt_save = (Button) findViewById(R.id.bt_save);
        mBt_http = (Button) findViewById(R.id.bt_http);
        mBt_del = (Button) findViewById(R.id.bt_del);
        mBt_cls = (Button) findViewById(R.id.bt_cls);
        mBt_0 = (Button) findViewById(R.id.bt_0);
        mBt_1 = (Button) findViewById(R.id.bt_1);
        mBt_2 = (Button) findViewById(R.id.bt_2);
        mBt_3 = (Button) findViewById(R.id.bt_3);
        mBt_4 = (Button) findViewById(R.id.bt_4);
        mBt_5 = (Button) findViewById(R.id.bt_5);
        mBt_6 = (Button) findViewById(R.id.bt_6);
        mBt_7 = (Button) findViewById(R.id.bt_7);
        mBt_8 = (Button) findViewById(R.id.bt_8);
        mBt_9 = (Button) findViewById(R.id.bt_9);

    }

    private void initData() {
        setSupportActionBar(mToolbar);

    }

    private void initListener() {
        mBt_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImage.setImageBitmap(null);
                mText.setText(null);
                HTML = null;
                imgUrl = null;
                bitmap = null;
                Number = mEdit.getText().toString();
                getImg();
            }
        });

        mBt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyStoragePermissions();
                saveBitmap(bitmap);
            }
        });
        mBt_http.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(webUrl))
                    return;
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                Uri u = Uri.parse(webUrl);
                i.setData(u);
                startActivity(i);
            }
        });
        mBt_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mEdit.getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    mEdit.setText(s.substring(0, s.length() - 1));
                    mEdit.setSelection(s.length() - 1);
                }
            }
        });
        mBt_cls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mEdit.getText().toString();
                mImage.setImageBitmap(null);
                mEdit.setText(null);
                mText.setText(null);
                HTML = null;
                imgUrl = null;
                bitmap = null;
            }
        });
        mBt_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdit.append("0");
            }
        });
        mBt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdit.append("1");
            }
        });
        mBt_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdit.append("2");
            }
        });
        mBt_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdit.append("3");
            }
        });
        mBt_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdit.append("4");
            }
        });
        mBt_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdit.append("5");
            }
        });
        mBt_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdit.append("6");
            }
        });
        mBt_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdit.append("7");
            }
        });
        mBt_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdit.append("8");
            }
        });
        mBt_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdit.append("9");
            }
        });
    }


    private void getImg() {
        if (Number.length() < 5) {
            Toast.makeText(this, "输入错误", Toast.LENGTH_SHORT).show();
            return;
        }
        webUrl = Bilibili + "av" + Number + ".html";
        new Thread() {
            public void run() {

                try {
                    HTML = getHTML(webUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                if (HTML != null && !HTML.equals("")) {
                    try {
                        imgUrl = getImageSrc(HTML);
                        URL uri = new URL(imgUrl);
                        InputStream in = uri.openStream();
                        bitmap = BitmapFactory.decodeStream(in);
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(0);
                }
            }
        }.start();

    }

    public void saveBitmap(Bitmap bm) {
        if (bm == null) {
            Toast.makeText(this, "图片为空", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String SAVE_PIC_PATH = Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)
                    ? Environment.getExternalStorageDirectory().getAbsolutePath() : "/mnt/sdcard";//保存到SD卡
            String SAVE_REAL_PATH = SAVE_PIC_PATH + "/BiliImage/";//保存的确切位置

            File foder = new File(SAVE_REAL_PATH);
            if (!foder.exists()) {
                foder.mkdirs();
            }
            File f = new File(SAVE_REAL_PATH, "Av" + Number + ".png");
            if (!f.exists()) {
                f.createNewFile();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
            bm.compress(Bitmap.CompressFormat.PNG, 90, bos);
            bos.flush();
            bos.close();
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(f);
            intent.setData(uri);
            this.sendBroadcast(intent);
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void verifyStoragePermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                }
            }
        }
    }


    private String getHTML(String url) throws Exception {
        URL uri = new URL(url);
        URLConnection connection = uri.openConnection();
        InputStream in = connection.getInputStream();
        byte[] buf = new byte[1024];
        StringBuffer sb = new StringBuffer();
        while ((in.read(buf, 0, buf.length)) > 0) {
            sb.append(new String(buf, ECODING));
        }
        in.close();
        return sb.toString();
    }

    private String getImageSrc(String htmlCode) {
        String s = null;
        Log.d("AAA", htmlCode);
        Pattern p = Pattern.compile("og:image.+?content=\"(.*?)\"");
        Matcher m = p.matcher(htmlCode);
        if (m.find()) {
            s = m.group(1);
            String[] ary1 = s.split("@");
            s = ary1[0];
            return s;
        }
        return s;

    }
}

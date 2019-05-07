package com.github.kk.activityresultdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.kk.activityresult.ActivityResult;
import com.github.kk.activityresult.ActivityResultCallback;
import com.github.kk.activityresult.ActivityResultInfo;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tv = findViewById(R.id.txt);

        findViewById(R.id.rxjava).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ActivityResult.in(MainActivity.this)
                        .startResult(SecondActivity.class)
                        .subscribe(new Observer<ActivityResultInfo>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ActivityResultInfo activityResultInfo) {
                                Intent intent = activityResultInfo.getData();
                                int resultCode = activityResultInfo.getResultCode();
                                if (resultCode == RESULT_OK) {
                                    String data = intent.getStringExtra("data");
                                    tv.setText("rxjava-》" + data);

                                    Snackbar.make(view, "返回数据->" + data, Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
            }
        });

        findViewById(R.id.callback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                ActivityResult.in(MainActivity.this)
                        .startResult(SecondActivity.class, new ActivityResultCallback() {
                            @Override
                            public void onResult(ActivityResultInfo activityResultInfo) {
                                Intent intent = activityResultInfo.getData();
                                int resultCode = activityResultInfo.getResultCode();
                                if (resultCode == RESULT_OK) {
                                    String data = intent.getStringExtra("data");
                                    tv.setText("callback-》" + data);

                                    Snackbar.make(view, "返回数据->" + data, Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

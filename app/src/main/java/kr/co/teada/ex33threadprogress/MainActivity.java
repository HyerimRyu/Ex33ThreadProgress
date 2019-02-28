package kr.co.teada.ex33threadprogress;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    ProgressDialog dialog;

    int gauge=0; //막대바 프로그래스의 값:로딩하면 쭉쭉 늘어나게

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickBtn(View view) {

        //널이 아니라는 것--> 다이얼로그가 있으면 만들지마!!!!!!
        if(dialog!=null) return;

        //wheel type progress dialog
        dialog=new ProgressDialog(this);
        dialog.setTitle("wheel dialog");
        dialog.setMessage("downloading....");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        //3초 뒤 다이얼로그 종료
        handler.sendEmptyMessageDelayed(0,3000);
    }

    Handler handler =new Handler(){
        //sendMessage()하면 자동으로 호출되는 메소드
        //딜레이를 줬기 때문에 3초 후 실행되는 메소드

        @Override
        public void handleMessage(Message msg) {
            dialog.dismiss();
            dialog=null; //null을 줘야 참조가 끊어져
        }
    };

    public void clickBtn2(View view) {

        if(dialog!=null) return; //혹시 다이얼로그가 널이 아니라면 띄우지마--> 기존것 있으니까 만들지마

        //막대바 형태의 다이얼로그
        dialog=new ProgressDialog(this);
        dialog.setTitle("막대바 다이얼로그");
        dialog.setMessage("다운로딩 중....");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        dialog.setMax(100); //기본값 : 100

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        dialog.setProgress(gauge);

        //상태바를 진행시키는 별도의 스레드 실행--> 만들면서 능력치 바꿔 (){}열고 {}안에 넣어
        new Thread(){
            @Override
            public void run() {
                gauge=0;

                while (gauge<100){
                    gauge++;
                    dialog.setProgress(gauge);
                    //0.05초 ..
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                dialog.dismiss();
                dialog=null;
            }
        }.start();
    }
}

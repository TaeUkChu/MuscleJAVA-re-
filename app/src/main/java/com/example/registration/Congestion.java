package com.example.registration;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Congestion {

    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    TextView TextView;
    int percent;

    Congestion(TextView TextView,  ImageView imageView, ImageView imageView2, ImageView imageView3, ImageView imageView4, ImageView imageView5, int percent) {
        TextView = this.TextView;
        imageView = this.imageView;
        imageView2= this.imageView2;
        imageView3= this.imageView3;
        imageView4= this.imageView4;
        imageView5= this.imageView5;
        percent = this.percent;
    }
    void show(){
        int sum = 10 - percent/10;
        int temp = percent/20;
        switch (temp) {
            case 0:
                imageView.setVisibility(View.VISIBLE);
                imageView2.setVisibility(View.INVISIBLE);
                imageView3.setVisibility(View.INVISIBLE);
                imageView4.setVisibility(View.INVISIBLE);
                imageView5.setVisibility(View.INVISIBLE);
                TextView.setText("["+ sum + "명 / 10명] \n" +"최대 수용:"+ percent +"%");
                TextView.setTextColor(Color.parseColor("#ffcc0000"));
                break;
            case 1:
                imageView.setVisibility(View.INVISIBLE);
                imageView2.setVisibility(View.VISIBLE);
                imageView3.setVisibility(View.INVISIBLE);
                imageView4.setVisibility(View.INVISIBLE);
                imageView5.setVisibility(View.INVISIBLE);
                break;
            case 2:
                imageView.setVisibility(View.INVISIBLE);
                imageView2.setVisibility(View.INVISIBLE);
                imageView3.setVisibility(View.VISIBLE);
                imageView4.setVisibility(View.INVISIBLE);
                imageView5.setVisibility(View.INVISIBLE);
                break;
            case 3:
                imageView.setVisibility(View.INVISIBLE);
                imageView2.setVisibility(View.INVISIBLE);
                imageView3.setVisibility(View.INVISIBLE);
                imageView4.setVisibility(View.VISIBLE);
                imageView5.setVisibility(View.INVISIBLE);
                break;
            case 4:
                imageView.setVisibility(View.INVISIBLE);
                imageView2.setVisibility(View.INVISIBLE);
                imageView3.setVisibility(View.INVISIBLE);
                imageView4.setVisibility(View.INVISIBLE);
                imageView5.setVisibility(View.VISIBLE);
                break;
        }
    }
}

 /*             테스트 코드
                // 임의 수정값, 새로고침 버튼 누를때마다 5단계 순차적으로 보여줌
                index += 1;
                if (index > 4) {
                    index = 0;
                }
                if (index == 0) {
                    imageView.setVisibility(View.VISIBLE);
                    imageView5.setVisibility(View.INVISIBLE);
                    TextView.setText("[95명/100]");
                    TextView.setTextColor(Color.parseColor("#ffcc0000"));

                } else if (index == 1) {
                    imageView2.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                    TextView.setText("[75명/100]");
                    TextView.setTextColor(Color.parseColor("#ffff8800"));

                } else if (index == 2) {
                    imageView3.setVisibility(View.VISIBLE);
                    imageView2.setVisibility(View.INVISIBLE);
                    TextView.setText("[50명/100]");
                    TextView.setTextColor(Color.parseColor("#ffffbb33"));

                } else if (index == 3) {
                    imageView4.setVisibility(View.VISIBLE);
                    imageView3.setVisibility(View.INVISIBLE);
                    TextView.setText("[35명/100]");
                    TextView.setTextColor(Color.parseColor("#ff99cc00"));

                } else if (index == 4) {
                    imageView5.setVisibility(View.VISIBLE);
                    imageView4.setVisibility(View.INVISIBLE);
                    TextView.setText("[10명/100]");
                    TextView.setTextColor(Color.parseColor("#ff669900"));

                }
            }
        }); */
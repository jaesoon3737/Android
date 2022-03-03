package soo.app.ab2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {

    ViewFlipper flipper;
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 이미지들이 담긴 컨테이너
        flipper = (ViewFlipper) findViewById(R.id.view_flipper);
        flipper.setOnTouchListener(new View.OnTouchListener() {
            float xAtDown = 0.0f;
            float xAtUp = 0.0f;
            public boolean onTouch(View v, MotionEvent event) {
                // 터치 이벤트가 일어난 뷰가 ViewFlipper가 아니면 return
                if(v != flipper) return false;

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    xAtDown = event.getX(); // 터치 시작지점 x좌표 저장
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    xAtUp = event.getX(); 	// 터치 끝난지점 x좌표 저장

                    if( xAtUp < xAtDown ) {
                        // 왼쪽 방향 에니메이션 지정
                        flipper.setInAnimation(AnimationUtils.loadAnimation(
                                MainActivity.this,
                                R.anim.push_left_in));
                        flipper.setOutAnimation(AnimationUtils.loadAnimation(
                                MainActivity.this,
                                R.anim.push_left_out));

                        // 다음 view 보여줌
                        flipper.showNext();
                    }
                    else if (xAtUp > xAtDown){
                        // 오른쪽 방향 에니메이션 지정
                        flipper.setInAnimation(AnimationUtils.loadAnimation(
                                MainActivity.this,
                                R.anim.push_right_in));
                        flipper.setOutAnimation(AnimationUtils.loadAnimation(
                                MainActivity.this,
                                R.anim.push_right_out));
                        // 전 view 보여줌
                        flipper.showPrevious();
                    }
                }
                return true;
            }
        });


        checkBox = (CheckBox)findViewById(R.id.chkAuto);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true) {
                    // 왼쪽 에니메이션 설정
                    flipper.setInAnimation(AnimationUtils.loadAnimation(
                            MainActivity.this,
                            R.anim.push_left_in));
                    flipper.setOutAnimation(AnimationUtils.loadAnimation(
                            MainActivity.this,
                            R.anim.push_left_out));

                    // 자동 Flipping 시작 (간격 3초)
                    flipper.setFlipInterval(3000);
                    flipper.startFlipping();
                }
                else {
                    // 자동 Flipping 해지
                    flipper.stopFlipping();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

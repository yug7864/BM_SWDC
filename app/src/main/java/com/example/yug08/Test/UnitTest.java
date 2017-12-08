package com.example.yug08.Test;

import android.util.Log;

import static com.example.yug08.BM_SWDC_yslee.IoTUtil.IoTUtil.intToState;
import static com.example.yug08.BM_SWDC_yslee.IoTUtil.IoTUtil.steteToInt;

/**
 * Created by yug08 on 2017-12-08.
 */


/* 단위 테스트 자동화 클레스 입니다. 1~4번 단위 테스트는 각 함수의 로그를 찍어 확인하였습니다. */

public class UnitTest {

    public void UnitTest5(){

        /* UnitTest 5 */
         if(steteToInt(true) == 1){
            Log.d("UnitTest 5_1"," 통과 입니다! " );
         }else {
            Log.d("UnitTest 5_1"," 불통과 입니다! " );
         }

        if(steteToInt(false) == 0){
            Log.d("UnitTest 5_2"," 통과 입니다! " );
        }else {
            Log.d("UnitTest 5_2"," 불통과 입니다! " );
        }

    }

    public void UnitTest6(){

        /* UnitTest 6 */
        if(intToState(1)){
            Log.d("UnitTest 6_1"," 통과 입니다! " );
        }else {
            Log.d("UnitTest 6_1"," 불통과 입니다! " );
        }

        if(intToState(0)){
            Log.d("UnitTest 6_2"," 불통과 입니다! " );
        }else {
            Log.d("UnitTest 6_2"," 통과 입니다! " );
        }
    }

}

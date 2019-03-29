package com.myLoan.br.base;

import android.content.Context;

import com.myLoan.br.Constant;
import com.tencent.mmkv.MMKV;

/**
 * @author wzx
 * @version 3.0.0 2018/11/19 11:55
 * @update wzx 2018/11/19 11:55
 * @updateDes
 * @include {@link }
 * @used {@link }
 */
public class MMkvInfo {

    private volatile static MMkvInfo MMkvInfo;
    private volatile static MMKV mMkv;
    private Context context;

    public static MMkvInfo getInstance() {
        return MMkvInfo;
    }

    private MMkvInfo(Context context) {
        this.context = context;
        //MMKV kv = MMKV.defaultMMKV();
        mMkv = MMKV.mmkvWithID("myloan_mmkvId", MMKV.SINGLE_PROCESS_MODE);
    }

    public static void init(Context context) {
        MMkvInfo = new MMkvInfo(context);
    }

    public boolean isThisVersionHasStartup() {
        return mMkv.decodeBool(Constant.SP_VERSION_FIRST_START);
    }

    public void saveThisVersionHasStartup() {
        mMkv.encode(Constant.SP_VERSION_FIRST_START, true);
    }


}

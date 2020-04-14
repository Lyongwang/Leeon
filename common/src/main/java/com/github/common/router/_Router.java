package com.github.common.router;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.github.annotation.router.RouterTable;

/**
 * Created by Lyongwang on 2020/4/13 18: 29.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class _Router implements IRouter {
    private static final String TAG = _Router.class.getSimpleName();
    private String uri;

    private  _Router() {
    }

    @Override
    public void go(Context context) {
        Intent intent = getIntent(context);
        if (intent == null){
            Log.i(TAG, "route to target error: " + uri);
        } else {
            context.startActivity(intent);
        }
    }

    private Intent getIntent(Context context) {
        Class<?> targetCls = RouterTable.get(uri);
        if (targetCls == null) {
            return null;
        } else {
            return new Intent(context, targetCls);
        }
    }

    private static class SingletonHolder{
        private static final _Router INSTANCE = new _Router();
    }

    static _Router getInstance(){
        return SingletonHolder.INSTANCE;
    }

    IRouter setUri(String uri) {
        this.uri = uri;
        return this;
    }
}

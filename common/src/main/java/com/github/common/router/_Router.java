package com.github.common.router;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.github.annotation.router.RouterTable;

import java.util.Map;
import java.util.Set;

/**
 * Created by Lyongwang on 2020/4/13 18: 29.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class _Router implements IRouter {
    private static final String              TAG = _Router.class.getSimpleName();
    private              String              uri;
    private              Map<String, Object> params;

    private _Router() {
    }

    @Override
    public void go(Context context) {
        Intent intent = getIntent(context);
        if (intent == null) {
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
            Intent intent = new Intent(context, targetCls);
            addIntentParams(intent);
            return intent;
        }
    }

    private void addIntentParams(Intent intent) {
        if (params == null || params.isEmpty()) {
            return;
        }
        Set<Map.Entry<String, Object>> entries = params.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            String param = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Double){
                intent.putExtra(param, (Double) value);
            } else if (value instanceof Float){
                intent.putExtra(param, (Float) value);
            } else if (value instanceof Long){
                intent.putExtra(param, (Long) value);
            } else if (value instanceof Integer){
                intent.putExtra(param, (Integer) value);
            } else if (value instanceof Boolean){
                intent.putExtra(param, (Boolean) value);
            } else {
                intent.putExtra(param, (String) value);
            }

        }
    }


    private static class SingletonHolder {
        private static final _Router INSTANCE = new _Router();
    }

    static _Router getInstance() {
        return SingletonHolder.INSTANCE;
    }

    IRouter setUri(String uri, Map<String, Object> params) {
        this.uri = uri;
        this.params = params;
        return this;
    }
}

package com.github.leeon.utils;

import android.content.Context;

import java.io.File;

import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Created by Lyongwang on 2020/11/28 15: 34.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class FileUtils {
    public static boolean copyFileFromAssets(Context context, String sourceFileName, String destFile) {
        File filePath = new File(destFile.substring(0, destFile.lastIndexOf(File.separator)));
        File file = new File(destFile);
        if (!filePath.exists()){
            filePath.mkdirs();
        }
        try(Source source = Okio.source(context.getAssets().open(sourceFileName));
            BufferedSink buffer = Okio.buffer(Okio.sink(file))) {
            buffer.writeAll(source);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

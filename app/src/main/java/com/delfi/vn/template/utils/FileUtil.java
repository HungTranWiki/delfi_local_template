package com.delfi.vn.template.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by hdadmin on 2/7/2017.
 */

public class FileUtil implements FileFilter {

    /**
     * Get a usable cache directory (external if available, internal otherwise).
     *
     * @param context The context to use
     * @param uniqueName A unique directory name to append to the cache dir
     * @return The cache dir
     */

    public  static  final String TAG = FileUtil.class.getSimpleName();
    private static final String[] okFileExtensions =  new String[] {"jpg", "png", "gif","jpeg"};
    public static final String USER_BEEPS_PATH = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/ScannerBeep");
    private static FileUtil instance ;
    private Context context ;
    public static String exported = "";
    public static String imported = "";
    private static final String ENCODING = ("UTF-8");

    public enum MODE{
        APPEND,
        OVERWRITE
    }

    public static FileUtil getInstance(Context context){
        if (instance==null){
            instance = new FileUtil();
            instance.context = context;
        }
        return instance;
    }

    @Override
    public boolean accept(File file) {
        return false;
    }

    public void clear(File file)  throws IOException {
        FileOutputStream outputStream = null;
        try {
            addFolder(file);
            outputStream = new FileOutputStream(file);
            Writer out = new BufferedWriter(new OutputStreamWriter(outputStream, ENCODING));
            out.write("" );
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void write(File file, String text, MODE mode) throws IOException {
        if(mode == MODE.OVERWRITE) {
            write(file, text);
        }
        else {
            append(file, text);

        }
    }

    public void write(File file, String[] text, MODE mode) throws IOException {
        if(text.length == 0){
            delete(file);
            return;
        }
        if(mode == MODE.OVERWRITE) {
            write(file, text[0]);
            for (int i = 1; i < text.length; i++) {
                append(file, text[i]);
            }
        }
        else {
            for (int i = 0; i < text.length; i++) {
                append(file, text[i]);
            }
        }
    }

    public String[] read(File file) throws IOException {
        ArrayList<String> list = readArr(file);
        return list.toArray(new String[list.size()]);
    }

    public ArrayList<String> readArr(File file) throws IOException {
        ArrayList<String> text = new ArrayList<String>();
        if(!file.exists())
            return text;
        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(inputStream, ENCODING);
            BufferedReader bufferedReader = new BufferedReader(isr);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                text.add(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return text;
    }

    public boolean delete(File file){
        try {
            return file.delete();
        }
        catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    private void write(File file, String text) throws IOException {
        FileOutputStream outputStream = null;
        try {
            addFolder(file);
            outputStream = new FileOutputStream(file);
            /*outputStream.write(text.getBytes());
            String line = "\r\n";
            outputStream.write(line.getBytes());
            outputStream.close();*/

            Writer out = new BufferedWriter(new OutputStreamWriter(outputStream, ENCODING));
            out.write(text + "\r\n");

            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private boolean addFolder(File file){
        File f = new File(file.getParent());
        if(!f.exists())
            return f.mkdirs();
        else
            return true;
    }

    private void append(File file, String text) throws IOException {
        FileOutputStream outputStream;
        try {
            addFolder(file);
            outputStream = new FileOutputStream(file, true);
            /*outputStream.write(text.getBytes());
            String line = "\r\n";
            outputStream.write(line.getBytes());
            outputStream.close();*/
            Writer out = new BufferedWriter(new OutputStreamWriter(outputStream, ENCODING));
            out.append(text);
            out.append("\r\n");
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void remove(File file1, File file2) throws Exception {
        String[] arr1 = read(file1);
        write(file2, arr1, MODE.OVERWRITE);
        delete(file1);
    }
}

package thanhhai.com.toeicpractice.SplashScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import thanhhai.com.toeicpractice.MainActivity;
import thanhhai.com.toeicpractice.R;

public class SplashScreen extends AppCompatActivity {

    private static final String DATABASE = "database";
    private static final String TIME = "time";
    private static final String MD5 = "md5";
    private static final String TYPE = ".db";

    private ProgressBar loading;
    private View download;

    private ProgressBar bar;
    private TextView total;
    private TextView percent;
    private TextView title;

    private Map<String, Long> map = new HashMap<>();
    private Deque<String> stack = new ArrayDeque<>();

    private StorageTask<FileDownloadTask.TaskSnapshot> task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loading = (ProgressBar) findViewById(R.id.pb_Loading);
        download = findViewById(R.id.linear_Download);

        bar = (ProgressBar) findViewById(R.id.pb_Download);
        total = (TextView) findViewById(R.id.tv_Total);
        percent = (TextView) findViewById(R.id.tv_Percent);
        title = (TextView) findViewById(R.id.tv_Title);

        String path = this.getDatabasePath();
        if (path == null) {
            this.showError("loi eee");
            return;
        }
        if (this.isInternet()) {
            this.checkExistDatabase(path);
        } else if (this.isFileExist(path) && this.isInternet() == false) {
            this.startMain();
        } else {
            this.showError("Application can ket noi internet");
        }
    }

    private boolean isInternet() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            Log.e("LOI", "Internet Connection Not Present");
            return false;
        }
    }

    public void showError(String message) {
        Toast.makeText(SplashScreen.this, message, Toast.LENGTH_LONG).show();

    }

    public static boolean isFileExist(String path) {
        File exercise = new File(path + File.separator + "exercise.db");
        File toeic = new File(path + File.separator + "toeic.db");
        return (exercise.exists() && !exercise.isDirectory() && toeic.exists() && !toeic.isDirectory());
    }

    public String getDatabasePath() {
        try {
            PackageManager m = this.getPackageManager();
            String s = this.getPackageName();
            String root = m.getPackageInfo(s, 0).applicationInfo.dataDir;
            File database = new File(root + File.separator + DATABASE);
            if (!database.exists() || !database.isDirectory()) {
                database.mkdir();
            }
            return database.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String fileToMD5(File file) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            MessageDigest digest = MessageDigest.getInstance("MD5");
            int numRead = 0;
            while (numRead != -1) {
                numRead = inputStream.read(buffer);
                if (numRead > 0)
                    digest.update(buffer, 0, numRead);
            }
            byte[] md5Bytes = digest.digest();
            return convertHashToString(md5Bytes);
        } catch (Exception e) {
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private String convertHashToString(byte[] md5Bytes) {
        String returnVal = "";
        for (int i = 0; i < md5Bytes.length; i++) {
            returnVal += Integer.toString((md5Bytes[i] & 0xff) + 0x100, 16).substring(1);
        }
        return returnVal;
    }

    public boolean checkFileDatabase(@NonNull String path, @NonNull String name, @NonNull String md5) {
        File file = new File(path + File.separator + name + TYPE);
        if (file.exists() && !file.isDirectory()) {
           // return md5.equals(this.fileToMD5(file));
            return true;
        }
        return false;
    }

    public void checkExistDatabase(final String path) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(DATABASE);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() instanceof Map) {
                    Map<String, Object> mapDatabase = (Map<String, Object>) dataSnapshot.getValue();
                    for (String key : mapDatabase.keySet()) {
                        Object obj = (Map<String, Object>) mapDatabase.get(key);
                        if (obj instanceof Map) {
                            Map<String, Object> mapExer = (Map<String, Object>) obj;
                            Object timeTemp = mapExer.get(TIME);
                            Object md5Temp = mapExer.get(MD5);
                            if (timeTemp instanceof Long && md5Temp instanceof String) {
                                Long time = (Long) timeTemp;
                                String md5 = (String) md5Temp;
                                if (time != SplashScreen.this.getTimeDatabase(key) || !SplashScreen.this.checkFileDatabase(path, key, md5)) {
                                    SplashScreen.this.map.put(key, time);
                                    SplashScreen.this.stack.addFirst(key);
                                }
                            }
                        }
                    }
                }
                SplashScreen.this.showDownload(path);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                SplashScreen.this.showError("Loi firebse");
            }
        });
    }

    public long getTimeDatabase(String name) {
        SharedPreferences sharedPref = this.getSharedPreferences(DATABASE, Context.MODE_PRIVATE);
        return sharedPref.getLong(name, 0);
    }

    public void setTimeDatabase(String key, long time) {
        SharedPreferences sharedPref = this.getSharedPreferences(DATABASE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(key, time);
        editor.commit();
    }

    public void showDownload(String pathDatabase) {
        if (this.map.size() > 0) {
            this.loading.setVisibility(View.GONE);
            this.download.setVisibility(View.VISIBLE);

            StorageReference storage = FirebaseStorage.getInstance().getReference().child(DATABASE);
            this.doDownload(pathDatabase, storage, this.map.size());
        } else {
            this.startMain();
        }
    }

    public void doDownload(final String pathDatabase, final StorageReference storage, final int maxTotal) {
        if (this.stack.isEmpty()) {
            this.finishDownload();
            this.startMain();
            return;
        }

        final String key = this.stack.pop();
        final long time = this.map.get(key).longValue();
        String name = key + TYPE;
        this.total.setText("[" + (maxTotal - this.stack.size()) + "/" + maxTotal + "]");
        this.percent.setText("0.00%");
        this.title.setText("Download file " + key + " ...");
        File file = new File(pathDatabase + File.separator + name);
        if (file.exists()) {
            file.delete();
        }

        this.task = storage.child(name).getFile(file).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                SplashScreen.this.setTimeDatabase(key, time);
                SplashScreen.this.doDownload(pathDatabase, storage, maxTotal);

            }
        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                if (taskSnapshot.getBytesTransferred() > 0 && taskSnapshot.getTotalByteCount() > 0) {
                    Double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    SplashScreen.this.percent.setText(String.format("%.2f", progress.doubleValue()) + "%");
                    SplashScreen.this.bar.setProgress(progress.intValue());
                }
            }
        });
    }

    public void finishDownload() {
        if (this.task != null) {
            this.task.cancel();
        }
        this.map.clear();
        this.stack.clear();
    }

    public void startMain() {
        Intent i = new Intent(SplashScreen.this, MainActivity.class);
        this.startActivity(i);
        this.finish();
    }
}
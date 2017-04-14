package by.belstu.services;

import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;



public class DownloadActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_download, btn_cancel, btn_previous;
    private DownloadManager downloadManager;
    private long downloadId;
    private Handler handlerUpdate;
    private TextView tv_status;
    private EditText ed_uri;
    int mNotificationId = 001;

    private Runnable runnableUpdate = new Runnable() {
        @Override
        public void run() {
            if(done)
                return;
            DownloadActivity.this.updateStatus();
            handlerUpdate.postDelayed(this, 1000);

        }
    };

    private volatile boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        done = false;

        btn_download =(Button)findViewById(R.id.downloadButton);
        btn_cancel = (Button)findViewById(R.id.cancelButton);
        btn_previous = (Button)findViewById(R.id.previousButton);
        tv_status = (TextView)findViewById(R.id.statusText);
        ed_uri = (EditText)findViewById(R.id.uriEdit);

        updateButtons();

        btn_download.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_previous.setOnClickListener(this);

        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(new DownloadReceiver(), intentFilter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.downloadButton:
                try {
                    Uri uriDownload = Uri.parse(ed_uri.getText().toString());
                    downloadFile(uriDownload);
                }catch (Exception e){
                    Log.d("EX: ", e.toString());                }
                break;
            case R.id.cancelButton:
                try {
                    downloadManager.remove(downloadId);
                }catch (Exception e){
                    Log.d("EX: ", e.toString());
                }
                break;
            case R.id.previousButton:
                try {
                    Intent i = new Intent(DownloadActivity.this, MainActivity.class);
                    startActivity(i);
                }catch (Exception e){
                    Log.d("EX: ", e.toString());                }
                break;
        }
        updateButtons();
    }

    private void updateButtons(){
        if(btn_cancel.isEnabled()){
            btn_cancel.setEnabled(false);
            btn_download.setEnabled(true);
        } else {
            btn_cancel.setEnabled(true);
            btn_download.setEnabled(false);
        }
    }

    private void downloadFile(Uri uri){
        try {
            downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(uri);

            request.setTitle("DownloadManager");
            request.setDescription("Downloading file, please wait...");

            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "save.iso");

            downloadId = downloadManager.enqueue(request);

            handlerUpdate = new Handler();
            handlerUpdate.post(runnableUpdate);
        } catch (Exception e){
            Log.d("EX: ", e.toString());
        }
    }

    private void updateStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);

        Cursor cursor = downloadManager.query(query);
        if (cursor.moveToFirst()){

            int colStatusIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
            int colReasonStatus = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
            int colFileIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);

            int status = cursor.getInt(colStatusIndex);
            int reason = cursor.getInt(colReasonStatus);
            //String filename = cursor.getString(colFileIndex);

            String statusTxt = null, reasonStr = null;
            switch (status){
                case DownloadManager.STATUS_FAILED:
                    statusTxt = "STATUS_FAILED";
                    done = true;
                    switch (reason){
                        case DownloadManager.ERROR_CANNOT_RESUME:
                            reasonStr = "ERROR_CANNOT_RESUME";
                            break;

                        case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                            reasonStr = "ERROR_DEVICE_NOT_FOUND";
                            break;

                        case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                            reasonStr = "ERROR_FILE_ALREADY_EXISTS";
                            break;

                        case DownloadManager.ERROR_FILE_ERROR:
                            reasonStr = "ERROR_FILE_ERROR";
                            break;

                        case DownloadManager.ERROR_HTTP_DATA_ERROR:
                            reasonStr = "ERROR_HTTP_DATA_ERROR";
                            break;

                        case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                            reasonStr = "ERROR_INSUFFICIENT_SPACE";
                            break;

                        case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                            reasonStr = "ERROR_TOO_MANY_REDIRECTS";
                            break;

                        case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                            reasonStr = "ERROR_UNHANDLED_HTTP_CODE";
                            break;

                        case DownloadManager.ERROR_UNKNOWN:
                            reasonStr = "ERROR_UNKNOWN";
                            break;

                    }
                    break;

                case DownloadManager.STATUS_PAUSED:
                    statusTxt = "STATUS_PAUSED";
                    switch (reason){
                        case DownloadManager.PAUSED_QUEUED_FOR_WIFI:
                            reasonStr = "PAUSED_QUEUED_FOR_WIFI";
                            break;

                        case DownloadManager.PAUSED_UNKNOWN:
                            reasonStr = "PAUSED_UNKNOWN";
                            break;

                        case DownloadManager.PAUSED_WAITING_FOR_NETWORK:
                            reasonStr = "PAUSED_WAITING_FOR_NETWORK";
                            break;

                        case DownloadManager.PAUSED_WAITING_TO_RETRY:
                            reasonStr = "PAUSED_WAITING_TO_RETRY";
                            break;
                    }
                    break;

                case DownloadManager.STATUS_PENDING:
                    statusTxt = "STATUS_PENDING";
                    break;

                case DownloadManager.STATUS_RUNNING:
                    statusTxt = "STATUS_RUNNING";
                    break;

                case DownloadManager.STATUS_SUCCESSFUL:
                    statusTxt = "STATUS_SUCCESSFUL";
                    done = true;

                    NotificationCompat.Builder mBuilder1 =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(DownloadActivity.this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Services")
                            .setContentText("Download failed!");
                    NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    mNotifyMgr.notify(mNotificationId, mBuilder1.build());

                    /*Intent i = new Intent();
                    i.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
                    startActivity(i);*/

                    break;
            }

            tv_status.setText(statusTxt + " " + (reasonStr != null ? reasonStr : ""));
        }
    }

    private class DownloadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            long refId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);

            if(refId == DownloadActivity.this.downloadId)
                Toast.makeText(getApplicationContext(), "Download completed", Toast.LENGTH_SHORT).show();

            DownloadActivity.this.updateButtons();
        }
    }
}

package thanhhai.com.toeicpractice.FCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import thanhhai.com.toeicpractice.MainActivity;
import thanhhai.com.toeicpractice.R;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        boolean display = false;
        if (remoteMessage.getNotification() != null) {
            displayNotification(remoteMessage.getNotification().getBody());
            display = true;
        }

        // hinh nhu no chi co 1 mau thoi, v chac no ko co mau rui hihi
        // no filter 1 mau, dạ e cam on a, a dang lam app j ha a
        // thi bang lai xe , android ha a
        // uh viet bang kotlin voi realm database, a viet quay video e coi vs, hihi
        // moi viet dc cai menu slide thoi, thay a it on ge, kim a khó dễ sợ
        // dam gio~ may ngay, chuan bi dam gio nua, suong hihi, e học qs mệt dam gio chay @@ co j dau,  taon do xao
        // e thay facebook a ít bạn bè nên a ko on thường kím a khó
        // co su dung facebook dau ma co ban => 2 thang kia choi game ket ban , chu a thuong su dung j a
        // tu ki , chan thế a, fb vo doc tin xam xam khong,  do vo group moi tao nick chu khong cung cha co facebok lun, fb xoa me 2 nam truoc troi
        // hihi a cu lên fb đều đều đi a e lun chờ a hahah, kim ng giúp mk khó gê, cais app nay xong roi ak, tao nick roi dang len app store, chu co khinh phi a,
        // 25$ hay saosko bjk, dung r a 25
        // co am thanh gi khong, cung ko pk nen cho am thanh j
        // viewpager ak sao ,animation khac the, cua google lun ak a+
        // dung cho app bang lai xe cung dc ak a
        // anh lam merial design j lun
        // kotlin e cung biết chut chut
        if (display == false)
            displayNotification(remoteMessage.getData().get("body"), remoteMessage.getData().get("title"));
    }

    private void displayNotification(String body, String title) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        if (Build.VERSION.SDK_INT < 16) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(sound)
                    .setContentIntent(pendingIntent);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.notification)
                    .setLargeIcon(bm)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(sound)
                    .setContentIntent(pendingIntent);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        }
    }

    private void displayNotification(String body) {
        displayNotification(body, "Update Phiên Bản Mới");
    }
}

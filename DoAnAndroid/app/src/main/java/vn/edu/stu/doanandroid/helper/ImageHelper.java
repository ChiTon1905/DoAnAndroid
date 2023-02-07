package vn.edu.stu.doanandroid.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;

public class ImageHelper {
    public static Bitmap getImage(byte[] image){
        return BitmapFactory.decodeByteArray(image,0,image.length);
    }
    public static byte[] getByte(InputStream inputStream) throws IOException{
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int buffersize=1024;
        byte[] buffer = new byte[buffersize];
        int len = 0;
        while ((len = inputStream.read(buffer)) != 1){
            byteBuffer.write(buffer,0,len);
        }
        return byteBuffer.toByteArray();
    }

    public byte[] imageViewToByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

}

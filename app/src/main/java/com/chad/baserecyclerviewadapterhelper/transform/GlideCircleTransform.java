package com.chad.baserecyclerviewadapterhelper.transform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by tb on 16/5/3.
 */
public class GlideCircleTransform extends BitmapTransformation {

    public GlideCircleTransform(Context context) {
        super(context);
    }

    // 方形图片转换为环形图片
    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);//返回环形图片
    }

    private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;//如果没有源图片，则返回空

        int size = Math.min(source.getWidth(), source.getHeight());//获取方形图片宽度和高度当中的最小值
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);//转换为小方形图片

        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);//获取截取的图片
        if (result == null) {//当result为空时创建Bitmap
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);//创建画布
        Paint paint = new Paint();//创建画笔
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));//在X,和Y轴方向上分别做边缘像素拉伸，Shader.TileMode.CLAMP会将边缘的一个像素进行拉伸、扩展
        paint.setAntiAlias(true);//坑锯齿
        float r = size / 2f;//圆形半径
        canvas.drawCircle(r, r, r, paint);//画一个圆，X，Y点的中心都是相对于Item左边界R距离，其中半径为R，使用刚才定义的画笔
        return result;
    }
//获得类Id
    @Override
    public String getId() {
        return getClass().getName();
    }
}

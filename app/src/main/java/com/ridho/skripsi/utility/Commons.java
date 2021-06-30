package com.ridho.skripsi.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import java.util.Random;

import static java.lang.Math.pow;

public class Commons {

    public static int generateRandom(){
        Random random = new Random();
        return random.nextInt(9999-1000) +1000;
    }

    public static Bitmap drawableToBitmapConverter (int drawable){
        return BitmapFactory.decodeResource(Resources.getSystem(), drawable);
    }

    public static double calcBleDistance(double value){
        double distance = pow(10, ((-60- value)/(10*2)))*3.2808;
        return Math.floor(distance*100)/100;
    }



    public static void checkPermission(Activity activity){
        if (Build.VERSION.SDK_INT >= 23){
            ActivityCompat.requestPermissions(activity,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                    }, Constant.PERMISSION_REQUEST_CODE);
        }
    }

    public static Typeface fontMontserratBlack(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_black.otf");
    }

    public static Typeface fontMontserratBlackItalic(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_blackitalic.otf");
    }

    public static Typeface fontMontserratBold(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_bold.otf");
    }

    public static Typeface MontserratBoldItalic(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_bolditalic.otf");
    }

    public static Typeface fontMontserratExtraBold(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_extrabold.otf");
    }

    public static Typeface fontMontserratExtraBoldItalic(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_extrabolditalic.otf");
    }

    public static Typeface fontMontserratExtraLight(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_extralight.otf");
    }

    public static Typeface fontMontserratExtraLightItalic(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_extralightitalic.otf");
    }

    public static Typeface fontMontserratRegular(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_regular.otf");
    }

    public static Typeface fontMontserratItalic(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_italic.otf");
    }

    public static Typeface fontMontserratLight(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_light.otf");
    }

    public static Typeface fontMontserratLightItalic(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_lightitalic.otf");
    }

    public static Typeface fontMontserratMedium(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_medium.otf");
    }

    public static Typeface fontMontserratMediumItalic(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_mediumitalic.otf");
    }

    public static Typeface fontMontserratSemibold(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_semibold.otf");
    }

    public static Typeface fontMontserratSemboldItalic(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_semibolditalic.otf");
    }

    public static Typeface fontMontserratThin(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_thin.otf");
    }

    public static Typeface fontMontserratThinItalic(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/montserrat_thinitalic.otf");
    }

    public static Typeface fontRobotoBlack(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/roboto_black.ttf");
    }

    public static Typeface fontRobotoBlackItalic(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/roboto_blackitalic.ttf");
    }

    public static Typeface fontRobotoBold(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/roboto_bold.ttf");
    }

    public static Typeface fontRobotoBoldItalic(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/roboto_bolditalic.ttf");
    }

    public static Typeface fontRobotoRegular(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/roboto_regular.ttf");
    }

    public static Typeface fontRobotoItalic(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/roboto_italic.ttf");
    }

    public static Typeface fontRobotoLight(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/roboto_light.ttf");
    }

    public static Typeface fontRobotoLightItalic(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/roboto_lightitalic.ttf");
    }

    public static Typeface fontRobotoMedium(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/roboto_medium.ttf");
    }

    public static Typeface fontRobotoMediumItalic(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/roboto_mediumitalic.ttf");
    }

    public static Typeface fontRobotoThin(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/roboto_thin.ttf");
    }

    public static Typeface fontRobotoThinItalic(Context context){
        return Typeface.createFromAsset(context.getAssets(), "fonts/roboto_thinitalic.ttf");
    }
}

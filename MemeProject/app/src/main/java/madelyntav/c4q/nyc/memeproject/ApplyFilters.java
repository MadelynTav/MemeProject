package madelyntav.c4q.nyc.memeproject;

import android.graphics.Bitmap;

/**
 * Created by c4q-ac35 on 6/4/15.
 */
public class ApplyFilters {
    public static Bitmap engrave(Bitmap src) {
        ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
        convMatrix.setAll(0);
        convMatrix.Matrix[0][0] = -2;
        convMatrix.Matrix[1][1] = 2;
        convMatrix.Factor = 1;
        convMatrix.Offset = 95;
        return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);
    }
}

package sample;
import javafx.scene.paint.Color;

public class FxUtils
{
    public static String toRGBCode( Color color )
    {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }

    public static String hsvToRgb(double hue, double saturation, double value) {
        int h = (int)(hue * 6);
        double f = hue * 6 - h;
        double p = value * (1 - saturation);
        double q = value * (1 - f * saturation);
        double t = value * (1 - (1 - f) * saturation);
        switch (h) {
            case 0: return rgbToHexString(new double[]{value, t, p});
            case 1: return rgbToHexString(new double[]{q, value, p});
            case 2: return rgbToHexString(new double[]{p, value, t});
            case 3: return rgbToHexString(new double[]{p, q, value});
            case 4: return rgbToHexString(new double[]{t, p, value});
            case 5: return rgbToHexString(new double[]{value, p, q});
            default: throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
        }
    }

    public static String rgbToHexString(double [] rgb) {
        return (String.format("#%02x%02x%02x", (int)Math.min(255,256*rgb[0]), (int)Math.min(255,256*rgb[1]), (int)Math.min(255,256*rgb[2])) + ';');
    }

    public static String HSLToRGB(double h, double s, double l){
        double r, g, b;

        if (s == 0f) {
            r = g = b = l; // achromatic
        } else {
            double q = l < 0.5f ? l * (1 + s) : l + s - l * s;
            double p = 2 * l - q;
            r = hueToRgb(p, q, h + 1/3.0);
            g = hueToRgb(p, q, h);
            b = hueToRgb(p, q, h - 1/3.0);
        }
        return rgbToHexString(new double[]{r, g, b});
    }

    public static double hueToRgb(double p, double q, double t) {
        if (t < 0f)
            t += 1f;
        if (t > 1f)
            t -= 1f;
        if (t < 1f/6f)
            return p + (q - p) * 6f * t;
        if (t < 1f/2f)
            return q;
        if (t < 2f/3f)
            return p + (q - p) * (2f/3f - t) * 6f;
        return p;
    }


}
package verificationcode;

import java.awt.*;
import java.util.Random;

/**
 * 生成web验证码有关的方法的类
 * @author sqm
 * @version 1.0
 */
public class VerificationCode {
    //懒汉式单例类,不调用不实例化自己
    /**
     *<p>
     *默认验证码的取值范围(字符集),包括部分数字和部分大写字母；验证码文字要排除:0,o,i,1
     *</p>
     */
    public final String VERIFICATION_CODE_TEXTS = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";


    /**
     *<p>
     *提供一个Random对象以便之后生成随机数等功能的调用
     *</p>
     */
    private static Random random = new Random();
    
    /**
     *<p>
     *私有化无参构造
     *</p>
     */
    private VerificationCode() { }

    /**
     *<p>
     *创建一个私有的静态内部类,静态是为了之后的方法直接调用,内部类实现延迟加载,不调用不执行,保证线程安全
     *</p>
     */
    private static class LazyHolder {
        private static final VerificationCode CODE = new VerificationCode();
    }

    /**
     *<p>
     *提供一个对外调取此类的实例对象的方法
     *</p>
     *@return   返回一个验证码类的实例
     */
    public static VerificationCode getCodeInstance() {
        return LazyHolder.CODE;
    }

    ////创建一个验证码图像
    //public boolean Image creativeCode() {
    //    BufferedImage image = new BufferedImage(100, 25, BufferedImage.TYPE_INT_RGB);
    //}

    /**
     *<p>
     *得到随机的rgb区间的Color对象
     *</p>
     *@param    min Color对象rgb值的区间的最小数值
     *@param    max Color对象rgb值的区间的最大数值
     *@return   返回一个随机的rgb区间的Color对象
     */
    public Color getRandColor(int min, int max) {
        //把取值范围限定在0-255
        int range =255;
        if (min < 0) {
            min = 0;
        }
        if (min > range) {
            min = range;
        }
        if (max < 0) {
            max = 0;
        }
        if (max > range) {
            max = range;
        }

        int r = min + random.nextInt(max - min);
        int g = min + random.nextInt(max - min);
        int b = min + random.nextInt(max - min);

        return new Color(r, g, b);
    }


    /**
     *<p>
     *得到一个随机颜色的Color对象
     *</p>
     *@return   返回一个随机颜色的Color对象
     */
    public Color getRandColor() {
        int rgb = getRandIntRGB();
        return new Color(rgb);
    }

    /**
     * <p>
     * 得到一个随机的rgb整数值,例如:0xFFFFFFFF
     * </p>
     * @return 返回一个随机的rgb整数值
     */
    public int getRandIntRGB() {
        //int rgb的rgb值为Color.getRGB()
        // 24-31 位表示 alpha(可忽略)，16-23 位表示红色，8-15 位表示绿色，0-7 位表示蓝色
        //先获得三个rgb数,再把这3个数字进行操作,第一个向左位移八位,拼接第二个,新数字再向左位移八位,拼接第三个可得到rgb的整数表示;
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        int rgb = r << 8;
        rgb = rgb | g;
        rgb = rgb << 8;
        rgb = rgb | b;
        return rgb;
    }

    /**
     *<p>
     *指定的矩形图形中使图形的x方向和y方向分别按照正弦曲线扭曲
     *</p>
     *@param    g   指定的绘图对象
     *@param    w   指定的要扭曲的矩形的宽度
     *@param    h   指定的要扭曲的矩形的高度
     *@param    color   指定的扭曲后的图形填充空白的颜色
     */
    public void shear(Graphics g, int w, int h, Color color){
        shearX(g, w, h, color);
        shearY(g, w, h, color);
    }

    /**
     *<p>
     *指定的矩形图形中使图形沿X坐标按照正弦曲线扭曲
     *</p>
     *@param    g   指定的绘图对象
     *@param    w   指定的要扭曲的矩形的宽度
     *@param    h   指定的要扭曲的矩形的高度
     *@param    color   指定的扭曲后的图形填充空白的颜色
     */
    public void shearX(Graphics g, int w, int h, Color color) {
        //指定一个变化周期(单次扭曲Y坐标的长度)
        int period = random.nextInt(5);

        //边框间隙
        boolean borderGap = true;
        //框架
        int frames = 1;
        //代表一个角度,用于设置开始扭曲的sin坐标
        int phase = random.nextInt(5);

        for (int i = 0; i < h; i++) {
            double periodDouble = (double) (period >> 1);
            double temp1 = (double) i / (double) period;
            double temp2 = 6.2831853071795862D * (double) phase;
            double temp3 = temp2/(double) frames;
            double temp4 = temp1 + temp3;
            double d = periodDouble * Math.sin(temp4);
            //图片水平方向像素上的偏移量
            int dRate = (int)d;
            //复制垂直坐标为i时的水平方向像素的位置到偏移处
            g.copyArea(0, i, w, 1, dRate, 0);

            //填充图片扭曲后水平方向的空白
            if (borderGap) {
                g.setColor(color);
                g.drawLine(dRate, i, 0, i);
                g.drawLine(dRate+w,i,w,i);
            }
        }
    }

    /**
     *<p>
     *指定的矩形图形中使图形沿Y坐标按照正弦曲线扭曲
     *</p>
     *@param    g   指定的绘图对象
     *@param    w   指定的要扭曲的矩形的宽度
     *@param    h   指定的要扭曲的矩形的高度
     *@param    color   指定的扭曲后的图形填充空白的颜色
     */
    public void shearY(Graphics g, int w, int h, Color color) {
        //指定一个变化周期(单次扭曲X坐标的长度)
        int period = random.nextInt(15) + 5;

        boolean borderGap = true;
        int frames = 20;
        //代表一个角度,用于设置开始扭曲的sin坐标
        int phase = random.nextInt(20);

        for (int i = 0; i < w; i++) {

            double periodDouble = (double) (period >> 1);
            double temp1 = (double) i / (double) period;
            //小数+D结尾代表double数值
            //temp2 = 2πr; r=7;
            double temp2 = 6.2831853071795862D * (double) phase;
            //temp3 = 2πr/frames;
            double temp3 = temp2/(double) frames;

            //当前x坐标的弧度
            double temp4 = temp1 + temp3;
            //当前x坐标对应的y坐标,也就是之后图片竖直方向像素要偏移的值
            double d = periodDouble * Math.sin(temp4);
            //图片竖直方向像素上的偏移量
            int dRate = (int)d;
            //复制水平坐标为i时的竖直方向像素的位置到偏移处
            g.copyArea(i, 0, 1, h, 0, dRate);

            //填充图片扭曲后垂直方向的空白
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, dRate, i, 0);
                g.drawLine(i,dRate+h,i,h);
            }
        }
    }

    /**
     *<p>
     * 使用默认字符集生成验证码的字符
     *</p>
     * @param codeLength    验证码长度
     * @return  返回指定长度默认字符集的字符串
     */
    public String generateCodeText(int codeLength){
        return generateCodeText(codeLength, VERIFICATION_CODE_TEXTS);
    }

    /**
     *<p>
     *生成验证码的字符
     *</p>
     *@param    codeLength  生成的验证码字符的个数
     *@param    codeSources  生成的验证码字符的z字符集
     *@return   返回指定长度指定字符集的字符串
     *
     */
    public String generateCodeText(int codeLength,String codeSources) {
        //判断字符来源是否给出,如果没有给出直接使用默认字符集
        if (codeSources == null || codeSources.length() == 0) {
            codeSources = VERIFICATION_CODE_TEXTS;
        }

        //生成一个字符缓冲区,长度为验证码字符的长度
        StringBuilder codeTexts = new StringBuilder(codeLength);
        //生成一个固定种子的随机数生成器对象，用于随机选择生成的字符
        //保证了同一次调用此方法时的验证码文字相同
        Random indexRand = new Random(System.currentTimeMillis());

        //获得源字符集的长度
        int len = codeSources.length();
        //取得指定个数的拼接字符串
        for (int i = 0; i < codeLength; i++) {
            codeTexts.append(codeSources.charAt(indexRand.nextInt(len)));
        }

        return codeTexts.toString();
    }




}


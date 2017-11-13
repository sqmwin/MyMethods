package verificationcode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * <p>
 * 方法的测试类
 * </p>
 *
 * @author sqm
 * @version 1.0
 * 2017-11-12
 */
public class Test {
    public static void main(String[] args) throws IOException {

        //生成一个验证码图像
        //创建一个验证码生成器
        VerificationCode code = VerificationCode.getCodeInstance();
        //获得验证码文字
        String codeText = code.generateCodeText(4);
        System.out.println(codeText);
        //获得验证码图片
        BufferedImage image = code.outputImage(150, 70, codeText);


        //把内存中生成的验证码输出到客户端
        //通过ImageIO中的方法把内存中的图片输出到指定输出路径(写成文件\写入输出流)当中---write
        //static boolean
        //write(RenderedImage im, String formatName, OutputStream output)
        //RenderedImage--要写出的图片对象(RenderedImage是一个接口,实现类是BufferedImage)
        //formatName--要写出的图片文件格式(jpg)
        //OutputStream或File--输出的地方

        //创建一个输出文件
        File file = new File("img\\"+codeText+".jpg");
        ImageIO.write(image,"jpg",file);

    }
}

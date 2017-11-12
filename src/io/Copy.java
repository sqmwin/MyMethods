package io;

import java.awt.*;
import java.io.*;

/**
 * <p>
 * 有关输入输出流的方法
 * </p>
 * @author sqm
 * @version 1.0
 */
public class Copy {
    /**
     * 私有化构造方法
     */
    private Copy() { }
    //通过静态内部类的形式保证线程安全,同时避免同步带来的性能影响
    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
     * 没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
     */
    private  static class LazyHolder { /*延迟加载类*/
        /**
         * 静态初始化器,由JVM保证线程安全
         */
        private static final Copy COPY = new Copy();
    }

    /**
     *
     *<p>
     *获得一个此类的单例对象
     *</p>
     *@return   返回一个此类的单例对象
     */

    public static Copy getCopyInstance(){
        return LazyHolder.COPY;
    }

    /**
     * <p>
     *将指定文件通过字节缓冲流输出到指定输出流
     * </p>
     *@param   file    想要输出(复制)的文件
     *@param   output  想要输出的输出流
     *@return  输出成功返回true,失败返回false
     *@author  sqm
     */
    public boolean copyFile(File file, OutputStream output) throws IOException{
        boolean flag = false;
        //把文件封装入字节输入流
        InputStream input = new FileInputStream(file);
        //把字节输入输出流封装入缓冲流当中,提高IO效率
        BufferedInputStream bufInput = new BufferedInputStream(input);
        BufferedOutputStream bufOutput = new BufferedOutputStream(output);

            byte[] bys = new byte[1024];
            int len = 0;
            while ((len = bufInput.read(bys)) != -1) {
                bufOutput.write(bys, 0, len);
                //如果可以写入则证明此方法成功,返回true
                flag = true;
            }
            
        return flag;
    }

    /**
     * <p>
     *将指定路径的文件通过字节缓冲流输出到指定输出流
     * </p>
     *@param   path    想要输出(复制)的文件的路径
     *@param   output  想要输出的输出流
     *@return  输出成功返回true,失败返回false
     *@author  sqm
     */
    public boolean copyFile(String path, OutputStream output) throws IOException{
        boolean flag = false;
        //通过路径来获取文件
        File file = new File(path);
        //把文件封装入字节输入流
        InputStream input = new FileInputStream(file);
        //把字节输入输出流封装入缓冲流当中,提高IO效率
        BufferedInputStream bufInput = new BufferedInputStream(input);
        BufferedOutputStream bufOutput = new BufferedOutputStream(output);

        byte[] bys = new byte[1024];
        int len = 0;
        while ((len = bufInput.read(bys)) != -1) {
            bufOutput.write(bys, 0, len);
            //如果可以写入则证明此方法成功,返回true
            flag = true;
        }

        return flag;
    }



}

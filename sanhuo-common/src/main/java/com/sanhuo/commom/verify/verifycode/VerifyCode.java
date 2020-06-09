package com.sanhuo.commom.verify.verifycode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>
 * 验证码基类
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/6/9:22:26
 */
public interface VerifyCode {
    /**
     * 输出图片验证码
     *
     * @param image
     * @param out
     * @throws IOException
     */
    void output(BufferedImage image, OutputStream out) throws IOException;

}

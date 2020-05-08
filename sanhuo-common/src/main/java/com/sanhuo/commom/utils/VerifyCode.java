package com.sanhuo.commom.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

//TODO 谷歌有自己的验证码包
public class VerifyCode {

    private int width = 100;
    private int high = 40;
    private Random r = new Random();

    private String[] fontNames = {"宋体", "华文楷体", "黑体", "微软雅黑", "楷体_GB2312"};

    private String codes = "23456789abcdefghijknmpqrstuvwxyzABCDEFGHIJKNMPQRSTUVWXYZ";

    private Color bgColor = new Color(255, 255, 255);
    private Color newColor = new Color(225, 225, 225);
    private String text;


    private Color randomColor() {
        int red = r.nextInt(150);
        int green = r.nextInt(150);
        int blue = r.nextInt(150);
        return new Color(red, green, blue);
    }


    private Font randomFont() {
        int index = r.nextInt(fontNames.length);
        String fontName = fontNames[index];
        int style = r.nextInt(4);
        int size = r.nextInt(5) + 24;
        return new Font(fontName, style, size);

    }

    private void drawLine(BufferedImage image, int num) {

        Graphics2D g2 = (Graphics2D) image.getGraphics();
        for (int i = 0; i < num; i++) {
            int x1 = r.nextInt(width);
            int y1 = r.nextInt(high);
            int x2 = r.nextInt(width);
            int y2 = r.nextInt(high);
            g2.setStroke(new BasicStroke(1.5F));
            g2.setColor(Color.BLUE);
            g2.drawLine(x1, y1, x2, y2);
        }
    }


    private char randomChar() {
        int index = r.nextInt(codes.length());
        return codes.charAt(index);
    }


    private BufferedImage createImage() {
        BufferedImage image = new BufferedImage(width, high, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(this.bgColor);
        g2.fillRect(0, 0, width, high);
        return image;
    }

    public BufferedImage getImage() {

        BufferedImage image = createImage();

        Graphics2D g2 = (Graphics2D) image.getGraphics();

        StringBuffer sb = new StringBuffer();


        for (int i = 0; i < 4; i++) {
            String s = randomChar() + "";
            sb.append(s);
            float x = i * 1.0F * width / 4;
            g2.setFont(randomFont());
            g2.setColor(randomColor());
            g2.drawString(s, x, high - 5);
        }
        this.text = sb.toString();
        drawLine(image, 5);
        return image;
    }

    public String getText() {
        return text;
    }


    public static void output(BufferedImage image, OutputStream out) throws IOException {
        ImageIO.write(image, "JPEG", out);
    }


}

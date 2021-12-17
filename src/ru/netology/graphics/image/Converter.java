package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class Converter implements TextGraphicsConverter {
    private int maxWidth = 0;
    private int maxHeight = 0;
    private double maxRatio = 0;
    private int styleNumber = 1;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        System.out.println(checkRatio(img.getWidth() / img.getHeight()));
        double ratio = setRatio(imgCheck(maxHeight, img.getHeight()), imgCheck(maxWidth, img.getWidth()));
        int newWidth = (int) Math.floor(img.getWidth() / ratio);
        int newHeight = (int) Math.floor(img.getHeight() / ratio);
        System.out.println("newWidth " + newWidth + " newHeight " + newHeight);
        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        Image imageObject = scaledImage;
        WritableRaster bwRaster = bwImg.getRaster();
        Schema schema = new Schema(styleNumber);
        String[] lines = new String[newHeight];
        char[][] textImg = new char[newHeight][((newWidth * 2))];
        for (int h = 0; h < newHeight - 1 ; h++) {
            for (int i = 0; i < newWidth - 1; i++) {
                int color = bwRaster.getPixel(i, h, new int[3])[0];
                char c = schema.convert(color);
                textImg[h][i] = c;
                i++;
                textImg[h][i] = c;
            }
            lines[h] = Arrays.toString(textImg[h]) + "\n";
        }

        return Arrays.deepToString(lines)
                .replace("[", "")
                .replace(", ", "")
                .replace("]", "");

    }

    @Override
    public void setMaxWidth(int width) {
        maxWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        maxHeight = height;
    }

    @Override
    public void setMaxRatio(double ratio) {
        maxRatio = ratio;

    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {

    }

    private double setRatio (double heightRatio, double widthRatio) {
        double ratio;
        if (heightRatio == 1 && widthRatio == 1) {
            ratio = 1;
        } else {
            if (widthRatio >= heightRatio) {
                ratio = widthRatio;
            } else {
                ratio = heightRatio;
            }
        }
        return ratio;
    }

    private String checkRatio (double ratio)  throws IOException, BadImageSizeException {
        String message;
        if (maxRatio != 0) {
            if (ratio > maxRatio) {
                throw new BadImageSizeException(ratio, maxRatio);
            } else {
                message = "Загруженное изображение подходит по соотношению сторон.";
            }
        } else {
            message = "Проверка изображения по соотношению сторон отключена.";
        }
        return message;
    }

    private double imgCheck(int maxValue, int currentValue) {
        double value;
        if (maxValue != 0) {
            double ratio = currentValue / maxValue;
            if (ratio <= 1) {
                value = 1;
            } else {
                value = ratio;
            }
        } else {
            value = 1;
        }
        return value;
    }
}
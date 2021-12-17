package ru.netology.graphics.image;

public class Schema implements TextColorSchema {
    private int styleNumber;

    public Schema(int styleNumber) {
        this.styleNumber = styleNumber;
    }

    private static char[][] styleList = new char[][]{
            new char[]{
                    '#',
                    '$',
                    '@',
                    '%',
                    '*',
                    '+',
                    '-',
                    '\''
            },
            new char[]{
                    '▇',
                    '●',
                    '◉',
                    '◍',
                    '◎',
                    '○',
                    '☉',
                    '◌',
                    '-'
            }
    };

    @Override
    public char convert(int color) {
        int symbolNum = ((int) Math.floor((color * styleList[styleNumber].length) / 256));
        return styleList[styleNumber][symbolNum];
    }
}
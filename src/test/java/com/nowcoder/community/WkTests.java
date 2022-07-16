package com.nowcoder.community;

import java.io.IOException;

public class WkTests {

    public static void main(String[] args) {
        String cmd = "E:\\install_app\\wkhtmltopdf\\bin/wkhtmltoimage --quality 75  https://www.nowcoder.com D:\\code\\data\\wk-images/3.png";
        try {
            Runtime.getRuntime().exec(cmd);
            System.out.println("ok.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

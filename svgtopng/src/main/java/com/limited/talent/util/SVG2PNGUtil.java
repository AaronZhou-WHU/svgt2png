package com.limited.talent.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.springframework.context.annotation.Configuration;

import java.io.*;

/**
 * @Author : zhoubin
 * @Description :
 * @Date : 18/12/17 14:58
 */
@Configuration
@Slf4j
public class SVG2PNGUtil {
    public String saveAsPNG(String svg,String picName){
        String type="image/png";
        String filename="";
        String projectPath = System.getProperty("user.dir")+File.separator;
        log.info("projectPath:"+projectPath);
        if (null != type && null != svg) {
            // 有些版本不支持tagname全小写
            svg = svg.replaceAll(":rect", "rect");
            svg = svg.replace("clippath", "clipPath");
            svg = svg.replace("lineargradient", "linearGradient");
            String ext = "";
            Transcoder t = null;
            if (type.equals("image/png")) {
                ext = "png";
                t = new PNGTranscoder();
            }
            else if (type.equals("image/jpeg")) {
                ext = "jpeg";
                t = new JPEGTranscoder();
            }
            if (null != t) {
                OutputStream out = null;
                try {
                    out = new FileOutputStream(projectPath+picName+"."+ext);
                    filename = picName+"."+ext;
                    byte[] bytes = svg.getBytes("utf-8");
                    //TranscoderInput input = new TranscoderInput(new ByteArrayInputStream(bytes));
                    TranscoderInput input = new TranscoderInput(new StringReader(svg));
                    TranscoderOutput output = new TranscoderOutput(out);
//                    System.out.println("in"+input);
//                    System.out.println("out:"+output);
                    t.transcode(input, output);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }catch (TranscoderException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        }
        return filename;
    }
}

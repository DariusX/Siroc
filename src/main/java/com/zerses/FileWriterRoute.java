package com.zerses;

import java.io.File;
import java.io.FileWriter;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class FileWriterRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer://myTimer?period=30000").id("fileWriterRoute").log(LoggingLevel.INFO, "File wroiter route").bean(new MyFileWriter(), "writeFileToggle");

    }

    public static class MyFileWriter {
        
        public static int fileNumber = 0;
        
        public void writeFileToggle() {
            try {
                fileNumber++;
                File outFile = new File("/staging/testfile"+fileNumber+".txt");
                if (outFile.exists()) {
                  outFile.delete();
                  System.out.println(String.format("Removing existing file"));
                }
                else {
                    FileWriter writer = new FileWriter(outFile);
                    System.out.println(String.format("Starting to write file"));

                    for (int i = 0; i < 50; i++) {
                        writer.write("-------- -------- Abcde --------\n");
                        writer.flush();
                        Thread.sleep(100);
                    }
   
                    writer.close();
                    System.out.println(String.format("Finished writing file"));
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println(String.format("Error on file"));
                e.printStackTrace();
            }

        }
    }

}

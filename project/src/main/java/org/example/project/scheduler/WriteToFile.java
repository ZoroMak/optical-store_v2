package org.example.project.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class WriteToFile {
    private static final String DIRECTORY_PATH = "src/main/resources/EntityStorage";
    private static final Logger LOGGER = LoggerFactory.getLogger(WriteToFile.class);

    public <T> void  write(List<T> list){
        clearDirectory();
        createFile("Product.json", list);

    }

    private void clearDirectory(){
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists() || !directory.isDirectory())
            return;

        File[] files = directory.listFiles();

        if (files == null)
            return;


        for (File file : files) {
            file.delete();
        }
    }

    private <T> void createFile(String nameFile, List<T> list) {
        File directory = new File(DIRECTORY_PATH);

        if (!directory.exists() || !directory.isDirectory()) {
            return;
        }

        File file = new File(directory, nameFile);

        try {

            if (file.createNewFile()) {
                LOGGER.info("Файл создан");
                fillFile(file, list);
            }

        } catch (IOException e) {
            LOGGER.error("Ошибка при создании файла: " + e.getMessage());
        }
    }

    private  <T> void fillFile(File file, List<T> list){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(file, list);
        } catch (IOException e) {
            LOGGER.error("Ошибка при записи данных в файл: " + e.getMessage());
        }
    }
}

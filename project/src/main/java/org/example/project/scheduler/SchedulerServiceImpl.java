package org.example.project.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.project.database.model.Product;
import org.example.project.database.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@ManagedResource(description = "Scheduler Service")
public class SchedulerServiceImpl implements SchedulerService {
    private static final String DIRECTORY_PATH = "src/main/resources/EntityStorage";
    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulerServiceImpl.class);

    private final ProductService productService;

    @Autowired
    public SchedulerServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Scheduled(cron = "0 */30 * * * *")
    @ManagedOperation(description = "Run scheduled task")
    @Override
    public void scheduleTask() {
        clearDirectory();

        List<Product> products = productService.findAll();

        createFile("Product.json", products);


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

    public <T> void fillFile(File file, List<T> list){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(file, list);
        } catch (IOException e) {
            LOGGER.error("Ошибка при записи данных в файл: " + e.getMessage());
        }
    }
}


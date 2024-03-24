package com.kevinAri.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevinAri.example.model.entity.TestStreamEntity;
import com.kevinAri.example.model.repository.TestStreamRepo;
import com.kevinAri.example.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class AppService {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TestStreamRepo testStreamRepo;

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    public void execute() {
        try {
//            populateTableWithRandomData(10000);

            validateQueryAndStreamResult();
//            compareQueryVsJavaStream();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // function
    private void populateTableWithRandomData(int nData) {
        List<TestStreamEntity> listTestStreamEntity = new LinkedList<>();
        for (int i=0; i<nData; i++) {
            TestStreamEntity testStreamEntity = new TestStreamEntity();
            testStreamEntity.uuid = CommonUtil.generateUuid();
            testStreamEntity.stringField = CommonUtil.generateRandomAlphanumeric();
            testStreamEntity.stringField2 = CommonUtil.generateRandomAlphanumeric();
            testStreamEntity.numberField = CommonUtil.generateRandomBigDecimal();
            testStreamEntity.dateField = CommonUtil.generateRandomDate();

            listTestStreamEntity.add(testStreamEntity);
        }
        testStreamRepo.saveAll(listTestStreamEntity);
    }


    private void validateQueryAndStreamResult() {
        List<TestStreamEntity> listFromQuery = doScript("query");
        List<TestStreamEntity> listFromStream = doScript("stream");
        if (listFromQuery.size() == listFromStream.size()) {
            boolean sameList = true;
            for (int i=0; i<listFromQuery.size(); i++) {
                if (listFromQuery.get(i).uuid != listFromStream.get(i).uuid) {
                    sameList = false;
                    break;
                }
            }
            System.out.println(sameList);
        }
    }

    private void compareQueryVsJavaStream() {
        List<String> scriptType = new ArrayList<>(Arrays.asList(
                "stream",
                "query",
                "empty"
        ));

        for (int k=0; k<5; k++) {
            System.out.println(k);
            for (int i=0; i<2; i++) {
                Date start = new Date();
                for (int j=0; j<100; j++) {
                    doScript(scriptType.get(i));
                }
                Date end = new Date();
                long diffInMillies = Math.abs(end.getTime() - start.getTime());
                System.out.println(diffInMillies);
            }
        }
        System.out.println();
    }

    private List<TestStreamEntity> doScript(String type) {
        List<TestStreamEntity> listTestStreamEntity = null;
        // hibernate query
        if ("query".equals(type)) {
            listTestStreamEntity = testStreamRepo.findByNumberFieldIsGreaterThan(
                    new BigDecimal("500000"),
                    Sort.by(Sort.Direction.ASC, "stringField", "stringField2"));
        }
        // java stream
        else if ("stream".equals(type)) {
            List<TestStreamEntity> listForStream = testStreamRepo.findAll();
            listTestStreamEntity = listForStream.stream()
                    .filter(testStreamEntity -> testStreamEntity.numberField.compareTo(new BigDecimal("500000")) > 0)
                    .sorted(Comparator
                            .comparing(TestStreamEntity::getStringField)
                            .thenComparing(TestStreamEntity::getStringField2))
                    .collect(Collectors.toList());
        }
        return listTestStreamEntity;
    }

}

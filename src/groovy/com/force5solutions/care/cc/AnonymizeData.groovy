package com.force5solutions.care.cc
public class AnonymizeData {

    static Map fieldNameIndexes = [:]
    static Map fieldNameTranslatedValuesMap = [:]

    public static String anonymize(String data, String fieldName) {
        Map m1 = fieldNameTranslatedValuesMap[fieldName]
        String translatedValue = m1?.get(data)
        if (!translatedValue) {
            if (!fieldNameIndexes[fieldName]) {
                fieldNameIndexes[fieldName] = 1
            }
            if (!fieldNameTranslatedValuesMap[fieldName]) {
                fieldNameTranslatedValuesMap.put(fieldName, [:])
            }
            fieldNameTranslatedValuesMap[fieldName].put(data, fieldName + "-" + fieldNameIndexes[fieldName].toString())
            fieldNameIndexes[fieldName] = fieldNameIndexes[fieldName] + 1
            Map m = fieldNameTranslatedValuesMap[fieldName]
            translatedValue = m[data]
        }
        return translatedValue
    }

    public static void initializeAnonymization() {
        fieldNameIndexes = [:]
        fieldNameTranslatedValuesMap = [:]
    }
}

package com.force5solutions.care.common

import java.util.regex.Pattern
import java.util.regex.Matcher

class CustomTag {
    String name
    String displayValue
    String value
    String dummyData

    static constraints = {
        name(unique: true)
        displayValue(unique: true)
        value(maxSize: 8000)
        dummyData(maxSize: 8000)
    }

    public static String replaceTagsWithDummyCode(String text) {
        Pattern pattern = Pattern.compile('''###(.*?)###''')
        Matcher matcher = pattern.matcher(text)
        String updatedText = ''
        while (matcher.find()) {
            updatedText = matcher.group()
            if (CustomTag.countByDisplayValue(updatedText)) {
                text = text.replace(updatedText, CustomTag.findByDisplayValue(updatedText).dummyData)
            }
        }

        pattern = Pattern.compile('\\$\\{.*?}')
        matcher = pattern.matcher(text)
        CustomTag customTag
        while (matcher.find()) {
            updatedText = matcher.group()
            if (CustomTag.countByDisplayValue(updatedText)) {
                customTag = CustomTag.findByDisplayValue(updatedText)
                text = text.replace(updatedText, customTag.dummyData)
            }
        }
        return text
    }


    public static Map<String, String> tagsMap() {
        Map<String, String> tags = [:]
        CustomTag.list([sort:'name'])?.each {CustomTag tag ->
            tags.put(tag.name, tag.displayValue)
        }
        return tags
    }

}

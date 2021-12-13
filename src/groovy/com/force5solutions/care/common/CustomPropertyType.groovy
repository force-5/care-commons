package com.force5solutions.care.common

enum CustomPropertyType {

    STRING('Text'),
    NUMBER('Number'),
    DATE('Date'),
    BOOLEAN('Checkbox')

    private final String name;

    CustomPropertyType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

}


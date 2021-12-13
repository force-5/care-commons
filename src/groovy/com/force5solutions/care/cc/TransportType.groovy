package com.force5solutions.care.cc

enum TransportType {

    EMAIL('Email'),
    WEB_SERVICES('Web Services')

    private final String name;

    TransportType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    static list() {
        [EMAIL, WEB_SERVICES]
    }

    static TransportType getTransportType(String s) {
        switch (s) {
            case 'Email': return TransportType.EMAIL
            case 'Web Services': return TransportType.WEB_SERVICES
        }
        return null
    }


}
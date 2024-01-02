package com.nagarro.services;

import java.io.IOException;
import java.util.List;

public interface NationalityService {
    List<String> getNationalityData(String name) throws IOException;
}


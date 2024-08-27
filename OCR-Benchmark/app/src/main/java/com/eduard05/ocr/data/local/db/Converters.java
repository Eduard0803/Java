package com.eduard05.ocr.data.local.db;

import androidx.room.TypeConverter;

import com.eduard05.ocr.data.local.db.dto.OCRResultDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @TypeConverter
    public static List<OCRResultDTO> fromString(String value) {
        Type listType = new TypeToken<List<OCRResultDTO>>() {}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<OCRResultDTO> list) {
        return gson.toJson(list);
    }
}

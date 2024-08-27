package com.eduard05.ocr.data.local.db.dto;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Converters {
    private static Gson gson = new Gson();

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

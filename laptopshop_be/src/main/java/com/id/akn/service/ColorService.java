package com.id.akn.service;

import com.id.akn.exception.ColorException;
import com.id.akn.model.Color;

import java.util.List;

public interface ColorService {
    List<Color> getAllColors();
    Color getColorById(Byte id) throws ColorException;
    Color createColor(Color color) throws ColorException;
    Color updateColor(Byte id, Color color) throws ColorException;
    void deleteColor(Byte id);
}

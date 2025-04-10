package com.id.akn.serviceimpl;

import com.id.akn.exception.ColorException;
import com.id.akn.model.Color;
import com.id.akn.repository.ColorRepository;
import com.id.akn.service.ColorService;
import com.id.akn.util.NormalizeString;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ColorServiceImpl implements ColorService {
    private ColorRepository colorRepository;

    @Override
    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }

    @Override
    public Color getColorById(Byte id) throws ColorException {
        return colorRepository.findById(id)
                .orElseThrow(() -> new ColorException("Color not found"));
    }


    @Override
    public Color createColor(Color color) throws ColorException {
        String normalizedColorName = NormalizeString.normalize(color.getName());
//        if (normalizedColorName == null || normalizedColorName.isEmpty()) {
//            throw new ColorException("Color name cannot be empty.");
//        }
        if (colorRepository.findByNameNormalize(normalizedColorName) != null) {
            throw new IllegalArgumentException("Color with the name '" + color.getName() + "' already exists.");
        }
        color.setName(normalizedColorName);
        return colorRepository.save(color);
    }

    @Override
    public Color updateColor(Byte id, Color colorDetails) throws ColorException {
        Color color = getColorById(id);
        String normalizedColorName = NormalizeString.normalize(colorDetails.getName());
//        if (normalizedColorName == null || normalizedColorName.isEmpty()) {
//            throw new ColorException("Color name cannot be empty.");
//        }
        Color existingColor = colorRepository.findByNameNormalize(normalizedColorName);
        if (existingColor != null && !existingColor.getId().equals(id)) {
            throw new ColorException("Color with the name '" + colorDetails.getName() + "' already exists.");
        }
        color.setName(normalizedColorName);
        return colorRepository.save(color);
    }

    @Override
    public void deleteColor(Byte id) {
        colorRepository.deleteById(id);
    }

}

package com.id.akn.controller;

import com.id.akn.exception.ColorException;
import com.id.akn.model.Color;
import com.id.akn.response.ApiRes;
import com.id.akn.service.ColorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colors")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ColorController {

    private ColorService colorService;

    @GetMapping
    public ResponseEntity<List<Color>> getAllColors() {
        return ResponseEntity.ok(colorService.getAllColors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Color> getColorById(@PathVariable Byte id) throws ColorException {
        return ResponseEntity.ok(colorService.getColorById(id));
    }

    @PostMapping("/api/admin")
    public ResponseEntity<Color> createColor(@RequestBody Color color) throws ColorException {
        return ResponseEntity.status(HttpStatus.CREATED).body(colorService.createColor(color));
    }

    @PutMapping("/api/admin/{id}")
    public ResponseEntity<Color> updateColor(@PathVariable Byte id, @RequestBody Color color) throws ColorException {
        return ResponseEntity.ok(colorService.updateColor(id, color));
    }

    @DeleteMapping("/api/admin/{id}")
    public ResponseEntity<ApiRes> deleteColor(@PathVariable Byte id) {
        colorService.deleteColor(id);
        ApiRes res=new ApiRes("Color deleted successfully",true);
        return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
    }
}

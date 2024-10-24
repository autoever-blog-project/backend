package com.example.dpm.puppy.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.dpm.puppy.dto.PuppyDto;
import com.example.dpm.puppy.model.PuppyImgEntity;
import com.example.dpm.puppy.service.PuppyImgService;
import com.example.dpm.puppy.service.PuppyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PuppyController {
	final PuppyService puppyService;

	final PuppyImgService puppyImgService;

	private static final String FOLDER_PATH = "c:\\images\\";

	@GetMapping("/mypage/puppy/{puppy_id}")
	public ResponseEntity<PuppyDto> get(@PathVariable(name = "puppy_id") int puppyId) {
		try {
			PuppyDto puppyDto = puppyService.get(puppyId);
			return ResponseEntity.status(HttpStatus.OK).body(puppyDto); // 200 OK와 함께 데이터 반환
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
		}
	}

	// 이미지 저장
	@PostMapping("/puppy/img")
	public ResponseEntity<String> uploadImage(@RequestPart(value = "image") MultipartFile image) {
		try {
			PuppyImgEntity imgEntity = puppyImgService.uploadImage(image);
			return ResponseEntity.status(HttpStatus.CREATED).body(imgEntity.getImgId().toString());
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Image upload failed: " + e.getMessage());
		}
	}

	// 하나 자체를 생성
	@PostMapping("/puppy")
	public ResponseEntity<Map<String, Integer>> register(@RequestBody PuppyDto puppyDto) {
		try {
			int puppyId = puppyService.AddPuppyInfo(puppyDto);
			Map<String, Integer> response = Map.of("Number", puppyId);
			return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201 Created와 함께 반환
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
		}
	}

	@PutMapping("/mypage/puppy/{puppy_id}")
	public ResponseEntity<Map<String, String>> modify(@PathVariable(name = "puppy_id") int puppy_Id,
			@RequestBody PuppyDto puppyDto) {
		try {
			puppyDto.setPuppyId(puppy_Id);
			puppyService.modify(puppyDto);
			return ResponseEntity.status(HttpStatus.OK).body(Map.of("result", "success")); // 200 OK 반환
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
		}
	}

	// 강아지 정보를 캘린더로 확인????
	// 강아지 정보 주기적으로 저장?? put? post?

}
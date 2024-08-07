package api_pr.weather.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import api_pr.weather.domain.Diary;
import api_pr.weather.service.DiaryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DiaryController {

	private final DiaryService diaryService;

	@PostMapping("/diary")
	public void createDiary(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate,
		@RequestBody String text) {
		diaryService.createDiary(localDate, text);
	}

	@GetMapping("/diary")
	public List<Diary> readDiary(
		@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
		return diaryService.readDiary(localDate);
	}

	@GetMapping("/diaryList")
	public List<Diary> readDiaryList(
		@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
		@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		return diaryService.readDiaryList(startDate, endDate);
	}

	@PutMapping("/diary")
	public Diary updateDiary(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate,@RequestBody String text) {
		return diaryService.updateDiary(localDate, text);
	}

	@DeleteMapping("/diary")
	public void deleteDiary(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
		diaryService.deleteDiary(localDate);
	}
}

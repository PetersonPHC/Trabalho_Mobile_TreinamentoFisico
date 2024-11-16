package fatec.phc.api.trainingschedule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fatec.phc.api.trainingschedule.model.Training;
import fatec.phc.api.trainingschedule.service.ITrainingService;

@RestController
@RequestMapping("/api/trainings")
public class TrainingController {
	
	@Autowired
	ITrainingService service;
	
	@GetMapping("/{id}")
	public ResponseEntity<Training> getTraining(@PathVariable Long id){
		Training training = service.findTraining(id);
		return ResponseEntity.status(HttpStatus.OK).body(training);
	}
	
	@PostMapping()
	public ResponseEntity<Training> createTraining(@RequestBody Training trainingJSON){
		Training training = service.createTraining(trainingJSON);
		return ResponseEntity.status(HttpStatus.CREATED).body(training);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Training> updateTraining(@PathVariable Long id, @RequestBody Training trainingJSON){
		Training updatedTraining = service.updateTraining(id, trainingJSON);
		return ResponseEntity.status(HttpStatus.OK).body(updatedTraining);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Training> deleteTraining(@PathVariable Long id){
		Training training = service.deleteTraining(id);
		return ResponseEntity.status(HttpStatus.OK).body(training);
	}
	
	@GetMapping("/type/{type}")
	public ResponseEntity<Iterable<Training>> getTrainingForType(@PathVariable String type){
		Iterable<Training> trainingsForType = service.findAllTrainingsForType(type);
		return ResponseEntity.status(HttpStatus.OK).body(trainingsForType);
	}
	
	@GetMapping()
	public ResponseEntity<Iterable<Training>> getTrainingAllTrainingsWithStoredProocedure(){
		Iterable<Training> allTrainings = service.findAllTrainings();
		return ResponseEntity.status(HttpStatus.OK).body(allTrainings);
	}
	

}

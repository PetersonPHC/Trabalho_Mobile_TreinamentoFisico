package fatec.phc.api.trainingschedule.service;

import java.time.LocalDate;

import fatec.phc.api.trainingschedule.model.Training;

public interface ITrainingService {
	Training createTraining(Training newTraining);
	Training findTraining(LocalDate date);
	Training updateTraining(LocalDate date, Training newTraining);
	Training deleteTraining(LocalDate date);
	Iterable<Training> findAllTrainingsForType(String type);
	Iterable<Training> findAllTrainings();
}

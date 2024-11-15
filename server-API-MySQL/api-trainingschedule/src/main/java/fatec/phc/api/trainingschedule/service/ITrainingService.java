package fatec.phc.api.trainingschedule.service;

import fatec.phc.api.trainingschedule.model.Training;

public interface ITrainingService {
	Training createTraining(Training newTraining);
	Training findTraining(Long idTraining);
	Training updateTraining(Long idTraining, Training newTraining);
	Training deleteTraining(Long idTraining);
	Iterable<Training> findAllTrainingsForType(String type);
	Iterable<Training> findAllTrainings();
}

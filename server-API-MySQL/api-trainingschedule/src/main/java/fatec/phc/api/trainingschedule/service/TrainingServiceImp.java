package fatec.phc.api.trainingschedule.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fatec.phc.api.trainingschedule.model.Training;
import fatec.phc.api.trainingschedule.repository.ITrainingRepository;
import jakarta.transaction.Transactional;

@Service
public class TrainingServiceImp implements ITrainingService{

	@Autowired
	ITrainingRepository repository;

	@Override
	public Training createTraining(Training newTraining){
		return repository.save(newTraining);
	}

	@Override
	public Training findTraining(Long idTraining) {
		Optional<Training> trainingExists = repository.findById(idTraining);
		if(trainingExists.isEmpty()){
			throw new NullPointerException("Training is not Exists in DataBase");
		}
		return trainingExists.get();
	}

	@Override
	public Training updateTraining(Long idTraining, Training newTraining) {
		Optional<Training> oldTraining = repository.findById(idTraining);
		if(oldTraining.isEmpty()){
			throw new NullPointerException("Training is not Exists in DataBase");
		}
		Training updateTraining = oldTraining.get();
		
		updateTraining.setType(newTraining.getType());			
		updateTraining.setDate(newTraining.getDate());
		updateTraining.setMuscularGroup(newTraining.getMuscularGroup());
		updateTraining.setExercises(newTraining.getExercises());
		updateTraining.setDuration(newTraining.getDuration());
		updateTraining.setGym(newTraining.getGym());
		
		return repository.save(updateTraining);
	}

	@Override
	public Training deleteTraining(Long idTraining) {
		Optional<Training> trainingExists = repository.findById(idTraining);
		if(trainingExists.isEmpty()){
			throw new NullPointerException("Training is not Exists in DataBase");
		}
		repository.deleteById(trainingExists.get().getId());
		return trainingExists.get();
	}

	@Override
	public Iterable<Training> findAllTrainingsForType(String type) {
		List<Training> listOfTrainingsForType = repository.findByType(type);
		if(listOfTrainingsForType.isEmpty()) {
			throw new NullPointerException("There are no trainings of this type in the database");
		}
		return listOfTrainingsForType;
	}

	@Override
	@Transactional
	public Iterable<Training> findAllTrainings() {
		
		List<Training> listAllTrainings = repository.fetchAllTrainings();
		if(listAllTrainings.isEmpty()) {
			throw new NullPointerException("There are no trainings in the database");
		}
		return listAllTrainings;
	}

	
	
}

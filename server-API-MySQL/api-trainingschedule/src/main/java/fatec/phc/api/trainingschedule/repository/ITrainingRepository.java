package fatec.phc.api.trainingschedule.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import fatec.phc.api.trainingschedule.model.Training;

public interface ITrainingRepository extends JpaRepository<Training, Long>{
	List<Training> findByType(String type);
	@Procedure(procedureName = "dataReportingStoredProcedure")
	List<Training> fetchAllTrainings();
	Training findByDate(LocalDate date);
}

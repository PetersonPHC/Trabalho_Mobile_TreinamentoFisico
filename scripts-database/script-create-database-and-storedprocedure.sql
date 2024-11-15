create database trainingschedule;
use trainingschedule;

show databases;
show tables;

delimiter $$
create procedure dataReportingStoredProcedure()
	begin
		select * from trainings;
	end $$
delimiter ;

call dataReportingStoredProcedure();
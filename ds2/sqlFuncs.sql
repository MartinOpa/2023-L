create or replace procedure deleteUser(pId int) as
begin
    UPDATE Review rev
    SET rev.userfirstname = 'Smazaný uživatel'
    WHERE rev.user_id = pId;
    
    UPDATE Clients u
    SET u.active = 0
    WHERE u.id = pId;

    COMMIT;
    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
end;

create or replace procedure insertReservation(pDate timestamp, pUser_Id int, pVIN VARCHAR2, pTask VARCHAR2, pTaskDescription VARCHAR2, pPaid CHAR)
as   
    capacity_cur int;
    v_discount NUMBER(2,1); 
begin
    UPDATE ReservationTimes rt
    SET rt.capacity = (rt.capacity - 1)
    WHERE rt.datetime = pDate;
    
    SELECT capacity INTO capacity_cur
    FROM ReservationTimes
    WHERE datetime = pDate;
    
    IF capacity_cur < 0    
    THEN RAISE VALUE_ERROR;
    END IF;
    
    SELECT discount INTO v_discount 
    FROM Clients 
    WHERE Clients.id = pUser_id;
    
    IF v_discount IS NULL THEN v_discount := 0; END IF;

    INSERT INTO Reservation res (res.RT_Datetime, res.User_id, res.Vehicle_VIN, res.task, res.taskdescription, res.discount, res.paid)
    VALUES (pDate, pUser_id, pVIN, pTask, pTaskDescription, v_discount, pPaid);

    COMMIT;
    
    EXCEPTION
        WHEN VALUE_ERROR THEN
            dbms_output.put_line('invalid transaction');
            ROLLBACK;
        WHEN OTHERS THEN
            ROLLBACK;
end;

select * from reservation;
select * from ReservationTimes;
select * from clients order by id;
select * from vehicle;
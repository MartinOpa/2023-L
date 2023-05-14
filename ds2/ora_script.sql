begin
   execute immediate 'drop table complaint';
exception
   when others then null;
end;
/
begin
   execute immediate 'drop table reply';
exception
   when others then null;
end;
/
begin
   execute immediate 'drop table review';
exception
   when others then null;
end;
/
begin
   execute immediate 'drop table reservation';
exception
   when others then null;
end;
/
begin
   execute immediate 'drop table reservationtimes';
exception
   when others then null;
end;
/
begin
   execute immediate 'drop table vehicle';
exception
   when others then null;
end;
/
begin
   execute immediate 'drop table clients';
exception
   when others then null;
end;
/


CREATE TABLE complaint (
    reservation_id INTEGER NOT NULL,
    complaint      NVARCHAR2(300) NOT NULL,
    resolved       CHAR(1) NOT NULL,
    complaintdate  DATE NOT NULL
);

ALTER TABLE complaint ADD CONSTRAINT complaint_pk PRIMARY KEY ( reservation_id )
;
 
CREATE TABLE reply (
    reply                 NVARCHAR2(300) NOT NULL,
    replydate             TIMESTAMP(3) NOT NULL,
    review_reservation_id INTEGER NOT NULL
);

ALTER TABLE reply ADD CONSTRAINT reply_pk PRIMARY KEY ( review_reservation_id )
;
 
CREATE TABLE reservation (
    id              INTEGER GENERATED ALWAYS AS IDENTITY,
    rt_datetime     TIMESTAMP(3) NOT NULL,
    user_id         INTEGER NOT NULL,
    vehicle_vin     VARCHAR2(30) NOT NULL,
    task            NVARCHAR2(30) NOT NULL,
    taskdescription NVARCHAR2(100),
    apxduration     TIMESTAMP(3),
    discount        NUMBER(2, 1),
    bill            NUMBER(20, 1),
    payment         INTEGER,
    paid            CHAR(1) NOT NULL
);

ALTER TABLE reservation ADD CONSTRAINT reservation_pk PRIMARY KEY ( id )
;
 
CREATE TABLE reservationtimes (
    datetime TIMESTAMP(3) NOT NULL,
    capacity INTEGER NOT NULL
);

ALTER TABLE reservationtimes ADD CONSTRAINT reservationtimes_pk PRIMARY KEY ( datetime )
;
 
CREATE TABLE review (
    reservation_id INTEGER GENERATED ALWAYS AS IDENTITY,
    user_id        INTEGER NOT NULL,
    userfirstname  NVARCHAR2(20) NOT NULL,
    reviewdate     TIMESTAMP(3) NOT NULL,
    lastedit       TIMESTAMP(3),
    reviewpts      INTEGER NOT NULL,
    reviewtext     NVARCHAR2(300)
);

ALTER TABLE review ADD CONSTRAINT review_pk PRIMARY KEY ( reservation_id )
;
 
CREATE TABLE clients (
    id          INTEGER GENERATED ALWAYS AS IDENTITY,
    login       NVARCHAR2(10) NOT NULL,
    firstname   NVARCHAR2(20) NOT NULL,
    lastname    NVARCHAR2(20) NOT NULL,
    phonenumber INTEGER,
    address     NVARCHAR2(60),
    email       NVARCHAR2(40) NOT NULL,
    type        INTEGER NOT NULL,
    partnerdate DATE,
    discount    NUMBER(2, 1),
    active	    CHAR(1) NOT NULL
);

ALTER TABLE clients ADD CONSTRAINT clients_pk PRIMARY KEY ( id )
;

CREATE TABLE vehicle (
    vin          VARCHAR2(30) NOT NULL,
    user_id      INTEGER NOT NULL,
    licenseplate VARCHAR2(10) NOT NULL,
    manufacturer NVARCHAR2(20) NOT NULL,
    model        NVARCHAR2(20) NOT NULL,
    year         INTEGER NOT NULL,
    chassis      VARCHAR2(10),
    valid	     CHAR(1)
);

ALTER TABLE vehicle ADD CONSTRAINT vehicle_pk PRIMARY KEY ( vin )
;
 

ALTER TABLE complaint
    ADD CONSTRAINT complaint_reservation_fk FOREIGN KEY ( reservation_id )
        REFERENCES reservation ( id )
;
 

ALTER TABLE reply
    ADD CONSTRAINT reply_review_fk FOREIGN KEY ( review_reservation_id )
        REFERENCES review ( reservation_id )
;
 

ALTER TABLE reservation
    ADD CONSTRAINT reservation_rt_fk FOREIGN KEY ( rt_datetime )
        REFERENCES reservationtimes ( datetime )
;
 

ALTER TABLE reservation
    ADD CONSTRAINT reservation_user_fk FOREIGN KEY ( user_id )
        REFERENCES clients ( id )
;
 

ALTER TABLE reservation
    ADD CONSTRAINT reservation_vehicle_fk FOREIGN KEY ( vehicle_vin )
        REFERENCES vehicle ( vin )
;
 

ALTER TABLE review
    ADD CONSTRAINT review_reservation_fk FOREIGN KEY ( reservation_id )
        REFERENCES reservation ( id )
;
 

ALTER TABLE review
    ADD CONSTRAINT review_user_fk FOREIGN KEY ( user_id )
        REFERENCES clients ( id )
;
 

ALTER TABLE vehicle
    ADD CONSTRAINT vehicle_user_fk FOREIGN KEY ( user_id )
        REFERENCES clients ( id )
;

insert into ReservationTimes (capacity, datetime) values (1000, TO_TIMESTAMP('20230303 12:30', 'yyyymmdd hh24:mi'));
insert into ReservationTimes (capacity, datetime) values (1000, TO_TIMESTAMP('20230303 11:30', 'yyyymmdd hh24:mi'));
insert into ReservationTimes (capacity, datetime) values (1000, TO_TIMESTAMP('20230303 10:30', 'yyyymmdd hh24:mi'));
insert into ReservationTimes (capacity, datetime) values (1000, TO_TIMESTAMP('20230303 09:30', 'yyyymmdd hh24:mi'));
insert into ReservationTimes (capacity, datetime) values (1000, TO_TIMESTAMP('20230303 08:30', 'yyyymmdd hh24:mi'));
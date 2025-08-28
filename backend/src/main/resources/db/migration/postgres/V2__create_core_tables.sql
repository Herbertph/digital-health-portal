-- USERS
create table if not exists users(
                                    id bigserial primary key,
                                    username varchar(50) not null unique,
    email varchar(120) not null unique,
    password_hash varchar(100) not null,
    role varchar(20) not null,
    created_at timestamptz not null default now()
    );

-- PATIENT PROFILES
create table if not exists patient_profiles(
                                               id bigserial primary key,
                                               user_id bigint not null unique references users(id),
    first_name varchar(60) not null,
    last_name varchar(60) not null,
    birth_date date
    );

-- DOCTOR PROFILES
create table if not exists doctor_profiles(
                                              id bigserial primary key,
                                              user_id bigint not null unique references users(id),
    specialty varchar(80) not null,
    license_number varchar(40) not null
    );

-- APPOINTMENTS
create table if not exists appointments(
                                           id bigserial primary key,
                                           patient_id bigint not null references patient_profiles(id),
    doctor_id bigint not null references doctor_profiles(id),
    start_at timestamptz not null,
    end_at timestamptz not null,
    status varchar(20) not null,
    notes varchar(500),
    constraint ck_appt_time check (end_at > start_at)
    );

create index if not exists idx_appt_doctor_start on appointments(doctor_id, start_at);
create index if not exists idx_appt_patient_start on appointments(patient_id, start_at);

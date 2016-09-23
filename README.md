# _Hair Salon Organizer_

#### _Epicodus: Java, Week Three, Friday, 9-23-16_

#### By _**Satchel Grant**_

## Description

_This is an app to organize stylists and clients within a hair salon company._

## Appointment Class Specifications
Spec description | Input | Output
Store time and date of appointment in database | date and time | void
Attach this to individual client | client name | void
Return stored information from database | NA | NA
Update time and date information in database | NA | NA
Delete this information from database | NA | NA


## Client Class Specifications
Spec description | Input | Output
Stores client name in database | First and last name | void
Return stored name from database | void | First and last name
Attach this to individual stylist | stylist name | void
Return list of this client's appointments | NA | NA
Update name information in database | NA | NA
Update stylist information in database | NA | NA
Delete this information from database | NA | NA


## Stylist Class Specifications
Spec description | Input | Output
Stores stylist name in database | First and last name | void
Return stored name from database | void | First and last name
Return list of this stylist's appointments | NA | NA
Update name information in database | NA | NA
Delete this information from database | NA | NA


## Setup/Installation Requirements

* _Clone this repository to your desktop_
* _In PSQL type the following command: CREATE DATABASE hair_salon;_
* _In terminal, navigate to the cloned /hair-salon folder and type: psql hair_salon < hair_salon.sql_
* _In PSQL type: CREATE DATABASE hair_salon_test TEMPLATE hair_salon;_
* _In terminal type: $ gradle run_
* _Using a browser, make an http request to url: localhost:4567_

## Known Bugs

_None_

## Support and contact details

* _Satchel Grant: grantsrb@gmail.com_

## Technologies Used

_Java_
_Spark_
_Apache Velocity_
_JUnit_
_PostgreSQL_

### License

*This webpage is licensed under the GPL license.*

Copyright (c) 2016 **_Satchel Grant_**
